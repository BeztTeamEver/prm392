package com.example.e_commerce.Fragment;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.e_commerce.Activity.CategoryProductsActivity;
import com.example.e_commerce.Activity.EditProductActivity;
import com.example.e_commerce.Database.Database;
import com.example.e_commerce.Model.Book;
import com.example.e_commerce.R;
import com.example.e_commerce.Repository.RepositoryBase;
import com.example.e_commerce.Service.IBookService;
import com.example.e_commerce.Service.IBookTypeService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ManageProductFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ManageProductFragment extends Fragment {

    ListView list_products;
    ArrayList<Book> products = new ArrayList<>();

    // Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private IBookService bookService = RepositoryBase.getBookService();

    public ManageProductFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ManageProductFragment.
     */
    // Rename and change types and number of parameters
    public static ManageProductFragment newInstance(String param1, String param2) {
        ManageProductFragment fragment = new ManageProductFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_manage_product, container, false);

        list_products = v.findViewById(R.id.manage_product_list);

        // TODO: get products from database and show it in listView

        //fill_list();
        getAllBook();
        return v;
    }

    private void getAllBook(){

        try{
            Call<List<Book>> call = bookService.getAll();
            call.enqueue(new Callback<List<Book>>() {
                @Override
                public void onResponse(Call<List<Book>> call, Response<List<Book>> response) {
                    List<Book> books = response.body();
                    if (books == null){
                        return;
                    }

                    for (Book book : books){
                        products.add(book);
                    }

                    ManageProductFragment.AdminManageProductAdapter adminManageProductAdapter
                            = new ManageProductFragment.AdminManageProductAdapter(products);
                    list_products.setAdapter(adminManageProductAdapter);
                }

                @Override
                public void onFailure(Call<List<Book>> call, Throwable t) {

                }
            });

        } catch (Exception e){
            Log.d("Error", e.getMessage());
        }
    }

    class AdminManageProductAdapter extends BaseAdapter {

        ArrayList<Book> products = new ArrayList<>();

        public AdminManageProductAdapter(ArrayList<Book> products) {
            this.products = products;
        }

        @Override
        public int getCount() {
            return products.size();
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public Object getItem(int i) {
            return products.get(i).getTitle();
        }

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {

            LayoutInflater layoutInflater = getLayoutInflater();
            View item = layoutInflater.inflate(R.layout.admin_product_item, null);

            ImageView product_image = (ImageView) item.findViewById(R.id.admin_product_iv_product_image);
            TextView product_name = (TextView) item.findViewById(R.id.admin_tv_product_name);
            TextView product_price = (TextView) item.findViewById(R.id.admin_product_tv_product_price);
            Button btn_edit = item.findViewById(R.id.admin_product_btn_edit);
            Button btn_del = item.findViewById(R.id.admin_product_btn_delete);

            product_name.setText(products.get(i).getTitle());
            product_price.setText(products.get(i).getPrice() + "");
            String url = products.get(i).getTitle();
            Glide.with(getContext()).load(url).into(product_image);

            btn_edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getActivity().getBaseContext(), EditProductActivity.class);
                    intent = new Intent(getActivity(), EditProductActivity.class);

                    intent.putExtra("id", products.get(i).getId());
                    intent.putExtra("quantity", products.get(i).getStock_quantity());
                    intent.putExtra("cat_id", products.get(i).getBook_type_id());
                    intent.putExtra("name", products.get(i).getTitle());
                    intent.putExtra("price", products.get(i).getPrice());
                    intent.putExtra("image", products.get(i).getImage_url());

                    getActivity().startActivity(intent);
                }
            });

            btn_del.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Database db = new Database(getContext());
                    db.delete_product(products.get(i).getId());

                    getAllBook();
                }
            });

            return item;
        }
    }
}
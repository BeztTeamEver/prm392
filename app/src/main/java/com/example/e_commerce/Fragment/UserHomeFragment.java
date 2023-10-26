package com.example.e_commerce.Fragment;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.e_commerce.Activity.ProductActivity;
import com.example.e_commerce.Database.Database;
import com.example.e_commerce.Model.Book;
import com.example.e_commerce.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UserHomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserHomeFragment extends Fragment {

    ListView user_list_products;
    ArrayList<Book> products = new ArrayList<>();
    // Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public UserHomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProductFragment.
     */
    // Rename and change types and number of parameters
    public static UserHomeFragment newInstance(String param1, String param2) {
        UserHomeFragment fragment = new UserHomeFragment();
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
        View v = inflater.inflate(R.layout.fragment_user_home, container, false);

        user_list_products = v.findViewById(R.id.user_home_list_products);

        // TODO: get products from database and show it in listView

        Database dp = new Database(getContext());
        products = dp.get_products();
        UserHomeProductAdapter userHomeProductAdapter = new UserHomeProductAdapter(products);
        user_list_products.setAdapter(userHomeProductAdapter);

        user_list_products.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent intent = new Intent(getActivity().getBaseContext(), ProductActivity.class);
                intent = new Intent(getActivity(), ProductActivity.class);

                intent.putExtra("id",products.get(i).getId());
                intent.putExtra("stock_quantity",products.get(i).getStock_quantity());
                intent.putExtra("book_type_id",products.get(i).getBook_type_id());
                intent.putExtra("title",products.get(i).getTitle());
                intent.putExtra("author",products.get(i).getAuthor());
                intent.putExtra("image_url",products.get(i).getImage_url());
                intent.putExtra("description",products.get(i).getDescription());
                intent.putExtra("price",products.get(i).getPrice());
                getActivity().startActivity(intent);
            }
        });

        return v;
    }


    class UserHomeProductAdapter extends BaseAdapter {

        ArrayList<Book> products = new ArrayList<>();

        public UserHomeProductAdapter(ArrayList<Book> products) {
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
            View item = layoutInflater.inflate(R.layout.user_home_product_item, null);

            ImageView product_image = (ImageView) item.findViewById(R.id.user_home_iv_product_image);
            TextView product_name = (TextView) item.findViewById(R.id.user_home_tv_product_name);
            TextView product_price = (TextView) item.findViewById(R.id.user_home_tv_product_price);

            product_name.setText(products.get(i).getTitle());
            product_price.setText(products.get(i).getPrice()+"");
            String url = products.get(i).getImage_url();
            Glide.with(getContext()).load(url).into(product_image);

            return item;
        }
    }

}
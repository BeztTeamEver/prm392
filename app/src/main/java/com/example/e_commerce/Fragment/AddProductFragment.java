package com.example.e_commerce.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.e_commerce.Activity.AdminActivity;
import com.example.e_commerce.Database.Database;
import com.example.e_commerce.Model.BookType;
import com.example.e_commerce.Model.Category;
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
 * Use the {@link AddProductFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddProductFragment extends Fragment {

    EditText txt_image_url, txt_title, txt_price, txt_stock_quantity, txt_description, txt_author ;
    Button btn_show, btn_add;
    Spinner spinner_categoty;
    ImageView iv_product_image;
    ArrayList<BookType> bookTypes = new ArrayList<BookType>();
    IBookTypeService bookTypeService = RepositoryBase.getBookTypeService();
    IBookService bookService = RepositoryBase.getBookService();

    // Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AddProductFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddProductFragment.
     */
    // Rename and change types and number of parameters
    public static AddProductFragment newInstance(String param1, String param2) {
        AddProductFragment fragment = new AddProductFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,  Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_add_product, container, false);

        spinner_categoty = v.findViewById(R.id.add_product_spinner_category);
        txt_title = v.findViewById(R.id.add_product_txt_title);
        txt_image_url = v.findViewById(R.id.add_product_txt_image_url);
        txt_price = v.findViewById(R.id.add_product_txt_price);
        txt_author = v.findViewById(R.id.add_product_txt_author);
        txt_description = v.findViewById(R.id.add_product_txt_description);
        txt_stock_quantity = v.findViewById(R.id.add_product_txt_stock_quantity);
        iv_product_image = v.findViewById(R.id.add_product_iv_image);
        btn_show = v.findViewById(R.id.add_product_btn_show_image);
        btn_add = v.findViewById(R.id.add_product_btn_add);

        bookService = RepositoryBase.getBookService();
        bookTypeService = RepositoryBase.getBookTypeService();

        showNameBookType(); // sprint

        btn_show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!txt_image_url.getText().toString().isEmpty()) {
                    String url = txt_image_url.getText().toString();
                    Glide.with(getContext()).load(url).into(iv_product_image);
                }
            }
        });

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: add product
                String image_url = txt_image_url.getText().toString();
                String title = txt_title.getText().toString();
                String author = txt_author.getText().toString();
                String description = txt_description.getText().toString();
                int price = -1;
                price = Integer.parseInt(txt_price.getText().toString());
                int stock_quantity = -1;
                stock_quantity = Integer.parseInt(txt_stock_quantity.getText().toString());
                String book_type_name = spinner_categoty.getSelectedItem().toString();
                int bookType_id = 0;
                if(!image_url.isEmpty() && !title.isEmpty() && !author.isEmpty() && !description.isEmpty() && price!=-1 && stock_quantity!=-1){
                    for(int i = 0 ; i < bookTypes.size() ; i++){
                        if(bookTypes.get(i).getType_name().equals(book_type_name)){
                            bookType_id = bookTypes.get(i).getId();
                            break;
                        }
                    }
                    Book product = new Book();
                    product.setTitle(title);
                    product.setPrice(price);
                    product.setStock_quantity(stock_quantity);
                    product.setImage_url(image_url);
                    product.setBook_type_id(bookType_id);
                    product.setAuthor(author);
                    product.setDescription(description);
                    product.setStatus(1);

                    createBook(product);

                    txt_image_url.setText("");
                    txt_title.setText("");
                    txt_price.setText("");
                    txt_stock_quantity.setText("");
                    txt_author.setText("");
                    txt_description.setText("");
                    iv_product_image.setImageResource(R.drawable.image_placeholder);

                    Intent myIntent = new Intent(getActivity().getBaseContext(), AdminActivity.class);
                    myIntent.putExtra("adminGate",5);
                    getActivity().startActivity(myIntent);

                }else{
                    Toast.makeText(getContext(), "Fill all data first", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return v;
    }

    private void createBook(Book book){
        try{
            Call<Book> call = bookService.create(book);

            call.enqueue(new Callback<Book>() {
                @Override
                public void onResponse(Call<Book> call, Response<Book> response) {
                    if (response.body() != null){

                        return;
                    }
                }

                @Override
                public void onFailure(Call<Book> call, Throwable t) {

                }
            });
        } catch (Exception e){
            Log.d("Error", e.getMessage());
        }
    }
    private void showNameBookType(){
        try{
            Call<List<BookType>> call = bookTypeService.getAll();
            call.enqueue(new Callback<List<BookType>>() {
                @Override
                public void onResponse(Call<List<BookType>> call, Response<List<BookType>> response) {
                    List<BookType> resList = response.body();
                    if (resList == null){
                        return;
                    }

                    for (BookType bookType : resList){
                        bookTypes.add(bookType);
                    }

                    ArrayList<String> book_type_name = new ArrayList<String>();
                    for(int i = 0 ; i < bookTypes.size() ; i++){
                        book_type_name.add(bookTypes.get(i).getType_name());
                    }

                    ArrayAdapter aa = new ArrayAdapter(getContext(),android.R.layout.simple_spinner_item,book_type_name);
                    aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner_categoty.setAdapter(aa);


                }

                @Override
                public void onFailure(Call<List<BookType>> call, Throwable t) {

                }
            });

        } catch (Exception e){
            Log.d("Error", e.getMessage());
        }
    }
}
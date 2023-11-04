package com.example.e_commerce.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.e_commerce.Activity.AdminActivity;
import com.example.e_commerce.Activity.EditCategoryActivity;
import com.example.e_commerce.Activity.SignUpActivity;
import com.example.e_commerce.Database.Database;
import com.example.e_commerce.Model.Book;
import com.example.e_commerce.Model.BookType;
import com.example.e_commerce.Model.Category;
import com.example.e_commerce.Model.User;
import com.example.e_commerce.R;
import com.example.e_commerce.Repository.RepositoryBase;
import com.example.e_commerce.Service.IBookTypeService;
import com.example.e_commerce.Service.IUserService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddCategoryFragment extends Fragment {

    EditText txt_name, txt_image;
    Button btn_show, btn_add;
    ImageView iv_image;

    IBookTypeService bookTypeService;


    // Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AddCategoryFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddCategoryFragment.
     */
    // Rename and change types and number of parameters
    public static AddCategoryFragment newInstance(String param1, String param2) {
        AddCategoryFragment fragment = new AddCategoryFragment();
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
        View v = inflater.inflate(R.layout.fragment_add_category, container, false);

        bookTypeService = RepositoryBase.getBookTypeService();

        txt_name = v.findViewById(R.id.add_category_txt_name);
        txt_image = v.findViewById(R.id.add_category_txt_image);

        iv_image = v.findViewById(R.id.add_category_iv_image);

        btn_show = v.findViewById(R.id.add_category_btn_show_image);
        btn_add = v.findViewById(R.id.add_category_btn_add);

        btn_show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!txt_image.getText().toString().isEmpty()){
                    String url = txt_image.getText().toString();
                    Glide.with(getContext()).load(url).into(iv_image);
                }
            }
        });

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: add categoty code
                String name = txt_name.getText().toString(),
                        image = txt_image.getText().toString();

                if(!name.isEmpty() && !image.isEmpty()){
                    BookType bookType = BookType.getInstance();
                    bookType.setType_name(name);
                    bookType.setImage_url(image);
                    addBookType(bookType);

                    txt_name.setText("");
                    txt_image.setText("");
                    iv_image.setImageResource(R.drawable.image_placeholder);

                    Intent myIntent = new Intent(getActivity().getBaseContext(),AdminActivity.class);
                    myIntent.putExtra("adminGate",3);
                    getActivity().startActivity(myIntent);

                }else{
                    Toast.makeText(getContext(), "Fill all data first", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return v;
    }
    private void addBookType(BookType bookType){
        try{
            Call<BookType> call = bookTypeService.create(bookType);

            call.enqueue(new Callback<BookType>() {
                @Override
                public void onResponse(Call<BookType> call, Response<BookType> response) {
                    if (response.body() != null){
                        /*Toast.makeText(MainActivity.this, "Save successfully"
                                , Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(MainActivity.this
                                , TraineeListActivity.class);
                        startActivity(intent);*/
                    }
                }
                @Override
                public void onFailure(Call<BookType> call, Throwable t) {
//                    Toast.makeText(AddProductFragment.this, "Save fail"
//                            , Toast.LENGTH_LONG).show();
                }
            });
        } catch (Exception e){
            Log.d("Error", e.getMessage());
        }
    }
}
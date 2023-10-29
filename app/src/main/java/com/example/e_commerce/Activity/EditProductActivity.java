package com.example.e_commerce.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.bumptech.glide.Glide;
import com.example.e_commerce.Database.Database;
import com.example.e_commerce.Model.Book;
import com.example.e_commerce.Model.Category;
import com.example.e_commerce.R;

import java.util.ArrayList;

public class EditProductActivity extends AppCompatActivity {

    EditText txt_image_url, txt_title, txt_author, txt_price, txt_stock_quantity, txt_description;
    Button btn_show, btn_edit;
    Spinner spinner_category;
    ImageView iv_product_image;
    ArrayList<Category> categories= new ArrayList<Category>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_product);

        getSupportActionBar().hide();

        txt_image_url = findViewById(R.id.edit_product_txt_image_url);
        txt_title = findViewById(R.id.edit_product_txt_title);
        txt_price = findViewById(R.id.edit_product_txt_price);
        txt_author = findViewById(R.id.edit_product_txt_author);
        txt_description = findViewById(R.id.edit_product_txt_description);
        txt_stock_quantity = findViewById(R.id.edit_product_txt_stock_quantity);
        iv_product_image = findViewById(R.id.edit_product_iv_image);
        btn_show = findViewById(R.id.edit_product_btn_show_image);
        btn_edit = findViewById(R.id.edit_product_btn_edit);
        spinner_category = findViewById(R.id.edit_product_spinner_category);

        Database db = new Database(this);

        Intent n = getIntent();
        int id =  n.getExtras().getInt("id") ;
        int stock_quantity = n.getExtras().getInt("stock_quantity") ;
        int book_type_id = n.getExtras().getInt("book_type_id") ;
        String title =  n.getExtras().getString("title") ;
        double price =  n.getExtras().getDouble("price") ;
        String description =  n.getExtras().getString("description") ;
        String image_url =  n.getExtras().getString("image_url") ;
        String author =  n.getExtras().getString("author") ;

        categories = db.get_categories();
        ArrayList<String> category_name = new ArrayList<String>();
        for(int i = 0 ; i < categories.size() ; i++){
            category_name.add(categories.get(i).getName());
        }

        String cat_name;
        int i = 0;
        for(i = 0 ; i < categories.size() ; i++){
            if(categories.get(i).getId() == book_type_id){
                cat_name = categories.get(i).getName();
                break;
            }
        }

        ArrayAdapter aa = new ArrayAdapter(getApplicationContext(),android.R.layout.simple_spinner_item,category_name);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_category.setAdapter(aa);

        spinner_category.setSelection(i);
        Glide.with(getApplicationContext()).load(image_url).into(iv_product_image);
        txt_image_url.setText(image_url);
        txt_title.setText(title);
        txt_author.setText(author);
        txt_description.setText(description);
        txt_price.setText(price+"");
        txt_stock_quantity.setText(stock_quantity+"");


        btn_show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = txt_image_url.getText().toString();
                Glide.with(getApplicationContext()).load(url).into(iv_product_image);
            }
        });

        // TODO: show product data in txt fildes

        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: edit product
                String title = txt_title.getText().toString();
                Double price = Double.parseDouble(txt_price.getText().toString());
                int stock_quantity = Integer.parseInt(txt_stock_quantity.getText().toString());
                String book_type_name = spinner_category.getSelectedItem().toString();
                String image_url = txt_image_url.getText().toString();
                String author = txt_author.getText().toString();
                String description = txt_description.getText().toString();

                int book_type_id = 0;
                for(int i = 0 ; i < categories.size() ; i++){
                   if(categories.get(i).getName() == book_type_name){
                       book_type_id = categories.get(i).getId();
                       break;
                   }
                }
                //Book product = new Book(id, stock_quantity, book_type_id, title, author, image_url, description, price);

                //Database db = new Database(getApplicationContext());
                //db.edit_product(product);
//                finish();
            }
        });

    }
}
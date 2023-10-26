package com.example.e_commerce.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.e_commerce.Database.Database;
import com.example.e_commerce.Model.Book;
import com.example.e_commerce.Model.User;
import com.example.e_commerce.R;

public class ProductActivity extends AppCompatActivity {

    ImageButton imageButton_increment_amount, imageButton_decrement_amount;
    TextView textView_product_amount;
    int amount = 1;

    ImageView product_image;
    TextView tv_title, tv_price, tv_description, tv_author;
    Button btn_add;
    int quantity;

    Intent n;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        Database db = new Database(this);

        n = getIntent();
        int id =  n.getExtras().getInt("id") ;
        int stock_quantity = n.getExtras().getInt("stock_quantity") ;
        int book_type_id = n.getExtras().getInt("book_type_id") ;
        String title =  n.getExtras().getString("title") ;
        double price =  n.getExtras().getDouble("price") ;
        String description =  n.getExtras().getString("description") ;
        String image_url =  n.getExtras().getString("image_url") ;
        String author =  n.getExtras().getString("author") ;

        imageButton_increment_amount = findViewById(R.id.btn_increment_amount);
        imageButton_decrement_amount = findViewById(R.id.btn_decrement_amount);
        textView_product_amount = findViewById(R.id.textView_product_amount);

        product_image = findViewById(R.id.imageView_product_image);
        tv_title = findViewById(R.id.textView_product_title);
        tv_author = findViewById(R.id.textView_product_author);
        tv_price = findViewById(R.id.textView_product_price);
        tv_description = findViewById(R.id.textView_product_description);
        btn_add = findViewById(R.id.user_home_btn_add_to_cart);

        String url = image_url;
        Glide.with(getApplicationContext()).load(url).into(product_image);

        tv_title.setText(title);
        tv_price.setText(price+"");


        imageButton_increment_amount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                amount = Integer.parseInt(textView_product_amount.getText().toString());
                amount++;
                textView_product_amount.setText(amount + "");
            }
        });

        imageButton_decrement_amount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                amount = Integer.parseInt(textView_product_amount.getText().toString());
                if(amount > 1){
                    amount--;
                    textView_product_amount.setText(amount + "");
                }
            }
        });

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User user = User.getInstance();
                quantity = Integer.parseInt(textView_product_amount.getText().toString());
                //Book product = new Book(id, stock_quantity, book_type_id, title
                // , author, image_url, description, price);
                //db.add_to_cart(product, user.getId());
            }
        });
    }
}
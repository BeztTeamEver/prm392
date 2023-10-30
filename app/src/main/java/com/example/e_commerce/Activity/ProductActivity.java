package com.example.e_commerce.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.e_commerce.Database.Database;
import com.example.e_commerce.Model.Book;
import com.example.e_commerce.Model.Cart;
import com.example.e_commerce.Model.User;
import com.example.e_commerce.R;
import com.example.e_commerce.Repository.RepositoryBase;
import com.example.e_commerce.Service.IBookTypeService;
import com.example.e_commerce.Service.ICartService;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductActivity extends AppCompatActivity {

    ImageButton imageButton_increment_amount, imageButton_decrement_amount;
    TextView textView_product_amount;
    int amount = 1;

    ImageView product_image;
    TextView tv_title, tv_price, tv_description, tv_author;
    Button btn_add;

    Intent n;
    private ICartService cartService = RepositoryBase.getCartService();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        n = getIntent();
        int user_id = n.getExtras().getInt("user_id");
        int book_id =  n.getExtras().getInt("id") ;
        int stock_quantity = n.getExtras().getInt("stock_quantity") ;
        int book_type_id = n.getExtras().getInt("book_type_id") ;
        String title =  n.getExtras().getString("title") ;
        int price =  n.getExtras().getInt("price") ;
        String description =  n.getExtras().getString("description") ;
        String image_url =  n.getExtras().getString("image_url") ;
        String author =  n.getExtras().getString("author") ;

        User user = User.getInstance();
        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        String userJson = sharedPreferences.getString("current_user", null);
        Gson gson = new Gson();
        user = gson.fromJson(userJson, User.class);

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
        tv_description.setText(description);
        tv_author.setText(author);


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
                Cart cart = new Cart(book_id, book_type_id, user_id, amount, title, price*amount, image_url);
                try{
                    Call<Cart> call = cartService.create(cart);
                    call.enqueue(new Callback<Cart>() {
                        @Override
                        public void onResponse(Call<Cart> call, Response<Cart> response) {
                            if (response.body() != null){
                                Intent myIntent = new Intent(ProductActivity.this, AdminActivity.class);
                                myIntent.putExtra("adminGate",4);
                                ProductActivity.this.startActivity(myIntent);
                            }
                        }
                        @Override
                        public void onFailure(Call<Cart> call, Throwable t) {
                            Toast.makeText(ProductActivity.this, "Save fail"
                                    , Toast.LENGTH_LONG).show();
                        }
                    });
                } catch (Exception e){
                    Log.d("Error", e.getMessage());
                }
            }
        });
    }
}
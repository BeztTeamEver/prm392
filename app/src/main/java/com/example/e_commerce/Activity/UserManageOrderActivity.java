package com.example.e_commerce.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.e_commerce.Model.Cart;
import com.example.e_commerce.Model.Order;
import com.example.e_commerce.Model.User;
import com.example.e_commerce.R;
import com.example.e_commerce.Repository.RepositoryBase;
import com.example.e_commerce.Service.ICartService;
import com.example.e_commerce.Service.IOrderService;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.Gson;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserManageOrderActivity extends AppCompatActivity {

    ListView user_manage_order_list;
    ArrayList<Order> orders = new ArrayList<>();
    float rate = -1;

    //location variables
    IOrderService orderService = RepositoryBase.getOrderService();
    private final static int REQUEST_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_manage_order);

        int total = 0;
        user_manage_order_list = findViewById(R.id.user_manage_order);
        getAllOrder();

        user_manage_order_list .setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent intent = new Intent(getApplicationContext(), ProductActivity.class);

                intent.putExtra("id",orders.get(i).getOrder_id());
                startActivity(intent);
            }
        });

    }

    class UserManageOrderAdapter extends BaseAdapter {

        ArrayList<Order> orders = new ArrayList<>();

        public UserManageOrderAdapter(ArrayList<Order> orders) {
            this.orders = orders;
        }

        @Override
        public int getCount() {
            return orders.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {

            LayoutInflater layoutInflater = getLayoutInflater();
            View item = layoutInflater.inflate(R.layout.user_manage_order_item, null);

            TextView product_state = (TextView) item.findViewById(R.id.user_manage_order_item_state);
            TextView product_price = (TextView) item.findViewById(R.id.user_manage_order_item_price);
            TextView product_create_at = (TextView) item.findViewById(R.id.user_manage_order_item_date);
            Button product_detail_btn = (Button) item.findViewById(R.id.user_manage_order_detail);

            product_create_at.setText(orders.get(i).getCreated_at() + "");
            product_state.setText(orders.get(i).getStatus());
            product_price.setText(orders.get(i).getTotal_amount() + "");

            product_detail_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent =  new Intent(getApplicationContext(), ProductActivity.class);
//                    intent = new Intent(getApplicationContext(), ProductActivity.class);

                    intent.putExtra("id", orders.get(i).getOrder_id());
//                    intent.putExtra("stock_quantity", products.get(i).getStock_quantity());
//                    intent.putExtra("book_type_id", products.get(i).getBook_type_id());
//                    intent.putExtra("title", products.get(i).getTitle());
//                    intent.putExtra("price", products.get(i).getPrice());
//                    intent.putExtra("description", products.get(i).getDescription());
//                    intent.putExtra("author", products.get(i).getAuthor());
//                    intent.putExtra("image_url", products.get(i).getImage_url());
//                    intent.putExtra("book_type_id", products.get(i).getBook_type_id());
//
                   startActivity(intent);
                }
            });

            return item;
        }
    }

    public void getAllOrder(){
        try{
            Call<List<Order>> call = orderService.getAll();
            call.enqueue(new Callback<List<Order>>() {
                @Override
                public void onResponse(Call<List<Order>> call, Response<List<Order>> response) {
                    orders.clear();
                    List<Order> list_orders = response.body();
                    if (list_orders == null){
                        return;
                    }
                    User user = User.getInstance();
                    SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
                    String userJson = sharedPreferences.getString("current_user", null);
                    Gson gson = new Gson();
                    user = gson.fromJson(userJson, User.class);

                    Collections.reverse(list_orders);
                    for (Order item : list_orders) {
                        if (item.getUser_id() == user.getId())
                            orders.add(item);
                    }
                    UserManageOrderAdapter userManageOrderAdapter = new UserManageOrderAdapter(orders);
                    user_manage_order_list.setAdapter(userManageOrderAdapter);
                }
                @Override
                public void onFailure(Call<List<Order>> call, Throwable t) {
                }
            });

        } catch (Exception e){
            Log.d("Error", e.getMessage());
        }
    }


}
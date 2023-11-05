package com.example.e_commerce.Activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.e_commerce.Fragment.AdminManageOrderFragment;
import com.example.e_commerce.Model.BookType;
import com.example.e_commerce.Model.Order;
import com.example.e_commerce.Model.OrderItem;
import com.example.e_commerce.R;
import com.example.e_commerce.Repository.RepositoryBase;
import com.example.e_commerce.Service.IOrderItemService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminDetailOrderActivity extends AppCompatActivity {
    ArrayList<OrderItem> orderItems = new ArrayList<>();
    IOrderItemService orderItemService = new RepositoryBase().getOrderItemService();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_detail_order);

        Intent n = getIntent();
        int id =  n.getExtras().getInt("id");
        Date create_at =  (Date)n.getSerializableExtra("create_at");
        int price =  n.getExtras().getInt("price");
        String status =  n.getExtras().getString("status");
        String address =  n.getExtras().getString("address");

        TextView product_status = (TextView) findViewById(R.id.admin_manage_order_item_status);
        TextView product_price = (TextView) findViewById(R.id.admin_manage_order_item_price);
        TextView product_create_at = (TextView) findViewById(R.id.admin_manage_order_item_date);
        TextView product_address= (TextView) findViewById(R.id.admin_manage_order_item_address);

        product_create_at.setText(create_at + "");
        product_status.setText(status);
        product_address.setText(address);
        product_price.setText(price + "");
    }

    class AdminDetailOrderAdapter extends BaseAdapter {

        ArrayList<OrderItem> orderItems = new ArrayList<>();

        public AdminDetailOrderAdapter (ArrayList<OrderItem> orderItems) {
            this.orderItems = orderItems;
        }

        @Override
        public int getCount() {
            return orderItems.size();
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public Object getItem(int i) {
            return orderItems.get(i).getId();
        }

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {

            LayoutInflater layoutInflater = getLayoutInflater();
            View item = layoutInflater.inflate(R.layout.admin_manage_order_product_item, null);

//            TextView product_status = (TextView) item.findViewById(R.id.admin_manage_order_status);
//            TextView product_price = (TextView) item.findViewById(R.id.admin_manage_order_price);
//            TextView product_create_at = (TextView) item.findViewById(R.id.admin_manage_order_date);
//            TextView product_address= (TextView) item.findViewById(R.id.admin_manage_order_address);
//
//            product_create_at.setText(orderItems.get(i).getCreated_at() + "");
//            product_status.setText(orderItems.get(i).getStatus());
//            product_address.setText(orderItems.get(i).getAddress());
//            product_price.setText(orderItems.get(i).getTotal_amount() + "");

            return item;
        }
    }
//    private void getAllOrder(String status){
//        try{
//            orderItems.clear();
//            Call<List<OrderItem>> call = orderItemService.getAll();
//            call.enqueue(new Callback<List<OrderItem>>() {
//                @Override
//                public void onResponse(Call<List<OrderItem>> call, Response<List<OrderItem>> response) {
//                    List<OrderItem> resList = response.body();
//                    if (resList == null){
//                        return;
//                    }
//                    for (OrderItem item : resList){
//                        if (status == "Tất cả")
//                            orderItems.add(item);
//                        else if(item.getStatus().equals(status))
//                            orders.add(item);
//                    }
//                    AdminManageOrderFragment.AdminManageOrderAdapter adapter = new AdminManageOrderFragment.AdminManageOrderAdapter(orders);
//                    list_order.setAdapter(adapter);
//                }
//
//                @Override
//                public void onFailure(Call<List<Order>> call, Throwable t) {
//
//                }
//            });
//
//        } catch (Exception e){
//            Log.d("Error", e.getMessage());
//        }
//    }
}
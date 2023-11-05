package com.example.e_commerce.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.e_commerce.R;

import java.util.Date;

public class AdminDetailOrderActivity extends AppCompatActivity {

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
}
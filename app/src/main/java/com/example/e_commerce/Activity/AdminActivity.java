package com.example.e_commerce.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.e_commerce.Activity.message.MainChatActivity;
import com.example.e_commerce.Fragment.AddCategoryFragment;
import com.example.e_commerce.Fragment.AddProductFragment;
import com.example.e_commerce.Fragment.AdminManageOrderFragment;
import com.example.e_commerce.Fragment.ChartFragment;
import com.example.e_commerce.Fragment.FeedbackFragment;
import com.example.e_commerce.Fragment.ManageCategoryFragment;
import com.example.e_commerce.Fragment.ManageProductFragment;
import com.example.e_commerce.R;
import com.example.e_commerce.Fragment.ReportFragment;

public class AdminActivity extends AppCompatActivity {

    Fragment admin_fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent n = getIntent();
        int id =  n.getExtras().getInt("adminGate") ;
        setContentView(R.layout.activity_admin);

        if(id == 1)
            getSupportFragmentManager().beginTransaction().replace(R.id.admin_container
                    , new ManageProductFragment()).commit();
        else if (id == 2) {
            getSupportFragmentManager().beginTransaction().replace(R.id.admin_container
                    , new ManageCategoryFragment()).commit();
            Toast.makeText(AdminActivity.this, "Chỉnh sửa thể loại thành công"
                    , Toast.LENGTH_LONG).show();
        } else if (id == 3) {
            getSupportFragmentManager().beginTransaction().replace(R.id.admin_container
                    , new ManageCategoryFragment()).commit();
            Toast.makeText(AdminActivity.this, "Tạo thể loại thành công"
                    , Toast.LENGTH_LONG).show();
        } else if (id == 4) {
            getSupportFragmentManager().beginTransaction().replace(R.id.admin_container
                    , new ManageProductFragment()).commit();
            Toast.makeText(AdminActivity.this, "Chỉnh sửa sách thành công"
                    , Toast.LENGTH_LONG).show();
        }  else if (id == 5) {
            getSupportFragmentManager().beginTransaction().replace(R.id.admin_container
                    , new ManageProductFragment()).commit();
            Toast.makeText(AdminActivity.this, "Tạo sách thành công"
                    , Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.admin_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.nav_manage_product) {
            getSupportFragmentManager().beginTransaction().replace(R.id.admin_container
                    , new ManageProductFragment()).commit();
        } else if (item.getItemId() == R.id.nav_add_product) {
            getSupportFragmentManager().beginTransaction().replace(R.id.admin_container
                    , new AddProductFragment()).commit();

        } else if (item.getItemId() == R.id.nav_manage_category) {
            getSupportFragmentManager().beginTransaction().replace(R.id.admin_container
                    , new ManageCategoryFragment()).commit();

        } else if (item.getItemId() == R.id.nav_add_category) {
            getSupportFragmentManager().beginTransaction().replace(R.id.admin_container
                    , new AddCategoryFragment()).commit();

        } else if (item.getItemId() == R.id.nav_report) {
            getSupportFragmentManager().beginTransaction().replace(R.id.admin_container
                    , new ReportFragment()).commit();

        } else if (item.getItemId() == R.id.nav_feedback) {
            getSupportFragmentManager().beginTransaction().replace(R.id.admin_container
                    , new FeedbackFragment()).commit();

        } else if (item.getItemId() == R.id.nav_chart) {
            getSupportFragmentManager().beginTransaction().replace(R.id.admin_container
                    , new ChartFragment()).commit();
        } else if (item.getItemId() == R.id.nav_chat){
            Intent intent = new Intent(AdminActivity.this, MainChatActivity.class);
            startActivity(intent);
        } else if (item.getItemId() == R.id.nav_notify){
            Intent intent = new Intent(AdminActivity.this, NotificationActivity.class);
            startActivity(intent);
        } else if (item.getItemId() == R.id.nav_order) {
            getSupportFragmentManager().beginTransaction().replace(R.id.admin_container
                    , new AdminManageOrderFragment()).commit();
        }
        return true;
    }
}
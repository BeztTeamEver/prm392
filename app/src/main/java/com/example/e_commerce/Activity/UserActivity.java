package com.example.e_commerce.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.e_commerce.Fragment.CartFragment;
import com.example.e_commerce.Fragment.ProfileFragment;
import com.example.e_commerce.R;
import com.example.e_commerce.Fragment.SearchFragment;
import com.example.e_commerce.Fragment.UserCategoryFragment;
import com.example.e_commerce.Fragment.UserHomeFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class UserActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        getSupportActionBar().hide();

//        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_ios_new_24);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        BottomNavigationView bottomNav = findViewById(R.id.user_bottom_nav);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
        getSupportFragmentManager().beginTransaction().replace(R.id.user_container, new UserHomeFragment()).commit();

    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selected_fragment = null;
            if (item.getItemId() == R.id.user_nav_home){
                selected_fragment = new UserHomeFragment();
            } else if(item.getItemId() == R.id.user_nav_category){
                selected_fragment = new UserCategoryFragment();
            } else if(item.getItemId() == R.id.user_nav_search){
                selected_fragment = new SearchFragment();
            } else if(item.getItemId() ==  R.id.user_nav_cart){
                selected_fragment = new CartFragment();
            } else if(item.getItemId() ==  R.id.user_nav_profile){
                selected_fragment = new ProfileFragment();
            }

            getSupportFragmentManager().beginTransaction().replace(R.id.user_container, selected_fragment).commit();
            return true;
        }
    };

}
package com.example.e_commerce.Activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.e_commerce.Fragment.UserHomeFragment;
import com.example.e_commerce.Model.User;
import com.example.e_commerce.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;

public class UserProfileActivity extends AppCompatActivity {
    TextView tv_username, tv_email, tv_password, tv_fullname, tv_phone_number;
    SharedPreferences.Editor myEdit;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        User user = User.getInstance();

        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref",MODE_PRIVATE);
        String userJson = sharedPreferences.getString("current_user", null);

        Gson gson = new Gson();
        user = gson.fromJson(userJson, User.class);

        tv_username = findViewById(R.id.profile_tv_username);
        tv_email = findViewById(R.id.profile_tv_email);
        tv_phone_number =findViewById(R.id.profile_tv_phone_number);
        tv_fullname = findViewById(R.id.profile_tv_fullname);

        tv_username.setText(user.getUsername());
        tv_email.setText(user.getEmail());
        tv_phone_number.setText(user.getPhone_number());
        tv_fullname.setText(user.getFullname());

    }
}

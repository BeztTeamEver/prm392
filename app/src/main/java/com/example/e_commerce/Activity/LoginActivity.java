package com.example.e_commerce.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.e_commerce.Model.User;
import com.example.e_commerce.R;
import com.example.e_commerce.Repository.RepositoryBase;
import com.example.e_commerce.Service.IUserService;
import com.google.gson.Gson;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    Button btn_login;
    EditText txt_username, txt_password;
    TextView textView_forgot_password, textView_signup;
    SharedPreferences.Editor myEdit;
    SharedPreferences sharedPreferences;

    IUserService userService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
//        startActivity(new Intent(LoginActivity.this, AdminActivity.class));//comment me after finish task
        //Hide ActionBar
        getSupportActionBar().hide();

        ///// Kill /////
        ////////////////////////////////////////

        userService = RepositoryBase.getUserService();


        txt_username = findViewById(R.id.login_txt_username);
        txt_password = findViewById(R.id.login_txt_password);
        btn_login = (Button) findViewById(R.id.login_btn_login);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                sharedPreferences = getSharedPreferences("MySharedPref",MODE_PRIVATE);
                myEdit = sharedPreferences.edit();
                myEdit.commit();

                // TODO: login
                String username = txt_username.getText().toString(),
                        password = txt_password.getText().toString();

                if(!username.isEmpty() && !password.isEmpty()){

                    authenticate(username, password);

                }else{
                    Toast.makeText(getApplicationContext(), "Fill your data first"
                            , Toast.LENGTH_SHORT).show();
                }
            }
        });

        textView_signup = (TextView) findViewById(R.id.login_tv_signup);
        textView_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Navigate to signup screen
                startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
            }
        });
    }

    public void authenticate(String username, String password) {
        Call<List<User>> call = userService.getUserByUsernameAndPassword(username, password);

        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if (response.isSuccessful()) {
                    List<User> users = response.body();
                    if (users == null || users.size() == 0) return ;
                    User user = users.get(0);
                    if (user != null) {
                        myEdit.putInt("id", user.getId());
                        myEdit.putString("username", user.getUsername());
                        myEdit.putString("email", user.getEmail());
                        myEdit.putString("phone_number", user.getPhone_number());
                        myEdit.putString("password", user.getPassword());
                        Gson gson = new Gson();
                        String userJson = gson.toJson(user);
                        myEdit.putString("current_user", userJson);
                        myEdit.apply();

                        if (username.equals("admin") && password.equals("admin"))
                            startActivity(new Intent(LoginActivity.this
                                    , AdminActivity.class));
                        else
                            startActivity(new Intent(LoginActivity.this
                                , UserActivity.class));
                    } else {
                        Toast.makeText(getApplicationContext(), "Make sure from your data"
                                , Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // Handle API error
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                // Handle network or other errors
            }
        });
    }
}
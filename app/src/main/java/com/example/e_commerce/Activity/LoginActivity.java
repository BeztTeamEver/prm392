package com.example.e_commerce.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

import com.google.firebase.auth.FirebaseAuth;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    Button btn_login;
    EditText txt_email, txt_password;
    TextView textView_forgot_password, textView_signup;

    IUserService userService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
//        startActivity(new Intent(LoginActivity.this, AdminActivity.class));//comment me after finish task
        //Hide ActionBar
        getSupportActionBar().hide();


        userService = RepositoryBase.getUserService();

        txt_email = findViewById(R.id.login_txt_email);
        txt_password = findViewById(R.id.login_txt_password);
        btn_login = (Button) findViewById(R.id.login_btn_login);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // TODO: login
                String email = txt_email.getText().toString(),
                        password = txt_password.getText().toString();

                if(!email.isEmpty() && !password.isEmpty()){

                    authenticate(email, password);

                }else{
                    Toast.makeText(getApplicationContext(), "Hãy điền đầy đủ thông tin!"
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

    public void authenticate(String email, String password) {
        if (email.equals("admin") && password.equals("admin")) {
            Intent myIntent = new Intent(LoginActivity.this, AdminActivity.class);
                            myIntent.putExtra("adminGate",1);
                            startActivity(myIntent);
        }else {
            Call<List<User>> call = userService.getUserByEmailAndPassword(email, password);

            call.enqueue(new Callback<List<User>>() {
                             @Override
                             public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                                 if (response.isSuccessful()) {
                                     List<User> users = response.body();
                                     if (users == null || users.size() == 0) {
                                         Toast.makeText(getApplicationContext(), "Thông tin đăng nhập không chính xác!"
                                                 , Toast.LENGTH_SHORT).show();
                                         return;
                                     } else {
                                         FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

                                             firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener((task) -> {
                                                 if (task.isSuccessful()) {
                                                     if (firebaseAuth.getCurrentUser().isEmailVerified()) {
                                                         startActivity(new Intent(LoginActivity.this
                                                                 , UserActivity.class));
                                                     } else {
                                                         Toast.makeText(LoginActivity.this, "Xin hãy xác thực email trước khi đăng nhập.",
                                                                 Toast.LENGTH_SHORT).show();
                                                     }

                                                 } else {
                                                     Toast.makeText(getApplicationContext(), "Thông tin đăng nhập không chính xác!"
                                                             , Toast.LENGTH_SHORT).show();
                                                 }
                                             });

                                     }
                                 }
                             }

                             @Override
                             public void onFailure(Call<List<User>> call, Throwable t) {

                             }
                         }
            );
        }
    }


}
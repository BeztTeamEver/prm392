package com.example.e_commerce.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.e_commerce.Database.Database;
import com.example.e_commerce.Model.User;
import com.example.e_commerce.R;
import com.example.e_commerce.Repository.RepositoryBase;
import com.example.e_commerce.Service.IUserService;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity {

    final Calendar myCalendar = Calendar.getInstance();
    EditText txt_username, txt_email, txt_password, txt_fullname, txt_phone_number;
    TextView tv_login;
    Button btn_signup;
    Spinner spinner_gender;

    IUserService userService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        getSupportActionBar().hide();
        //Database db = new Database(this);

        userService = RepositoryBase.getUserService();

        txt_username = findViewById(R.id.signup_txt_username);
        txt_email = findViewById(R.id.signup_txt_email);
        txt_password = findViewById(R.id.signup_txt_password);
        txt_fullname = findViewById(R.id.signup_txt_fullname);
        txt_phone_number = findViewById(R.id.signup_txt_phone_number);

        tv_login = (TextView) findViewById(R.id.signup_tv_login);
        tv_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
            }
        });

        btn_signup = (Button) findViewById(R.id.signup_btn_signup);
        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: SignUp

                String username = txt_username.getText().toString(),
                        email = txt_email.getText().toString(),
                        password = txt_password.getText().toString(),
                        fullname = txt_fullname.getText().toString(),
                        phone_number = txt_phone_number.getText().toString();

                if(!username.isEmpty() && !email.isEmpty() && !password.isEmpty()
                        && !fullname.isEmpty() && !phone_number.isEmpty()) {
                    User user = User.getInstance();
                    user.setFullname(fullname);
                    user.setEmail(email);
                    user.setPhone_number(phone_number);
                    user.setUsername(username);
                    user.setPassword(password);
                    user.setStatus(1);
                    user.setRole("Customer");
                    user.setCreateAt(Calendar.getInstance().getTime());

                    //db.insert_user(user);
                    addUser(user);

                    Toast.makeText(getApplicationContext(), "Đăng ký thành công", Toast.LENGTH_SHORT).show();

                    //startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
                }else{
                    Toast.makeText(getApplicationContext(), "Hãy điền hết thông tin", Toast.LENGTH_SHORT).show();
                }
                //startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
            }
        });

    }

    private void addUser(User user){

        try{
            Call<User> call = userService.create(user);

            call.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    if (response.body() != null){
                        /*Toast.makeText(MainActivity.this, "Save successfully"
                                , Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(MainActivity.this
                                , TraineeListActivity.class);
                        startActivity(intent);*/

                    }
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    Toast.makeText(SignUpActivity.this, "Save fail"
                            , Toast.LENGTH_LONG).show();
                }
            });
        } catch (Exception e){
            Log.d("Error", e.getMessage());
        }
    }

}
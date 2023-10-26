package com.example.e_commerce.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class SignUpActivity extends AppCompatActivity {

    final Calendar myCalendar = Calendar.getInstance();
    EditText txt_username, txt_email, txt_password, txt_fullname, txt_phone_number;
    TextView tv_login;
    Button btn_signup;
    Spinner spinner_gender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        getSupportActionBar().hide();
        Database db = new Database(this);

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
                if(!username.isEmpty() && !email.isEmpty() && !password.isEmpty() && !fullname.isEmpty() && !phone_number.isEmpty()) {
                    User user = User.getInstance();
                    user.setFullname(fullname);
                    user.setEmail(email);
                    user.setPhone_number(phone_number);
                    user.setUsername(username);
                    user.setPassword(password);
                    db.insert_user(user);
                    Toast.makeText(getApplicationContext(), "Đăng ký thành công", Toast.LENGTH_SHORT).show();

                    startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
                }else{
                    Toast.makeText(getApplicationContext(), "Hãy điền hết thông tin", Toast.LENGTH_SHORT).show();
                }
                startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
            }
        });


    }
}
package com.example.e_commerce.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.e_commerce.R;
import com.example.e_commerce.Service.FCMSend;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

public class NotificationActivity extends AppCompatActivity {


    EditText etTitle, etMessage;
    Button btnSend;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification2);

        etTitle = findViewById(R.id.etTitle);
        etMessage = findViewById(R.id.etMessage);


        btnSend = findViewById(R.id.btnSendNoti);

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = etTitle.getText().toString();
                String message = etMessage.getText().toString();

                String token = "eFEp8rEnSXOkCf0C7ABDmp:APA91bEGccwMbF8_Lqp9HLHp13pNA2vQlLsUz8ksq5PdT5T3zR-p-kVOVNJf5yTT3Xz2VCtb5EygNwtbATR2Dx-eRcA73NLlGHxZJXvCLkPJtGpLGObN9ZqULsVsyp9n-pAQ8QIZ39Ss";

                FCMSend.pushNotification(NotificationActivity.this
                        ,token
                        , "Nhớ em"
                        , "Anh thật sự rất nhớ em");
            }
        });

        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            return;
                        }

                        // Get new FCM registration token
                        String token = task.getResult();

                        // Log and toast
                        System.out.println("TOKEN " + token);
                    }
                });



    }
}
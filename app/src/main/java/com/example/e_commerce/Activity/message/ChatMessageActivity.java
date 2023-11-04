package com.example.e_commerce.Activity.message;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_commerce.Database.Database;
import com.example.e_commerce.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ChatMessageActivity extends AppCompatActivity {

    private final List<ChatList> chatLists = new ArrayList<>();
    ImageView btnSendMessage, profilePic;
    EditText messageEditTxt;
    TextView name;
    DatabaseReference databaseReference;
    String chatKey;
    String getUserMobile = "";
    String getMobile = "";
    RecyclerView chattingRecycleView;
    ChatAdapter chatAdapter;
    private boolean loadingFirstTime = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_message);

        databaseReference
                = FirebaseDatabase
                .getInstance()
                .getReferenceFromUrl("https://prm392-52326-default-rtdb.firebaseio.com/");

        binding();

        final String getName = getIntent().getStringExtra("name");
        final String getProfilePic = getIntent().getStringExtra("profile_pic");
        chatKey = getIntent().getStringExtra("chat_key");
        getMobile = getIntent().getStringExtra("mobile");

        //get user mobile from memory
        SharedPreferences preferences = getSharedPreferences("MyPreferencesChat", Context.MODE_PRIVATE);
        getUserMobile = preferences.getString("mobileChat", null);

        name.setText(getName);
        Picasso.get().load(getProfilePic).into(profilePic);

        chattingRecycleView.setHasFixedSize(true);
        chattingRecycleView.setLayoutManager(new LinearLayoutManager(ChatMessageActivity.this));

        chatAdapter = new ChatAdapter(chatLists, ChatMessageActivity.this);

        chattingRecycleView.setAdapter(chatAdapter);


        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (chatKey.isEmpty()) {
                    //generate chat key by default chatKey is 1
                    chatKey = "1";

                    if (snapshot.hasChild("chat")) {
                        chatKey = String.valueOf(snapshot.child("chat").getChildrenCount() + 1);
                    }

                }
                if (snapshot.hasChild("chat")){
                    if (snapshot.child("chat").child(chatKey).hasChild("messages")){
                        chatLists.clear();
                        for (DataSnapshot messagesSnapshot : snapshot.child("chat")
                                .child(chatKey).child("messages")
                                .getChildren()){

                            if (messagesSnapshot.hasChild("msg") && messagesSnapshot.hasChild("mobile")){
                                final String messageTimestamps = messagesSnapshot.getKey();
                                final String getMobile = messagesSnapshot.child("mobile").getValue(String.class);
                                final String getMsg = messagesSnapshot.child("msg").getValue(String.class);

                                Timestamp timestamp = new Timestamp(Long.parseLong(messageTimestamps));
                                Date date = new Date(timestamp.getTime());
                                SimpleDateFormat simpleDateFormat =
                                        new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());

                                SimpleDateFormat simpleTimeFormat =
                                        new SimpleDateFormat("hh:mm aa", Locale.getDefault());

                                ChatList chatList = new ChatList(getMobile, getName, getMsg
                                        , simpleDateFormat.format(date)
                                        , simpleTimeFormat.format(date));

                                /*if (loadingFirstTime *//*|| Long.parseLong(messageTimestamps) > 1*//*){
                                    //save lastMessage*/

                                    loadingFirstTime = false;

                                    chatLists.add(chatList);

                                    chatAdapter.updateChatList(chatLists);

                                    chattingRecycleView.scrollToPosition(chatLists.size() - 1);

                                //}

                            }

                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // This code will be triggered whenever there is a change in the data
                if (chatKey.isEmpty()) {
                    // generate chat key by default chatKey is 1
                    chatKey = "1";

                    if (snapshot.hasChild("chat")) {
                        chatKey = String.valueOf(snapshot.child("chat").getChildrenCount() + 1);
                    }
                }
                if (snapshot.hasChild("chat")) {
                    if (snapshot.child("chat").child(chatKey).hasChild("messages")) {
                        chatLists.clear();
                        for (DataSnapshot messagesSnapshot : snapshot.child("chat")
                                .child(chatKey).child("messages")
                                .getChildren()) {
                            // Your existing code to populate chatLists
                            if (messagesSnapshot.hasChild("msg") && messagesSnapshot.hasChild("mobile")) {
                                final String messageTimestamps = messagesSnapshot.getKey();
                                final String getMobile = messagesSnapshot.child("mobile").getValue(String.class);
                                final String getMsg = messagesSnapshot.child("msg").getValue(String.class);

                                // Parse timestamp
                                Timestamp timestamp = new Timestamp(Long.parseLong(messageTimestamps));
                                Date date = new Date(timestamp.getTime());
                                SimpleDateFormat simpleDateFormat =
                                        new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());

                                SimpleDateFormat simpleTimeFormat =
                                        new SimpleDateFormat("hh:mm aa", Locale.getDefault());

                                ChatList chatList = new ChatList(getMobile, getName, getMsg
                                        , simpleDateFormat.format(date)
                                        , simpleTimeFormat.format(date));

                                chatLists.add(chatList);
                            }
                        }
                        // Update the UI with chatLists after processing the data
                        chatAdapter.updateChatList(chatLists);
                        chattingRecycleView.scrollToPosition(chatLists.size() - 1);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle errors
            }
        });



        btnSendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String currentTimestamp = String.valueOf(System.currentTimeMillis()).substring(0, 10);
                final String getTextMessage = messageEditTxt.getText().toString();
                databaseReference.child("chat").child(chatKey)
                        .child("user_1").setValue(getUserMobile);

                databaseReference.child("chat").child(chatKey)
                        .child("user_2").setValue(getMobile);

                databaseReference.child("chat").child(chatKey)
                        .child("messages").child(currentTimestamp)
                        .child("msg").setValue(getTextMessage);

                databaseReference.child("chat").child(chatKey)
                        .child("messages").child(currentTimestamp)
                        .child("mobile").setValue(getUserMobile);

                messageEditTxt.setText("");

            }
        });

    }

    private void binding() {

        btnSendMessage = findViewById(R.id.btnSendMessage);
        profilePic = findViewById(R.id.profilePic);
        messageEditTxt = findViewById(R.id.messageEditTxt);
        name = findViewById(R.id.name);
        chattingRecycleView = findViewById(R.id.chattingRecycleView);

    }
}
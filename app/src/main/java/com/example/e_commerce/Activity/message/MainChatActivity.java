package com.example.e_commerce.Activity.message;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.example.e_commerce.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MainChatActivity extends AppCompatActivity {

    private String mobile;
    private String email;
    private String name;
    private RecyclerView messagesRecyclerView;

    private String chatKey = "";

    private String lastMessage = "";
    private int unseenMessages = 0;

    private boolean dataSet = false;



    private final List<MessagesList> messagesLists = new ArrayList<>();

    private MessagesAdapter messagesAdapter;

    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_chat);

        databaseReference
                = FirebaseDatabase
                .getInstance()
                .getReferenceFromUrl("https://prm392-52326-default-rtdb.firebaseio.com/");

        final ImageView userProfilePic = findViewById(R.id.userProfilePic);

        messagesRecyclerView = findViewById(R.id.messagesRecyclerView);

        mobile = getIntent().getStringExtra("mobile");
        email = getIntent().getStringExtra("email");
        name = getIntent().getStringExtra("name");

        messagesRecyclerView.setHasFixedSize(true);
        messagesRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        //set adapter to recycle view
        messagesAdapter = new MessagesAdapter(messagesLists, MainChatActivity.this);

        messagesRecyclerView.setAdapter(messagesAdapter);

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                try{

                    final String profilePicUrl = snapshot.child("users-chat")
                            .child(mobile)
                            .child("profile_pic")
                            .getValue(String.class);

                    if (!profilePicUrl.isEmpty()){
                        Picasso.get().load(profilePicUrl).into(userProfilePic);
                    }

                }catch(Exception e){
                    Log.d("Error", e.getMessage());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                messagesLists.clear();
                unseenMessages = 0;
                lastMessage = "";
                chatKey = "";
                try{

                    for (DataSnapshot dataSnapshot : snapshot.child("users-chat").getChildren()){
                        final String getMobile = dataSnapshot.getKey();

                        dataSet = false;

                        if (!getMobile.equals(mobile)){
                            final String getName = dataSnapshot
                                    .child("name")
                                    .getValue(String.class);

                            final String getProfilePic = dataSnapshot
                                    .child("profile_pic")
                                    .getValue(String.class);

                            String lastMessage = "";
                            int unseenMessages = 0;

                            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    int getChatCounts = (int) snapshot.getChildrenCount();

                                    if (getChatCounts > 0){
                                        for (DataSnapshot dataSnapshot1 : snapshot.getChildren()){

                                            final String getKey = dataSnapshot1.getKey();

                                            chatKey = getKey;

                                            if (dataSnapshot1.hasChild("user_1")
                                                    && dataSnapshot1.hasChild("user_2")
                                                    && (dataSnapshot1.hasChild("messages"))){

                                                final String getUserOne = dataSnapshot1
                                                        .child("user_1")
                                                        .getValue(String.class);

                                                final String getUserTwo = dataSnapshot1
                                                        .child("user_2")
                                                        .getValue(String.class);

                                                if ((getUserOne.equals(getMobile) && getUserTwo.equals(mobile))
                                                        || (getUserOne.equals(mobile) && getUserTwo.equals(getMobile))){

                                                }
                                            }





                                        }
                                    }

                                    if (!dataSet){
                                        MessagesList messagesList = new MessagesList
                                                (getName, getMobile, lastMessage, getProfilePic, unseenMessages, chatKey);
                                        messagesLists.add(messagesList);
                                        messagesAdapter.updateData(messagesLists);
                                    }


                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });



                        }
                    }


                }catch(Exception e){
                    Log.d("Error",e.getMessage());
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
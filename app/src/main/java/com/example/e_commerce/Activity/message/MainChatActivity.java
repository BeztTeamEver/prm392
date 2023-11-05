package com.example.e_commerce.Activity.message;

<<<<<<< HEAD
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

=======
>>>>>>> 0ac2fa701b53a1e6b3db6b627ebb80a5c45f80f7
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

<<<<<<< HEAD
import com.example.e_commerce.Common.ApplicationUser;
import com.example.e_commerce.Model.User;
=======
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

>>>>>>> 0ac2fa701b53a1e6b3db6b627ebb80a5c45f80f7
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

<<<<<<< HEAD
    private final List<MessagesList> messagesLists = new ArrayList<>();
    DatabaseReference databaseReference;
=======
>>>>>>> 0ac2fa701b53a1e6b3db6b627ebb80a5c45f80f7
    private String mobile;
    private String email;
    private String name;
    private RecyclerView messagesRecyclerView;
<<<<<<< HEAD
    private String chatKey = "";
    private String lastMessage = "";
    private int unseenMessages = 0;
    private boolean dataSet = false;
    private MessagesAdapter messagesAdapter;

=======

    private String chatKey = "";

    private String lastMessage = "";
    private int unseenMessages = 0;

    private boolean dataSet = false;



    private final List<MessagesList> messagesLists = new ArrayList<>();

    private MessagesAdapter messagesAdapter;

    DatabaseReference databaseReference;

>>>>>>> 0ac2fa701b53a1e6b3db6b627ebb80a5c45f80f7
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_chat);

<<<<<<< HEAD
        messagesLists.clear();

=======
>>>>>>> 0ac2fa701b53a1e6b3db6b627ebb80a5c45f80f7
        databaseReference
                = FirebaseDatabase
                .getInstance()
                .getReferenceFromUrl("https://prm392-52326-default-rtdb.firebaseio.com/");

        final ImageView userProfilePic = findViewById(R.id.userProfilePic);

        messagesRecyclerView = findViewById(R.id.messagesRecyclerView);

<<<<<<< HEAD
        User currentUser = ApplicationUser.getUserFromSharedPreferences(this);

        mobile = currentUser.getPhone_number();
        email = currentUser.getEmail();
        name = currentUser.getUsername();
=======
        mobile = getIntent().getStringExtra("mobile");
        email = getIntent().getStringExtra("email");
        name = getIntent().getStringExtra("name");
>>>>>>> 0ac2fa701b53a1e6b3db6b627ebb80a5c45f80f7

        messagesRecyclerView.setHasFixedSize(true);
        messagesRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        //set adapter to recycle view
        messagesAdapter = new MessagesAdapter(messagesLists, MainChatActivity.this);

        messagesRecyclerView.setAdapter(messagesAdapter);

<<<<<<< HEAD

        //set up user info in chat screen
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                try {
=======
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                try{
>>>>>>> 0ac2fa701b53a1e6b3db6b627ebb80a5c45f80f7

                    final String profilePicUrl = snapshot.child("users-chat")
                            .child(mobile)
                            .child("profile_pic")
                            .getValue(String.class);

<<<<<<< HEAD
                    if (!profilePicUrl.isEmpty()) {
                        Picasso.get().load(profilePicUrl).into(userProfilePic);
                    }

                } catch (Exception e) {
=======
                    if (!profilePicUrl.isEmpty()){
                        Picasso.get().load(profilePicUrl).into(userProfilePic);
                    }

                }catch(Exception e){
>>>>>>> 0ac2fa701b53a1e6b3db6b627ebb80a5c45f80f7
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
<<<<<<< HEAD
                messagesLists.clear(); // Clear the existing list to prevent duplicates

                for (DataSnapshot dataSnapshot : snapshot.child("users-chat").getChildren()) {
                    final String receiveMobile = dataSnapshot.getKey();
                    dataSet = false;

                    if (!receiveMobile.equals(mobile)) {
                        final String receiveName = dataSnapshot.child("name").getValue(String.class);
                        final String receiveProfilePic = dataSnapshot.child("profile_pic").getValue(String.class);

                        // Check if the user already exists in the list by comparing receiveMobile
                        boolean userExists = false;
                        for (MessagesList messagesList : messagesLists) {
                            if (messagesList.getMobile().equals(receiveMobile)) {
                                // User already exists, update their information
                                userExists = true;
                                messagesList.setName(receiveName);
                                messagesList.setProfilePic(receiveProfilePic);
                                break;
                            }
                        }

                        if (!userExists) {
                            MessagesList messagesList = new MessagesList(
                                    receiveName, receiveMobile, lastMessage, receiveProfilePic, unseenMessages, chatKey);
                            messagesLists.add(messagesList);
                        }
                    }
                }

                messagesAdapter.updateData(messagesLists);
=======

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


>>>>>>> 0ac2fa701b53a1e6b3db6b627ebb80a5c45f80f7
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
<<<<<<< HEAD
                // Handle database error
            }
        });
    }

=======

            }
        });
    }
>>>>>>> 0ac2fa701b53a1e6b3db6b627ebb80a5c45f80f7
}
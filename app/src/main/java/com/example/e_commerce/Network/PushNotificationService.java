package com.example.e_commerce.Network;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
<<<<<<< HEAD
import android.util.Log;
=======
>>>>>>> 0ac2fa701b53a1e6b3db6b627ebb80a5c45f80f7

import androidx.annotation.NonNull;
import androidx.core.app.NotificationManagerCompat;

<<<<<<< HEAD
import com.example.e_commerce.Common.ApplicationUser;
import com.example.e_commerce.Model.NotificationModel;
import com.example.e_commerce.Model.User;
import com.example.e_commerce.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
=======
import com.example.e_commerce.R;
>>>>>>> 0ac2fa701b53a1e6b3db6b627ebb80a5c45f80f7
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class PushNotificationService extends FirebaseMessagingService {

    @SuppressLint("NewApi")
    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        String title = remoteMessage.getNotification().getTitle();
        String text = remoteMessage.getNotification().getBody();
<<<<<<< HEAD

        NotificationModel notificationModelFirebase
                = new NotificationModel(title, text);
        addNotificationToFireBase(notificationModelFirebase);

=======
>>>>>>> 0ac2fa701b53a1e6b3db6b627ebb80a5c45f80f7
        String CHANNEL_ID = "MESSAGE";
        CharSequence name;
        NotificationChannel channel = new NotificationChannel(
                CHANNEL_ID,
<<<<<<< HEAD
                "Message NotificationModel",
=======
                "Message Notification",
>>>>>>> 0ac2fa701b53a1e6b3db6b627ebb80a5c45f80f7
                NotificationManager.IMPORTANCE_HIGH);
        getSystemService(NotificationManager.class).createNotificationChannel(channel);
        Context context;
        Notification.Builder notification = new Notification.Builder(this, CHANNEL_ID)
                .setContentTitle(title)
                .setContentText(text)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setAutoCancel(true);
        NotificationManagerCompat.from(this).notify(1, notification.build());
        super.onMessageReceived(remoteMessage);
    }
<<<<<<< HEAD

    private void addNotificationToFireBase(NotificationModel notificationModel) {
        User currentUser = ApplicationUser.getUserFromSharedPreferences(this);

        try {
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://prm392-52326-default-rtdb.firebaseio.com/");
            String mobileTxt = currentUser.getPhone_number();
            String titleTxt = notificationModel.getTitle();
            String contentTxt = notificationModel.getContent();

            // Get the current timestamp
            String timestamp = String.valueOf(System.currentTimeMillis());

            // Create a reference to the "notification" node for the user
            DatabaseReference userNotificationRef = databaseReference.child("notification").child(mobileTxt);

            // Create a child node with the timestamp as the key
            DatabaseReference notificationTimestampRef = userNotificationRef.child(timestamp);

            // Set the "title" and "content" under the timestamped entry
            notificationTimestampRef.child("title").setValue(titleTxt);
            notificationTimestampRef.child("content").setValue(contentTxt);
        } catch (Exception e) {
            Log.d("DatabaseError", e.getMessage());
        }
    }


    /*private void addNotificationToFireBase(NotificationModel notificationModel){
        User currentUser = ApplicationUser.getUserFromSharedPreferences(this);
        try {

            DatabaseReference databaseReference
                    = FirebaseDatabase
                    .getInstance()
                    .getReferenceFromUrl("https://prm392-52326-default-rtdb.firebaseio.com/");


            String mobileTxt = currentUser.getPhone_number();
            String titleTxt = notificationModel.getTitle();
            String contentTxt = notificationModel.getContent();

            DatabaseReference mobileRef = databaseReference.child("notification").child(mobileTxt);

            mobileRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (!snapshot.exists()) {
                        // The mobile number doesn't exist, so create a new entry
                        String timestamp = String.valueOf(System.currentTimeMillis());
                        mobileRef.child(timestamp).child("title").setValue(titleTxt);
                        mobileRef.child(timestamp).child("content").setValue(contentTxt);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    // Handle any database error if needed
                }
            });


        } catch (Exception e) {
            Log.d("DatabaseError", e.getMessage());
        }


    }*/
=======
>>>>>>> 0ac2fa701b53a1e6b3db6b627ebb80a5c45f80f7
}

package com.example.e_commerce.Common;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.e_commerce.Model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ApplicationUser {

    public static String userToJson(User user) {
        Gson gson = new Gson();
        return gson.toJson(user);
    }

    public static User userFromJson(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, User.class);
    }


    public static void saveCurrentUser(Context context, User user){

        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            return;
                        }

                        // Get new FCM registration token
                        String token = task.getResult();

                        User existingUser = getUserFromSharedPreferences(context);

                        //if (existingUser != null) return;

                        SharedPreferences preferences = context.getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.clear();

                        // Serialize the User object to a JSON string
                        String userJson = userToJson(user);

                        // Save the JSON string in SharedPreferences
                        editor.putString("userJson", userJson);
                        editor.putString("token", token);
                        editor.apply();

                        // Log and toast
                        System.out.println("TOKEN " + token);
                    }
                });


    }

    public static User getUserFromSharedPreferences(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
        String userJson = preferences.getString("userJson", null);

        if (userJson != null) {
            // Deserialize the JSON string to a User object
            return userFromJson(userJson);
        } else {
            // Handle the case where the User data doesn't exist in SharedPreferences
            return null;
        }
    }

    public static String getDeviceToken(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
        String token = preferences.getString("token", null);

        if (token != null) {
            // Deserialize the JSON string to a User object
            return token;
        } else {
            // Handle the case where the User data doesn't exist in SharedPreferences
            return null;
        }
    }

    public static String getCurrentMobile(Context context){
        /*SharedPreferences preferences = context.getSharedPreferences("MyPreferencesChat", Context.MODE_PRIVATE);
        return preferences.getString("mobileChat", null);*/
        User user = getUserFromSharedPreferences(context);
        return user.getPhone_number();
    }




    public static String readJSONFromAssets(Context context, String fileName) {
        AssetManager assetManager = context.getAssets();
        try {
            // Open the JSON file
            InputStream inputStream = assetManager.open(fileName);

            // Read the JSON data
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder stringBuilder = new StringBuilder();
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }

            // Close the input stream
            inputStream.close();

            // Return the JSON data as a string
            return stringBuilder.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static User parseUserFromJsonInAsset(Context context, String fileName) {
        String jsonContent = readJSONFromAssets(context, fileName);

        if (jsonContent != null) {
            Gson gson = new Gson();
            return gson.fromJson(jsonContent, User.class);
        } else {
            // Handle the case where reading the JSON file failed
            Log.d("Erro while read json", "ApplicationUser,java");
            return null;
        }
    }

    public static void registerUserToFireBase(User user) {

        try {

            DatabaseReference databaseReference
                    = FirebaseDatabase
                    .getInstance()
                    .getReferenceFromUrl("https://prm392-52326-default-rtdb.firebaseio.com/");

            String mobileTxt = user.getPhone_number();
            String emailTxt = user.getEmail();
            String nameTxt = user.getFullname();
            String avatarUrl = user.getAvatarUrl();

            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.child("users-chat").hasChild(mobileTxt)) {
                        return;

                    } else {
                        databaseReference.child("users-chat").child(mobileTxt)
                                .child("email").setValue(emailTxt);

                        databaseReference.child("users-chat").child(mobileTxt)
                                .child("name").setValue(nameTxt);

                        databaseReference.child("users-chat").child(mobileTxt)
                                .child("profile_pic").setValue(avatarUrl);
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        } catch (Exception e) {
            Log.d("DatabaseError", e.getMessage());
        }
    }

}

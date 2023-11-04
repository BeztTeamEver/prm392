package com.example.e_commerce.Common;

import android.content.Context;
import android.content.SharedPreferences;

public class ApplicationUser {

    public static String getCurrentMobile(Context context){
        SharedPreferences preferences = context.getSharedPreferences("MyPreferencesChat", Context.MODE_PRIVATE);
        return preferences.getString("mobileChat", null);
    }

}

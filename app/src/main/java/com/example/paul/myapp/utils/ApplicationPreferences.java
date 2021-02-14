package com.example.paul.myapp.utils;


import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;

import com.example.paul.myapp.view.SplashActivity;

import java.util.Map;
import java.util.Set;

public class ApplicationPreferences  {

    private static ApplicationPreferences appPref;
    private static SharedPreferences sharePref;

    private static final String USER_ID="id";

    private ApplicationPreferences() {}

    public static ApplicationPreferences getInstance(Activity activity) {

        if (appPref == null) {
            sharePref = activity.getSharedPreferences("sharePref.calendar",Context.MODE_PRIVATE);
            appPref =new ApplicationPreferences();
        }
        return appPref;
    }

    public void deleteUsername(){

        SharedPreferences.Editor editor = sharePref.edit();
        editor.remove(USER_ID);
        editor.apply();
    }


    public void setUserId(String id) {

        SharedPreferences.Editor editor = sharePref.edit();
        editor.putString(USER_ID,id);
        editor.apply();
    }

    public String getUserId() {
        return sharePref.getString(USER_ID,"");

    }

}

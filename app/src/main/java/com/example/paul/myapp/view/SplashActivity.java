package com.example.paul.myapp.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.example.paul.myapp.R;
import com.example.paul.myapp.utils.ApplicationPreferences;


public class SplashActivity extends Activity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        if (isUserLoggedIn()) {

            openMainActivity();
        } else {
            openLogin();
        }
    }

    private void openLogin() {

        Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();

    }

    private void openMainActivity() {

        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }


    boolean isUserLoggedIn() {

        ApplicationPreferences appref = ApplicationPreferences.getInstance(this);

        return !appref.getUserId().equals("");


    }


}



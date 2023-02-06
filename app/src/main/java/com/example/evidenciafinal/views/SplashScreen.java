package com.example.evidenciafinal.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.evidenciafinal.R;

import java.util.Timer;
import java.util.TimerTask;


public class SplashScreen extends AppCompatActivity {
    Timer timer;
    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        //Get Shared Preferences
        SharedPreferences preferences = getApplicationContext().getSharedPreferences("UserPreferences", Context.MODE_PRIVATE);
        name = preferences.getString("name", null);
        //To go to the next activity after some seconds
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if(name == null){
                    //FirstLogin
                    StartActivity(Registration.class);

                }else {
                    //Not First Log-In
                    StartActivity(Dashboard.class);
                }
            }
        }, 5000);

    }

    private void StartActivity(Class<?> intentClass) {
        Intent intent = new Intent(SplashScreen.this, intentClass);
        startActivity(intent);
        finish();
    }
}
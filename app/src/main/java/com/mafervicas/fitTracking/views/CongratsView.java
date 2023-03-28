package com.mafervicas.fitTracking.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.mafervicas.fitTracking.R;

import java.util.Timer;
import java.util.TimerTask;

public class CongratsView extends AppCompatActivity {
    Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_congrats_view);
        //To go to the next activity after some seconds
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                    StartActivity(Dashboard.class);
            }
        }, 5000);
    }
    private void StartActivity(Class<?> intentClass) {
        Intent intent = new Intent(CongratsView.this, intentClass);
        startActivity(intent);
        finish();
    }
}
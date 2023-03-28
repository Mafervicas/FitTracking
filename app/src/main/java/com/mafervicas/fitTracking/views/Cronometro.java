package com.mafervicas.fitTracking.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Chronometer;
import android.widget.ImageButton;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.mafervicas.fitTracking.R;

public class Cronometro extends AppCompatActivity {
    //Inicializamos variables
    private Chronometer chronometer;
    private boolean corriendo; //F o V si está en funcionamiento
    private long pausaOffset; //Diferencia entre inicio y pausa
    ImageButton buttonReturnDashboard;

    //For adView
    private AdView mAdView;

    //BackButton override
    @Override
    public void onBackPressed() {
       openMainActivity();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cronometro);

        //Encontrar por id
        chronometer = findViewById(R.id.chronometer);
        buttonReturnDashboard = findViewById(R.id.includedButton).findViewById(R.id.ibLogo);
        mAdView = findViewById(R.id.adView);

        //For AdView - Initialize
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(@NonNull InitializationStatus initializationStatus) {

            }
        });
        //Request
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        //Button Dashboard
        buttonReturnDashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMainActivity();
            }
        });

    }

    public void openMainActivity(){
        Intent intent = new Intent(this, Dashboard.class);
        startActivity(intent);
    }

    public void iniciarChronometer(View v){
        //Primero verificamos que no esté corriendo
        if (!corriendo){
            chronometer.setBase(SystemClock.elapsedRealtime() - pausaOffset);
            chronometer.start();//Para iniciarlo
            corriendo = true ;

        }

    }
    public void pausarChronometer(View v){
        if (corriendo){
            chronometer.stop();
            pausaOffset = SystemClock.elapsedRealtime() - chronometer.getBase();
            corriendo = false;
        }


    }
    public void resetChronometer(View v){
        chronometer.setBase(SystemClock.elapsedRealtime());
        pausaOffset = 0;

    }
}
package com.example.evidenciafinal.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Chronometer;
import android.widget.ImageButton;

import com.example.evidenciafinal.R;
import com.example.evidenciafinal.views.Dashboard;

public class Cronometro extends AppCompatActivity {
    //Inicializamos variables
    private Chronometer chronometer;
    private boolean corriendo; //F o V si está en funcionamiento
    private long pausaOffset; //Diferencia entre inicio y pausa
    ImageButton buttonReturnDashboard;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cronometro);

        //Encontrar por id
        chronometer = findViewById(R.id.chronometer);
        buttonReturnDashboard = findViewById(R.id.includedButton).findViewById(R.id.ibLogo);

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
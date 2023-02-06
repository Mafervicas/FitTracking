package com.example.evidenciafinal.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.evidenciafinal.R;

import java.text.DecimalFormat;

public class Dashboard extends AppCompatActivity {
    //Starting with the initialization of variables
    AppCompatButton btImc, btWater, btCalories;
    TextView tvBienvenidaDashbord;
    ImageButton ibCronometro, ibTracker, ibUpdate, ibRecomendaciones, ibEstadisticas;
    private static final DecimalFormat df = new DecimalFormat("0.0");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        onInit();

        //Connect XML Components
        ibCronometro = (ImageButton) findViewById(R.id.ibCronometro);
        ibRecomendaciones = (ImageButton) findViewById(R.id.ibRecomendaciones);

        //For buttons
        ibCronometro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StartActivity(Cronometro.class);
            }
        });

        ibRecomendaciones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StartActivity(Recomendaciones.class);
            }
        });

    }

    private void onInit() {
        //Connect XML Components
        btImc = (AppCompatButton) findViewById(R.id.btIMC);
        btWater = (AppCompatButton) findViewById(R.id.btWater);
        btCalories = (AppCompatButton) findViewById(R.id.btCalories);
        tvBienvenidaDashbord = (TextView) findViewById(R.id.tvBienvenidaDashbord);

        //Get Shared Preferences
        SharedPreferences preferences = getApplicationContext().getSharedPreferences("UserPreferences", Context.MODE_PRIVATE);
        String name = preferences.getString("name", null);
        double water = Double.parseDouble(preferences.getString("water", null));
        double imc = Double.parseDouble(preferences.getString("imc", null));
        double kcals = Double.parseDouble(preferences.getString("kcals","0"));

        //Put on text
        tvBienvenidaDashbord.setText("Hola nuevamente, " + name);
        btCalories.setText(df.format(kcals) + " kls");
        btWater.setText(df.format(water) + " lts");
        btImc.setText(df.format(imc) + " IMC ");

    }

    private void StartActivity(Class<?> intentClass) {
        Intent intent = new Intent(Dashboard.this, intentClass);
        startActivity(intent);
        finish();
    }
}
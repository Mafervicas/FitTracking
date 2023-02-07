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
import android.widget.Toast;

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
        ibUpdate = (ImageButton) findViewById(R.id.ibUpdate);
        ibTracker = (ImageButton) findViewById(R.id.ibTracker);

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

        ibUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StartActivity(NewInformation.class);
            }
        });

        ibTracker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StartActivity(kcalTracker.class);
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
        double kcals = Double.parseDouble(preferences.getString("kcalsRestantes","0"));

        //Put on text
        tvBienvenidaDashbord.setText("Hola nuevamente, " + name);
        btCalories.setText(df.format(kcals) + " kls");
        btWater.setText(df.format(water) + " lts");
        btImc.setText(df.format(imc) + " IMC ");

        //For toast
        String caloriesToast = "Las calorías recomendadas para una pérdida saludable de peso para ti son: "+ df.format(kcals) + " kcals";
        String waterToast = "Gracias a la información que nos proporcionaste, los litros de agua que deberías estar tomando son: "+ df.format(water);
        String IMCResultado = getFinalIMC(imc);

        btCalories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Dashboard.this, caloriesToast, Toast.LENGTH_LONG).show();
            }
        });

        btWater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Dashboard.this, waterToast, Toast.LENGTH_LONG).show();
            }
        });

        btImc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Dashboard.this, "Según la OMS tu IMC se encuentra en "+ IMCResultado, Toast.LENGTH_SHORT).show();
            }
        });

    }

    private String getFinalIMC(double IMC) {
        //Condicionamos el saludable
        if(IMC<18.5){
            return "Insuficiencia ponderal";
        } else if (IMC>= 18.5 && IMC<= 24.99){
            return "Intervalo normal";
        } else if (IMC>=25.00 && IMC<=29.99){
            return "Pre-Obecidad";
        } else if (IMC>=30.00 && IMC<=34.99){
            return "Obesidad de clase I";
        } else if (IMC>=35.00 && IMC<=39.99){
            return "Obesidad de clase II";
        }
        else{
            return "Obesidad de clase III";
        }
    }

    private void StartActivity(Class<?> intentClass) {
        Intent intent = new Intent(Dashboard.this, intentClass);
        startActivity(intent);
        finish();
    }
}
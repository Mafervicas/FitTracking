package com.example.evidenciafinal.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.evidenciafinal.R;
import com.example.evidenciafinal.model.DatosDB;
import com.google.android.material.textfield.TextInputEditText;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class kcalTracker extends AppCompatActivity {
    //Starting with the initialization of variables
    AppCompatButton btCalPorDay, btCalRestantes ;
    Button btGuardar, btFinDia;
    ImageButton buttonReturnDashboard;
    TextInputEditText tiCalsIngresadas;
    private static final DecimalFormat df = new DecimalFormat("0.0");
    Double newCalories, kcals, kcalsRestantes;
    ProgressBar myProgressBar;
    Integer goalAccomplished;

    //SharedPreferences
    SharedPreferences sp;
    //Get Shared Preferences

    //Call db
    DatosDB myDb;

    //To get the date
    Date date = Calendar.getInstance().getTime();
    DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
    String strDate = dateFormat.format(date);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kcal_tracker);

        //Connect XML Components
        btCalPorDay = (AppCompatButton) findViewById(R.id.btCalPorDay);
        btCalRestantes = (AppCompatButton) findViewById(R.id.btCalRestantes);
        btGuardar = (Button) findViewById(R.id.btGuardar);
        btFinDia = (Button) findViewById(R.id.btFinDia);
        myProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        tiCalsIngresadas = (TextInputEditText) findViewById(R.id.tiCalsIngresadas);

        onInit();

        //Call DB constructor Llama el constructor
        myDb = new DatosDB(this);

        //Button Dashboard
        buttonReturnDashboard = findViewById(R.id.includedButton).findViewById(R.id.ibLogo);
        buttonReturnDashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMainActivity();
            }
        });

        //BtGuardar
        btGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String CalsIngresadas = tiCalsIngresadas.getText().toString();
                Double CalsIngresadasDouble = Double.parseDouble(CalsIngresadas);
                newCalories = kcalsRestantes - CalsIngresadasDouble;
                //Add changes to Shared preferences
                sp = getSharedPreferences("UserPreferences", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.putString("kcalsRestantes", String.valueOf(newCalories));
                editor.commit();
                //Return To Main Activity
                openMainActivity();
            }
        });

        //BtFinDia
        btFinDia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myProgressBar.setVisibility(View.VISIBLE);
                //To know if you accomplished the goal
                goalAccomplished = 0;
                if (kcalsRestantes <= kcals){
                    goalAccomplished = 1;
                }
                //Add data to bd
                boolean addInformation = myDb.insertKcalData(strDate, kcals, kcalsRestantes, goalAccomplished);
                if(addInformation = true) {
                     //Make invisible progress bar
                    myProgressBar.setVisibility(View.INVISIBLE);
                    //Return again to the kcals needed
                    sp = getSharedPreferences("UserPreferences", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString("kcalsRestantes", String.valueOf(kcals));
                    editor.commit();
                    //Make Toast & Return
                    Toast.makeText(kcalTracker.this, "Datos agregados Correctamente", Toast.LENGTH_LONG).show();
                    openMainActivity();

                } else {
                    Toast.makeText(kcalTracker.this, "Problemas al insertar", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void onInit() {
        SharedPreferences preferences = getApplicationContext().getSharedPreferences("UserPreferences", Context.MODE_PRIVATE);
        kcals = Double.parseDouble(preferences.getString("kcals","0"));
        kcalsRestantes = Double.parseDouble(preferences.getString("kcalsRestantes", String.valueOf(kcals)));

        //Put on text
        btCalPorDay.setText(df.format(kcals) + " kls");
        btCalRestantes.setText(df.format(kcalsRestantes) + " kls");
    }

    public void openMainActivity(){
        Intent intent = new Intent(this, Dashboard.class);
        startActivity(intent);
    }
}
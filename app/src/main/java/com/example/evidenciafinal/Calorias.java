package com.example.evidenciafinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.evidenciafinal.views.Dashboard;

public class Calorias extends AppCompatActivity {

    //Llamamos a la base de datos
    DevDB myDb;


    private ImageButton imageButton, btCalcula;

    //Inicializamos
    Button btGuardarKcal, btGuardarDB; //Primero botón de Guardar kcal en Preferences
    EditText etKcalTotal, kcalIngreso, etKcalRestantes;
    int kcalsQueSeGuardaran, restaKcals, totalKcals, acumulacionkCals;


    //Agregamos Preferences
    private SharedPreferences mSharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calorias);
        getSupportActionBar().hide(); //Ocultamos la barra de default

        //Para regresar a Home
        imageButton = (ImageButton) findViewById(R.id.ibBack);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMainActivity();
            }
        });


        //Encontrar por Id
        etKcalTotal = (EditText) findViewById(R.id.etKcalTotal);
        btGuardarKcal = (Button) findViewById(R.id.btGuardarKcal);
        kcalIngreso = (EditText) findViewById(R.id.kcalIngreso);
        btCalcula = (ImageButton) findViewById(R.id.btCalcula);
        etKcalRestantes = (EditText) findViewById(R.id.etKcalRestantes);
        btGuardarDB = (Button) findViewById(R.id.btGuardarDB);
        //Llama el constructor
        myDb = new DevDB(this);

        //Añadir a base de datos
        AddData();

        //Para recuperar la información de Share preferences 1
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        int in1= prefs.getInt("kcalsQueSeGuardaran", 0);
        etKcalTotal.setText(""+in1);



        //Para guardar como preferences con el botón de guardar
        btGuardarKcal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kcalsQueSeGuardaran = Integer.parseInt(etKcalTotal.getText().toString());

                //Para guardar la información
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(Calorias.this);
                SharedPreferences.Editor editor= prefs.edit();

                editor.putInt("kcalsQueSeGuardaran", kcalsQueSeGuardaran);
                editor.apply();

                //Avisamos
                Toast.makeText(Calorias.this, "Calorías Guardadas",Toast.LENGTH_LONG).show();
            }
        });

        //Para restar calorias con el botón Calcula
        btCalcula.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                restaKcals = Integer.parseInt(etKcalRestantes.getText().toString());
                acumulacionkCals=  Integer.parseInt(kcalIngreso.getText().toString());
                totalKcals = acumulacionkCals - restaKcals;
                kcalIngreso.setText(""+totalKcals);
                etKcalRestantes.setText("");

                //Avisamos
                Toast.makeText(Calorias.this, "Calorías Restadas, agrega a Kcal totales",Toast.LENGTH_LONG).show();

            }
        });

    }

    public void openMainActivity(){
        Intent intent = new Intent(this, Dashboard.class);
        startActivity(intent);
    }

    public void AddData(){
        btGuardarDB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Convertimos
                //Agarramos texto
                boolean seInserta= myDb.insertarDataCalorias(etKcalTotal.getText().toString(), kcalIngreso.getText().toString());
                if(seInserta = true)
                    Toast.makeText(Calorias.this, "Agregado correctamente",Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(Calorias.this, "Problemas al insertar",Toast.LENGTH_LONG).show();
            }
        });
    }
}
package com.example.evidenciafinal.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.evidenciafinal.R;
import com.example.evidenciafinal.model.DatosDB;
import com.google.android.material.textfield.TextInputEditText;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Registration extends AppCompatActivity {
    //Starting with the initialization of variables
    private Button btnSiguiente;
    DatosDB myDb;
    TextInputEditText tiNombre, tiEdad, tiAltura, tiPeso;
    SharedPreferences sp;
    ProgressBar myProgressBar;

    //String with the information of the water
    private String stringQueMandaremos;
    public static final String STRING_QUE_MANDAREMOS = "com.example.evidenciafinal.STRING_QUE_MANDAREMOS";

    //To get the date
    Date date = Calendar.getInstance().getTime();
    DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
    String strDate = dateFormat.format(date);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        //Connect XML Components
        tiNombre = (TextInputEditText) findViewById(R.id.tiNombre);
        tiPeso = (TextInputEditText) findViewById(R.id.tiPeso);
        tiAltura = (TextInputEditText) findViewById(R.id.tiAltura);
        tiEdad = (TextInputEditText) findViewById(R.id.tiEdad);
        btnSiguiente = (Button) findViewById(R.id.btnSiguiente);
        myProgressBar = (ProgressBar) findViewById(R.id.progressBar);

        //Call DB constructor Llama el constructor
        myDb = new DatosDB(this);

        //When a button is clicked
        btnSiguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Convert to text
                String peso = tiPeso.getText().toString();
                String altura = tiAltura.getText().toString();
                String edad = tiEdad.getText().toString();
                String nombre = tiNombre.getText().toString();
                String nameStr = tiNombre.getText().toString();

                if(peso.isEmpty() || altura.isEmpty() || edad.isEmpty() || nombre.isEmpty()){
                    //Missing fields
                    Toast.makeText(Registration.this, "Favor de llenar todos los campos",Toast.LENGTH_LONG).show();
                } else {
                    //Show ProgressBar
                    myProgressBar.setVisibility(View.VISIBLE);

                    //Convert to correct data form
                    Double pesoDouble = Double.parseDouble(peso);
                    Double alturaDouble = Double.parseDouble(altura);
                    Integer edadInt = Integer.parseInt(edad);

                    //We calculate IMC
                    Double IMC = pesoDouble / (alturaDouble * alturaDouble);
                    Double Ingesta = (35 * pesoDouble)/100;

                    //We calculate KCals
                    Double kcalsInicales = (655 + (9.6 * pesoDouble) + (1.8 * alturaDouble) - (4.7 * edadInt));
                    Double kcalsFinales = kcalsInicales * 1.55;

                    //Add name to Shared preferences
                    sp = getSharedPreferences("UserPreferences", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString("name", nameStr);
                    editor.putString("imc", IMC.toString());
                    editor.putString("water", Ingesta.toString());
                    editor.putString("kcals", kcalsFinales.toString());
                    editor.commit();
                    addData(pesoDouble, alturaDouble, edadInt, IMC, Ingesta, kcalsFinales);

                }

            }
        });
    }

    public void addData(Double pesoDouble, Double alturaDouble, Integer edadInt, Double IMC, Double ingesta, Double kcals) {

        //Add information to the db
        boolean addInformation= myDb.insertMainData(strDate, pesoDouble, alturaDouble, edadInt, IMC, ingesta, kcals);
        if(addInformation = true) {
            //Make invisible progress bar
            myProgressBar.setVisibility(View.INVISIBLE);
            Toast.makeText(Registration.this, "Agregado correctamente", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, Dashboard.class);
            intent.putExtra(STRING_QUE_MANDAREMOS, stringQueMandaremos);
            startActivity(intent);

        } else {
            Toast.makeText(Registration.this, "Problemas al insertar", Toast.LENGTH_LONG).show();
        }
    }
}
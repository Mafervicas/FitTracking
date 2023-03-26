package com.mafervicas.fitTracking.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.mafervicas.fitTracking.R;
import com.mafervicas.fitTracking.model.DatosDB;
import com.google.android.material.textfield.TextInputEditText;
import com.mafervicas.fitTracking.utils.Constants;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class NewInformation extends AppCompatActivity {

    //Starting with the initialization of variables
    private Button btnSiguiente;
    DatosDB myDb;
    TextInputEditText tiEdad, tiAltura, tiPeso;
    SharedPreferences sp;
    ProgressBar myProgressBar;
    Spinner s;
    ImageButton buttonReturnDashboard;

    String exerciseFreq;
    Integer exerciseMultiply;


    //To get the date
    Date date = Calendar.getInstance().getTime();
    DateFormat dateFormat = new SimpleDateFormat(Constants.PATTERN_DATE);
    String strDate = dateFormat.format(date);

    //BackButton override
    @Override
    public void onBackPressed() {
        openMainActivity();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_information);

        //Connect XML Components
        tiPeso = (TextInputEditText) findViewById(R.id.tiPeso);
        tiAltura = (TextInputEditText) findViewById(R.id.tiAltura);
        tiEdad = (TextInputEditText) findViewById(R.id.tiEdad);
        btnSiguiente = (Button) findViewById(R.id.btnGuardar);
        myProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        s = (Spinner) findViewById(R.id.spinnerExercise);

        //Spinner
        s.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                exerciseMultiply = adapterView.getSelectedItemPosition();
                exerciseFreq = adapterView.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //Button Dashboard
        buttonReturnDashboard = findViewById(R.id.includedButton).findViewById(R.id.ibLogo);
        buttonReturnDashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMainActivity();
            }
        });

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

                if(peso.isEmpty() || altura.isEmpty() || edad.isEmpty()){
                    //Missing fields
                    Toast.makeText(NewInformation.this, Constants.COMPLETE_INFO,Toast.LENGTH_LONG).show();
                } else {
                    //Show ProgressBar
                    myProgressBar.setVisibility(View.VISIBLE);

                    //Convert to correct data form
                    Double pesoDouble = Double.parseDouble(peso);
                    Double alturaDouble = Double.parseDouble(altura);
                    Integer edadInt = Integer.parseInt(edad);

                    //We calculate IMC
                    Double IMC = pesoDouble / (alturaDouble * alturaDouble);
                    Double Ingesta = (35 * pesoDouble)/1000;

                    //Get how much to multiply because exercise;
                    Double valueExercise = getInfoExercise(exerciseMultiply);

                    //We calculate KCals
                    Double kcalsInicales = (655 + (9.6 * pesoDouble) + (1.8 * alturaDouble) - (4.7 * edadInt));
                    Double kcalsFinales = kcalsInicales * valueExercise;


                    //Add name to Shared preferences
                    sp = getSharedPreferences("UserPreferences", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString("imc", IMC.toString());
                    editor.putString("water", Ingesta.toString());
                    editor.putString("kcals", kcalsFinales.toString());
                    editor.putString("freqEjercicio", exerciseFreq);
                    editor.commit();
                    addData(pesoDouble, alturaDouble, edadInt, IMC, Ingesta, kcalsFinales);

                }

            }
        });
    }

    private Double getInfoExercise(Integer exerciseMultiply) {
        if (exerciseMultiply == 1){
            return 1.2;
        } else if(exerciseMultiply == 2){
            return 1.375;
        } else if (exerciseMultiply == 3){
            return 1.55;
        } else if (exerciseMultiply == 4){
            return 1.72;
        } else {
            return 1.9;
        }
    }

    public void addData(Double pesoDouble, Double alturaDouble, Integer edadInt, Double IMC, Double ingesta, Double kcals) {

        //Add information to the db
        boolean addInformation= myDb.insertMainData(strDate, pesoDouble, alturaDouble, edadInt, IMC, ingesta, kcals, exerciseFreq);
        if(addInformation == true) {
            //Make invisible progress bar
            myProgressBar.setVisibility(View.INVISIBLE);
            Toast.makeText(NewInformation.this, Constants.SUCCESS_DATA, Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, Dashboard.class);
            startActivity(intent);

        } else {
            Toast.makeText(NewInformation.this, Constants.ERROR_MODAL, Toast.LENGTH_LONG).show();
        }
    }

    public void openMainActivity(){
        Intent intent = new Intent(this, Dashboard.class);
        startActivity(intent);
    }
}
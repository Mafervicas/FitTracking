package com.example.evidenciafinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.evidenciafinal.views.Dashboard;

import java.text.DecimalFormat;

public class Calculos extends AppCompatActivity {

    //Inicializamos
    private ImageButton btSiguiente;
    private ImageButton imageButton;
    private ImageButton btCalcula;

    DevDB myDb;

    EditText etPeso, etAltura, etEdad;
    TextView tvTuIMC, tvSaludable;

    //String que pase la ingesta
    private String stringQueMandaremos;
    public static final String STRING_QUE_MANDAREMOS = "com.example.evidenciafinal.STRING_QUE_MANDAREMOS";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculos);
        getSupportActionBar().hide(); //Ocultamos la barra de default

        //Para el Botón de ir a IngestaAgua
        btSiguiente = (ImageButton)findViewById(R.id.btOtrosCalculos);
        btSiguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openIngesta();
            }
        });

        //Para el Botón de ir a Home
        imageButton = (ImageButton) findViewById(R.id.ibBack);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMainActivity();
            }
        });

        //Conectamos con componentes en XML
        etAltura = (EditText) findViewById(R.id.etAltura);
        etPeso = (EditText) findViewById(R.id.etPeso);
        etEdad = (EditText) findViewById(R.id.etPeso);
        btCalcula = (ImageButton) findViewById(R.id.ibCalula);
        tvTuIMC = (TextView) findViewById(R.id.textView7);
        tvSaludable = (TextView) findViewById(R.id.textView8);

        //Llama el constructor
        myDb = new DevDB(this);

        //Hablar al método cuando se da click al botón de add
        AddData();

        //Ocultamos Botón otros Cálculos
        btSiguiente.setVisibility(View.INVISIBLE);
    }
    public void openMainActivity(){
        Intent intent = new Intent(this, Dashboard.class);
        startActivity(intent);
    }

    public void openIngesta(){
        Intent intent = new Intent(this, IngestaAgua.class);
        intent.putExtra(STRING_QUE_MANDAREMOS, stringQueMandaremos);
        startActivity(intent);
    }

    //Metodo de añadir
    public void AddData(){
        btCalcula.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Convertimos
                String peso = etPeso.getText().toString();
                Double pesoDouble = Double.parseDouble(peso);
                String altura = etAltura.getText().toString();
                Double alturaDouble = Double.parseDouble(altura);
                String edad = etEdad.getText().toString();
                Integer edadInt = Integer.parseInt(edad);

                //Calculamos
                Double IMC = pesoDouble / (alturaDouble * alturaDouble);
                Double Ingesta = 35 * pesoDouble;

                //Agarramos texto y lo colocamos en DB
                boolean seInserta= myDb.insertarDataCalculos(pesoDouble, alturaDouble, edadInt, IMC, Ingesta);
                if(seInserta = true)
                    Toast.makeText(Calculos.this, "Agregado correctamente",Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(Calculos.this, "Problemas al insertar",Toast.LENGTH_LONG).show();

                //Ahora cambiamos TextViews
                  //Pero primero condicionamos el decimal a mostrar
                DecimalFormat df = new DecimalFormat("#.000");
                DecimalFormat df2 = new DecimalFormat("#.0");
                tvTuIMC.setText("IMC: " + df.format(IMC));
                stringQueMandaremos = df2.format(Ingesta);

                //Condicionamos el saludable
                if (IMC>= 18.5 && IMC<= 24.99){
                    tvSaludable.setText("Saludable");
                } else{
                    tvSaludable.setText("No Saludable");
                }

                //Aparecemos el botón
                btSiguiente.setVisibility(View.VISIBLE);
            }
        });
    }
}
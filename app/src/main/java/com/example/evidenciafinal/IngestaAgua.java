package com.example.evidenciafinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.evidenciafinal.views.Dashboard;

public class IngestaAgua extends AppCompatActivity{

    //Inicializamos
    private ImageButton imageButton;
    private ImageButton calculaMl;
    private TextView mililitros;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingesta_agua);
        getSupportActionBar().hide(); //Ocultamos la barra de default

        //Para el Botón de ir a Cronómetro
        imageButton = (ImageButton) findViewById(R.id.ibBack);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMainActivity();
            }
        });


        //Find by id
        mililitros = (TextView) findViewById(R.id.textView8);
        calculaMl = (ImageButton) findViewById(R.id.ibCalculaMl);

        //Creamos Objeto
        Intent intent = getIntent();
        String extraText = intent.getStringExtra(Calculos.STRING_QUE_MANDAREMOS);

        //Guardamos en TextView con Botón
        calculaMl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mililitros.setText(extraText + " ml");
            }
        });

    }

    //Regresar a Home
    public void openMainActivity(){
        Intent intent = new Intent(this, Dashboard.class);
        startActivity(intent);
    }
}
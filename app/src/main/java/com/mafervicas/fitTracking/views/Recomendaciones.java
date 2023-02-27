package com.mafervicas.fitTracking.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.mafervicas.fitTracking.R;

public class Recomendaciones extends AppCompatActivity {

    private Button btPinterest;
    String _url= "https://pin.it/wOsUkVv";
    private Button btYT;
    String _url2= "https://youtube.com/playlist?list=PLZxAgr_1BtHpTCgiWkY4-RIJmLpSqR_Po";
    private Button btFitmales;
    String _url3= "https://www.instagram.com/fitfemalesclub/";
    private Button btKatb;
    String _url4= "https://www.instagram.com/katb_fit/";
    private Button btCoach;
    String _url5= "https://www.instagram.com/katherineastor/";
    private Button btVitonica;
    String _url6= "https://www.vitonica.com/";
    private Button btWinnie;
    String _url7= "https://winniegana.com/";
    ImageButton buttonReturnDashboard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recomendaciones);

        //Button Dashboard
        buttonReturnDashboard = findViewById(R.id.includedButton).findViewById(R.id.ibLogo);
        buttonReturnDashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMainActivity();
            }
        });

        //Botón Pinterest
        btPinterest = findViewById(R.id.btPinterest);
        btPinterest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri _link= Uri.parse(_url);
                Intent i = new Intent(Intent.ACTION_VIEW,_link);
                startActivity(i);
            }
        });

        //Botón Youtube
        btYT = findViewById(R.id.btYT);
        btYT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri _link= Uri.parse(_url2);
                Intent i = new Intent(Intent.ACTION_VIEW,_link);
                startActivity(i);
            }
        });

        //Botón Fitmalesclub
        btFitmales = findViewById(R.id.btFitmales);
        btFitmales.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri _link= Uri.parse(_url3);
                Intent i = new Intent(Intent.ACTION_VIEW,_link);
                startActivity(i);
            }
        });

        //Botón Kath
        btKatb = findViewById(R.id.btKatb);
        btKatb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri _link= Uri.parse(_url4);
                Intent i = new Intent(Intent.ACTION_VIEW,_link);
                startActivity(i);
            }
        });

        //Coach Fav
        btCoach = findViewById(R.id.btCoach);
        btCoach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri _link= Uri.parse(_url5);
                Intent i = new Intent(Intent.ACTION_VIEW,_link);
                startActivity(i);
            }
        });

        //Vitónica
        btVitonica = findViewById(R.id.btVitonica);
        btVitonica.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri _link= Uri.parse(_url6);
                Intent i = new Intent(Intent.ACTION_VIEW,_link);
                startActivity(i);
            }
        });

        //Winnie
        btWinnie = findViewById(R.id.btWinnie);
        btWinnie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri _link= Uri.parse(_url7);
                Intent i = new Intent(Intent.ACTION_VIEW,_link);
                startActivity(i);
            }
        });

    }
    public void openMainActivity(){
        Intent intent = new Intent(this, Dashboard.class);
        startActivity(intent);
    }
}
package com.mafervicas.fitTracking.views;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.mafervicas.fitTracking.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.mafervicas.fitTracking.model.DatosDB;
import com.mafervicas.fitTracking.utils.Constants;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;


public class Stadistics extends AppCompatActivity {

    //Inicialization
    LineChart lineChart;
    LineData lineData;
    LineDataSet lineDataSet;
    ArrayList lineEntries;
    ImageButton buttonReturnDashboard;
    DatosDB myDb;
    TextView tvlbls, tvlbls2, tvlbls3;
    Timer timer;
    ImageView ivFelicidades, ivAnimo;
    Button btConsultaCalorias;
    private static final DecimalFormat df = new DecimalFormat("0.0");

    //BackButton override
    @Override
    public void onBackPressed() {
        openMainActivity();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stadistics);
        //Conect XML components
        tvlbls = (TextView) findViewById(R.id.tvlbls);
        tvlbls2 = (TextView) findViewById(R.id.tvlbls2);
        tvlbls3 = (TextView) findViewById(R.id.tvlbls3);
        ivFelicidades = (ImageView) findViewById(R.id.ivFelicidades);
        ivAnimo = (ImageView) findViewById(R.id.ivAnimo);
        btConsultaCalorias = (Button) findViewById(R.id.btConsultaCalorias);
        //Bring al variables needed for the graph
        lineChart = findViewById(R.id.lineChart);
        getEntries();
        lineDataSet = new LineDataSet(lineEntries, "");
        lineData = new LineData(lineDataSet);
        //Draw points over time
        lineChart.animateX(1900);
        //Set data
        lineChart.setData(lineData);
        //Set style
        lineDataSet.setColors(ColorTemplate.JOYFUL_COLORS);
        lineDataSet.setValueTextColor(Color.BLACK);
        lineDataSet.setValueTextSize(18f);
        lineDataSet.setFormSize(0f);
        lineDataSet.setDrawCircleHole(false);

        //Not show XAxis
        XAxis xAxis = lineChart.getXAxis();
        xAxis.setTextSize(1f);
        xAxis.setTextColor(Color.WHITE);

        //Not Show YAxis
        YAxis yAxis = lineChart.getAxisLeft();
        yAxis.setTextSize(12f);
        yAxis.setTextColor(Color.WHITE);
        YAxis yRight = lineChart.getAxisRight();
        yRight.setTextSize(12f);
        yRight.setTextColor(Color.WHITE);

        //Button Dashboard
        buttonReturnDashboard = findViewById(R.id.includedButton).findViewById(R.id.ibLogo);
        buttonReturnDashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMainActivity();
            }
        });

        //Button get all info
        btConsultaCalorias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor resa = myDb.showAllData();
                StringBuffer buffer = new StringBuffer();
                while (resa.moveToNext()) {
                    buffer.append("---- Fecha: " + resa.getString(1) + " ----\n");
                    buffer.append("Peso: " + resa.getDouble(2) + "\n");
                    buffer.append("IMC: " + df.format(resa.getDouble(5)) + "\n");
                    buffer.append("Kcals que debiste consumir: " + resa.getInt(7) + "\n");
                    buffer.append("Frequencia de ejercicio: " + resa.getString(8) + "\n");
                    buffer.append("\n");
                }
                Mensaje("Información", buffer.toString());
            }
        });
    }

    private void getEntries() {
        //Call DB constructor Llama el constructor
        myDb = new DatosDB(this);

        //Inicialize Array for lineEntries
        lineEntries = new ArrayList<>();
        //Use an accumulator
        float accumulador = 0f;

        Cursor resultQuery = myDb.getLast5();
        if (resultQuery.getCount() == 0) {
            //Show a tost
            Toast.makeText(this, Constants.NO_REGISTERS, Toast.LENGTH_SHORT).show();
            return;
        }
        StringBuffer buffer = new StringBuffer();
        String[] minMaxDates = new String[5];
        Double[] minMaxIMC = new Double[2];
        while (resultQuery.moveToNext()) {
            buffer.append("Fecha: " + resultQuery.getString(1) + "\n");
            buffer.append("Peso: " + resultQuery.getString(2) + "\n");
            buffer.append("IMC: " + resultQuery.getString(5) + "\n");
            buffer.append("\n");
            lineEntries.add(new Entry(accumulador, resultQuery.getFloat(5)));
            if(accumulador == 1f){
                minMaxDates[0] = resultQuery.getString(1);
                minMaxIMC[0] = resultQuery.getDouble(5);
            } else {
                minMaxDates[1] = resultQuery.getString(1);
                minMaxIMC[1] = resultQuery.getDouble(5);
            }
            accumulador++;
        }
        Log.d("HOLISSS", buffer.toString());
        try {
            if (accumulador>=1f){
                tvlbls.setText(minMaxDates[0].toString());
                tvlbls2.setText(minMaxDates[1].toString());
                showImage(minMaxIMC[0], minMaxIMC[1]);
            } else {
                tvlbls3.setText(minMaxDates[0].toString());
            }
        } catch (Exception e){
            Toast.makeText(this, Constants.ADD_INFO, Toast.LENGTH_LONG).show();
            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                   openMainActivity();
                }
            }, 5000);

        }


    }

    private void showImage(Double empezo, Double termino) {
        if (empezo<termino){
            ivFelicidades.setVisibility(VISIBLE);
        } else {
            ivAnimo.setVisibility(VISIBLE);
            Toast.makeText(this, "El objetivo era disminuir el IMC y no se logró en este periodo", Toast.LENGTH_LONG).show();
        }
    }

    public void openMainActivity(){
        Intent intent = new Intent(this, Dashboard.class);
        startActivity(intent);
    }

    //Method that creates the message as alert
    public void Mensaje(String title, String Message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        //Show the message
        builder.show();
    }

}
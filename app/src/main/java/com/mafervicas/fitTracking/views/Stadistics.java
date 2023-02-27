package com.mafervicas.fitTracking.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
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

import java.util.ArrayList;


public class Stadistics extends AppCompatActivity {

    //Inicialization
    LineChart lineChart;
    LineData lineData;
    LineDataSet lineDataSet;
    ArrayList lineEntries;
    ImageButton buttonReturnDashboard;
    DatosDB myDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stadistics);
        //Bring al variables needed for the graph
        lineChart = findViewById(R.id.lineChart);
        getEntries();
        lineDataSet = new LineDataSet(lineEntries, "");
        lineData = new LineData(lineDataSet);
        // draw points over time
        lineChart.animateX(1900);
        lineChart.setData(lineData);
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
            Toast.makeText(this, "No se encontró registros", Toast.LENGTH_SHORT).show();
            return;
        }
        StringBuffer buffer = new StringBuffer();
        while (resultQuery.moveToNext()) {
            buffer.append("Registro ID: " + resultQuery.getString(0) + "\n");
            buffer.append("Fecha: " + resultQuery.getString(1) + "\n");
            buffer.append("Peso: " + resultQuery.getString(2) + "\n");
            buffer.append("IMC: " + resultQuery.getString(5) + "\n");
            buffer.append("\n");
            Log.d("Acumulador", "antes " + accumulador);
            lineEntries.add(new Entry(accumulador, resultQuery.getFloat(5)));
            accumulador++;
            Log.d("Acumulador", "después " + accumulador);
        }
        Log.d("HOLISSS", buffer.toString());
    }

    public void openMainActivity(){
        Intent intent = new Intent(this, Dashboard.class);
        startActivity(intent);
    }

}
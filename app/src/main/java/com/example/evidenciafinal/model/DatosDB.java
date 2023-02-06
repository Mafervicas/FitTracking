package com.example.evidenciafinal.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatosDB extends SQLiteOpenHelper {
    //Creation of DB
    public static final String DATABASE_NAME = "FitTrackingPersonal";
    public static final String TABLE_NAME = "mainData_tb";

    //Columns
    public static final String COL_1= "Fecha";
    public static final String COL_2 = "Peso";
    public static final String COL_3 = "Altura";
    public static final String COL_4 = "Edad";
    public static final String COL_5 = "IMC";
    public static final String COL_6 = "IngestaAgua";
    public static final String COL_7 = "Kcals";
    public static final String COL_8 = "ExerciseFreq";

    //Creation of Table 2
    public static final String TABLE_NAME2 ="calorias_tb";
    //Columns
    public static final String COL_10 = "FechaKcal";
    public static final String COL_11 = "KcalMeta";
    public static final String COL_12 = "KcalAcumuladas";
    public static final String COL_13 = "ObjetivoCumplido";

    //DB Constructor
    public DatosDB(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //Create a query to create table
        String table1 = "CREATE TABLE "+ TABLE_NAME + "(Fecha STRING PRIMARY KEY, Peso DOUBLE, Altura DOUBLE, Edad INTEGER, IMC DOUBLE, IngestaAgua DOUBLE, Kcals DOUBLE, ExerciseFreq STRING)";
        String table2 = "CREATE TABLE " + TABLE_NAME2 + "(FechaKCal STRING PRIMARY KEY, KcalMeta DOUBLE, KcalAcumuladas DOUBLE, ObjectivoCumplido BOOLEAN)";
        sqLiteDatabase.execSQL(table1);
        sqLiteDatabase.execSQL(table2);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        //Delete table
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        //Creates it again
        onCreate(sqLiteDatabase);
    }

    //Method to Insert Data on DB
    public boolean insertMainData(String fecha, Double peso, Double altura, Integer edad, Double imc, Double ingestaagua, Double kcals, String exercisefreq){
        //Create the instance
        SQLiteDatabase db = this.getWritableDatabase();
        //Assing values
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1, fecha);
        contentValues.put(COL_2, peso);
        contentValues.put(COL_3, altura);
        contentValues.put(COL_4, edad);
        contentValues.put(COL_5, imc);
        contentValues.put(COL_6, ingestaagua);
        contentValues.put(COL_7, kcals);
        contentValues.put(COL_7, exercisefreq);
        //Insert data
        long result = db.insert(TABLE_NAME,null, contentValues);
        //Return if the info was added successfully or not
        if(result == -1)
            return false;
        else
            return true;
    }

    //Method to Insert Kcals on DB
    public boolean insertKcalData(String fechakcal, Double kcalmeta,Double kcalacumuladas, Boolean objetivocumplido){
        //Create the instance
        SQLiteDatabase db = this.getWritableDatabase();
        //Assing values
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_10, fechakcal);
        contentValues.put(COL_11, kcalmeta);
        contentValues.put(COL_12, kcalacumuladas);
        contentValues.put(COL_13, objetivocumplido);
        //Insert data
        long result = db.insert(TABLE_NAME,null, contentValues);
        //Return if the info was added successfully or not
        if(result == -1)
            return false;
        else
            return true;
    }
}

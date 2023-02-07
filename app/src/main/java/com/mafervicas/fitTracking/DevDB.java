package com.mafervicas.fitTracking;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DevDB extends SQLiteOpenHelper {
    //Creamos base de datos
    public static final String DATABASE_NAME = "FfitLife.db";
    public static final String TABLE_NAME = "calculos_tb";
    //Columnas
    public static final String COL_1= "RegistroID";
    public static final String COL_2 = "Peso";
    public static final String COL_3 = "Altura";
    public static final String COL_4 = "Edad";
    public static final String COL_5 = "IMC";
    public static final String COL_6 = "IngestaAgua";

    //Tabla 2
    public static final String TABLE_NAME2 ="calorias_tb";
    //Columnas
    public static final String COL_7 = "NumDia";
    public static final String COL_8 = "KcalMeta";
    public static final String COL_9 = "KcalAcumuladas";

    //Tabla 3
    public static final String TABLE_NAME3 ="usuario_tb";
    //Columnas
    public static final String COL_10 = "Usuario";
    public static final String COL_11 = "NombreCompleto";

    //Constructor crea la tabla1
    public DevDB(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Ejecutará el query
        String table1 = "CREATE TABLE "+ TABLE_NAME + "(RegistroID INTEGER PRIMARY KEY AUTOINCREMENT, Peso DOUBLE, Altura DOUBLE, Edad INTEGER, IMC DOUBLE, IngestaAgua DOUBLE)";
        String table2 = "CREATE TABLE " + TABLE_NAME2 + "(NumDia INTEGER PRIMARY KEY AUTOINCREMENT, KcalMeta INTEGER, KcalAcumuladas INTEGER)";
        String table3 = "CREATE TABLE " + TABLE_NAME3 + "(Usuario STRING PRIMARY KEY, NombreCompleto STRING)";
        db.execSQL(table1);
        db.execSQL(table2);
        db.execSQL(table3);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Borrar la tabla
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME2);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME3);
        //La vuelvE a crear
        onCreate(db);
    }

    //Método para insertar datos en Tabla Cálculos
    public boolean insertarDataCalculos(Double peso, Double altura, Integer edad, Double imc, Double ingestaagua){
        //Instanciamos
        SQLiteDatabase db = this.getWritableDatabase();
        //Asignaremos los valores
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, peso);
        contentValues.put(COL_3, altura);
        contentValues.put(COL_4, edad);
        contentValues.put(COL_5, imc);
        contentValues.put(COL_6, ingestaagua);
        //Insertamos data
        long result = db.insert(TABLE_NAME,null, contentValues);
        //Regresamos si se pusieron los valores o no
        if(result == -1)
            return false;
        else
            return true;
    }

    //Método para borrar datos
    public Integer BorrarDatosCalculo(String registroId) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "RegistroID = ?",new String[] {registroId});
    }

    //Método para Mostrar datos
    public Cursor mostrarDataCalculos() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_NAME, null);
        return res;
    }


    //Tabla 2
    //Constructor crea la tabla2

    //Método para insertar datos en Tabla Cálculos
    public boolean insertarDataCalorias(String kcalMeta, String kcalAcumuladas){
        //Instanciamos
        SQLiteDatabase db = this.getWritableDatabase();
        //Asignaremos los valores
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_8, kcalMeta);
        contentValues.put(COL_9, kcalAcumuladas);
        //Insertamos data
        long result = db.insert(TABLE_NAME2,null, contentValues);
        //Regresamos si se pusieron los valores o no
        if(result == -1)
            return false;
        else
            return true;
    }

    //Método para borrar datos
    public Integer BorrarDatosCalorias(String numDia) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME2, "NumDia = ?",new String[] {numDia});
    }

    //Método para Mostrar datos
    public Cursor mostrarDataCalorias() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_NAME2, null);
        return res;
    }

    //Método para insertar datos en Tabla Usuario
    public boolean insertarDataUsuario(String usuario, String nombreCompleto){
        //Instanciamos
        SQLiteDatabase db = this.getWritableDatabase();
        //Asignaremos los valores
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_10, usuario);
        contentValues.put(COL_11, nombreCompleto);
        //Insertamos data
        long result = db.insert(TABLE_NAME3,null, contentValues);
        //Regresamos si se pusieron los valores o no
        if(result == -1)
            return false;
        else
            return true;
    }

    //Método para borrar datos
    public Integer BorrarDatosUsuario(String usuario) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME3, "Usuario = ?",new String[] {usuario});
    }

    //Método para Mostrar datos
    public Cursor mostrarDataUsuario() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_NAME3, null);
        return res;
    }
}

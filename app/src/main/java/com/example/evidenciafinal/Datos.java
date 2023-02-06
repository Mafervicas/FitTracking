package com.example.evidenciafinal;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.evidenciafinal.views.Dashboard;

public class Datos extends AppCompatActivity {

    DevDB myDb;
    //Inicializamos variables
    Button btConsultaCalculos, btConsultaUsuarios, btConsultaCalorias, btBorrarCalculos;
    Button btBorrarUsuarios, btBorrarCalorias;

    EditText etID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datos);
        getSupportActionBar().hide(); //Ocultamos la barra de default

        //Encontramos por ID
        btConsultaCalculos = (Button) findViewById(R.id.btConsultaCalculos);
        btConsultaCalorias = (Button) findViewById(R.id.btConsultaCalorias);
        btConsultaUsuarios = (Button) findViewById(R.id.btConsultaUsuario);
        btBorrarCalculos = (Button) findViewById(R.id.btBorrarCalculos);
        btBorrarCalorias = (Button) findViewById(R.id.btBorrarCalorias);
        btBorrarUsuarios = (Button) findViewById(R.id.btBorrarUsuario);
        etID = (EditText) findViewById(R.id.etID);

        //Para el Botón de ir a Home
        ImageButton imageButton = (ImageButton) findViewById(R.id.ibBack);
        imageButton.setOnClickListener(v -> openMainActivity());

        //Llama el constructor
        myDb = new DevDB(this);

        //Llamamos los métodos
        viewAllCalculos();
        viewAllCalorias();
        viewAllUsuario();

        BorrarCalculos();
        BorrarCalorias();
        BorrarUsuario();


    }


    public void viewAllCalculos() {
        btConsultaCalculos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor res = myDb.mostrarDataCalculos();
                //Checando si hay data o no
                if (res.getCount() == 0) {
                    //Se mostrará el mensaje
                    Mensaje("Error", "No se encontró info");
                    return;
                }
                StringBuffer buffer = new StringBuffer();
                while (res.moveToNext()) {
                    buffer.append("Registro ID: " + res.getString(0) + "\n");
                    buffer.append("Peso: " + res.getString(1) + "\n");
                    buffer.append("Altura: " + res.getString(2) + "\n");
                    buffer.append("Edad: " + res.getString(3) + "\n");
                    buffer.append("IMC: " + res.getString(4) + "\n");
                    buffer.append("Ingesta Agua: " + res.getString(5) + "\n");
                    buffer.append("\n");
                }
                Mensaje("Información", buffer.toString());
            }
        });
    }

    public void viewAllCalorias() {
        btConsultaCalorias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor resa = myDb.mostrarDataCalorias();
                //Checando si hay data o no
                if (resa.getCount() == 0) {
                    //Se mostrará el mensaje
                    Mensaje("Error", "No se encontró info");
                    return;
                }
                    StringBuffer buffer = new StringBuffer();
                    while (resa.moveToNext()) {
                        buffer.append("Numero de día: " + resa.getInt(0) + "\n");
                        buffer.append("Kcal de Meta: " + resa.getInt(1) + "\n");
                        buffer.append("Kcal Acumuladas: " + resa.getInt(2) + "\n");
                        buffer.append("\n");
                    }
                    Mensaje("Información", buffer.toString());
                }
       });
    }

    public void viewAllUsuario() {
        btConsultaUsuarios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor res = myDb.mostrarDataUsuario();
                //Checando si hay data o no
                if (res.getCount() == 0) {
                    //Se mostrará el mensaje
                    Mensaje("Error", "No se encontró info");
                    return;
                }
                StringBuffer buffer = new StringBuffer();
                while (res.moveToNext()) {
                        buffer.append("Usuario: " + res.getString(0) + "\n");
                        buffer.append("Nombre Completo: " + res.getString(1) + "\n");
                        buffer.append("\n");
                    }
                    Mensaje("Información", buffer.toString());
                }
        });
    }

        //Método que regresa un mensaje como alerta
        public void Mensaje(String title, String Message){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setCancelable(true);
            builder.setTitle(title);
            builder.setMessage(Message);
            //Mostrar el mensaje
            builder.show();
        }

    //Método para borrar
    public void BorrarCalorias() {
        btBorrarCalorias.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Integer deletedRows = myDb.BorrarDatosCalorias(etID.getText().toString());
                        if(deletedRows > 0)
                            Toast.makeText(Datos.this,"Info Eliminada",Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(Datos.this,"Error",Toast.LENGTH_LONG).show();
                    }
                }
        );
    }

    public void BorrarCalculos() {
        btBorrarCalculos.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Integer deletedRows = myDb.BorrarDatosCalculo(etID.getText().toString());
                        if(deletedRows > 0)
                            Toast.makeText(Datos.this,"Info Eliminada",Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(Datos.this,"Error",Toast.LENGTH_LONG).show();
                    }
                }
        );
    }

    public void BorrarUsuario() {
        btBorrarUsuarios.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Integer deletedRows = myDb.BorrarDatosUsuario(etID.getText().toString());
                        if(deletedRows > 0)
                            Toast.makeText(Datos.this,"Info Eliminada",Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(Datos.this,"Error",Toast.LENGTH_LONG).show();
                    }
                }
        );
    }

    public void openMainActivity(){
        Intent intent = new Intent(this, Dashboard.class);
        startActivity(intent);
    }
    }

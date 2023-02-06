package com.example.evidenciafinal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.ViewParent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.Toast;

import com.example.evidenciafinal.views.Dashboard;
import com.google.android.material.tabs.TabLayout;

public class Perfil extends AppCompatActivity{

    private TabLayout tabLayout;
    private ViewPager viewPager;

    DevDB myDb;

    //Inicializamos variables
    ImageView ivImagen;
    EditText etUsuario, etNombre;
    Button btCargarImagen, btGuardarDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);
        getSupportActionBar().hide(); //Ocultamos la barra de default


        //Para Regresar al view home
        ImageButton imageButton = (ImageButton) findViewById(R.id.ibBack);
        imageButton.setOnClickListener(v -> openMainActivity());


        //Encontrar por ID
        ivImagen = (ImageView) findViewById(R.id.ivImagen);
        etNombre = (EditText) findViewById(R.id.etNombre);
        etUsuario = (EditText) findViewById(R.id.etUsuario);
        btGuardarDB = (Button) findViewById(R.id.btGuardarDB);

        //Llama el constructor
        myDb = new DevDB(this);

        //Hablar al método cuando se da click al botón de add
        AddData();

        //Fragmentos
        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewpager);

        tabLayout.setupWithViewPager(viewPager);

        VPAdapter vpAdapter= new VPAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        vpAdapter.addFragment(new Fragment1(),"QUOTE 1");
        vpAdapter.addFragment(new Fragment2(),"QUOTE 2");
        vpAdapter.addFragment(new Fragment3(),"QUOTE 3");
        viewPager.setAdapter(vpAdapter);
    }



    //Metodo de añadir
    public void AddData(){
        btGuardarDB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Convertimos
                //Agarramos texto
                boolean seInserta= myDb.insertarDataUsuario(etUsuario.getText().toString(), etNombre.getText().toString());
                if(seInserta = true)
                    Toast.makeText(Perfil.this, "Agregado correctamente",Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(Perfil.this, "Problemas al insertar",Toast.LENGTH_LONG).show();
            }
        });
    }

    //Regresar a Home
    public void openMainActivity(){
        Intent intent = new Intent(this, Dashboard.class);
        startActivity(intent);
    }

    public void onClick(View view) {
        cargarImagen();
    }

    private void cargarImagen(){
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/");
        startActivityForResult(intent.createChooser(intent, "Seleccione la Aplicación"),10);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        //Si se da el permiso
        if(resultCode== RESULT_OK){
            Uri path=data.getData();
            ivImagen.setImageURI(path);

        }
    }





}
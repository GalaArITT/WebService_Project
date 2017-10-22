package com.example.oliver.webservice_project;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class Configurar extends AppCompatActivity {
    EditText IP;
    FloatingActionButton boton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configurar);
        IP = (EditText)findViewById(R.id.IP);
        boton = (FloatingActionButton)findViewById(R.id.boton);
        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Guardar();
                startActivity(new Intent(Configurar.this,MainActivity.class));
            }
        });
        Leer();


    }

    protected void Guardar(){
        SharedPreferences preferencias= getSharedPreferences("Config", Context.MODE_APPEND);
        SharedPreferences.Editor editor = preferencias.edit();
        editor.putString("IP", IP.getText().toString());
        editor.commit();
    }

    protected void Leer(){
        SharedPreferences preferencias = getSharedPreferences("Config", Context.MODE_APPEND);
        IP.setText(preferencias.getString("IP",""));
    }





}

package com.example.oliver.webservice_project;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class Insertar extends AppCompatActivity {
    URL url;

    String IP = "http://192.168.1.66/datos1";
    String GET_TODO = IP + "/obtener_alumnos.php";
    String GET_BY_ID = IP + "/obtener_alumno_por_id.php";

    String INSERT = IP + "/insertar_alumno.php";
    String DELETE = IP + "/borrar_alumno.php";
    String UPDATE = IP + "/actualizar_alumno.php";
    WServices hconexion;
    String json_string,ID_ALUMNO,NOMBRE,DIRECCION;

    EditText nombre;
    EditText direccion;
    FloatingActionButton guardar;
    Insertar puntero;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insertar);
        direcciones();
        nombre = (EditText)findViewById(R.id.nombre);
        direccion = (EditText)findViewById(R.id.Direccion);
        guardar = (FloatingActionButton)findViewById(R.id.boton_insertarR);
        puntero = this;
        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(insertar(nombre.getText().toString(),direccion.getText().toString())){
                    puntero.finish();
                }
                Snackbar.make(getCurrentFocus(), "No se pudo Insertar", Snackbar.LENGTH_LONG).setActionTextColor(Color.RED).show();
            }
        });
    }

    private boolean insertar(String nombre, String direcion) {
        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            url = new URL(INSERT);
            HttpURLConnection connection  = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            JSONObject json = new JSONObject();
            json.put("nombre",nombre);
            json.put("direccion",direcion);
            OutputStream os = connection.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os,"UTF-8"));
            writer.write(json.toString());
            writer.flush();
            writer.close();
            os.close();
            int respuesta = 0;
            respuesta = connection.getResponseCode();
            InputStream inputStream =  connection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder stringBuilder = new StringBuilder();
            if (respuesta == HttpURLConnection.HTTP_OK) {
                while ((json_string = bufferedReader.readLine()) != null) {
                    stringBuilder.append(json_string + "\n");
                }
                bufferedReader.close();
                inputStream.close();
                connection.disconnect();
            }
        }catch (java.net.MalformedURLException e){
            return false;
        }catch (IOException e) {
            return false;
        }catch (org.json.JSONException e){
            return false;
        }
        return true;
    }
    private void direcciones() {
        IP = "http://"+getSharedPreferences("Config", Context.MODE_APPEND).getString("IP","sdfsdfsdfsdf")+"/datos1";
        GET_TODO = IP + "/obtener_alumnos.php";
        GET_BY_ID = IP + "/obtener_alumno_por_id.php";
        INSERT = IP + "/insertar_alumno.php";
        DELETE = IP + "/borrar_alumno.php";
        UPDATE = IP + "/actualizar_alumno.php";
    }
}

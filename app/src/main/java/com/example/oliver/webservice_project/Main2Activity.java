package com.example.oliver.webservice_project;

import android.content.Context;
import android.content.Intent;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

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

public class Main2Activity extends AppCompatActivity {
    TextView ID;
    EditText NOMBRE;
    EditText DIRECCION;
    FloatingActionButton DELETE_;
    FloatingActionButton UPDATE_;
    Main2Activity puntero;

    URL url;
    String IP = "";
    String GET_TODO = IP + "/obtener_alumnos.php";
    String GET_BY_ID = IP + "/obtener_alumno_por_id.php";

    String INSERT = IP + "/insertar_alumno.php";
    String DELETE = IP + "/borrar_alumno.php";
    String UPDATE = IP + "/actualizar_alumno.php";
    MainActivity.WServices hconexion;
    String json_string,ID_ALUMNO,NOMBRE_,DIRECCION_;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        ID = (TextView)findViewById(R.id.ID);
        NOMBRE = (EditText)findViewById(R.id.nombre);
        DIRECCION = (EditText)findViewById(R.id.Direccion);
        DELETE_ = (FloatingActionButton)findViewById(R.id.boton_eliminar);
        UPDATE_ = (FloatingActionButton)findViewById(R.id.boton_actualizar);
        puntero = this;

        direcciones();

        Intent paquete = this.getIntent();
        ID.setText(paquete.getStringExtra("ID_ALUMNO"));
        NOMBRE.setText(paquete.getStringExtra("NOMBRE"));
        DIRECCION.setText(paquete.getStringExtra("DIRECCION"));
        DELETE_.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eliminar();
                puntero.finish();
            }
        });

        UPDATE_.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update();
                puntero.finish();
            }
        });
    }

    private boolean update() {
        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            url = new URL(UPDATE);
            HttpURLConnection connection =  (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestMethod("POST");
            JSONObject json = new JSONObject();
            json.put("idalumno",ID.getText());
            json.put("nombre",NOMBRE.getText());
            json.put("direccion",DIRECCION.getText());
            OutputStream os = connection.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os,"UTF-8"));
            writer.write(json.toString());
            writer.flush();
            writer.close();
            os.close();
            int respuesta = connection.getResponseCode();
            InputStream inputStream = connection.getInputStream();
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

    private boolean eliminar() {
        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            url = new URL(DELETE);
            HttpURLConnection connection =  (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            JSONObject json = new JSONObject();
            json.put("idalumno",ID.getText());
            OutputStream os = connection.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os,"UTF-8"));
            writer.write(json.toString());
            writer.flush();
            writer.close();
            os.close();
            int respuesta = 0;
            respuesta = connection.getResponseCode();
            InputStream inputStream = connection.getInputStream();
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

package com.example.oliver.webservice_project;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.Vector;

public class MainActivity extends AppCompatActivity {
    EditText buscar;
    FloatingActionButton buscarButton;
    FloatingActionButton botonInsertar;
    ListView lista;
    MainActivity puntero;
    Vector ID;
    ArrayList NOMBRES;

    URL url;
    String IP = "";
    String GET_TODO = IP + "/obtener_alumnos.php";
    String GET_BY_ID = IP + "/obtener_alumno_por_id.php";

    String INSERT = IP + "/insertar_alumno.php";
    String DELETE = IP + "/borrar_alumno.php";
    String UPDATE = IP + "/actualizar_alumno.php";
    WServices hconexion;
    String json_string,ID_ALUMNO,NOMBRE,DIRECCION;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        direcciones();

        buscar = (EditText)findViewById(R.id.text);
        buscarButton = (FloatingActionButton)findViewById(R.id.boton_buscar_);
        botonInsertar = (FloatingActionButton)findViewById(R.id.boton_insertar_);
        lista = (ListView)findViewById(R.id.lista);
        puntero = this;
        ID = new Vector();
        NOMBRES = new ArrayList();

        botonInsertar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(puntero, Insertar.class),1);
            }
        });
        buscarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(LeerElemento(buscar.getText().toString())){
                    startActivityForResult(new Intent(puntero, Main2Activity.class).putExtra("ID_ALUMNO",ID_ALUMNO).putExtra("NOMBRE",NOMBRE).putExtra("DIRECCION",DIRECCION),3);
                    return;
                }
                Snackbar.make(getCurrentFocus(), "Este registro no Existe", Snackbar.LENGTH_LONG).setAction("Este registro no Existe",null).setActionTextColor(Color.RED).show();
            }
        });
        buscarButton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                startActivityForResult(new Intent(puntero, Configurar.class),4);
                return false;
            }
        });
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(LeerElemento(ID.get(position).toString())){
                    startActivityForResult(new Intent(puntero, Main2Activity.class).putExtra("ID_ALUMNO",ID_ALUMNO).putExtra("NOMBRE",NOMBRE).putExtra("DIRECCION",DIRECCION),3);
                    return;
                }
                Snackbar.make(view, "No hay ", Snackbar.LENGTH_LONG).setActionTextColor(Color.RED).show();
            }
        });
        if(!getConnectionStatus()){
            startActivityForResult(new Intent(puntero, Configurar.class),4);
        }
        if(!llenarLista()){

        }
    }

    private void direcciones() {
        IP = "http://"+getSharedPreferences("Config", Context.MODE_APPEND).getString("IP","sdfsdfsdfsdf")+"/datos1";
        GET_TODO = IP + "/obtener_alumnos.php";
        GET_BY_ID = IP + "/obtener_alumno_por_id.php";
        INSERT = IP + "/insertar_alumno.php";
        DELETE = IP + "/borrar_alumno.php";
        UPDATE = IP + "/actualizar_alumno.php";
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        direcciones();
        if(!llenarLista()){

        }
    }

    private boolean LeerElemento(String id) {
        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            String ruta = GET_BY_ID+"?idalumno="+id;
            url = new URL(ruta);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
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
                String temporal = stringBuilder.toString();
                JSONObject jsonObj = new JSONObject(temporal);
                JSONObject c = jsonObj.getJSONObject("alumno");
                ID_ALUMNO = c.getString("idAlumno");
                NOMBRE = c.getString("nombre");
                DIRECCION = c.getString("direccion");
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

    private boolean llenarLista() {
        try {
            ID.removeAllElements();
            NOMBRES.removeAll(NOMBRES);
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            url = new URL(GET_TODO);
            HttpURLConnection connection =  (HttpURLConnection) url.openConnection();
            int respuesta  = 0;
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
                String temporal = stringBuilder.toString();
                JSONObject jsonObj = new JSONObject(temporal);
                JSONArray alumnos = jsonObj.getJSONArray("alumnos");
                ID.removeAllElements();
                for (int i = 0; i < alumnos.length(); i++) {
                    JSONObject c = alumnos.getJSONObject(i);
                    ID.add(c.getString("idalumno"));
                    NOMBRES.add(c.getString("nombre"));
                }
                ArrayAdapter adapter = new ArrayAdapter(puntero.getApplicationContext(),android.R.layout.simple_list_item_1,NOMBRES.toArray());
                lista.setAdapter(adapter);
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

    public class WServices extends AsyncTask<String, Void, String> {

        URL url;
        @Override
        protected String doInBackground(String... params) {
            String cadena = "";
            String ruta ="";


            if (params[1] == "5") {
                try {
                    url = new URL(UPDATE);
                    HttpURLConnection connection = null; // Abrir conexion
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("POST");
                    connection.setRequestMethod("POST");
                    JSONObject json = new JSONObject();
                    json.put("idalumno",params[2]);
                    json.put("nombre",params[3]);
                    json.put("direccion",params[4]);
                    OutputStream os = connection.getOutputStream();
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os,"UTF-8"));
                    writer.write(json.toString());
                    writer.flush();
                    writer.close();
                    os.close();
                    int respuesta = 0;
                    respuesta = connection.getResponseCode();
                    InputStream inputStream = null;
                    inputStream = connection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                    StringBuilder stringBuilder = new StringBuilder();

                    if (respuesta == HttpURLConnection.HTTP_OK) {
                        while ((json_string = bufferedReader.readLine()) != null) {
                            stringBuilder.append(json_string + "\n");
                        }
                        bufferedReader.close();
                        inputStream.close();
                        connection.disconnect();
                        cadena = stringBuilder.toString().trim();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return cadena;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            //resultado.setText(s);
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }

    private boolean getConnectionStatus () {
        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            Socket s = new Socket(getSharedPreferences("Config", Context.MODE_APPEND).getString("IP","sdfsdfsdfsdf"), 80);
            if(s.isConnected()){
                s.close();
                return true;
            }
            s.close();
        } catch (Exception e) {
            return false;
        }
        return false;
    }
}

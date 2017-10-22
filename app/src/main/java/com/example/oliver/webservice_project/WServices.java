package com.example.oliver.webservice_project;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by oliver on 21/10/17.
 */

public class WServices extends AsyncTask<String, Void, String> {
    String IP = "http://172.20.5.177/datos1";
    String GET_TODO = IP + "/obtener_alumnos.php";
    String GET_BY_ID = IP + "/obtener_alumno_por_id.php";
    String INSERT = IP + "/insertar_alumno.php";
    String DELETE = IP + "/borrar_alumno.php";
    String UPDATE = IP + "/actualizar_alumno.php";
    WServices hconexion;
    String json_string,id,nom,dire;
    URL url;
    @Override
    protected String doInBackground(String... params) {
        String cadena = "";
        String ruta ="";
        if (params[1] == "1") {
            try {
                url = new URL(GET_TODO);
                HttpURLConnection connection = null; // Abrir conexion
                connection = (HttpURLConnection) url.openConnection();
                int respuesta = 0;
                respuesta = connection.getResponseCode();
                InputStream inputStream = null;
                inputStream = connection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder stringBuilder = new StringBuilder();

                if (respuesta == HttpURLConnection.HTTP_OK) {
                    //Toast.makeText(getApplicationContext(), "HTTP_OK "+GET_TODO, Toast.LENGTH_SHORT).show();
                    //String abc = json_string;
                    while ((json_string = bufferedReader.readLine()) != null) {
                        stringBuilder.append(json_string + "\n");
                    }

                    bufferedReader.close();
                    inputStream.close();
                    connection.disconnect();
                    String temporal = stringBuilder.toString();
                    JSONObject jsonObj = new JSONObject(temporal);
                    // Getting JSON Array node
                    JSONArray alumnos = jsonObj.getJSONArray("alumnos");

                    // looping through All Contacts
                    for (int i = 0; i < alumnos.length(); i++) {
                        JSONObject c = alumnos.getJSONObject(i);
                        cadena += "ID: "+c.getString("idalumno") +" Nombre: "+c.getString("nombre") +"Direccion: "+ c.getString("direccion")+ "\n";
                    }
                    return cadena;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (params[1] == "2") {
            try {
                ruta = GET_BY_ID+"?idalumno="+id;
                url = new URL(ruta);
                HttpURLConnection connection = null; // Abrir conexion
                connection = (HttpURLConnection) url.openConnection();
                int respuesta = 0;
                respuesta = connection.getResponseCode();
                InputStream inputStream = null;
                inputStream = connection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder stringBuilder = new StringBuilder();

                if (respuesta == HttpURLConnection.HTTP_OK) {
                    //Toast.makeText(getApplicationContext(), "HTTP_OK "+GET_TODO, Toast.LENGTH_SHORT).show();
                    //String abc = json_string;
                    while ((json_string = bufferedReader.readLine()) != null) {
                        stringBuilder.append(json_string + "\n");
                    }
                    bufferedReader.close();
                    inputStream.close();
                    connection.disconnect();
                    String temporal = stringBuilder.toString();
                    JSONObject jsonObj = new JSONObject(temporal);
                    // Getting JSON Array node
                    JSONArray alumnos = jsonObj.getJSONArray("alumnos");
                    JSONObject c = alumnos.getJSONObject(0);
                    cadena += "ID: "+c.getString("idalumno") +" Nombre: "+c.getString("nombre") +"Direccion: "+ c.getString("direccion")+ "\n";
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (params[1] == "3") {
            try {
                url = new URL(INSERT);
                HttpURLConnection connection = null; // Abrir conexion
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                JSONObject json = new JSONObject();
                json.put("nombre",params[2]);
                json.put("direccion",params[3]);
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
                        // Getting JSON Array node
                    }
                    bufferedReader.close();
                    inputStream.close();
                    connection.disconnect();
                    //cadena = stringBuilder.toString().trim();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (params[1] == "4") {
            try {
                url = new URL(DELETE);
                HttpURLConnection connection = null; // Abrir conexion
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                JSONObject json = new JSONObject();
                json.put("idalumno",params[2]);
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
                    //Toast.makeText(getApplicationContext(), "HTTP_OK "+GET_TODO, Toast.LENGTH_SHORT).show();
                    //String abc = json_string;
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
                    //Toast.makeText(getApplicationContext(), "HTTP_OK "+GET_TODO, Toast.LENGTH_SHORT).show();
                    //String abc = json_string;
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

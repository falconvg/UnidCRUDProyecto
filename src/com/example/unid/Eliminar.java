package com.example.unid;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.app.AlertDialog;
import android.net.Uri;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Eliminar extends Activity {

private String URL = "http://unid20183.herokuapp.com/api_alumnos?user_hash=12345&action=get&";
private String URL2 = "http://unid20183.herokuapp.com/api_alumnos?user_hash=12345&action=delete&";
private String getProductosURL = "";
private String queryParams = "";
private Button btneliminar;
private EditText txtideliminar;
private TextView txtideliminar1;
private TextView txtdomicilio;
private TextView txtnombre;
private TextView txtmaestria;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.eliminar);                                             

        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitNetwork().build());
        
        txtideliminar = (EditText) findViewById(R.id.txtideliminar);
        
        txtdomicilio = (TextView) findViewById(R.id.txtdomicilio);
        txtmaestria = (TextView) findViewById(R.id.txtmaestria);

        btneliminar = (Button) findViewById(R.id.btneliminar);
        btneliminar.setOnClickListener(onClickListener);

    }
    
    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v == btneliminar)
            	btneliminar_onClick();
        }
    };


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
           
    private void btneliminar_onClick() {
        String id_alumno = txtideliminar.getText().toString();
        Uri.Builder builder = new Uri.Builder();
        builder.appendQueryParameter("id_alumno", id_alumno);
        queryParams = builder.build().getEncodedQuery();
        getProductosURL = URL2;
        getProductosURL += queryParams;
        Log.d("Parametros", queryParams);
        Log.d("Consulta", getProductosURL);
        webServiceRest(getProductosURL);            
    }

    private void webServiceRest(String requestURL){
        try{
            URL url = new URL(requestURL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line = "";
            String webServiceResult="";
            while ((line = bufferedReader.readLine()) != null){
                webServiceResult += line;
            }
            bufferedReader.close();
            parseInformation(webServiceResult);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private void parseInformation(String jsonResult){
        JSONArray jsonArray = null;
        @SuppressWarnings("unused")
		String id_alumno;
        String nombre;
        String domicilio;
        String maestria;
        try{
            jsonArray = new JSONArray(jsonResult);
        }catch (JSONException e){
            e.printStackTrace();
        }
        if (jsonArray != null){
            Log.d("jsonArray ",""+jsonArray.length());
            for(int i=0;i<jsonArray.length();i++){
                try{
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    id_alumno = jsonObject.getString("id_alumno");
                    nombre = jsonObject.getString("nombre");
                    domicilio = jsonObject.getString("domicilio");
                    maestria = jsonObject.getString("maestria");
                               
                    txtideliminar1.setText(id_alumno);
                    txtideliminar1.setText("Alumno Eliminado");    
                    txtnombre.setText(nombre);
                    txtdomicilio.setText(domicilio);
                    txtmaestria.setText(maestria);
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
        }else{
        	txtideliminar1.setText("Alumno Eliminado");        	
            Message("Error","Registro no encontrado");
        }
    }

    private void Message(String title, String message){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle(title);
        alertDialog.setMessage(message);
        alertDialog.show();
    }                                             
}
    

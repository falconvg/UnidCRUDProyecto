package com.example.unid;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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

public class Insertar extends Activity {

    private String URL = "";
    private Button btnins;
    private EditText txt_nombre;
    private EditText txtdomicilio;
    private EditText txt_telefono;
    private EditText txt_maestria;
    
    

    @Override
	public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.insertar);
        
        
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitNetwork().build());
        
        txt_nombre=(EditText) findViewById(R.id.editTextnombre);
        txtdomicilio=(EditText) findViewById(R.id.editTextdomicilio);
        txt_telefono=(EditText) findViewById(R.id.editTexttelefono);
        txt_maestria=(EditText) findViewById(R.id.editTextmaestria);    
               
        btnins=(Button)findViewById(R.id.btninserttttttttt);                      
        btnins.setOnClickListener(onClickListener);               
    }
    
    
    
    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v == btnins)
            	btnins_onClick();
        }
    };
    
    private void btnins_onClick() {
    	String nombre = txt_nombre.getText().toString();
        String domicilio = txtdomicilio.getText().toString();
        String telefono = txt_telefono.getText().toString();
        String maestria = txt_maestria.getText().toString();                
        Uri.Builder builder = new Uri.Builder();                
        URL = "http://unid20183.herokuapp.com/api_alumnos?user_hash=12345&action=put&nombre="+nombre+"&domicilio="+domicilio+"&telefono="+telefono+"&maestria="+maestria+"";
        webServiceRest(URL);
    }
    
    
    

	private void webServiceRest(String requestURL){
        try{
            java.net.URL url = new URL(requestURL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line = "";
            String webServiceResult="";
            while ((line = bufferedReader.readLine()) != null){
                webServiceResult += line;
            }
            bufferedReader.close();           
            Intent inten= new Intent(Insertar.this, Lista.class);
            startActivity(inten);
            
            finish();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
	
	

}
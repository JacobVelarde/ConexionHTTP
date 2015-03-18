package com.example.conexionhttp;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;



import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ConexionHttpActivity extends Activity implements OnClickListener {
	TextView salida;
	Button btnPrueba,btnConsultar,btnInsertar;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_conexion_http);
		
		salida=(TextView)findViewById(R.id.textView2);
		
		btnPrueba=(Button)findViewById(R.id.button1);
		btnConsultar=(Button)findViewById(R.id.button2);
		btnInsertar=(Button)findViewById(R.id.button3);
		
		btnPrueba.setOnClickListener(this);
		btnConsultar.setOnClickListener(this);
		btnInsertar.setOnClickListener(this);
	
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.conexion_http, menu);
		return true;
	}
	
	public String ConexionWeb(String direccion) throws Exception{
		String pagina="";
		URL url = new URL(direccion);
		  HttpURLConnection conexion = (HttpURLConnection)url.openConnection();
		if (conexion.getResponseCode() == HttpURLConnection.HTTP_OK){
			BufferedReader reader = new BufferedReader(new InputStreamReader(conexion.getInputStream()));
			
			String linea = reader.readLine();
			
			while(linea != null){
				pagina += linea+"\n";
				linea = reader.readLine();
			}
			reader.close();
		}
		else {
			salida.append("ERROR");
		}
		conexion.disconnect();
		return pagina;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v.getId()==R.id.button1){
			//String valor=et1.getText().toString().trim();
			try{
				 salida.setText(ConexionWeb("http://tiburcio.cdmon.org/~10091168/datos.txt"));
			}catch (Exception e){
				salida.setText("ERROR" + e.getMessage());
				
			}		
		}
		
		if(v.getId()==R.id.button2){
			try{
				 salida.setText(ConexionWeb("http://tiburcio.cdmon.org/~10091168/servidor.php?consulta=1"));
			}catch (Exception e){
				salida.setText("ERROR" + e.getMessage());
				
			}	
		}
		if(v.getId()==R.id.button3){
			Intent intent=new Intent(this,Insertar.class);
			startActivity(intent);
		}
		
	}
}

package com.example.conexionhttp;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Insertar extends Activity implements OnClickListener {

	EditText nombre, modelo, marca, precio;
	Button btnGuardar;
	TextView salida;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.insertar);

		nombre = (EditText) findViewById(R.id.editText1);
		modelo = (EditText) findViewById(R.id.editText2);
		marca = (EditText) findViewById(R.id.editText3);
		precio = (EditText) findViewById(R.id.editText4);
		salida=(TextView)findViewById(R.id.textView1);

		btnGuardar = (Button) findViewById(R.id.button1);

		btnGuardar.setOnClickListener(this);
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
	
	public void mensaje(String mensaje){
		Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show();
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.button1) {
			
			if(nombre.getText().toString().trim().compareTo("")==0){
				mensaje("Ingrese el nombre");
			}else{
				if(modelo.getText().toString().trim().compareTo("")==0){
					mensaje("Ingrese el modelo");
				}else{
					if(marca.getText().toString().trim().compareTo("")==0){
						mensaje("Ingrese la marca");
					}else{
						if(precio.getText().toString().trim().compareTo("")==0){
							mensaje("Ingrese el precio");
						}else{
							String nom=nombre.getText().toString().trim();
							String mod=modelo.getText().toString().trim();
							String mar=marca.getText().toString().trim();
							String pre=precio.getText().toString().trim();
							try{
								 ConexionWeb("http://tiburcio.cdmon.org/~10091168/servidor.php?idnombre="+nom+"&modelo="+mod+"&marca="+mar+"&precio="+pre);
								 Intent intent=new Intent(this,ConexionHttpActivity.class);
								 startActivity(intent);
							}catch (Exception e){
								Toast.makeText(this, "Error: "+e.getMessage(), Toast.LENGTH_LONG).show();
								
							}	
							
						}
					}
				}
			}
		}
	}
}

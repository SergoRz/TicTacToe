package com.example.versus.tictactoe;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class resultados extends AppCompatActivity {
    ListView lista;
    ArrayList<String> resul;
    ResultadoAdapter adaptador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultados);

        lista = (ListView) findViewById(R.id.listView); //Objeto ListView donde se mostraran los titulares

        resul = new ArrayList(); //Constructor del ArrayList en el cual se introduciran los titulares
        leerFicheroMemoriaInterna(resul); //Se rellena el ArrayList de titulares
        adaptador = new ResultadoAdapter(this, resul); //Constructor del adaptador de la lista

        lista.setAdapter(adaptador); //Se le asigna el adaptador a la lista
    }



    private void leerFicheroMemoriaInterna(ArrayList<String> resultados) {
        BufferedReader lector;
        try {
            lector = new BufferedReader(new InputStreamReader(openFileInput(nombreFichero())));
            String texto = lector.readLine();

            while(texto != null) {
                if(texto.endsWith(":")){
                    String texto2 = lector.readLine();
                    resultados.add(texto + "\n" + texto2);
                }

                texto = lector.readLine();
            }

            lector.close();
        }
        catch (Exception ex) {
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void wipeList(View v){
        try {
            OutputStreamWriter osw = new OutputStreamWriter(openFileOutput(nombreFichero(), Context.MODE_PRIVATE));
            osw.write("");
            osw.close();
        }
        catch (IOException e) {
            Toast.makeText(this, getResources().getString(R.string.errorFichTelf), Toast.LENGTH_SHORT).show();
        }

        finish();
        startActivity(getIntent());
    }

    public void back(View v){
        Intent intent = new Intent(resultados.this, PantallaPrincipal.class);
        startActivity(intent);

        finish();
    }

    public String nombreFichero(){
        SharedPreferences prefs = getSharedPreferences("MisPreferencias", MODE_PRIVATE);
        String nombre;

        nombre = prefs.getString("nombreFichero", "ejecuciones.txt");

        return nombre;
    }
}
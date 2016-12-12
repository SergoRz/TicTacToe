package com.example.versus.tictactoe;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Locale;

public class PantallaResultados extends AppCompatActivity {
    ListView lista;
    ArrayList<String> resul;
    ResultadoAdapter adaptador;

    /**
     * Metodo que se ejecuta al iniciar la Activity, se instancian las variables y se carga la lista de resultados
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultados);

        lista = (ListView) findViewById(R.id.listView); //Objeto ListView donde se mostraran los resultados

        resul = new ArrayList(); //Constructor del ArrayList en el cual se introduciran los resultados
        leerFicheroMemoriaInterna(resul); //Se rellena el ArrayList de resultados
        adaptador = new ResultadoAdapter(this, resul); //Constructor del adaptador de la lista

        lista.setAdapter(adaptador); //Se le asigna el adaptador a la lista
    }

    /**
     * Metodo que se encarga de acceder al fichero y leer los resultados.
     * Se encarga de añadir cada resultado a la lista.
     * @param resultados ArrayList donde se guardan los datos leidos.
     */
    private void leerFicheroMemoriaInterna(ArrayList<String> resultados) {
        BufferedReader lector;
        try {
            //Se crea un lector
            lector = new BufferedReader(new InputStreamReader(openFileInput(nombreFichero())));
            String texto = lector.readLine();

            while(texto != null) { //Mientras haya lineas..
                if(texto.endsWith(":")){
                    String texto2 = lector.readLine();
                    resultados.add(texto + "\n" + texto2); //Se añade la linea al ListView
                }

                texto = lector.readLine();
            }

            lector.close(); //Se cierra el lector
        }
        catch (Exception ex) {
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Metodo que se encarga de vaciar el fichero de resultados.
     * Se ejecuta al hacer clic en el boton borrar lista.
     * Se pide confirmacion.
     * @param v Boton borrar lista
     */
    public void wipeList(View v){
        new AlertDialog.Builder(this) //Se crea un dialogo de confirmacion
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle(getResources().getString(R.string.salir))
                .setMessage(getResources().getString(R.string.borrarLista))
                .setNegativeButton(android.R.string.cancel, null)//sin listener
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {//un listener que al pulsar, cierre la aplicacion
                    @Override
                    public void onClick(DialogInterface dialog, int which){ //Si se pulsa aceptar..
                        //Salir
                        try {
                            //Se vacia el fichero
                            OutputStreamWriter osw = new OutputStreamWriter(openFileOutput(nombreFichero(), Context.MODE_PRIVATE));
                            osw.write("");
                            osw.close();
                        }
                        catch (IOException e) {
                            Toast.makeText(PantallaResultados.this, getResources().getString(R.string.errorFichTelf), Toast.LENGTH_SHORT).show();
                        }

                        finish();
                        startActivity(getIntent());
                    }
                })
                .show();

    }

    /**
     * Metodo que permite volver a la pantalla anterior.
     * @param v Boton volver
     */
    public void back(View v){
        Intent intent = new Intent(PantallaResultados.this, PantallaPrincipal.class);
        startActivity(intent);

        finish();
    }

    /**
     * Metodo que se encarga de recoger el nombre del fichero de las preferencias de la aplicacion.
     * @return Devuelve el nombre del fichero
     */
    public String nombreFichero(){
        //Se accede a las preferencias de la aplicacion
        SharedPreferences prefs = getSharedPreferences("MisPreferencias", MODE_PRIVATE);
        String nombre;

        //Se recoge el nombre del fichero
        nombre = prefs.getString("nombreFichero", "ejecuciones.txt");

        return nombre;
    }

    //Guardamos los datos de la aplicación
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        cargarIdioma();
    }
    //Cuando la aplicacion se restaura se cargan los datos que habias guardado previamente
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    /**
     * Metodo que se encarga de cargar el idioma de la aplicacion.
     * Se ejecuta al girar el dispositivo.
     */
    public void cargarIdioma(){
        SharedPreferences prefs = getSharedPreferences("MisPreferencias", MODE_PRIVATE);
        String idioma = prefs.getString("idioma", "spanish");

        Resources res = this.getApplicationContext().getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        android.content.res.Configuration conf = res.getConfiguration();
        Locale loc;

        if(idioma.equals("spanish")) {

            loc = new Locale("es");
        }
        else{
            loc = new Locale("en");
        }

        conf.setLocale(loc);
        res.updateConfiguration(conf, dm);
    }

    /**
     * Metodo que se encarga de volver a la pantalla anterior al pulsar en el boton atras del dispositivo
     * @param keyCode Codigo del boton pulsado
     * @param event Pulsacion
     * @return Devuelve true si se ejecuta con exito
     */
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) { //Si el codigo del boton pulsado es igual que el codigo del boton atras..

            //Se muestra la pantalla anterior y se cierra la actual
            Intent intent = new Intent(PantallaResultados.this, PantallaPrincipal.class);
            startActivity(intent);

            finish();
        }
        //para las demas cosas, se reenvia el evento al listener habitual
        return super.onKeyDown(keyCode, event);
    }
}

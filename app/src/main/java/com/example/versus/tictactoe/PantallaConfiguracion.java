package com.example.versus.tictactoe;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import java.util.Locale;

public class PantallaConfiguracion extends AppCompatActivity {

    //Se declaran las variables que se van a utilizar
    private RadioButton _idiomaSpanish;
    private RadioButton _idiomaEnglish;
    private CheckBox _SD;
    private EditText _fichero;

    /**
     * Metodo que se ejecuta al iniciar la Activity, se instancian las variables y se cargan las preferencias
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.configuracion);

        _idiomaSpanish = (RadioButton) findViewById(R.id.rbEspañol);
        _idiomaEnglish = (RadioButton) findViewById(R.id.rbIngles);
        _SD = (CheckBox) findViewById(R.id.cbSD);
        _fichero = (EditText) findViewById(R.id.etFileName);

        cargarPrefs(); //Se cargan las preferencias
    }

    /**
     * Metodo que se encarga de cargar las preferencias que tiene la aplicacion
     * y las muestra en su sitio correspondiente.
     */
    public void cargarPrefs(){
        //Se accede a las preferencias
        SharedPreferences prefs = getSharedPreferences("MisPreferencias", MODE_PRIVATE);

        //Se recogen los datos necesarios y se ponen en su lugar
        String idioma = prefs.getString("idioma", "spanish");

        if(idioma.equals("spanish")){
            _idiomaSpanish.setChecked(true);
        }
        else{
            _idiomaEnglish.setChecked(true);
        }

        _SD.setChecked(prefs.getBoolean("guardarSD", true));

        _fichero.setText(prefs.getString("nombreFichero", "ejecuciones.txt"));

    }


    /**
     * Metodo que se encarga de guardar las preferencias de la pantalla en las preferencais de la aplicacion
     * @param v Boton Guardar
     */
    public void guardarPrefs(View v){
        if(!recogerFichero().equals("")){ //Si el nombre del fichero no esta vacio..
            //Se accede a las preferencias
            SharedPreferences prefs = getSharedPreferences("MisPreferencias", MODE_PRIVATE);
            //Se editan las preferencias
            SharedPreferences.Editor editor = prefs.edit();

            editor.putString("idioma", recogerIdioma());
            editor.putBoolean("guardarSD", recogerLocSD());
            editor.putString("nombreFichero", recogerFichero());
            editor.commit();

            //Se reinicia la pantalla
            finish();
            startActivity(getIntent());
        }
        else{ //Si esta vacio..
            //Se notifica al usuario
            Toast.makeText(this, getResources().getString(R.string.ficheroVacio), Toast.LENGTH_SHORT).show();
        }

    }

    /**
     * Metodo que se encarga de recoger el idioma de la pantalla y cambiarlo en la aplicacion
     * @return Devuelve un String con el idioma
     */
    public String recogerIdioma(){
        String idioma;
        //Se recogen los recursos y la configuracion de la aplicacion
        Resources res = this.getApplicationContext().getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        android.content.res.Configuration conf = res.getConfiguration();
        Locale loc;

        if(_idiomaSpanish.isChecked()) { //Si el RadioButton de Español esta clickado..
            idioma = "spanish";
            loc = new Locale("es"); //Se crea un nuevo local "es"
        }
        else{
            idioma = "english";
            loc = new Locale("en");
        }

        //Se establece el idioma en la configuracion de la aplicacion
        conf.setLocale(loc);
        res.updateConfiguration(conf, dm);

        return idioma;
    }

    /**
     * Metodo que se encarga de recoger el estado del boton de tarjeta SD
     * @return Devuelve si esta clicado o no.
     */
    public boolean recogerLocSD(){
        boolean loc;

        if(_SD.isChecked()) {
            loc = true;
        }
        else{
            loc = false;
        }

        return loc;
    }


    /**
     * Metodo que se encarga de recoger el nombre del fichero escrito en pantalla
     * @return devuelve el nombre del fichero
     */
    public String recogerFichero(){
        String fichero = _fichero.getText().toString();

        return fichero;
    }

    /**
     * Metodo que permite volver a la pantalla anterior.
     * @param v Boton volver
     */
    public void back(View v){
        //Se crea un intent con la nueva pantalla
        Intent intent = new Intent(PantallaConfiguracion.this, PantallaPrincipal.class);
        startActivity(intent);

        //Se cierra la pantalla actual
        finish();
    }

    /**
     * Metodo que se encarga de comprobar si el dispositivo tiene o no tarjeta SD.
     * Se ejecuta al interaccionar con el CheckBox tarjetaSD
     * @param v CheckBox tarjetaSD
     */
    public void comprobarSD(View v){
        boolean sdDisponible = false;
        boolean sdAccesoEscritura = false;

        //Comprobamos el estado de la memoria externa (tarjeta SD)
        String estado = Environment.getExternalStorageState();

        if (estado.equals(Environment.MEDIA_MOUNTED)) { //Si esta la tarjeta montada..
            sdDisponible = true;
            sdAccesoEscritura = true;
        }
        else if (estado.equals(Environment.MEDIA_MOUNTED_READ_ONLY)) { //Si solo permite modo lectura..
            sdDisponible = true;
            sdAccesoEscritura = false;
        }
        else {
            sdDisponible = false;
            sdAccesoEscritura = false;
        }

        if(!sdDisponible){
            _SD.setChecked(false);
            Toast.makeText(this, "No hay tarjeta SD disponible", Toast.LENGTH_SHORT).show();
        }
        else if(sdDisponible && !sdAccesoEscritura){
            _SD.setChecked(false);
            Toast.makeText(this, "No esta permitido escribir en la tarjeta SD", Toast.LENGTH_SHORT).show();
        }
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {

        Intent intent = new Intent(PantallaConfiguracion.this, PantallaPrincipal.class);
        startActivity(intent);

        finish();
        }
        //para las demas cosas, se reenvia el evento al listener habitual
        return super.onKeyDown(keyCode, event);
    }
    //Guardamos los datos de la aplicación
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putBoolean("btnSpanishChecked", _idiomaSpanish.isChecked());
        outState.putBoolean("btnEnglishChecked", _idiomaEnglish.isChecked());

        outState.putBoolean("btnSDChecked", _SD.isChecked());

        cargarIdioma();
    }

    //Cuando la aplicacion se restaura se cargan los datos que habias guardado previamente
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        _idiomaSpanish.setChecked(savedInstanceState.getBoolean("btnSpanishChecked"));
        _idiomaEnglish.setChecked(savedInstanceState.getBoolean("btnEnglishChecked"));
        _SD.setChecked(savedInstanceState.getBoolean("btnSDChecked"));

    }

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

}

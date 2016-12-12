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

    private RadioButton _idiomaSpanish;
    private RadioButton _idiomaEnglish;
    private CheckBox _SD;
    private EditText _fichero;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.configuracion);

        _idiomaSpanish = (RadioButton) findViewById(R.id.rbEspañol);
        _idiomaEnglish = (RadioButton) findViewById(R.id.rbIngles);
        _SD = (CheckBox) findViewById(R.id.cbSD);
        _fichero = (EditText) findViewById(R.id.etFileName);

        cargarPrefs();
    }

    public void cargarPrefs(){
        SharedPreferences prefs = getSharedPreferences("MisPreferencias", MODE_PRIVATE);

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


    public void guardarPrefs(View v){
        if(recogerFichero().equals("")){
            SharedPreferences prefs = getSharedPreferences("MisPreferencias", MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();

            editor.putString("idioma", recogerIdioma());
            editor.putBoolean("guardarSD", recogerLocSD());
            editor.putString("nombreFichero", recogerFichero());
            editor.commit();

            finish();
            startActivity(getIntent());
        }
        else{
            Toast.makeText(this, getResources().getString(R.string.ficheroVacio), Toast.LENGTH_SHORT).show();
        }

    }

    public String recogerIdioma(){
        String idioma;
        Resources res = this.getApplicationContext().getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        android.content.res.Configuration conf = res.getConfiguration();
        Locale loc;

        if(_idiomaSpanish.isChecked()) {
            idioma = "spanish";
            loc = new Locale("es");
        }
        else{
            idioma = "english";
            loc = new Locale("en");
        }

        conf.setLocale(loc);
        res.updateConfiguration(conf, dm);

        return idioma;
    }

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


    public String recogerFichero(){
        String fichero = _fichero.getText().toString();

        return fichero;
    }

    public void back(View v){
        Intent intent = new Intent(PantallaConfiguracion.this, PantallaPrincipal.class);
        startActivity(intent);

        finish();
    }

    public void comprobarSD(View v){
        boolean sdDisponible = false;
        boolean sdAccesoEscritura = false;

        //Comprobamos el estado de la memoria externa (tarjeta SD)
        String estado = Environment.getExternalStorageState();

        if (estado.equals(Environment.MEDIA_MOUNTED)) {
            sdDisponible = true;
            sdAccesoEscritura = true;
        }
        else if (estado.equals(Environment.MEDIA_MOUNTED_READ_ONLY)) {
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

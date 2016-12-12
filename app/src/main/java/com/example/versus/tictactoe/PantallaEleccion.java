package com.example.versus.tictactoe;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import java.util.Locale;

public class PantallaEleccion extends AppCompatActivity {

    Jugador jugador1;
    Jugador jugador2;

    EditText _nombreJ1;
    EditText _nombreJ2;
    RadioButton _rbOrangeJug1;
    RadioButton _rbOrangeJug2;
    RadioButton _rbGreenJug1;
    RadioButton _rbGreenJug2;
    RadioButton _rbBlueJug1;
    RadioButton _rbBlueJug2;


    /**
     * Metodo que se ejecuta al iniciar la Activity, se instancian las variables y se cargan algunos atributos de los objetos
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pantalla_eleccion);

        _nombreJ1 = (EditText) findViewById(R.id.etNomJug1);
        _nombreJ2 = (EditText) findViewById(R.id.etNomJug2);
        _rbOrangeJug1 = (RadioButton) findViewById(R.id.rbOrangeJug1);
        _rbOrangeJug2 = (RadioButton) findViewById(R.id.rbOrangeJug2);
        _rbGreenJug1 = (RadioButton) findViewById(R.id.rbGreenJug1);
        _rbGreenJug2 = (RadioButton) findViewById(R.id.rbGreenJug2);
        _rbBlueJug1 = (RadioButton) findViewById(R.id.rbBlueJug1);
        _rbBlueJug2 = (RadioButton) findViewById(R.id.rbBlueJug2);

        _rbOrangeJug1.setChecked(true);
        _rbOrangeJug2.setEnabled(false);
        _rbGreenJug2.setChecked(true);
        _rbGreenJug1.setEnabled(false);

        jugador1 = new Jugador("X");
        jugador2 = new Jugador("O");

        jugador1.setColor("NARANJA");
        jugador2.setColor("VERDE");
    }

    /**
     * Metodo que se encarga de establecer los nombres de los jugadores y mostrar la pantalla de PantallPartida.
     * Se ejecuta al hacer click en el boton de Jugar
     * @param v Boton Jugar
     */
    public void goPlay(View v){
        if(comprobarNombres()){ //Si los nombre son validos..
            //Se asignan los nombres a los jugadores
            jugador1.setNombre(_nombreJ1.getText().toString());
            jugador2.setNombre(_nombreJ2.getText().toString());
            //Se inicia la pantalla de PantallaPartida
            Intent intent = new Intent(PantallaEleccion.this, PantallaPartida.class);
            intent.putExtra("jugador1", jugador1);
            intent.putExtra("jugador2", jugador2);
            startActivity(intent);
        }
    }

    /**
     * Metodo que se encarga de establecer el color de cada jugador cuando se selecciona.
     * Se ejecuta al clicar un RadioButton de color.
     * @param rb RadioButton clicado.
     */
    public void putColor(View rb){
        switch (rb.getId()) { //Switch para identificar el RadioButton clicado.
            case R.id.rbOrangeJug1: //Si se clica el Naranja del Jugador 1..
                _rbOrangeJug2.setEnabled(false); //Se deshabilita ese color para el otro jugador
                _rbGreenJug2.setEnabled(true); //Se habilita el color verde para el otro jugador
                _rbBlueJug2.setEnabled(true);//Se habilita el color verde para el otro jugador
                jugador1.setColor("NARANJA");
                break;
            case R.id.rbGreenJug1:
                _rbOrangeJug2.setEnabled(true);
                _rbGreenJug2.setEnabled(false);
                _rbBlueJug2.setEnabled(true);
                jugador1.setColor("VERDE");
                break;
            case R.id.rbBlueJug1:
                _rbOrangeJug2.setEnabled(true);
                _rbGreenJug2.setEnabled(true);
                _rbBlueJug2.setEnabled(false);
                jugador1.setColor("AZUL");
                break;
            case R.id.rbOrangeJug2:
                _rbOrangeJug1.setEnabled(false);
                _rbGreenJug1.setEnabled(true);
                _rbBlueJug1.setEnabled(true);
                jugador2.setColor("NARANJA");
                break;
            case R.id.rbGreenJug2:
                _rbOrangeJug1.setEnabled(true);
                _rbGreenJug1.setEnabled(false);
                _rbBlueJug1.setEnabled(true);
                jugador2.setColor("VERDE");
                break;
            case R.id.rbBlueJug2:
                _rbOrangeJug1.setEnabled(true);
                _rbGreenJug1.setEnabled(true);
                _rbBlueJug1.setEnabled(false);
                jugador2.setColor("AZUL");
                break;
        }
    }

    public boolean comprobarNombres(){
        boolean correcto = true;
        if(_nombreJ1.getText().toString().equals("") || _nombreJ2.getText().toString().equals("")){
            Toast.makeText(this, "No se han introducido nombres", Toast.LENGTH_SHORT).show();
            correcto = false;
        }
        else if(_nombreJ1.getText().toString().equals(_nombreJ2.getText().toString())){
            Toast.makeText(this, "Los nombres no pueden ser iguales", Toast.LENGTH_SHORT).show();
            correcto = false;
        }

        return correcto;
    }

    public void goBack(View v){
        finish();
        Intent intent = new Intent(PantallaEleccion.this, PantallaPrincipal.class);
        startActivity(intent);
    }

    //Guardamos los datos de la aplicación
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString("colorJ1", jugador1.getColor());
        outState.putString("colorJ2", jugador2.getColor());

        outState.putBoolean("OJ1Checked", _rbOrangeJug1.isChecked());
        outState.putBoolean("OJ2Checked", _rbOrangeJug2.isChecked());
        outState.putBoolean("GJ1Checked", _rbGreenJug1.isChecked());
        outState.putBoolean("GJ2Checked", _rbGreenJug2.isChecked());
        outState.putBoolean("BJ1Checked", _rbBlueJug1.isChecked());
        outState.putBoolean("BJ2Checked", _rbBlueJug2.isChecked());

        outState.putBoolean("OJ1Enabled", _rbOrangeJug1.isEnabled());
        outState.putBoolean("OJ2Enabled", _rbOrangeJug2.isEnabled());
        outState.putBoolean("GJ1Enabled", _rbGreenJug1.isEnabled());
        outState.putBoolean("GJ2Enabled", _rbGreenJug2.isEnabled());
        outState.putBoolean("BJ1Enabled", _rbBlueJug1.isEnabled());
        outState.putBoolean("BJ2Enabled", _rbBlueJug2.isEnabled());

        cargarIdioma();
    }
    //Cuando la aplicacion se restaura se cargan los datos que habias guardado previamente
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        jugador1.setColor(savedInstanceState.getString("colorJ1"));
        jugador2.setColor(savedInstanceState.getString("colorJ2"));

        _rbOrangeJug1.setChecked(savedInstanceState.getBoolean("OJ1Checked"));
        _rbOrangeJug2.setChecked(savedInstanceState.getBoolean("OJ2Checked"));
        _rbGreenJug1.setChecked(savedInstanceState.getBoolean("GJ1Checked"));
        _rbGreenJug2.setChecked(savedInstanceState.getBoolean("GJ2Checked"));
        _rbBlueJug1.setChecked(savedInstanceState.getBoolean("BJ1Checked"));
        _rbBlueJug2.setChecked(savedInstanceState.getBoolean("BJ2Checked"));

        _rbOrangeJug1.setEnabled(savedInstanceState.getBoolean("OJ1Enabled"));
        _rbOrangeJug2.setEnabled(savedInstanceState.getBoolean("OJ2Enabled"));
        _rbGreenJug1.setEnabled(savedInstanceState.getBoolean("GJ1Enabled"));
        _rbGreenJug2.setEnabled(savedInstanceState.getBoolean("GJ2Enabled"));
        _rbBlueJug1.setEnabled(savedInstanceState.getBoolean("BJ1Enabled"));
        _rbBlueJug2.setEnabled(savedInstanceState.getBoolean("BJ2Enabled"));
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

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Intent intent = new Intent(PantallaEleccion.this, PantallaPrincipal.class);
        startActivity(intent);

        finish();

        //para las demas cosas, se reenvia el evento al listener habitual
        return super.onKeyDown(keyCode, event);
    }

}

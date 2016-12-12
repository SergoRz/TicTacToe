package com.example.versus.tictactoe;

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

import java.util.Locale;

public class PantallaPrincipal extends AppCompatActivity {

    /**
     * Metodo que se ejecuta al iniciar la Activity, se instancian las variables y se carga el idioma
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cargarIdioma();
        setContentView(R.layout.menu_principal);
    }

    /**
     * Metodo que se encarga de inciar la Activity PantallaConfiguracion y cerrar la actual
     * Se ejecuta al hacer click en el boton de configuracion
     * @param v Boton Configuracion
     */
    public void goConfig(View v){
        Intent intent = new Intent(PantallaPrincipal.this, PantallaConfiguracion.class);
        startActivity(intent);
        finish();
    }

    /**
     * Metodo que se encarga de inciar la Activity PantallaEleccion y cerrar la actual
     * Se ejecuta al hacer click en el boton de configuracion
     * @param v Boton Play
     */
    public void goChoose(View v){
        Intent intent = new Intent(PantallaPrincipal.this, PantallaEleccion.class);
        startActivity(intent);
        finish();
    }

    /**
     * Metodo que se encarga de inciar la Activity PantallaResultados y cerrar la actual
     * Se ejecuta al hacer click en el boton de configuracion
     * @param v Boton Resultados
     */
    public void goRes(View v){
        Intent intent = new Intent(PantallaPrincipal.this, PantallaResultados.class);
        startActivity(intent);
        finish();
    }

    /**
     * Metodo que se encarga de cargar el idioma de la aplicacion.
     * Se ejecuta al girar el dispositivo.
     */
    public void cargarIdioma(){
        //Se accede a las preferencias y se establece el idioma
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
            new AlertDialog.Builder(this) //Se muestra un dialog para confirmar la salida
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle(getResources().getString(R.string.salir))
                    .setMessage(getResources().getString(R.string.salirApp))
                    .setNegativeButton(android.R.string.cancel, null)//sin listener
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {//un listener que al pulsar, cierre la aplicacion
                        @Override
                        public void onClick(DialogInterface dialog, int which){ //Si se pulsa aceptar..
                            //Salir
                            PantallaPrincipal.this.finish();
                        }
                    })
                    .show();
            // Si el listener devuelve true, significa que el evento esta procesado, y nadie debe hacer nada mas
            return true;
        }
        //para las demas cosas, se reenvia el evento al listener habitual
        return super.onKeyDown(keyCode, event);
    }
}

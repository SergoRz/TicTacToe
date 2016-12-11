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
import android.view.Window;
import android.widget.Toast;

import java.util.ArrayList;

import java.util.Locale;

public class PantallaPrincipal extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cargarIdioma();
        setContentView(R.layout.menu_principal);

    }

    public void goConfig(View v){
        finish();
        Intent intent = new Intent(PantallaPrincipal.this, Configuracion.class);
        startActivity(intent);
    }

    public void goChoose(View v){
        finish();
        Intent intent = new Intent(PantallaPrincipal.this, PantallaEleccion.class);
        startActivity(intent);
    }

    public void goRes(View v){
        finish();
        Intent intent = new Intent(PantallaPrincipal.this, resultados.class);
        startActivity(intent);
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
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle(getResources().getString(R.string.salir))
                    .setMessage(getResources().getString(R.string.salirApp))
                    .setNegativeButton(android.R.string.cancel, null)//sin listener
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {//un listener que al pulsar, cierre la aplicacion
                        @Override
                        public void onClick(DialogInterface dialog, int which){
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

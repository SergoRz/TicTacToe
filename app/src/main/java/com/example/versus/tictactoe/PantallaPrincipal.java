package com.example.versus.tictactoe;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
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
}

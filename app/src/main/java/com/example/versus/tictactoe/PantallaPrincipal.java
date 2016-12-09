package com.example.versus.tictactoe;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

public class PantallaPrincipal extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_principal);
    }

    public void goConfig(View v){
        Intent intent = new Intent(PantallaPrincipal.this, Configuracion.class);
        startActivity(intent);
    }

    public void goPlay(View v){
        Intent intent = new Intent(this, ActivityPartida.class);
        startActivity(intent);
    }
}

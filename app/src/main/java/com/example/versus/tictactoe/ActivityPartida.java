package com.example.versus.tictactoe;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class ActivityPartida extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partida);
        Jugador j1 = null;
        Jugador j2 = null;
        Partida p = new Partida(j1,j2);
    }

    public void colocarPieza(){

    }

}

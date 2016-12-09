package com.example.versus.tictactoe;

import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class ActivityPartida extends AppCompatActivity {

    Jugador j1 = null;
    Jugador j2 = null;
    int turno;
    Partida partida = new Partida(j1,j2);
    ArrayList<Pieza> tablero = new ArrayList<Pieza>();
    TextView TVTurno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partida);
        turno = 1;
        TVTurno = (TextView) findViewById(R.id.turno);
        j1 = new Jugador("Sergio", "NARANJA");
        j2 = new Jugador("Emilio", "VERDE");
        crearTablero();
    }


    public void crearTablero(){
        //Creacion de las piezas
        Pieza p11 = new Pieza(11);
        Pieza p12 = new Pieza(12);
        Pieza p13 = new Pieza(13);

        Pieza p21 = new Pieza(21);
        Pieza p22 = new Pieza(22);
        Pieza p23 = new Pieza(23);

        Pieza p31 = new Pieza(31);
        Pieza p32 = new Pieza(32);
        Pieza p33 = new Pieza(33);

        tablero.add(p11);
        tablero.add(p12);
        tablero.add(p13);
        tablero.add(p21);
        tablero.add(p22);
        tablero.add(p23);
        tablero.add(p31);
        tablero.add(p32);
        tablero.add(p33);
    }
    public void colocarPieza(View v){
        Pieza piezaSeleccionada = null;
        Button botonPulsado = (Button) findViewById(v.getId());

        for(Pieza p : tablero){
            if(("p" + String.valueOf(p.getNumero())).equals(v.getId())){
                piezaSeleccionada = p;
                break;
            }
        }

        if(botonPulsado.getBackground().equals("@drawable/btn_default_material")){
            if(turno == 1){
                j1.getCombinacion().combinacion.add(piezaSeleccionada);
                turno = 2;
                colorearPieza(botonPulsado, piezaSeleccionada, j1);
            }else{
                j2.getCombinacion().combinacion.add(piezaSeleccionada);
                turno = 1;
                colorearPieza(botonPulsado, piezaSeleccionada, j2);
            }
        } else{
            //toast no hagas trampa
        }
    }

    public void colorearPieza(Button botonPulsado, Pieza piezaSeleccionada, Jugador jugador){
        jugador.getCombinacion().combinacion.add(piezaSeleccionada);
        switch (jugador.getColor().toUpperCase()){
            case "NARANJA":
                botonPulsado.setBackground(Drawable.createFromPath("@drawable/holo_orange_dark"));
                break;
            case "VERDE":
                botonPulsado.setBackground(Drawable.createFromPath("@drawable/holo_green_dark"));
                break;
            case "AZUL":
                botonPulsado.setBackground(Drawable.createFromPath("@drawable/holo_blue_dark"));
                break;
        }
        TVTurno.setText("TURNO " + jugador.getNombre().toUpperCase());
    }
}

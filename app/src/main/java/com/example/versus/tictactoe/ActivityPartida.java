package com.example.versus.tictactoe;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
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
        j1 = new Jugador("Sergio", "NARANJA", "X");
        j2 = new Jugador("Emilio", "VERDE", "O");
        turno = 1;
        TVTurno = (TextView) findViewById(R.id.turno);
        TVTurno.setText("TURNO " + j1.getNombre().toUpperCase());
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

        switch (j1.getColor().toUpperCase()){
            case "NARANJA":
                TVTurno.setTextColor(Color.parseColor("#FF9D09"));
                break;
            case "VERDE":
                TVTurno.setTextColor(Color.parseColor("#12B81D"));
                break;
            case "AZUL":
                TVTurno.setTextColor(Color.parseColor("#0D7CE5"));
                break;
        }
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

        ColorStateList mList = botonPulsado.getTextColors();
        int color = mList.getDefaultColor();
        //Log.d(String.valueOf(color),"Entramos en onCreate");
        //Toast.makeText(this, String.valueOf(color),Toast.LENGTH_SHORT).show();

        if(color == Color.BLACK){
            if(turno == 1){
                j1.getCombinacion().combinacion.add(piezaSeleccionada);
                turno = 2;
                colorearPieza(botonPulsado, piezaSeleccionada, j1, j2);
            }else{
                j2.getCombinacion().combinacion.add(piezaSeleccionada);
                turno = 1;
                colorearPieza(botonPulsado, piezaSeleccionada, j2, j1);
            }
        } else{
            Toast.makeText(this, "¡No hagas trampas!", Toast.LENGTH_SHORT).show();
        }
    }

    public void colorearPieza(Button botonPulsado, Pieza piezaSeleccionada, Jugador jugadorActual, Jugador siguienteJugador ){
        jugadorActual.getCombinacion().combinacion.add(piezaSeleccionada);
        switch (jugadorActual.getColor().toUpperCase()){
            case "NARANJA":
                botonPulsado.setTextColor(Color.parseColor("#FF9D09"));
                break;
            case "VERDE":
                botonPulsado.setTextColor(Color.parseColor("#12B81D"));
                break;
            case "AZUL":
                botonPulsado.setTextColor(Color.parseColor("#0D7CE5"));
                break;
        }

        actualizarColorRotulo(siguienteJugador);
        botonPulsado.setText(jugadorActual.getSimbolo());
        TVTurno.setText("TURNO " + siguienteJugador.getNombre().toUpperCase());
    }

    public void actualizarColorRotulo(Jugador siguienteJugador){
        switch (siguienteJugador.getColor().toUpperCase()){
            case "NARANJA":
                TVTurno.setTextColor(Color.parseColor("#FF9D09"));
                break;
            case "VERDE":
                TVTurno.setTextColor(Color.parseColor("#12B81D"));
                break;
            case "AZUL":
                TVTurno.setTextColor(Color.parseColor("#0D7CE5"));
                break;
        }
    }

    public void salir(View v){
        //Con esto solo vuele al menu pricipal, no cierra la APP
        finish();
    }

    public void volver(View v){
        finish();
    }
}

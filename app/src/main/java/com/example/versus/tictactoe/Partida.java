package com.example.versus.tictactoe;

import android.util.Log;

import java.util.ArrayList;

public class Partida{

    private Jugador j1;
    private Jugador j2;
    private ArrayList<Combinacion> combinacionesGanadoras = new ArrayList<Combinacion> ();

    public Partida(Jugador j1, Jugador j2) {
        this.j1 = j1;
        this.j2 = j2;
        iniciarPartida();
    }

    public void iniciarPartida(){
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

        //Creacion de las combinaciones ganadoras
        ArrayList<Pieza> aP1 = new ArrayList<Pieza>();
        aP1.add(p11);
        aP1.add(p12);
        aP1.add(p13);
        Combinacion cGanadora1 = new Combinacion(aP1);

        ArrayList<Pieza> aP2 = new ArrayList<Pieza>();
        aP2.add(p21);
        aP2.add(p22);
        aP2.add(p23);
        Combinacion cGanadora2 = new Combinacion(aP2);

        ArrayList<Pieza> aP3 = new ArrayList<Pieza>();
        aP3.add(p31);
        aP3.add(p32);
        aP3.add(p33);
        Combinacion cGanadora3 = new Combinacion(aP3);

        ArrayList<Pieza> aP4 = new ArrayList<Pieza>();
        aP4.add(p11);
        aP4.add(p21);
        aP4.add(p31);
        Combinacion cGanadora4 = new Combinacion(aP4);

        ArrayList<Pieza> aP5 = new ArrayList<Pieza>();
        aP5.add(p12);
        aP5.add(p22);
        aP5.add(p32);
        Combinacion cGanadora5 = new Combinacion(aP5);

        ArrayList<Pieza> aP6 = new ArrayList<Pieza>();
        aP6.add(p13);
        aP6.add(p23);
        aP6.add(p33);
        Combinacion cGanadora6 = new Combinacion(aP6);

        ArrayList<Pieza> aP7 = new ArrayList<Pieza>();
        aP7.add(p11);
        aP7.add(p22);
        aP7.add(p33);
        Combinacion cGanadora7 = new Combinacion(aP7);

        ArrayList<Pieza> aP8 = new ArrayList<Pieza>();
        aP8.add(p31);
        aP8.add(p22);
        aP8.add(p13);
        Combinacion cGanadora8 = new Combinacion(aP8);

        combinacionesGanadoras.add(cGanadora1);
        combinacionesGanadoras.add(cGanadora2);
        combinacionesGanadoras.add(cGanadora3);
        combinacionesGanadoras.add(cGanadora4);
        combinacionesGanadoras.add(cGanadora5);
        combinacionesGanadoras.add(cGanadora6);
        combinacionesGanadoras.add(cGanadora7);
        combinacionesGanadoras.add(cGanadora8);
    }

    public boolean comprobarGanador(Jugador jugador){
        boolean igual = false;
        Log.d("Combinacion " + jugador.getNombre(), jugador.getCombinacion().toString());
        if(jugador.getCombinacion().combinacion.size() > 2) {
            for(Combinacion cPartida: combinacionesGanadoras){
                Log.d("Combinacion partida", cPartida.toString());
                if(jugador.getCombinacion().equals(cPartida)){
                    Log.d("MSG:", "Dentro de if1");
                    igual = true;
                    break;
                }
            }
        }

        return igual;
    }

}

package com.example.versus.tictactoe;

import java.util.ArrayList;

public class Partida{

    private Jugador j1;
    private Jugador j2;
    private ArrayList<Combinacion> combinacinesGanadoras = new ArrayList<Combinacion> ();

    public Partida(Jugador j1, Jugador j2) {
        this.j1 = j1;
        this.j2 = j2;
    }

    public void iniciarPartida(){
        //Creacion de las piezas
        Pieza p11 = new Pieza(1,1);
        Pieza p12 = new Pieza(1,2);
        Pieza p13 = new Pieza(1,3);

        Pieza p21 = new Pieza(2,1);
        Pieza p22 = new Pieza(2,2);
        Pieza p23 = new Pieza(2,3);

        Pieza p31 = new Pieza(3,1);
        Pieza p32 = new Pieza(3,2);
        Pieza p33 = new Pieza(3,3);

        //Creacion de las combinaciones ganadoras
        ArrayList<Pieza> aP1 = new ArrayList<Pieza>();

        //Combinacion cGanadora1 = new Combinacion;

    }

    public boolean comprobarSolucion(){

        return false;
    }

}

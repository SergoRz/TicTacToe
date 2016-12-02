package com.example.versus.tictactoe;

import java.util.ArrayList;

/**
 * Created by Versus on 02/12/2016.
 */

public class Partida {

    private Jugador j1;
    private Jugador j2;
    private ArrayList<Combinacion> combinacinesGanadoras = new ArrayList<Combinacion> ();

    public Partida(Jugador j1, Jugador j2) {
        this.j1 = j1;
        this.j2 = j2;
    }

    public void iniciarPartida(){
        Pieza p11 = new Pieza(1,1);
        Pieza p12 = new Pieza(1,2);
        Pieza p13 = new Pieza(1,3);

        Pieza p21 = new Pieza(2,1);
        Pieza p22 = new Pieza(2,2);
        Pieza p23 = new Pieza(2,3);

        Pieza p31 = new Pieza(3,1);
        Pieza p32 = new Pieza(3,2);
        Pieza p33 = new Pieza(3,3);


    }

}

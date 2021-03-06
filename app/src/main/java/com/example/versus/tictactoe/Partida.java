package com.example.versus.tictactoe;

import java.io.Serializable;
import java.util.ArrayList;

public class Partida implements Serializable{


    private Jugador j1; //Jugador 1 de la partida
    private Jugador j2; //Jugador 1 de la partida
    private ArrayList<Combinacion> combinacionesGanadoras = new ArrayList<Combinacion> (); //Combinaciones ganadoras de la partida

    public Partida(Jugador j1, Jugador j2) {
        this.j1 = j1;
        this.j2 = j2;
        iniciarPartida();
    }

    /**
     * Metodo que se encarga de generar todas las combinaciones ganadoras de la partida e introducirlas en el ArrayList
     * de combinacionesGanadoras
     */
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

        //Se añaden al array combinacionesGanadoras las combinaciones ganadoras
        combinacionesGanadoras.add(cGanadora1);
        combinacionesGanadoras.add(cGanadora2);
        combinacionesGanadoras.add(cGanadora3);
        combinacionesGanadoras.add(cGanadora4);
        combinacionesGanadoras.add(cGanadora5);
        combinacionesGanadoras.add(cGanadora6);
        combinacionesGanadoras.add(cGanadora7);
        combinacionesGanadoras.add(cGanadora8);
    }

    /**
     * Metodo que se encarga de comporbar si un jugador ha ganado la partida,
     * @param jugador Jugador que se comprueba
     * @return Devuelve true, si ha ganado, o false si no ha ganado
     */
    public boolean comprobarGanador(Jugador jugador){
        boolean igual = false;
        if(jugador.getCombinacion().combinacion.size() > 2) { //Si la combinacion del jugador tiene mas de dos piezas..
            for(Combinacion cPartida: combinacionesGanadoras){ //Se recorre las combinaciones ganadoras de la partida
                if(jugador.getCombinacion().equals(cPartida)){ //Si la combinacion del jugador coincide con alguna ganadora..
                    igual = true; //Son iguales
                    break;
                }
            }
        }
        return igual;
    }

    public Jugador getJ1() {
        return j1;
    }

    public Jugador getJ2() {
        return j2;
    }


}

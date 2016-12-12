package com.example.versus.tictactoe;

import java.io.Serializable;
import java.util.ArrayList;


public class Combinacion implements Serializable{

    ArrayList<Pieza> combinacion; //ArayList de piezas, que forman una combinacion.

    public Combinacion(ArrayList<Pieza> combinacion) {
        this.combinacion = combinacion;
    }

    public Combinacion(){ this.combinacion = new ArrayList<Pieza>();}

    /**
     * Metodo que se encarga de comparar unca combinacion con la que se recibe por parametro.
     * @param combinacion1 Combinacion con la cual se compara
     * @return Devuelve true, si las piezas que contien son iguales, o false, si no son iguales.
     */
    public boolean equals(Combinacion combinacion1){
        boolean igualComb;
        int numPiezasEnComb = 0;

        for(Pieza p1: combinacion) { //Recorre las piezas de la combinacion
            for(Pieza p2: combinacion1.combinacion){ //Recorre las piezas de la combinacion recibida
                if(p1.equals(p2)){ //Si las piezas son iguales..
                    numPiezasEnComb++; //Se suma 1 al numero de piezas iguales
                    break;
                }
            }
        }

        if(numPiezasEnComb < 3) igualComb = false; //Si el numero de piezas es menor que 3 se devulve false
        else igualComb = true; //Si no se devuelve true

        return igualComb;
    }


    public String toString(){
        String cadena = "";
        for(Pieza p: combinacion)
            cadena = cadena + p.getNumero() + "/";

        return cadena;
    }
}

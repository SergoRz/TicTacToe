package com.example.versus.tictactoe;

import java.io.Serializable;
import java.util.ArrayList;

public class Combinacion implements Serializable{

    ArrayList<Pieza> combinacion;

    public Combinacion(ArrayList<Pieza> combinacion) {
        this.combinacion = combinacion;
    }

    public Combinacion(){ this.combinacion = new ArrayList<Pieza>();}

    public boolean equals(Combinacion combinacion1){
        boolean igualComb;
        int numPiezasEnComb = 0;

        for(Pieza p1: combinacion) {
            for(Pieza p2: combinacion1.combinacion){
                if(p1.equals(p2)){
                    numPiezasEnComb++;
                    break;
                }
            }
        }

        if(numPiezasEnComb < 3) igualComb = false;
        else igualComb = true;

        return igualComb;
    }

    public String toString(){
        String cadena = "";
        for(Pieza p: combinacion)
            cadena = cadena + p.getNumero() + "/";

        return cadena;
    }
}

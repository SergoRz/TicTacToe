package com.example.versus.tictactoe;

import java.util.ArrayList;

public class Combinacion {

    ArrayList<Pieza> combinacion;

    public Combinacion(ArrayList<Pieza> combinacion) {
        this.combinacion = combinacion;
    }

    public Combinacion(){ this.combinacion = new ArrayList<Pieza>();}

    public boolean equals(Combinacion combinacion1){
        boolean igual = true;

        for(Pieza p: combinacion) {
            if(!combinacion1.combinacion.contains(p)){
                igual = false;
            }
        }

        return igual;
    }
}

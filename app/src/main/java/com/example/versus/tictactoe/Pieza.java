package com.example.versus.tictactoe;


import java.io.Serializable;

public class Pieza implements Serializable{

    private int numero; //Numero de la pieza que le identifica

    public Pieza(int numero) {
        this.numero = numero;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public boolean equals(Pieza pieza){
        if(this.numero == pieza.getNumero()) return true;
        else return false;
    }
}

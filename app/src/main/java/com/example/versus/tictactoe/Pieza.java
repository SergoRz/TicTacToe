package com.example.versus.tictactoe;


public class Pieza {

    private int numero;

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

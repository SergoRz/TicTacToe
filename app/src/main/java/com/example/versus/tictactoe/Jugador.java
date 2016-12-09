package com.example.versus.tictactoe;


public class Jugador {

    private String nombre;
    private String color;
    private String simbolo;
    private Combinacion combinacion;

    public Jugador(String nombre, String color, String simbolo) {
        this.nombre = nombre;
        this.color = color;
        this.simbolo = simbolo;
        this.combinacion = new Combinacion();
    }

    public boolean equals(Jugador jugador){
        if(jugador.nombre.equals(this.nombre) && jugador.color.equals(this.color))return true;
        else return false;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Combinacion getCombinacion() {
        return combinacion;
    }

    public void setCombinacion(Combinacion combinacion) {
        this.combinacion = combinacion;
    }

    public String getSimbolo() {
        return simbolo;
    }

    public void setSimbolo(String simbolo) {
        this.simbolo = simbolo;
    }
}

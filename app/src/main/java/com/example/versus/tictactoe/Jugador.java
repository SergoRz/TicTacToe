package com.example.versus.tictactoe;


import android.os.Parcel;
import android.os.Parcelable;

public class Jugador implements Parcelable {

    private String nombre;
    private String color;
    private String simbolo;
    private Combinacion combinacion;

    public Jugador(String simbolo){
        this.simbolo = simbolo;
    }

    public Jugador(String nombre, String color, String simbolo) {
        this.nombre = nombre;
        this.color = color;
        this.simbolo = simbolo;
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

    protected Jugador(Parcel in) {
        nombre = in.readString();
        color = in.readString();
        simbolo = in.readString();
        combinacion = (Combinacion) in.readValue(Combinacion.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(nombre);
        dest.writeString(color);
        dest.writeString(simbolo);
        dest.writeValue(combinacion);
    }


    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Jugador> CREATOR = new Parcelable.Creator<Jugador>() {
        @Override
        public Jugador createFromParcel(Parcel in) {
            return new Jugador(in);
        }

        @Override
        public Jugador[] newArray(int size) {
            return new Jugador[size];
        }
    };
}

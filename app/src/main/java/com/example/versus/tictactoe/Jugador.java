package com.example.versus.tictactoe;


import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class Jugador implements Parcelable {

    private String nombre; //Nombre del jugador
    private String color; //Color del jugador
    private String simbolo; //Simbolo del jugador
    private Combinacion combinacion; //Combinacion del jugador

    public Jugador(String simbolo){
        this.simbolo = simbolo;
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

    /**
     * Recrea un parcel con el objeto Jugador y le asigna los valores
     * @param in Parcel que recibe
     */
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

    /**
     * Se encarga de escribir los datos del objeto al Parcel
     * @param dest Parcelo donde se escribe
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(nombre);
        dest.writeString(color);
        dest.writeString(simbolo);
        dest.writeValue(combinacion);
    }

    /**
     * Creador del Parcelable
     */
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

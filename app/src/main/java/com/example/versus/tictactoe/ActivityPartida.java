package com.example.versus.tictactoe;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ActivityPartida extends AppCompatActivity {

    Jugador j1 = null;
    Jugador j2 = null;
    int turno;
    Partida partida = new Partida(j1,j2);
    ArrayList<Pieza> tablero = new ArrayList<Pieza>();
    TextView TVTurno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partida);
        j1 = new Jugador("Sergio", "NARANJA", "X");
        j2 = new Jugador("Emilio", "VERDE", "O");
        turno = 1;
        TVTurno = (TextView) findViewById(R.id.turno);
        TVTurno.setText("TURNO " + j1.getNombre().toUpperCase());
        crearTablero();

    }

    public void crearTablero(){
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

        tablero.add(p11);
        tablero.add(p12);
        tablero.add(p13);
        tablero.add(p21);
        tablero.add(p22);
        tablero.add(p23);
        tablero.add(p31);
        tablero.add(p32);
        tablero.add(p33);

        switch (j1.getColor().toUpperCase()){
            case "NARANJA":
                TVTurno.setTextColor(Color.parseColor("#FF9D09"));
                break;
            case "VERDE":
                TVTurno.setTextColor(Color.parseColor("#12B81D"));
                break;
            case "AZUL":
                TVTurno.setTextColor(Color.parseColor("#0D7CE5"));
                break;
        }
    }
    public void colocarPieza(View v){
        Button botonPulsado = (Button) findViewById(v.getId());

        ColorStateList mList = botonPulsado.getTextColors();
        int color = mList.getDefaultColor();

        if(color == Color.BLACK){
            if(turno == 1){
                turno = 2;
                addPiezaJugador(v, j1);
                colorearPieza(botonPulsado, j1, j2);
            }else{
                turno = 1;
                addPiezaJugador(v, j2);
                colorearPieza(botonPulsado, j2, j1);
            }
        } else{
            Toast.makeText(this, "¡No hagas trampas!", Toast.LENGTH_SHORT).show();
        }
    }

    public void colorearPieza(Button botonPulsado, Jugador jugadorActual, Jugador siguienteJugador ){
        switch (jugadorActual.getColor().toUpperCase()){
            case "NARANJA":
                botonPulsado.setTextColor(Color.parseColor("#FF9D09"));
                break;
            case "VERDE":
                botonPulsado.setTextColor(Color.parseColor("#12B81D"));
                break;
            case "AZUL":
                botonPulsado.setTextColor(Color.parseColor("#0D7CE5"));
                break;
        }

        mostrarGanador(jugadorActual, siguienteJugador);
        actualizarColorRotulo(siguienteJugador);
        botonPulsado.setText(jugadorActual.getSimbolo());
        TVTurno.setText("TURNO " + siguienteJugador.getNombre().toUpperCase());
    }

    public void actualizarColorRotulo(Jugador siguienteJugador){
        switch (siguienteJugador.getColor().toUpperCase()){
            case "NARANJA":
                TVTurno.setTextColor(Color.parseColor("#FF9D09"));
                break;
            case "VERDE":
                TVTurno.setTextColor(Color.parseColor("#12B81D"));
                break;
            case "AZUL":
                TVTurno.setTextColor(Color.parseColor("#0D7CE5"));
                break;
        }
    }

    public void salir(View v){
        //Con esto solo vuele al menu pricipal, no cierra la APP
        finish();
    }

    public void volver(View v){
        finish();
    }

    public void mostrarGanador(Jugador jugadorAcutal, Jugador siguienteJugador){
        //Log.d("Combinacion " + jugador.getNombre(), jugador.getCombinacion().toString());
        if(partida.comprobarGanador(jugadorAcutal))
            Toast.makeText(this, "Ganador: " + jugadorAcutal.getNombre(), Toast.LENGTH_SHORT).show();
            guardarPartida(jugadorAcutal, siguienteJugador);
    }

    public void guardarPartida(Jugador ganador, Jugador perdedor){
        SharedPreferences prefs = getSharedPreferences("MisPreferencias", MODE_PRIVATE);
        String resultado = getDateTime() + ": " + ganador.getNombre() + " ha ganado a " + perdedor.getNombre();

        if(prefs.getBoolean("guardarSD", true)){
            guardarPartidaSD(resultado);
        }
        if(prefs.getBoolean("guardarMI", true)){
            guardarPartidaMI(resultado);
        }
    }

    public void guardarPartidaMI(String resultado){
        try {
            OutputStreamWriter osw = new OutputStreamWriter(openFileOutput(nombreFichero(), Context.MODE_PRIVATE));
            osw.write(resultado);
            osw.close();
        }
        catch (IOException e) {
            Toast.makeText(this, "No se ha podido guardar la partida en la memoria del telefono", Toast.LENGTH_SHORT).show();
        }
    }

    public void guardarPartidaSD(String resultado){
        try {
            File ruta_sd = Environment.getExternalStorageDirectory();
            File f = new File(ruta_sd.getAbsolutePath(), nombreFichero());
            OutputStreamWriter fout = new OutputStreamWriter(new FileOutputStream(f));

            fout.write(resultado);
            fout.close();
        }
        catch (Exception ex) {
            Toast.makeText(this, "No se ha podido guardar la partida en la tarjeta SD", Toast.LENGTH_SHORT).show();
        }
    }

    public String nombreFichero(){
        SharedPreferences prefs = getSharedPreferences("MisPreferencias", MODE_PRIVATE);
        String nombre;

        nombre = prefs.getString("nombreFichero", "ejecuciones.txt");

        return nombre;
    }

    private String getDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);
    }

    public void addPiezaJugador(View v, Jugador jugador){
        switch (v.getId()) {
            case R.id.p11:
                jugador.getCombinacion().combinacion.add(tablero.get(0));
                break;
            case R.id.p12:
                jugador.getCombinacion().combinacion.add(tablero.get(1));
                break;
            case R.id.p13:
                jugador.getCombinacion().combinacion.add(tablero.get(2));
                break;
            case R.id.p21:
                jugador.getCombinacion().combinacion.add(tablero.get(3));
                break;
            case R.id.p22:
                jugador.getCombinacion().combinacion.add(tablero.get(4));
                break;
            case R.id.p23:
                jugador.getCombinacion().combinacion.add(tablero.get(5));
                break;
            case R.id.p31:
                jugador.getCombinacion().combinacion.add(tablero.get(6));
                break;
            case R.id.p32:
                jugador.getCombinacion().combinacion.add(tablero.get(7));
                break;
            case R.id.p33:
                jugador.getCombinacion().combinacion.add(tablero.get(8));
                break;
            default:
                Log.d("Error", "No se añade");
        }
    }
}

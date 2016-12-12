package com.example.versus.tictactoe;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
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
import java.util.Locale;

public class PantallaPartida extends AppCompatActivity {

    //Se declaran las variables que se van a utilizar
    Jugador j1;
    Jugador j2;
    int turno;
    Partida partida;
    ArrayList<Pieza> tablero;
    TextView TVTurno;
    Button btn11;
    Button btn12;
    Button btn13;
    Button btn21;
    Button btn22;
    Button btn23;
    Button btn31;
    Button btn32;
    Button btn33;
    Button btnReiniciar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partida);

        //Se obtienen los datos que se reciben de la Activity PantallaEleccion
        Bundle extras = getIntent().getExtras();
        j1 = extras.getParcelable("jugador1");
        j1.setCombinacion(new Combinacion());
        j2 = extras.getParcelable("jugador2");
        j2.setCombinacion(new Combinacion());

        //Se crea un objeto partida con los datos de los jugadores 1 y 2
        partida = new Partida(j1,j2);
        turno = 1;
        TVTurno = (TextView) findViewById(R.id.turno);

        TVTurno.setText(String.format((getResources().getString(R.string.turno)), (j1.getNombre().toUpperCase())));

        btn11 = (Button) findViewById(R.id.p11);
        btn12 = (Button) findViewById(R.id.p12);
        btn13 = (Button) findViewById(R.id.p13);
        btn21 = (Button) findViewById(R.id.p21);
        btn22 = (Button) findViewById(R.id.p22);
        btn23 = (Button) findViewById(R.id.p23);
        btn31 = (Button) findViewById(R.id.p31);
        btn32 = (Button) findViewById(R.id.p32);
        btn33 = (Button) findViewById(R.id.p33);
        btnReiniciar = (Button) findViewById(R.id.btnReiniciar);

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

        tablero = new ArrayList<>();

        tablero.add(p11);
        tablero.add(p12);
        tablero.add(p13);
        tablero.add(p21);
        tablero.add(p22);
        tablero.add(p23);
        tablero.add(p31);
        tablero.add(p32);
        tablero.add(p33);

        btnReiniciar.setClickable(false);
        actualizarColorRotulo(partida.getJ1());
    }

    public void colocarPieza(View v){
        Button botonPulsado = (Button) findViewById(v.getId());

        ColorStateList mList = botonPulsado.getTextColors();
        int color = mList.getDefaultColor();

        if(color == Color.BLACK){
            if(turno == 1){
                addPiezaJugador(v, partida.getJ1());
                colorearPieza(botonPulsado, partida.getJ1(),  partida.getJ2());
                mostrarGanador(partida.getJ1(),  partida.getJ2());
                turno = 2;
            }else{
                addPiezaJugador(v,  partida.getJ2());
                colorearPieza(botonPulsado,  partida.getJ2(), partida.getJ1());
                mostrarGanador( partida.getJ2(),  partida.getJ1());
                turno = 1;
            }
        } else{
            Toast.makeText(this, getResources().getString(R.string.trampas), Toast.LENGTH_SHORT).show();
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

        actualizarColorRotulo(siguienteJugador);
        botonPulsado.setText(jugadorActual.getSimbolo());
        TVTurno.setText(String.format((getResources().getString(R.string.turno)), (siguienteJugador.getNombre().toUpperCase())));
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

    public void reiniciar(View v){
        ArrayList<Button> aButton = new ArrayList<>();
        aButton.add(btn11);
        aButton.add(btn12);
        aButton.add(btn13);
        aButton.add(btn21);
        aButton.add(btn22);
        aButton.add(btn23);
        aButton.add(btn31);
        aButton.add(btn32);
        aButton.add(btn33);

        for(Button b: aButton){
            b.setTextColor(Color.BLACK);
            b.setText("");
            b.setClickable(true);
        }

        turno = 1;
        partida.getJ1().getCombinacion().combinacion.clear();
        partida.getJ2().getCombinacion().combinacion.clear();

        TVTurno.setText(String.format((getResources().getString(R.string.turno)), (partida.getJ1().getNombre().toUpperCase())));
        actualizarColorRotulo(partida.getJ1());
        findViewById(R.id.btnReiniciar).setClickable(true);
    }

    public void volver(View v){
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle(getResources().getString(R.string.salir))
                .setMessage(getResources().getString(R.string.salirPartida))
                .setNegativeButton(android.R.string.cancel, null)//sin listener
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {//un listener que al pulsar, cierre la aplicacion
                    @Override
                    public void onClick(DialogInterface dialog, int which){
                        //Salir
                        PantallaPartida.this.finish();
                    }
                })
                .show();
    }

    public void mostrarGanador(Jugador jugadorActual, Jugador siguienteJugador){
        if(partida.comprobarGanador(jugadorActual)) {
            findViewById(R.id.btnReiniciar).setClickable(true);
            deshabilitarBotones();
            TVTurno.setText(String.format(getResources().getString(R.string.ganador), jugadorActual.getNombre().toUpperCase()));
            actualizarColorRotulo(jugadorActual);
            Toast.makeText(this, String.format(getResources().getString(R.string.ganador), jugadorActual.getNombre().toUpperCase()), Toast.LENGTH_SHORT).show();
            guardarPartida(jugadorActual, siguienteJugador, "ganada");
        }else{
            if(jugadorActual.getCombinacion().combinacion.size() == 4 && siguienteJugador.getCombinacion().combinacion.size() == 4) {
                findViewById(R.id.btnReiniciar).setClickable(true);
                deshabilitarBotones();
                TVTurno.setText(getResources().getString(R.string.empate));
                TVTurno.setTextColor(Color.parseColor("#000000"));
                Toast.makeText(this, getResources().getString(R.string.empate), Toast.LENGTH_SHORT).show();
                guardarPartida(jugadorActual, siguienteJugador, "empatada");
            }
        }
    }

    public void guardarPartida(Jugador ganador, Jugador perdedor, String res){
        String resultado;
        SharedPreferences prefs = getSharedPreferences("MisPreferencias", MODE_PRIVATE);
        if(res.equals("ganada")){
            resultado = getDateTime() + ":\r" + ganador.getNombre() + " ha ganado a " + perdedor.getNombre();
        }
        else{
            resultado = getDateTime() + ":\r" + ganador.getNombre() + " ha empatado con " + perdedor.getNombre();
        }

        if(prefs.getBoolean("guardarSD", true)){
            guardarPartidaSD(resultado);
        }
        if(prefs.getBoolean("guardarMI", true)){
            guardarPartidaMI(resultado);
        }
    }

    public void guardarPartidaMI(String resultado){
        try {
            OutputStreamWriter osw = new OutputStreamWriter(openFileOutput(nombreFichero(), Context.MODE_APPEND));
            osw.write(resultado + "\n");
            osw.close();
        }
        catch (IOException e) {
            Toast.makeText(this, getResources().getString(R.string.errorFichTelf), Toast.LENGTH_SHORT).show();
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
            Toast.makeText(this, getResources().getString(R.string.errorFichSD), Toast.LENGTH_SHORT).show();
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
        }
    }

    public void deshabilitarBotones(){
        ArrayList<Button> aButton = new ArrayList<>();
        aButton.add(btn11);
        aButton.add(btn12);
        aButton.add(btn13);
        aButton.add(btn21);
        aButton.add(btn22);
        aButton.add(btn23);
        aButton.add(btn31);
        aButton.add(btn32);
        aButton.add(btn33);

        for(Button b: aButton){
            b.setClickable(false);
        }
    }

    //Guardamos los datos de la aplicación
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        int color;
        ColorStateList mList;

        outState.putSerializable("partida", partida);

        outState.putSerializable("tablero", tablero);

        outState.putInt("turno", turno);

        outState.putString("textoTurno", (String)TVTurno.getText());
        mList = TVTurno.getTextColors();
        color = mList.getDefaultColor();
        outState.putInt("colorTurno", color);

        mList = btn11.getTextColors();
        color = mList.getDefaultColor();
        outState.putInt("color11",color);
        outState.putString("text11", (String)btn11.getText());

        mList = btn12.getTextColors();
        color = mList.getDefaultColor();
        outState.putInt("color12",color);
        outState.putString("text12", (String)btn12.getText());

        mList = btn13.getTextColors();
        color = mList.getDefaultColor();
        outState.putInt("color13",color);
        outState.putString("text13", (String)btn13.getText());

        mList = btn21.getTextColors();
        color = mList.getDefaultColor();
        outState.putInt("color21",color);
        outState.putString("text21", (String)btn21.getText());

        mList = btn22.getTextColors();
        color = mList.getDefaultColor();
        outState.putInt("color22",color);
        outState.putString("text22", (String)btn22.getText());

        mList = btn23.getTextColors();
        color = mList.getDefaultColor();
        outState.putInt("color23",color);
        outState.putString("text23", (String)btn23.getText());

        mList = btn31.getTextColors();
        color = mList.getDefaultColor();
        outState.putInt("color31",color);
        outState.putString("text31", (String)btn31.getText());

        mList = btn32.getTextColors();
        color = mList.getDefaultColor();
        outState.putInt("color32",color);
        outState.putString("text32", (String)btn32.getText());

        mList = btn33.getTextColors();
        color = mList.getDefaultColor();
        outState.putInt("color33",color);
        outState.putString("text33", (String)btn33.getText());

        outState.putBoolean("btnReiniciarClickable", btnReiniciar.isClickable());

        cargarIdioma();

    }
    //Cuando la aplicacion se restaura se cargan los datos que habias guardado previamente
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        //NO ACABA LAS PARTIDAS CUANDO SE GIRA EN MITAD DE UNA
        //SUPONEMOS QUE LO QUE PASA ES QUE LA COMBINACION DEL JUGADOR NO SE GUARDA
        partida = (Partida) savedInstanceState.getSerializable("partida");

        tablero = (ArrayList) savedInstanceState.getSerializable("tablero");

        turno = savedInstanceState.getInt("turno");

        TVTurno.setText(savedInstanceState.getString("textoTurno"));
        TVTurno.setTextColor(savedInstanceState.getInt("colorTurno"));

        btn11.setText(savedInstanceState.getString("text11"));
        btn11.setTextColor(savedInstanceState.getInt("color11"));

        btn12.setText(savedInstanceState.getString("text12"));
        btn12.setTextColor(savedInstanceState.getInt("color12"));

        btn13.setText(savedInstanceState.getString("text13"));
        btn13.setTextColor(savedInstanceState.getInt("color13"));

        btn21.setText(savedInstanceState.getString("text21"));
        btn21.setTextColor(savedInstanceState.getInt("color21"));

        btn22.setText(savedInstanceState.getString("text22"));
        btn22.setTextColor(savedInstanceState.getInt("color22"));

        btn23.setText(savedInstanceState.getString("text23"));
        btn23.setTextColor(savedInstanceState.getInt("color23"));

        btn31.setText(savedInstanceState.getString("text31"));
        btn31.setTextColor(savedInstanceState.getInt("color31"));

        btn32.setText(savedInstanceState.getString("text32"));
        btn32.setTextColor(savedInstanceState.getInt("color32"));

        btn33.setText(savedInstanceState.getString("text33"));
        btn33.setTextColor(savedInstanceState.getInt("color33"));

        btnReiniciar.setClickable(savedInstanceState.getBoolean("btnReiniciarClickable"));
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle(getResources().getString(R.string.salir))
                    .setMessage(getResources().getString(R.string.salirPartida))
                    .setNegativeButton(android.R.string.cancel, null)//sin listener
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {//un listener que al pulsar, cierre la aplicacion
                        @Override
                        public void onClick(DialogInterface dialog, int which){
                            //Salir
                            PantallaPartida.this.finish();
                        }
                    })
                    .show();
            // Si el listener devuelve true, significa que el evento esta procesado, y nadie debe hacer nada mas
            return true;
        }
        //para las demas cosas, se reenvia el evento al listener habitual
        return super.onKeyDown(keyCode, event);
    }

    public void cargarIdioma(){
        SharedPreferences prefs = getSharedPreferences("MisPreferencias", MODE_PRIVATE);
        String idioma = prefs.getString("idioma", "spanish");

        Resources res = this.getApplicationContext().getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        android.content.res.Configuration conf = res.getConfiguration();
        Locale loc;

        if(idioma.equals("spanish")) {

            loc = new Locale("es");
        }
        else{
            loc = new Locale("en");
        }

        conf.setLocale(loc);
        res.updateConfiguration(conf, dm);
    }
}

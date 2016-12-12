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
import android.util.Log;
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

    //
    /**
     * Método que se ejecuta cuando se carga la PantallaPartida
     * @param savedInstanceState
     */
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

        //Se inicializa el turno a 1
        turno = 1;

        //Se obtiene el TextView del turno y se actualiza el texto con el nombre del jugador 1
        TVTurno = (TextView) findViewById(R.id.turno);
        TVTurno.setText(String.format((getResources().getString(R.string.turno)), (j1.getNombre().toUpperCase())));

        //Se obtienen los botones del tablero
        btn11 = (Button) findViewById(R.id.p11);
        btn12 = (Button) findViewById(R.id.p12);
        btn13 = (Button) findViewById(R.id.p13);
        btn21 = (Button) findViewById(R.id.p21);
        btn22 = (Button) findViewById(R.id.p22);
        btn23 = (Button) findViewById(R.id.p23);
        btn31 = (Button) findViewById(R.id.p31);
        btn32 = (Button) findViewById(R.id.p32);
        btn33 = (Button) findViewById(R.id.p33);

        //Se obtiene el boton reinicar
        btnReiniciar = (Button) findViewById(R.id.btnReiniciar);

        crearTablero();
    }

    /**
     * Método que crea el tablero, crea las piezas y las añade a un ArrayList<Pieza>
     */
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

        //Se instancia el ArrayList
        tablero = new ArrayList<>();

        //Se añaden las piezas al ArrayList tablero
        tablero.add(p11);
        tablero.add(p12);
        tablero.add(p13);
        tablero.add(p21);
        tablero.add(p22);
        tablero.add(p23);
        tablero.add(p31);
        tablero.add(p32);
        tablero.add(p33);

        //Se establece la propiedad Clickable de btnReiniciar a false
        btnReiniciar.setClickable(false);

        //Se actualiza el color del rotulo con el color del jugador 1
        actualizarColorRotulo(partida.getJ1());
    }

    //Método que se ejecuta cuando se pulsa una pieza del taablero
    //permite colocar una pieza en el tablero y en la combinacion del usuario
    //ademas de comprobar el ganador/empate.
    /**
     * Método que se ejecuta cuando se pulsa una pieza del taablero
     * permite colocar una pieza en el tablero y en la combinacion del usuario
     * ademas de comprobar el ganador/empate.
     * @param v boton pulsado
     */
    public void colocarPieza(View v){
        //Se crea un objeto button buscando por la id del View que se recibe.
        Button botonPulsado = (Button) findViewById(v.getId());

        //Se crea un objeto ColorStateList que nos va a permitir saber cual es el color que tiene el boton
        ColorStateList mList = botonPulsado.getTextColors();
        int color = mList.getDefaultColor();

        //Si el color es negro
        if(color == Color.BLACK){
            //Si el turno es del jugador 1
            if(turno == 1){
                //Se añade la pieza al la combinacion del jugador 1
                addPiezaJugador(v, partida.getJ1());
                //Se colorea el tablero y se actualiza el textView del turno
                colorearPieza(botonPulsado, partida.getJ1(),  partida.getJ2());
                //Se comprueba el ganador
                mostrarGanador(partida.getJ1(),  partida.getJ2());
                //Se cambia el turno al jugador 2
                turno = 2;
            }else{ //Si el turno es del jugador 1
                //Se añade la pieza al la combinacion del jugador 2
                addPiezaJugador(v,  partida.getJ2());
                //Se colorea el tablero y se actualiza el textView del turno
                colorearPieza(botonPulsado,  partida.getJ2(), partida.getJ1());
                //Se comprueba el ganador
                mostrarGanador( partida.getJ2(),  partida.getJ1());
                //Se cambia el turno al jugador 1
                turno = 1;
            }
        } else{ //Si la el color del texto del boton no es negro significa que esta ocupada
            //Se informa al usuario diciendole que no haga trampas.
            Toast.makeText(this, getResources().getString(R.string.trampas), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Método que permite colorear el texto del boton, actualizar el rotulo.
     * @param botonPulsado boton pulsado
     * @param jugadorActual jugador que posee el turno
     * @param siguienteJugador jugador al que se le va a dar el turno
     */
    public void colorearPieza(Button botonPulsado, Jugador jugadorActual, Jugador siguienteJugador ){
        //Dependiendo del color del usuario, pone el color del texto del boton de un color u otro.
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

        //Actualiza el rotulo de turno
        actualizarColorRotulo(siguienteJugador);
        botonPulsado.setText(jugadorActual.getSimbolo());
        TVTurno.setText(String.format((getResources().getString(R.string.turno)), (siguienteJugador.getNombre().toUpperCase())));
    }

    /**
     * Método que permite actualizar el color del rotulo
     * @param siguienteJugador jugador al que se le va a dar el turno
     */
    public void actualizarColorRotulo(Jugador siguienteJugador){
        //Dependiendo del color del usuario, pone el color del rotulo de turno de un color u otro.
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

    /**
     * Método que permite reiniciar la partida
     * @param v Boton reiniciar
     */
    public void reiniciar(View v){
        //Se meten todos los botones del tablero en un ArrayList<Button>
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

        //Se recorre el array de buttons y se resetean a los valores iniciales.
        for(Button b: aButton){
            b.setTextColor(Color.BLACK);
            b.setText("");
            b.setClickable(true);
        }

        //Se le da el turno al jugador 1
        turno = 1;
        //Se vacian las combinaciones de los jugadores
        partida.getJ1().getCombinacion().combinacion.clear();
        partida.getJ2().getCombinacion().combinacion.clear();

        //Se actualiza el rotulo
        TVTurno.setText(String.format((getResources().getString(R.string.turno)), (partida.getJ1().getNombre().toUpperCase())));
        actualizarColorRotulo(partida.getJ1());
        //Se establece la propiedad Clickable a false
        findViewById(R.id.btnReiniciar).setClickable(false);
    }

    /**
     * Método que te permite volver a la PantallaEleccion, con confirmacion
     * @param v Boton volver
     */
    public void volver(View v){
        //Se muestra el dialog que permite decidir si volver o no
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

    /**
     * Método que permite comprobar el ganador e informar al usuario del ganador o del empate.
     * @param jugadorActual jugador que posee el turno
     * @param siguienteJugador jugador al que se le va a pasar el turno
     */
    public void mostrarGanador(Jugador jugadorActual, Jugador siguienteJugador){
        //Si el jugador actual es el ganador
        if(partida.comprobarGanador(jugadorActual)) {
            //Se deja pulsar el boton Reiniciar
            findViewById(R.id.btnReiniciar).setClickable(true);
            //Se deshabilitan los botones del tablero
            deshabilitarBotones();
            //Se actualiza el rotulo mostrando el ganador
            TVTurno.setText(String.format(getResources().getString(R.string.ganador), jugadorActual.getNombre().toUpperCase()));
            //Se pone el rotulo del color del ganador
            actualizarColorRotulo(jugadorActual);
            //Se muestra un Toast que muestra el ganador
            Toast.makeText(this, String.format(getResources().getString(R.string.ganador), jugadorActual.getNombre().toUpperCase()), Toast.LENGTH_SHORT).show();
            //Se guarda la partida con ganador
            guardarPartida(jugadorActual, siguienteJugador, "ganada");
        }else{//Si no es el ganador
            //Se comprueba si han empatado, es decir, si ambos tienen 4 piezas y ninguno ha ganado
            if(jugadorActual.getCombinacion().combinacion.size() == 4 && siguienteJugador.getCombinacion().combinacion.size() == 4) {
                //Se deja pulsar el boton Reiniciar
                findViewById(R.id.btnReiniciar).setClickable(true);
                //Se deshabilitan los botones del tablero
                deshabilitarBotones();
                //Se actualiza el rotulo mostrando empate
                TVTurno.setText(getResources().getString(R.string.empate));
                //Se pone el rotulo de color negro
                TVTurno.setTextColor(Color.parseColor("#000000"));
                //Se muestra un Toast que muestra "Empate"
                Toast.makeText(this, getResources().getString(R.string.empate), Toast.LENGTH_SHORT).show();
                //Se guarda la partida empatada
                guardarPartida(jugadorActual, siguienteJugador, "empatada");
            }
        }
    }

    /**
     * Método que pertmite guardar una los datos de una partida
     * @param ganador jugador que ha ganado o empatado
     * @param perdedor jugador que ha perdido o empatado
     * @param res indica como ha quedado la partida ganada o empatada
     */
    public void guardarPartida(Jugador ganador, Jugador perdedor, String res){
        String resultado;
        //Abrimos el fichero de la preferencias
        SharedPreferences prefs = getSharedPreferences("MisPreferencias", MODE_PRIVATE);
        //Dependiendo del resultado de la partida se guarda con un texto u otro
        if(res.equals("ganada")){
            resultado = getDateTime() + ":\r" + ganador.getNombre() + " ha ganado a " + perdedor.getNombre();
        }
        else{
            resultado = getDateTime() + ":\r" + ganador.getNombre() + " ha empatado con " + perdedor.getNombre();
        }

        //Si la opcion de guardar en la SD es true se guarda en la SD
        if(prefs.getBoolean("guardarSD", true)){
            guardarPartidaSD(resultado);
        }
        //Se guardan los datos en la memoria local
        guardarPartidaMI(resultado);
    }

    /**
     * Método que pertmite guardar una los datos de una partida en la memoria interna
     * @param resultado string que va a escribir en el fichero
     */
    public void guardarPartidaMI(String resultado){
        try {
            //Se guarda el resultado en el fichero obtenido de las preferencias mediante el método nombreFichero()
            OutputStreamWriter osw = new OutputStreamWriter(openFileOutput(nombreFichero(), Context.MODE_APPEND));
            osw.write(resultado + "\n");
            osw.close();
        }
        catch (IOException e) {
            //Si se produce algun error al guardar, se muestra un Toast de informacion
            Toast.makeText(this, getResources().getString(R.string.errorFichTelf), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Método que pertmite guardar una los datos de una partida en la tarjeta SD
     * @param resultado string que va a escribir en el fichero
     */
    public void guardarPartidaSD(String resultado){
        try {
            //Se obtiene la ruta del la tarjeta SD
            File ruta_sd = Environment.getExternalStorageDirectory();
            //Se instancia el fichero con la ruta y el nombre
            File f = new File(ruta_sd.getAbsolutePath(), nombreFichero());
            OutputStreamWriter fout = new OutputStreamWriter(new FileOutputStream(f));
            //Se escribe el resultado en el fichero
            fout.write(resultado);
            fout.close();
        }
        catch (Exception ex) {
            //Si se produce algun error al guardar, se muestra un Toast de informacion
            Toast.makeText(this, getResources().getString(R.string.errorFichSD), Toast.LENGTH_SHORT).show();
        }
    }

    //Método que busca el nombre del fichero donde se guardan las partidas en el fichero MisPreferencias
    public String nombreFichero(){
        //Abre el fichero MisPreferencias
        SharedPreferences prefs = getSharedPreferences("MisPreferencias", MODE_PRIVATE);
        String nombre;
        //Obtiene el nombre del fichero y si no lo encuentra devuelve "ejecuciones.txt" por defecto
        nombre = prefs.getString("nombreFichero", "ejecuciones.txt");

        return nombre;
    }

    //Método que permite obtener la fecha y la hora del dispositivo
    private String getDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);
    }

    /**
     * Método que permite añadir una pieza a la combinacion del jugador, dependiendo de la pieza pulsada
     * @param v boton pulsado
     * @param jugador contenedor donde guardamos los datos de la activity
     */
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


    /**
     * Método que permite deshabilitar todos los botones del teclado
     */
    public void deshabilitarBotones(){
        //Se crea un ArrayList con todos los botones del tablero
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

        //Recorre el array y establece la propiedad Clickable a false
        for(Button b: aButton){
            b.setClickable(false);
        }
    }

    /**
     * Guardamos el estado de los datos de la aplicacion para cuando se cambie la orientacion
     * @param outState contenedor donde guardamos los datos de la activity
     */
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

    /**
     * Cuando la aplicacion se restaura se cargan los datos que habiamos guardado previamente
     * @param savedInstanceState contener donde se guardan los datos de la activity
     */
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

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

    /**
     * Metodo que se encarga de volver a la pantalla anterior al pulsar en el boton atras del dispositivo
     * @param keyCode Codigo del boton pulsado
     * @param event Pulsacion
     * @return Devuelve true si se ejecuta con exito
     */
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {//Si el codigo del boton pulsado es igual que el codigo del boton atras..
            new AlertDialog.Builder(this)//Se muestra un dialog para confirmar la salida
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle(getResources().getString(R.string.salir))
                    .setMessage(getResources().getString(R.string.salirPartida))
                    .setNegativeButton(android.R.string.cancel, null)//sin listener
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {//un listener que al pulsar, cierre la aplicacion
                        @Override
                        public void onClick(DialogInterface dialog, int which){//Si se pulsa aceptar..
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

    /**
     * Metodo que se encarga de cargar el idioma de la aplicacion.
     * Se ejecuta al girar el dispositivo.
     */
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

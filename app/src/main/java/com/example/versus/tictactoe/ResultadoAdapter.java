package com.example.versus.tictactoe;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by EmilioCB on 18/10/2016.
 */

public class ResultadoAdapter extends ArrayAdapter<String> {
    private final Context contexto;
    private final ArrayList<String> array_resultados;

    /**
     * Constructor de la clase
     * @param context Contexto de la Activity
     * @param array_resultados ArrayList de los titulares
     */
    public ResultadoAdapter(Context context, ArrayList<String> array_resultados) {
        super(context, -1, array_resultados);
        this.contexto = context;
        this.array_resultados = array_resultados;
    }

    /**
     * Metodo que se encarga de darle una disposicion de layout a la view.
     * @param position Posicion del ArrayList en la que se encuentra
     * @param convertView
     * @param parent
     * @return Devuelve la disposicion de la view
     */
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) contexto.getSystemService(Context.LAYOUT_INFLATER_SERVICE); //Se crea el LayoutInflater

        View layoutResultado = inflater.inflate(R.layout.fila_resultado, parent, false); //Instancia el layout personalizado(fila) en esta Vista.

        TextView linea = (TextView) layoutResultado.findViewById(R.id.textViewTitulo); //TextView del titulo, se asocia con el del XML

        String res = array_resultados.get(position); //Se recorren los titulares

        linea.setText(res); //Se recoge el titulo del titular en el que se encuentra

        return layoutResultado;
    }
}

package com.example.versus.tictactoe;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class PantallaEleccion extends AppCompatActivity {

    Jugador jugador1;
    Jugador jugador2;

    EditText _nombreJ1;
    EditText _nombreJ2;
    RadioButton _rbOrangeJug1;
    RadioButton _rbOrangeJug2;
    RadioButton _rbGreenJug1;
    RadioButton _rbGreenJug2;
    RadioButton _rbBlueJug1;
    RadioButton _rbBlueJug2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pantalla_eleccion);

        _nombreJ1 = (EditText) findViewById(R.id.etNomJug1);
        _nombreJ2 = (EditText) findViewById(R.id.etNomJug2);
        _rbOrangeJug1 = (RadioButton) findViewById(R.id.rbOrangeJug1);
        _rbOrangeJug2 = (RadioButton) findViewById(R.id.rbOrangeJug2);
        _rbGreenJug1 = (RadioButton) findViewById(R.id.rbGreenJug1);
        _rbGreenJug2 = (RadioButton) findViewById(R.id.rbGreenJug2);
        _rbBlueJug1 = (RadioButton) findViewById(R.id.rbBlueJug1);
        _rbBlueJug2 = (RadioButton) findViewById(R.id.rbBlueJug2);

        _rbOrangeJug1.setChecked(true);
        _rbOrangeJug2.setEnabled(false);
        _rbGreenJug2.setChecked(true);
        _rbGreenJug1.setEnabled(false);

        jugador1 = new Jugador("X");
        jugador2 = new Jugador("O");

        jugador1.setColor(_rbOrangeJug1.getText().toString());
        jugador2.setColor(_rbGreenJug2.getText().toString());
    }


    public void goPlay(View v){
        if(comprobarNombres()){
            jugador1.setNombre(_nombreJ1.getText().toString());
            jugador2.setNombre(_nombreJ2.getText().toString());
            Intent intent = new Intent(PantallaEleccion.this, ActivityPartida.class);
            intent.putExtra("jugador1", jugador1);
            intent.putExtra("jugador2", jugador2);
            startActivity(intent);
        }
    }

    public void putColor(View rb){
        switch (rb.getId()) {
            case R.id.rbOrangeJug1:
                _rbOrangeJug2.setEnabled(false);
                _rbGreenJug2.setEnabled(true);
                _rbBlueJug2.setEnabled(true);
                jugador1.setColor(_rbOrangeJug1.getText().toString());
                break;
            case R.id.rbGreenJug1:
                _rbOrangeJug2.setEnabled(true);
                _rbGreenJug2.setEnabled(false);
                _rbBlueJug2.setEnabled(true);
                jugador1.setColor(_rbGreenJug1.getText().toString());
                break;
            case R.id.rbBlueJug1:
                _rbOrangeJug2.setEnabled(true);
                _rbGreenJug2.setEnabled(true);
                _rbBlueJug2.setEnabled(false);
                jugador1.setColor(_rbBlueJug1.getText().toString());
                break;
            case R.id.rbOrangeJug2:
                _rbOrangeJug1.setEnabled(false);
                _rbGreenJug1.setEnabled(true);
                _rbBlueJug1.setEnabled(true);
                jugador2.setColor(_rbOrangeJug2.getText().toString());
                break;
            case R.id.rbGreenJug2:
                _rbOrangeJug1.setEnabled(true);
                _rbGreenJug1.setEnabled(false);
                _rbBlueJug1.setEnabled(true);
                jugador2.setColor(_rbGreenJug2.getText().toString());
                break;
            case R.id.rbBlueJug2:
                _rbOrangeJug1.setEnabled(true);
                _rbGreenJug1.setEnabled(true);
                _rbBlueJug1.setEnabled(false);
                jugador2.setColor(_rbBlueJug2.getText().toString());
                break;
        }
    }

    public boolean comprobarNombres(){
        boolean correcto = true;
        if(_nombreJ1.getText().toString().equals("") || _nombreJ2.getText().toString().equals("")){
            Toast.makeText(this, "No se han introducido nombres", Toast.LENGTH_SHORT).show();
            correcto = false;
        }
        else if(_nombreJ1.getText().toString().equals(_nombreJ2.getText().toString())){
            Toast.makeText(this, "Los nombres no pueden ser iguales", Toast.LENGTH_SHORT).show();
            correcto = false;
        }

        return correcto;
    }

    public void goBack(View v){
        finish();
        Intent intent = new Intent(PantallaEleccion.this, PantallaPrincipal.class);
        startActivity(intent);
    }

}

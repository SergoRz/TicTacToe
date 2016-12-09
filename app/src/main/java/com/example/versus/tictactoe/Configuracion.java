package com.example.versus.tictactoe;

import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;

import java.util.Locale;

public class Configuracion extends AppCompatActivity {

    private RadioButton _idiomaSpanish;
    private RadioButton _idiomaEnglish;
    private CheckBox _SD;
    private CheckBox _MI;
    private EditText _fichero;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.configuracion);

        _idiomaSpanish = (RadioButton) findViewById(R.id.rbEspañol);
        _idiomaEnglish = (RadioButton) findViewById(R.id.rbIngles);
        _SD = (CheckBox) findViewById(R.id.cbSD);
        _MI = (CheckBox) findViewById(R.id.cbMemory);
        _fichero = (EditText) findViewById(R.id.etFileName);

        cargarPrefs();



    }

    public void cargarPrefs(){
        SharedPreferences prefs = getSharedPreferences("MisPreferencias", MODE_PRIVATE);

        String idioma = prefs.getString("idioma", "spanish");

        if(idioma.equals("spanish")){
            _idiomaSpanish.setChecked(true);
        }
        else{
            _idiomaEnglish.setChecked(true);
        }

        _SD.setChecked(prefs.getBoolean("guardarSD", true));

        _MI.setChecked(prefs.getBoolean("guardarMI", true));

        _fichero.setText(prefs.getString("nombreFichero", "ejecuciones.txt"));

    }


    public void guardarPrefs(View v){
        SharedPreferences prefs = getSharedPreferences("MisPreferencias", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        editor.putString("idioma", recogerIdioma());
        editor.putBoolean("guardarSD", recogerLocSD());
        editor.putBoolean("guardarMI", recogerLocMI());
        editor.putString("nombreFichero", recogerFichero());
        editor.commit();

        finish();
        startActivity(getIntent());
    }

    public String recogerIdioma(){
        String idioma;
        Resources res = this.getApplicationContext().getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        android.content.res.Configuration conf = res.getConfiguration();
        Locale loc;

        if(_idiomaSpanish.isChecked()) {
            idioma = "spanish";
            loc = new Locale("es");
        }
        else{
            idioma = "english";
            loc = new Locale("en");
        }

        conf.setLocale(loc);
        res.updateConfiguration(conf, dm);

        return idioma;
    }

    public boolean recogerLocSD(){
        boolean loc;

        if(_SD.isChecked()) {
            loc = true;
        }
        else{
            loc = false;
        }

        return loc;
    }

    public boolean recogerLocMI(){
        boolean loc;

        if(_MI.isChecked()) {
            loc = true;
        }
        else{
            loc = false;
        }

        return loc;
    }

    public String recogerFichero(){
        String fichero = _fichero.getText().toString();

        return fichero;
    }

    public void back(View v){
        finish();
    }

}

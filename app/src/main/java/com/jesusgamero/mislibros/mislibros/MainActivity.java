package com.jesusgamero.mislibros.mislibros;

/**
 * Created by Jesús Gamero de 2DAW on 07/02/2017.
 */

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.sql.SQLException;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    ListView lista_libros;
    BDLibros DB;
    private Cursor filas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), DetalleLibro.class);
                intent.putExtra("id", 0);
                startActivity(intent);
            }
        });

        DB = new BDLibros(this);
        insertaEjemplos();
        actualizaLista();
        lista_libros.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this, DetalleLibro.class);
        intent.putExtra("id", id);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    protected void onResume() {
        super.onResume();
        actualizaLista();
    }

    public void actualizaLista() {

        try {
            DB.openR();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        lista_libros = (ListView) findViewById(R.id.lista_libros);
        filas = DB.verLibros();

        if (filas.moveToFirst()) {
            Adaptador adaptador = new Adaptador(this, filas, 0);
            lista_libros.setAdapter(adaptador);
        } else {
            lista_libros.removeAllViewsInLayout();
        }
        DB.close();
    }

    public void insertaEjemplos() {

        try {
            DB.openW();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            if (DB.numeroLibros() == 0) { //Si no hay libros en la base de datos, inserrto alguno de prueba.
                DB.insertaLibro("LA QUIMICA", "STEPHENIE MEYER", "SUMA", "9788491291244", "2016",
                        "635", 1, 1, 3f, "En esta trepidante y adictiva novela, una exagente que huye de la organización en la que trabajaba deberá aceptar un último caso para limpiar su nombre y salvar su vida.");
                DB.insertaLibro("TRES VECES TU", "FEDERICO MOCCIA", "PLANETA", "9788408165996", "2017",
                        "816", 1, 1, 2f, "Seis años después, las vidas de nuestros protagonistas han cambiado. Han conseguido ser felices, pero cuando menos se lo esperan, sus caminos se vuelven a cruzar...");
                DB.insertaLibro("1984", "GEORGE ORWELL", "DEBOLSILLO", "9788499890944", "2013",
                        "352", 0, 1, 4f, "En el año 1984 Londres es una ciudad lúgubre en la que la Policía del Pensamiento controla de forma asfixiante la vida de los ciudadanos. Winston Smith es un peón de este engranaje perverso, su cometido es reescribir la historia para adaptarla a lo que el Partido considera la versión oficial de los hechos... hasta que decide replantearse la verdad del sistema que los gobierna y somete.");
                DB.insertaLibro("EL PRINCIPITO ", "ANTOINE DE SAINT-EXUPERY", "SALAMANDRA ", "9788498381498", "2008",
                        "96", 1, 1, 5f, "El valor de la amistad, el heroísmo como meta y la responsabilidad como motor de la conducta moral encuentran su plasmación definitiva en el mundo que descubre El principito , añorado planeta del que todos los hombres han sido exiliados y al que sólo mediante la fabulación cabe regresar. ");
                DB.insertaLibro("PATRIA", "FERNANDO ARAMBURU", "TUSQUETS", "9788490663196", "2016",
                        "648", 0, 1, 3f, "El día en que ETA anuncia el abandono de las armas, Bittori se dirige al cementerio para contarle a la tumba de su marido el Txato, asesinado por los terroristas, que ha decidido volver a la casa donde vivieron.");
                DB.insertaLibro("MI REVOLUCION ANTICANCER", "ODILE FERNANDEZ ", "PLANETA", "9788408165194", "2017",
                        "280", 1, 1, 3f, "Este libro va a quitarle el estigma negativo a la palabra cáncer: cáncer no es igual a muerte, dolor o sufrimiento. Este libro va a dar esperanza.");
                DB.insertaLibro("MADRE HAY MAS QUE UNA", "SAMANTA VILLAR ", "PLANETA", "9788408165170", "2017",
                        "272", 1, 1, 2f, "Cuando Samanta Villar tomó la decisión de ser madre, resultó que era demasiado mayor según los parámetros médicos. No solo eso, sino que posteriormente descubrió que era infértil. Pero su voluntad de ser madre prevaleció.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        DB.close();
    }
}

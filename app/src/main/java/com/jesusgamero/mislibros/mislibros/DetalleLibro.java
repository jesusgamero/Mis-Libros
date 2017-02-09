package com.jesusgamero.mislibros.mislibros;

/**
 * Created by Jesús Gamero de 2DAW on 07/02/2017.
 */

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import java.sql.SQLException;


public class DetalleLibro extends AppCompatActivity {

    boolean insertar, editar, eliminar;
    long ID;
    EditText ed_titulo;
    EditText ed_autor;
    EditText ed_editorial;
    EditText ed_isbn;
    EditText ed_paginas;
    EditText ed_anio;
    CheckBox cb_ebook;
    CheckBox cb_leido;
    RatingBar nota;
    EditText ed_resumen;
    private BDLibros DB = new BDLibros(this);
    private Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);

        ed_titulo = (EditText) findViewById(R.id.ed_titulo);
        ed_autor = (EditText) findViewById(R.id.ed_autor);
        ed_editorial = (EditText) findViewById(R.id.ed_editorial);
        ed_isbn = (EditText) findViewById(R.id.ed_isbn);
        ed_paginas = (EditText) findViewById(R.id.ed_paginas);
        ed_anio = (EditText) findViewById(R.id.ed_anio);
        cb_ebook = (CheckBox) findViewById(R.id.cb_ebook);
        cb_leido = (CheckBox) findViewById(R.id.cb_leido);
        nota = (RatingBar) findViewById(R.id.nota);
        ed_resumen = (EditText) findViewById(R.id.ed_resumen);

        ID = getIntent().getLongExtra("id", 0);

        if (ID != 0) {

            try {
                DB.openR();
            } catch (SQLException e) {
                e.printStackTrace();
            }

            insertar = false;
            editar = true;
            eliminar = true;

            cursor = DB.verLibro(ID);

            ed_titulo.setText(cursor.getString(cursor.getColumnIndexOrThrow("titulo")));
            ed_autor.setText(cursor.getString(cursor.getColumnIndexOrThrow("autor")));
            ed_editorial.setText(cursor.getString(cursor.getColumnIndexOrThrow("editorial")));
            ed_isbn.setText(cursor.getString(cursor.getColumnIndexOrThrow("isbn")));
            ed_paginas.setText(cursor.getString(cursor.getColumnIndexOrThrow("paginas")));
            ed_anio.setText(cursor.getString(cursor.getColumnIndexOrThrow("anio")));
            cb_ebook.setChecked(cursor.getInt(cursor.getColumnIndexOrThrow("ebook")) != 0);
            cb_leido.setChecked(cursor.getInt(cursor.getColumnIndexOrThrow("leido")) != 0);
            nota.setRating(cursor.getFloat(cursor.getColumnIndexOrThrow("nota")));
            ed_resumen.setText(cursor.getString(cursor.getColumnIndexOrThrow("resumen")));

            DB.close();
        } else {
            insertar = true;
            editar = false;
            eliminar = false;
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.guardar).setVisible(insertar);
        menu.findItem(R.id.eliminar).setVisible(eliminar);
        menu.findItem(R.id.edit).setVisible(editar);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.edit:
                Actualizar();
                break;
            case R.id.guardar: {
                Insertar();
                break;
            }
            case R.id.eliminar: {
                MuestraAlertaEliminar("Eliminar libro", "¿Quieres eliminar '" + ed_titulo.getText().toString() + "'?").show();
                break;
            }
        }
        return true;
    }


    public void Insertar() {
        try {
            if (ed_titulo.getText().toString().matches("") || ed_autor.getText().toString().matches("")) {
                Toast.makeText(this, "Introduce un título y un autor", Toast.LENGTH_SHORT).show();
            } else {
                try {
                    DB.openW();
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                DB.insertaLibro(
                        ed_titulo.getText().toString(),
                        ed_autor.getText().toString(),
                        ed_editorial.getText().toString(),
                        ed_isbn.getText().toString(),
                        ed_anio.getText().toString(),
                        ed_paginas.getText().toString(),
                        (cb_ebook.isChecked() ? 1 : 0),
                        (cb_leido.isChecked() ? 1 : 0),
                        nota.getRating(),
                        ed_resumen.getText().toString());

                DB.close();

                Toast.makeText(this, ed_titulo.getText().toString() + " añadido", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Error, no se ha insertado el libro", Toast.LENGTH_SHORT).show();
        }
    }

    public void Eliminar() {
        try {
            try {
                DB.openW();
            } catch (SQLException e) {
                e.printStackTrace();
            }

            DB.borraLibro(ID);

            DB.close();
        } catch (Exception e) {
            Toast.makeText(this, "Error, no se ha podído eliminar", Toast.LENGTH_SHORT).show();
        }
    }

    public void Actualizar() {
        try {
            if (ed_titulo.getText().toString().matches("") || ed_autor.getText().toString().matches("")) {
                Toast.makeText(this, "Introduce un título y un autor", Toast.LENGTH_SHORT).show();
            } else {
                try {
                    DB.openW();
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                DB.actualizaLibro(ID,
                        ed_titulo.getText().toString(),
                        ed_autor.getText().toString(),
                        ed_editorial.getText().toString(),
                        ed_isbn.getText().toString(),
                        ed_anio.getText().toString(),
                        ed_paginas.getText().toString(),
                        (cb_ebook.isChecked() ? 1 : 0),
                        (cb_leido.isChecked() ? 1 : 0),
                        nota.getRating(),
                        ed_resumen.getText().toString()
                );

                Toast.makeText(this, ed_titulo.getText().toString() + "actualizado", Toast.LENGTH_SHORT).show();
                DB.close();
            }

        } catch (Exception e) {
            Toast.makeText(this, "Error, el libro no se ha modificado", Toast.LENGTH_SHORT).show();
        }
    }

    private AlertDialog MuestraAlertaEliminar(String titulo, String mensaje) {

        AlertDialog.Builder alerta = new AlertDialog.Builder(this);

        alerta.setTitle(titulo);
        alerta.setMessage(mensaje);

        DialogInterface.OnClickListener listenerCancelar = new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(), "Operación cancelada", Toast.LENGTH_SHORT).show();
                dialog.cancel();
            }
        };

        DialogInterface.OnClickListener listenerOK = new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Eliminar();
                Toast.makeText(getApplicationContext(), "Libro eliminado", Toast.LENGTH_SHORT).show();
                finish();
            }
        };

        alerta.setPositiveButton("Aceptar", listenerOK);
        alerta.setNegativeButton("Cancelar", listenerCancelar);

        return alerta.create();
    }

}

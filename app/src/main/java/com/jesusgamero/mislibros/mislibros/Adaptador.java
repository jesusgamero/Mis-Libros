package com.jesusgamero.mislibros.mislibros;

/**
 * Created by Jesús Gamero de 2DAW on 07/02/2017.
 */

import android.annotation.TargetApi;
import android.content.Context;
import android.database.Cursor;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

public class Adaptador extends CursorAdapter {

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public Adaptador(Context context, Cursor cursor, int flags) {
        super(context, cursor, 0);
    }


    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.activity_element, parent, false);
    }


    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        TextView titulo = (TextView) view.findViewById(R.id.titulof);
        TextView autor = (TextView) view.findViewById(R.id.autorf);
        RatingBar nota = (RatingBar) view.findViewById(R.id.nota);
        ImageView imagen = (ImageView) view.findViewById(R.id.imagenf);

        String ntitulo = cursor.getString(cursor.getColumnIndexOrThrow("titulo"));
        String nautor = cursor.getString(cursor.getColumnIndexOrThrow("autor"));
        Float nnota = cursor.getFloat(cursor.getColumnIndexOrThrow("nota"));

        titulo.setText(ntitulo);
        autor.setText(nautor);
        nota.setRating(nnota);

        switch ((int) (Math.random() * 3)) {
            case 0:
                imagen.setImageResource(R.drawable.libro1);
                break;
            case 1:
                imagen.setImageResource(R.drawable.libro2);
                break;
            case 2:
                imagen.setImageResource(R.drawable.libro3);
                break;
            default:
                imagen.setImageResource(R.drawable.libro1);
                break;

        }
    }
}
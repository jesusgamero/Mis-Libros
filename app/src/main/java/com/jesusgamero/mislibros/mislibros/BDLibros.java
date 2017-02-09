package com.jesusgamero.mislibros.mislibros;

/**
 * Created by Jesús Gamero de 2DAW on 07/02/2017.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.sql.SQLException;

public class BDLibros {

    public static final String ROW_ID = "_id";
    public static final String TITULO = "titulo";
    public static final String AUTOR = "autor";
    public static final String EDITORIAL = "editorial";
    public static final String ISBN = "isbn";
    public static final String ANIO = "anio";
    public static final String PAGINAS = "paginas";
    public static final String EBOOK = "ebook";
    public static final String LEIDO = "leido";
    public static final String NOTA = "nota";
    public static final String RESUMEN = "resumen";

    static final String TAG = "BDLibros";
    static final String DATABASE_NAME = "MisLibros";
    static final String DATABASE_TABLE = "libro";
    static final int DATABASE_VERSION = 2;

    private static final String DATABASE_CREATE = "CREATE TABLE libro (_id integer primary key autoincrement, "
            + TITULO + " text, "
            + AUTOR + " text, "
            + EDITORIAL + " text, "
            + ISBN + " text, "
            + ANIO + " text, "
            + PAGINAS + " text, "
            + EBOOK + " integer, "
            + LEIDO + " integer, "
            + NOTA + " float, "
            + RESUMEN + " text "
            + ");";
    final Context mCtx;
    DatabaseHelper mDbHelper;
    SQLiteDatabase myBD;

    public BDLibros(Context ctx) {
        this.mCtx = ctx;
        mDbHelper = new DatabaseHelper(mCtx);
    }

    public long insertaLibro(String titulo, String autor, String editorial, String isbn, String anio,
                             String paginas, Integer ebook, Integer leido, Float nota, String resumen) {

        ContentValues campo = new ContentValues();

        campo.put(TITULO, titulo);
        campo.put(AUTOR, autor);
        campo.put(EDITORIAL, editorial);
        campo.put(ISBN, isbn);
        campo.put(ANIO, anio);
        campo.put(PAGINAS, paginas);
        campo.put(EBOOK, ebook);
        campo.put(LEIDO, leido);
        campo.put(NOTA, nota);
        campo.put(RESUMEN, resumen);

        return this.myBD.insert(DATABASE_TABLE, null, campo);
    }

    public boolean borraLibro(long rowId) {

        return this.myBD.delete(DATABASE_TABLE, ROW_ID + "=" + rowId, null) > 0; //$NON-NLS-1$
    }

    public boolean actualizaLibro(long rowId, String titulo, String autor, String editorial, String isbn, String anio,
                                  String paginas, Integer ebook, Integer leido, Float nota, String resumen) {
        ContentValues campos = new ContentValues();

        campos.put(TITULO, titulo);
        campos.put(AUTOR, autor);
        campos.put(EDITORIAL, editorial);
        campos.put(ISBN, isbn);
        campos.put(ANIO, anio);
        campos.put(PAGINAS, paginas);
        campos.put(EBOOK, ebook);
        campos.put(LEIDO, leido);
        campos.put(NOTA, nota);
        campos.put(RESUMEN, resumen);

        return this.myBD.update(DATABASE_TABLE, campos, ROW_ID + "=" + rowId, null) > 0; //$NON-NLS-1$
    }

    public Cursor verLibros() {

        return this.myBD.rawQuery("SELECT * FROM libro", null);
    }

    public Cursor verLibro(long rowId) {

        Cursor cursor = myBD.rawQuery("SELECT * FROM libro" + " WHERE _id = " + rowId, null);

        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public int numeroLibros() throws SQLException {

        Cursor cursor = myBD.rawQuery("SELECT COUNT(*) FROM libro", null);
        cursor.moveToFirst();
        return cursor.getInt(0);
    }

    public BDLibros openW() throws SQLException {
        myBD = this.mDbHelper.getWritableDatabase();
        return this;
    }

    public BDLibros openR() throws SQLException {
        myBD = mDbHelper.getReadableDatabase();
        return this;
    }

    public void close() {
        this.mDbHelper.close();
    }

    private class DatabaseHelper extends SQLiteOpenHelper {

        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(DATABASE_CREATE);
        }


        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w(TAG, "Upgrading database from version " + oldVersion + " to " //$NON-NLS-1$//$NON-NLS-2$
                    + newVersion + ", which will destroy all old data"); //$NON-NLS-1$
            //db.execSQL("DROP TABLE IF EXISTS usersinfo"); //$NON-NLS-1$
            onCreate(db);
        }
    }

}

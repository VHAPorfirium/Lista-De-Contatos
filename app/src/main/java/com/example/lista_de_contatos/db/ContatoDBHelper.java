package com.example.lista_de_contatos.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.lista_de_contatos.db.ContratoContato.ContatoEntry;

public class ContatoDBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "contatos.db";
    private static final int DATABASE_VERSION = 1;

    public ContatoDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_CONTATOS_TABLE = "CREATE TABLE " + ContatoEntry.TABLE_NAME + " ("
                + ContatoEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + ContatoEntry.COLUMN_NOME + " TEXT NOT NULL, "
                + ContatoEntry.COLUMN_TELEFONE + " TEXT, "
                + ContatoEntry.COLUMN_ENDERECO + " TEXT, "
                + ContatoEntry.COLUMN_EMAIL + " TEXT, "
                + ContatoEntry.COLUMN_LINKEDIN + " TEXT, "
                + ContatoEntry.COLUMN_FOTO + " TEXT, "
                + ContatoEntry.COLUMN_CONTATO_FAVORITO + " INTEGER DEFAULT 0"
                + ");";
        db.execSQL(SQL_CREATE_CONTATOS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Descartar a tabela antiga e recriar a nova para simplificar
        db.execSQL("DROP TABLE IF EXISTS " + ContatoEntry.TABLE_NAME);
        onCreate(db);
    }
}

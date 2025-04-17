package com.example.lista_de_contatos.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.lista_de_contatos.db.ContratoContato.ContatoEntry;

public class ContatoDBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "contatos.db";
    private static final int DATABASE_VERSION = 1;

    // Cria o helper com nome e versão do banco
    public ContatoDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Executado na primeira criação do banco, define a tabela de contatos
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

    // Executado quando a versão do banco muda; descarta e recria a tabela
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + ContatoEntry.TABLE_NAME);
        onCreate(db);
    }
}

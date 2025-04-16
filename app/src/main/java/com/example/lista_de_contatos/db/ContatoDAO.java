package com.example.lista_de_contatos.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.lista_de_contatos.models.Contato;
import com.example.lista_de_contatos.db.ContratoContato.ContatoEntry;

import java.util.ArrayList;
import java.util.List;

public class ContatoDAO {

    private SQLiteDatabase database;
    private ContatoDBHelper dbHelper;

    public ContatoDAO(Context context) {
        dbHelper = new ContatoDBHelper(context);
        database = dbHelper.getWritableDatabase();
    }

    public long inserirContato(Contato contato) {
        ContentValues values = new ContentValues();
        values.put(ContatoEntry.COLUMN_NOME, contato.getNome());
        values.put(ContatoEntry.COLUMN_TELEFONE, contato.getTelefone());
        values.put(ContatoEntry.COLUMN_ENDERECO, contato.getEndereco());
        values.put(ContatoEntry.COLUMN_EMAIL, contato.getEmail());
        values.put(ContatoEntry.COLUMN_LINKEDIN, contato.getLinkedin());
        values.put(ContatoEntry.COLUMN_FOTO, contato.getFoto());
        values.put(ContatoEntry.COLUMN_CONTATO_FAVORITO, contato.isContatoFavorito() ? 1 : 0);
        long id = database.insert(ContatoEntry.TABLE_NAME, null, values);
        contato.setId(id);
        return id;
    }

    public int atualizarContato(Contato contato) {
        ContentValues values = new ContentValues();
        values.put(ContatoEntry.COLUMN_NOME, contato.getNome());
        values.put(ContatoEntry.COLUMN_TELEFONE, contato.getTelefone());
        values.put(ContatoEntry.COLUMN_ENDERECO, contato.getEndereco());
        values.put(ContatoEntry.COLUMN_EMAIL, contato.getEmail());
        values.put(ContatoEntry.COLUMN_LINKEDIN, contato.getLinkedin());
        values.put(ContatoEntry.COLUMN_FOTO, contato.getFoto());
        values.put(ContatoEntry.COLUMN_CONTATO_FAVORITO, contato.isContatoFavorito() ? 1 : 0);
        String selection = ContatoEntry._ID + " = ?";
        String[] selectionArgs = { String.valueOf(contato.getId()) };
        return database.update(ContatoEntry.TABLE_NAME, values, selection, selectionArgs);
    }

    public int deletarContato(long id) {
        String selection = ContatoEntry._ID + " = ?";
        String[] selectionArgs = { String.valueOf(id) };
        return database.delete(ContatoEntry.TABLE_NAME, selection, selectionArgs);
    }

    public List<Contato> getAllContatos() {
        List<Contato> contatos = new ArrayList<>();
        Cursor cursor = database.query(ContatoEntry.TABLE_NAME, null, null, null, null, null, ContatoEntry.COLUMN_NOME + " ASC");
        if (cursor != null && cursor.moveToFirst()) {
            do {
                Contato contato = new Contato();
                contato.setId(cursor.getLong(cursor.getColumnIndexOrThrow(ContatoEntry._ID)));
                contato.setNome(cursor.getString(cursor.getColumnIndexOrThrow(ContatoEntry.COLUMN_NOME)));
                contato.setTelefone(cursor.getString(cursor.getColumnIndexOrThrow(ContatoEntry.COLUMN_TELEFONE)));
                contato.setEndereco(cursor.getString(cursor.getColumnIndexOrThrow(ContatoEntry.COLUMN_ENDERECO)));
                contato.setEmail(cursor.getString(cursor.getColumnIndexOrThrow(ContatoEntry.COLUMN_EMAIL)));
                contato.setLinkedin(cursor.getString(cursor.getColumnIndexOrThrow(ContatoEntry.COLUMN_LINKEDIN)));
                contato.setFoto(cursor.getString(cursor.getColumnIndexOrThrow(ContatoEntry.COLUMN_FOTO)));
                int fav = cursor.getInt(cursor.getColumnIndexOrThrow(ContatoEntry.COLUMN_CONTATO_FAVORITO));
                contato.setContatoFavorito(fav == 1);
                contatos.add(contato);
            } while (cursor.moveToNext());
        }
        if (cursor != null)
            cursor.close();
        return contatos;
    }

    // Retorna o contato do dono (identificado pelo email fixo "meuemail@exemplo.com")
    public Contato getContatoDono() {
        Contato contato = null;
        String selection = ContatoEntry.COLUMN_EMAIL + " = ?";
        String[] selectionArgs = { "meuemail@exemplo.com" };
        Cursor cursor = database.query(ContatoEntry.TABLE_NAME, null, selection, selectionArgs, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            contato = new Contato();
            contato.setId(cursor.getLong(cursor.getColumnIndexOrThrow(ContatoEntry._ID)));
            contato.setNome(cursor.getString(cursor.getColumnIndexOrThrow(ContatoEntry.COLUMN_NOME)));
            contato.setTelefone(cursor.getString(cursor.getColumnIndexOrThrow(ContatoEntry.COLUMN_TELEFONE)));
            contato.setEndereco(cursor.getString(cursor.getColumnIndexOrThrow(ContatoEntry.COLUMN_ENDERECO)));
            contato.setEmail(cursor.getString(cursor.getColumnIndexOrThrow(ContatoEntry.COLUMN_EMAIL)));
            contato.setLinkedin(cursor.getString(cursor.getColumnIndexOrThrow(ContatoEntry.COLUMN_LINKEDIN)));
            contato.setFoto(cursor.getString(cursor.getColumnIndexOrThrow(ContatoEntry.COLUMN_FOTO)));
            int fav = cursor.getInt(cursor.getColumnIndexOrThrow(ContatoEntry.COLUMN_CONTATO_FAVORITO));
            contato.setContatoFavorito(fav == 1);
        }
        if (cursor != null)
            cursor.close();
        return contato;
    }

    // Retorna todos os contatos exceto o contato do dono (email fixo)
    public List<Contato> getAllContatosExcludingOwner() {
        List<Contato> contatos = new ArrayList<>();
        String selection = ContatoEntry.COLUMN_EMAIL + " <> ?";
        String[] selectionArgs = { "meuemail@exemplo.com" };
        Cursor cursor = database.query(ContatoEntry.TABLE_NAME, null, selection, selectionArgs, null, null, ContatoEntry.COLUMN_NOME + " ASC");
        if (cursor != null && cursor.moveToFirst()) {
            do {
                Contato contato = new Contato();
                contato.setId(cursor.getLong(cursor.getColumnIndexOrThrow(ContatoEntry._ID)));
                contato.setNome(cursor.getString(cursor.getColumnIndexOrThrow(ContatoEntry.COLUMN_NOME)));
                contato.setTelefone(cursor.getString(cursor.getColumnIndexOrThrow(ContatoEntry.COLUMN_TELEFONE)));
                contato.setEndereco(cursor.getString(cursor.getColumnIndexOrThrow(ContatoEntry.COLUMN_ENDERECO)));
                contato.setEmail(cursor.getString(cursor.getColumnIndexOrThrow(ContatoEntry.COLUMN_EMAIL)));
                contato.setLinkedin(cursor.getString(cursor.getColumnIndexOrThrow(ContatoEntry.COLUMN_LINKEDIN)));
                contato.setFoto(cursor.getString(cursor.getColumnIndexOrThrow(ContatoEntry.COLUMN_FOTO)));
                int fav = cursor.getInt(cursor.getColumnIndexOrThrow(ContatoEntry.COLUMN_CONTATO_FAVORITO));
                contato.setContatoFavorito(fav == 1);
                contatos.add(contato);
            } while (cursor.moveToNext());
        }
        if (cursor != null)
            cursor.close();
        return contatos;
    }

    public void close() {
        dbHelper.close();
    }
}

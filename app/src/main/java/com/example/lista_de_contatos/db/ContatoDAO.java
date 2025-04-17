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

    // Inicializa o helper e abre o banco para escrita
    public ContatoDAO(Context context) {
        dbHelper = new ContatoDBHelper(context);
        database = dbHelper.getWritableDatabase();
    }

    // Insere um novo contato no banco e atualiza seu ID
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

    // Atualiza os dados de um contato existente pelo seu ID
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

    // Remove um contato do banco pelo seu ID
    public int deletarContato(long id) {
        String selection = ContatoEntry._ID + " = ?";
        String[] selectionArgs = { String.valueOf(id) };
        return database.delete(ContatoEntry.TABLE_NAME, selection, selectionArgs);
    }

    // Recupera todos os contatos ordenados por nome
    public List<Contato> getAllContatos() {
        List<Contato> contatos = new ArrayList<>();
        Cursor cursor = database.query(
                ContatoEntry.TABLE_NAME,
                null, null, null, null, null,
                ContatoEntry.COLUMN_NOME + " ASC"
        );
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
                contato.setContatoFavorito(
                        cursor.getInt(cursor.getColumnIndexOrThrow(ContatoEntry.COLUMN_CONTATO_FAVORITO)) == 1
                );
                contatos.add(contato);
            } while (cursor.moveToNext());
            cursor.close();
        }
        return contatos;
    }

    // Fecha o helper para liberar recursos
    public void close() {
        dbHelper.close();
    }
}

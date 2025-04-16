package com.example.lista_de_contatos.db;

import android.provider.BaseColumns;

public final class ContratoContato {
    private ContratoContato() { }

    public static class ContatoEntry implements BaseColumns {
        public static final String TABLE_NAME = "contatos";
        public static final String COLUMN_NOME = "nome";
        public static final String COLUMN_TELEFONE = "telefone";
        public static final String COLUMN_ENDERECO = "endereco";
        public static final String COLUMN_EMAIL = "email";
        public static final String COLUMN_LINKEDIN = "linkedin";
        public static final String COLUMN_FOTO = "foto";
        public static final String COLUMN_CONTATO_FAVORITO = "favorito";
    }
}

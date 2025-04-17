package com.example.lista_de_contatos.db;

import android.provider.BaseColumns;

public final class ContratoContato { // Define o contrato do banco (tabela e colunas)
    private ContratoContato() { } // Impede instanciação desta classe utilitária

    public static class ContatoEntry implements BaseColumns { // Declara as colunas da tabela “contatos”
        public static final String TABLE_NAME = "contatos";          // Nome da tabela
        public static final String COLUMN_NOME = "nome";             // Coluna para nome
        public static final String COLUMN_TELEFONE = "telefone";     // Coluna para telefone
        public static final String COLUMN_ENDERECO = "endereco";     // Coluna para endereço
        public static final String COLUMN_EMAIL = "email";           // Coluna para e‑mail
        public static final String COLUMN_LINKEDIN = "linkedin";     // Coluna para LinkedIn
        public static final String COLUMN_FOTO = "foto";             // Coluna para URI da foto
        public static final String COLUMN_CONTATO_FAVORITO = "favorito"; // Coluna para flag de favorito
    }
}

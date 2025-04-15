package com.example.lista_de_contatos.utils;

//Classe que define as constantes utilizadas em toda a aplicação.
public class Constants {
    // Chave para passar o objeto Contato entre Activities via Intent
    public static final String EXTRA_CONTATO = "extra_contato";

    // Constantes para a tabela de contatos no SQLite
    public static final String TABLE_CONTATOS = "contatos";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NOME = "nome";
    public static final String COLUMN_TELEFONE = "telefone";
    public static final String COLUMN_ENDERECO = "endereco";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_LINKEDIN = "linkedin";
    public static final String COLUMN_FOTO = "foto";
    public static final String COLUMN_CONTATO_FAVORITO = "contato_favorito";
}

package com.example.lista_de_contatos.models;

import java.io.Serializable;

// Classe que representa um Contato
public class Contato implements Serializable {

    // Atributos da classe
    private String nome;
    private String telefone;
    private String endereco;
    private String email;
    private String linkedin;
    private String foto; // Pode representar o caminho da foto ou URL
    private boolean contatoFavorito;


    //Construtor vazio para instâncias sem inicialização imediata.
    public Contato() {
    }

    //Construtor completo.
    public Contato(String nome, String telefone, String endereco, String email, String linkedin, String foto, boolean contatoFavorito) {
        this.nome = nome;
        this.telefone = telefone;
        this.endereco = endereco;
        this.email = email;
        this.linkedin = linkedin;
        this.foto = foto;
        this.contatoFavorito = contatoFavorito;
    }

    // Getters e Setters

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLinkedin() {
        return linkedin;
    }

    public void setLinkedin(String linkedin) {
        this.linkedin = linkedin;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public boolean isContatoFavorito() {
        return contatoFavorito;
    }

    public void setContatoFavorito(boolean contatoFavorito) {
        this.contatoFavorito = contatoFavorito;
    }


    //Retorna uma representação em String do objeto Contato.
    @Override
    public String toString() {
        return "Contato{" +
                "nome='" + nome + '\'' +
                ", telefone='" + telefone + '\'' +
                ", endereco='" + endereco + '\'' +
                ", email='" + email + '\'' +
                ", linkedin='" + linkedin + '\'' +
                ", foto='" + foto + '\'' +
                ", contatoFavorito=" + contatoFavorito +
                '}';
    }
}

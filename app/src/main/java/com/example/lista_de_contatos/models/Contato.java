package com.example.lista_de_contatos.models;

import java.io.Serializable;

public class Contato implements Serializable {
    // Campo para o ID gerado pelo banco
    private long id;
    private String nome;
    private String telefone;
    private String endereco;
    private String email;
    private String linkedin;
    private String foto;
    private boolean contatoFavorito;

    public Contato() { }

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
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }

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

    @Override
    public String toString() {
        return "Contato{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", telefone='" + telefone + '\'' +
                ", endereco='" + endereco + '\'' +
                ", email='" + email + '\'' +
                ", linkedin='" + linkedin + '\'' +
                ", foto='" + foto + '\'' +
                ", contatoFavorito=" + contatoFavorito +
                '}';
    }
}

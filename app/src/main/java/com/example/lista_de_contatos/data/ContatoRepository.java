package com.example.lista_de_contatos.data;

import com.example.lista_de_contatos.models.Contato;
import java.util.ArrayList;
import java.util.List;

public class ContatoRepository {
    private static ContatoRepository instance;
    private List<Contato> contatos;

    private ContatoRepository() {
        contatos = new ArrayList<>();
    }

    public static ContatoRepository getInstance(){
        if(instance == null){
            instance = new ContatoRepository();
        }
        return instance;
    }

    public List<Contato> getContatos(){
        return contatos;
    }

    public void adicionarContato(Contato contato){
        contatos.add(contato);
    }

    // Em um caso real, você teria métodos para atualizar e excluir contatos também.
}

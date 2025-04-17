package com.example.lista_de_contatos.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lista_de_contatos.R;
import com.example.lista_de_contatos.adapters.ContatoAdapter;
import com.example.lista_de_contatos.db.ContatoDAO;
import com.example.lista_de_contatos.models.Contato;
import com.example.lista_de_contatos.sensors.ProximitySensorManager;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ProximitySensorManager.OnProximityListener {

    private RecyclerView recyclerView;
    private ContatoAdapter contatoAdapter;
    private SearchView searchView;
    private ContatoDAO contatoDAO;
    private ProximitySensorManager proximityManager;

    // Inicializa UI, busca contatos e configura sensor de proximidade
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        contatoDAO = new ContatoDAO(this);

        recyclerView = findViewById(R.id.recyclerViewContatos);
        searchView  = findViewById(R.id.search_view_contatos);

        // Carrega todos os contatos do banco de dados
        List<Contato> contatosList = contatoDAO.getAllContatos();
        contatoAdapter = new ContatoAdapter(contatosList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(contatoAdapter);

        // Filtragem pelo SearchView
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener(){
            @Override
            public boolean onQueryTextSubmit(String query) { return false; }
            @Override
            public boolean onQueryTextChange(String newText) {
                contatoAdapter.getFilter().filter(newText);
                return false;
            }
        });

        // Clique em item abre DetalhesActivity
        contatoAdapter.setOnItemClickListener(new ContatoAdapter.OnItemClickListener(){
            @Override
            public void onItemClick(Contato contato) {
                Intent intent = new Intent(MainActivity.this, DetalhesActivity.class);
                intent.putExtra("EXTRA_CONTATO", contato);
                startActivity(intent);
            }
        });

        // FAB para adicionar novo contato
        findViewById(R.id.fabAddContato).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AdicionarNumeroActivity.class));
            }
        });

        // Gerenciador do sensor de proximidade
        proximityManager = new ProximitySensorManager(this, this);
    }

    // Re-registra sensor e atualiza lista de contatos ao voltar ao foco
    @Override
    protected void onResume() {
        super.onResume();
        proximityManager.register();
        contatoAdapter.updateList(contatoDAO.getAllContatos());
    }

    // Desregistra o sensor para economizar recursos
    @Override
    protected void onPause() {
        super.onPause();
        proximityManager.unregister();
    }

    // Fecha o DAO ao destruir a Activity
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (contatoDAO != null) contatoDAO.close();
    }

    // Dispara quando algo se aproxima do sensor: liga para favorito(s)
    @Override
    public void onObjectNear() {
        List<Contato> todos = contatoDAO.getAllContatos();
        final List<Contato> favoritos = new ArrayList<>();
        for (Contato c : todos) {
            if (c.isContatoFavorito()) {
                favoritos.add(c);
            }
        }

        if (favoritos.isEmpty()) {
            Toast.makeText(this, "Nenhum favorito definido", Toast.LENGTH_SHORT).show();
        }
        else if (favoritos.size() == 1) {
            Contato unico = favoritos.get(0);
            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + unico.getTelefone()));
            startActivity(intent);
        }
        else {
            String[] nomes = new String[favoritos.size()];
            for (int i = 0; i < favoritos.size(); i++) {
                nomes[i] = favoritos.get(i).getNome();
            }

            new AlertDialog.Builder(this)
                    .setTitle("Escolha um favorito")
                    .setItems(nomes, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Contato escolhido = favoritos.get(which);
                            Intent intent = new Intent(Intent.ACTION_DIAL,
                                    Uri.parse("tel:" + escolhido.getTelefone()));
                            startActivity(intent);
                        }
                    })
                    .setNegativeButton("Cancelar", null)
                    .show();
        }
    }
}

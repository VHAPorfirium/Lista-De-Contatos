package com.example.lista_de_contatos.activity;

import android.content.Intent;
import android.net.Uri;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lista_de_contatos.R;
import com.example.lista_de_contatos.adapters.ContatoAdapter;
import com.example.lista_de_contatos.db.ContatoDAO;
import com.example.lista_de_contatos.models.Contato;

import java.util.List;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private RecyclerView recyclerView;
    private ContatoAdapter contatoAdapter;
    private SearchView searchView;
    private ContatoDAO contatoDAO;

    // Sensor
    private SensorManager sensorManager;
    private Sensor proximitySensor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        contatoDAO = new ContatoDAO(this);

        recyclerView = findViewById(R.id.recyclerViewContatos);
        searchView = findViewById(R.id.search_view_contatos);

        // Carrega todos os contatos do banco de dados
        List<Contato> contatosList = contatoDAO.getAllContatos();
        // Se estiver vazia, não insere dummy – deixa a lista vazia para que o usuário adicione novos contatos.
        contatoAdapter = new ContatoAdapter(contatosList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(contatoAdapter);

        // Configura a filtragem através do SearchView
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener(){
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                contatoAdapter.getFilter().filter(newText);
                return false;
            }
        });

        // Ao clicar em um item da lista, abre a tela de detalhes do contato
        contatoAdapter.setOnItemClickListener(new ContatoAdapter.OnItemClickListener(){
            @Override
            public void onItemClick(Contato contato) {
                Intent intent = new Intent(MainActivity.this, DetalhesActivity.class);
                intent.putExtra("EXTRA_CONTATO", contato);
                startActivity(intent);
            }
        });

        // Botão flutuante para adicionar um novo contato
        findViewById(R.id.fabAddContato).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AdicionarNumeroActivity.class);
                startActivity(intent);
            }
        });

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        if (sensorManager != null) {
            proximitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (proximitySensor != null) {
            sensorManager.registerListener(this, proximitySensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
        // Atualiza a lista de contatos
        List<Contato> updatedList = contatoDAO.getAllContatos();
        contatoAdapter.updateList(updatedList);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (contatoDAO != null) {
            contatoDAO.close();
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_PROXIMITY) {
            if (event.values[0] < proximitySensor.getMaximumRange()) {
                List<Contato> contatos = contatoDAO.getAllContatos();
                Contato favorito = null;
                for (Contato c : contatos) {
                    if (c.isContatoFavorito()) {
                        favorito = c;
                        break;
                    }
                }
                if (favorito != null) {
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:" + favorito.getTelefone()));
                    startActivity(intent);
                }
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) { }
}

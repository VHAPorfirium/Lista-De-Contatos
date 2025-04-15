package com.example.lista_de_contatos.activity;

import android.content.Intent;
import android.net.Uri;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lista_de_contatos.R;
import com.example.lista_de_contatos.adapters.ContatoAdapter;
import com.example.lista_de_contatos.data.ContatoRepository;
import com.example.lista_de_contatos.models.Contato;

import java.util.List;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private RecyclerView recyclerView;
    private ContatoAdapter contatoAdapter;
    private SearchView searchView;
    private LinearLayout layoutContatoDono; // Seção para o contato do dono

    // Variáveis para o sensor de proximidade
    private SensorManager sensorManager;
    private Sensor proximitySensor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicializa as views
        recyclerView = findViewById(R.id.recyclerViewContatos);
        searchView = findViewById(R.id.search_view_contatos);
        layoutContatoDono = findViewById(R.id.layout_contato_dono);

        // Configura o clique para a seção do contato do dono
        layoutContatoDono.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Crie o objeto Contato com suas informações (substitua pelos seus dados reais)
                Contato contatoDono = new Contato(
                        "Meu Nome",
                        "(99) 99999-9999",
                        "Meu Endereço, nº 123",
                        "meuemail@exemplo.com",
                        "linkedin.com/in/meuperfil",
                        "",       // Se você tiver uma foto ou caminho, coloque aqui; ou deixe vazio
                        false     // Esse campo não interfere na exibição deste contato
                );

                // Abre a tela de detalhes passando as informações do dono
                Intent intent = new Intent(MainActivity.this, DetalhesActivity.class);
                intent.putExtra("EXTRA_CONTATO", contatoDono);
                startActivity(intent);
            }
        });

        // Carrega a lista de contatos do repositório
        List<Contato> contatosList = ContatoRepository.getInstance().getContatos();
        if(contatosList.isEmpty()){
            // Se a lista estiver vazia, adicione alguns contatos dummy para teste
            contatosList.add(new Contato("Alice", "(11) 11111-1111", "Rua A, 123", "alice@mail.com", "linkedin.com/in/alice", "", true));
            contatosList.add(new Contato("Bob", "(22) 22222-2222", "Rua B, 456", "bob@mail.com", "linkedin.com/in/bob", "", false));
        }

        // Configura o adapter – os contatos já serão ordenados em ordem alfabética no adapter
        contatoAdapter = new ContatoAdapter(contatosList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(contatoAdapter);

        // Configura a filtragem através do SearchView
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Opcional: tratar submit (fechar teclado, por exemplo)
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                contatoAdapter.getFilter().filter(newText);
                return false;
            }
        });

        // Quando um item da lista é clicado, abre a tela de detalhes com os dados do contato
        contatoAdapter.setOnItemClickListener(new ContatoAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Contato contato) {
                Intent intent = new Intent(MainActivity.this, DetalhesActivity.class);
                intent.putExtra("EXTRA_CONTATO", contato);
                startActivity(intent);
            }
        });

        // Configura o botão flutuante para adicionar um novo contato
        findViewById(R.id.fabAddContato).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AdicionarNumeroActivity.class);
                startActivity(intent);
            }
        });

        // Configura o sensor de proximidade
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        if (sensorManager != null) {
            proximitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        }
    }

    // (Métodos onSensorChanged, onAccuracyChanged, onResume e onPause permanecem inalterados)

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_PROXIMITY) {
            if (event.values[0] < proximitySensor.getMaximumRange()) {
                Contato favorito = null;
                // Percorre a lista para encontrar um contato favorito
                for (Contato contato : ContatoRepository.getInstance().getContatos()) {
                    if (contato.isContatoFavorito()) {
                        favorito = contato;
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
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Não utilizado neste exemplo
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (proximitySensor != null) {
            sensorManager.registerListener(this, proximitySensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }
}

package com.example.lista_de_contatos.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.lista_de_contatos.R;
import com.example.lista_de_contatos.models.Contato;

public class DetalhesActivity extends AppCompatActivity {

    private ImageView imgFotoContato;
    private TextView txtNomeContato, txtTelefoneContato, txtEmailContato, txtLinkedinContato, txtEnderecoContato;
    private CheckBox chkFavorito;
    private Button btnLigar, btnEnviarEmail, btnEditarContato;
    private Contato contato;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes);

        // Buscar as views
        imgFotoContato = findViewById(R.id.imgFotoContato);
        txtNomeContato = findViewById(R.id.txtNomeContato);
        txtTelefoneContato = findViewById(R.id.txtTelefoneContato);
        txtEmailContato = findViewById(R.id.txtEmailContato);
        txtLinkedinContato = findViewById(R.id.txtLinkedinContato);
        txtEnderecoContato = findViewById(R.id.txtEnderecoContato);
        chkFavorito = findViewById(R.id.chkFavorito);
        btnLigar = findViewById(R.id.btnLigar);
        btnEnviarEmail = findViewById(R.id.btnEnviarEmail);
        btnEditarContato = findViewById(R.id.btnEditarContato);

        // Recupera o contato passado via Intent
        contato = (Contato) getIntent().getSerializableExtra("EXTRA_CONTATO");
        if (contato != null) {
            loadContatoData();
        }

        // Ação para realizar ligação
        btnLigar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (contato != null) {
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:" + contato.getTelefone()));
                    startActivity(intent);
                }
            }
        });

        // Ação para enviar e-mail
        btnEnviarEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (contato != null) {
                    Intent intent = new Intent(Intent.ACTION_SENDTO);
                    intent.setData(Uri.parse("mailto:" + contato.getEmail()));
                    startActivity(intent);
                }
            }
        });

        // Ação para editar o contato
        btnEditarContato.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetalhesActivity.this, EditarNumeroActivity.class);
                intent.putExtra("EXTRA_CONTATO", contato);
                startActivity(intent);
            }
        });
    }

    /**
     * Carrega os dados do contato nas views.
     */
    private void loadContatoData() {
        txtNomeContato.setText(contato.getNome());
        txtTelefoneContato.setText(contato.getTelefone());
        txtEmailContato.setText(contato.getEmail());
        txtLinkedinContato.setText(contato.getLinkedin());
        txtEnderecoContato.setText(contato.getEndereco());
        chkFavorito.setChecked(contato.isContatoFavorito());

        // Se houver foto, pode usar uma biblioteca (Glide, Picasso) para carregar, senão usa placeholder
        if (contato.getFoto() != null && !contato.getFoto().isEmpty()) {
            // Exemplo:
            // Glide.with(this).load(contato.getFoto()).into(imgFotoContato);
        } else {
            imgFotoContato.setImageResource(R.drawable.ic_contact_placeholder);
        }
    }
}

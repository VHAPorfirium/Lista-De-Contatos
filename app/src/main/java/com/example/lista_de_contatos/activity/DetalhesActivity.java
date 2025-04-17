package com.example.lista_de_contatos.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.lista_de_contatos.R;
import com.example.lista_de_contatos.db.ContatoDAO;
import com.example.lista_de_contatos.models.Contato;

public class DetalhesActivity extends AppCompatActivity {

    private ImageView imgFotoContato;
    private TextView txtNomeContato, txtTelefoneContato, txtEmailContato, txtLinkedinContato, txtEnderecoContato;
    private CheckBox chkFavorito;
    private Button btnLigar, btnEnviarEmail, btnEditarContato, btnExcluirContato;
    private Contato contato;
    private ContatoDAO contatoDAO;

    // Called when the Activity is created: binds views and sets up button actions
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes);

        contatoDAO = new ContatoDAO(this);

        imgFotoContato       = findViewById(R.id.imgFotoContato);
        txtNomeContato       = findViewById(R.id.txtNomeContato);
        txtTelefoneContato   = findViewById(R.id.txtTelefoneContato);
        txtEmailContato      = findViewById(R.id.txtEmailContato);
        txtLinkedinContato   = findViewById(R.id.txtLinkedinContato);
        txtEnderecoContato   = findViewById(R.id.txtEnderecoContato);
        chkFavorito          = findViewById(R.id.chkFavorito);
        btnLigar             = findViewById(R.id.btnLigar);
        btnEnviarEmail       = findViewById(R.id.btnEnviarEmail);
        btnEditarContato     = findViewById(R.id.btnEditarContato);
        btnExcluirContato    = findViewById(R.id.btnExcluirContato);

        contato = (Contato) getIntent().getSerializableExtra("EXTRA_CONTATO");
        if (contato != null) {
            loadContatoData();
        }

        btnLigar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if (contato != null) {
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(android.net.Uri.parse("tel:" + contato.getTelefone()));
                    startActivity(intent);
                }
            }
        });

        btnEnviarEmail.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if (contato != null) {
                    Intent intent = new Intent(Intent.ACTION_SENDTO);
                    intent.setData(android.net.Uri.parse("mailto:" + contato.getEmail()));
                    startActivity(intent);
                }
            }
        });

        btnEditarContato.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(DetalhesActivity.this, EditarNumeroActivity.class);
                intent.putExtra("EXTRA_CONTATO", contato);
                startActivity(intent);
            }
        });

        btnExcluirContato.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if (contato != null) {
                    int linhasAfetadas = contatoDAO.deletarContato(contato.getId());
                    if (linhasAfetadas > 0) {
                        Toast.makeText(DetalhesActivity.this, "Contato excluído com sucesso", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(DetalhesActivity.this, "Erro ao excluir contato", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    // Populates all UI elements with the contact's data
    private void loadContatoData(){
        txtNomeContato.setText(contato.getNome());
        txtTelefoneContato.setText(contato.getTelefone());
        txtEmailContato.setText(contato.getEmail());
        txtLinkedinContato.setText(contato.getLinkedin());
        txtEnderecoContato.setText(contato.getEndereco());
        chkFavorito.setChecked(contato.isContatoFavorito());
        if (contato.getFoto() != null && !contato.getFoto().isEmpty()){
            // Exemplo com Glide:
            // Glide.with(this).load(contato.getFoto()).into(imgFotoContato);
        } else {
            imgFotoContato.setImageResource(R.drawable.ic_contact_placeholder);
        }
    }

    // Releases the DAO resource when the Activity is destroyed
    @Override
    protected void onDestroy(){
        super.onDestroy();
        if (contatoDAO != null) {
            contatoDAO.close();
        }
    }
}

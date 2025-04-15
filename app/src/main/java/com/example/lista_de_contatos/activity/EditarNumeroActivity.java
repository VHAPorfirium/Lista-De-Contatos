package com.example.lista_de_contatos.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.lista_de_contatos.R;
import com.example.lista_de_contatos.models.Contato;

public class EditarNumeroActivity extends AppCompatActivity {

    private ImageView imgFotoContatoEditar;
    private Button btnEditarFoto, btnAtualizarContato;
    private EditText edtNomeEditar, edtTelefoneEditar, edtEmailEditar, edtLinkedinEditar, edtEnderecoEditar;
    private CheckBox chkFavoritoEditar;
    private Contato contato;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_numero);

        // Inicializa as views
        imgFotoContatoEditar = findViewById(R.id.imgFotoContatoEditar);
        btnEditarFoto = findViewById(R.id.btnEditarFoto);
        edtNomeEditar = findViewById(R.id.edtNomeEditar);
        edtTelefoneEditar = findViewById(R.id.edtTelefoneEditar);
        edtEmailEditar = findViewById(R.id.edtEmailEditar);
        edtLinkedinEditar = findViewById(R.id.edtLinkedinEditar);
        edtEnderecoEditar = findViewById(R.id.edtEnderecoEditar);
        chkFavoritoEditar = findViewById(R.id.chkFavoritoEditar);
        btnAtualizarContato = findViewById(R.id.btnAtualizarContato);

        // Recupera o contato a ser editado via Intent
        contato = (Contato) getIntent().getSerializableExtra("EXTRA_CONTATO");
        if (contato != null) {
            loadContatoData();
        }

        // Lógica para editar a foto (por exemplo, abrir a galeria)
        btnEditarFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Implemente a ação para selecionar e atualizar a foto
            }
        });

        // Ao atualizar, os dados já são alterados no objeto, que foi passado por referência
        btnAtualizarContato.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contato.setNome(edtNomeEditar.getText().toString().trim());
                contato.setTelefone(edtTelefoneEditar.getText().toString().trim());
                contato.setEmail(edtEmailEditar.getText().toString().trim());
                contato.setLinkedin(edtLinkedinEditar.getText().toString().trim());
                contato.setEndereco(edtEnderecoEditar.getText().toString().trim());
                contato.setContatoFavorito(chkFavoritoEditar.isChecked());

                // Se o objeto foi passado por referência, as alterações já refletem no repositório.
                // Caso contrário, você precisará atualizar na fonte de dados.

                finish();
            }
        });
    }

    /**
     * Carrega os dados do contato nos campos para edição.
     */
    private void loadContatoData() {
        edtNomeEditar.setText(contato.getNome());
        edtTelefoneEditar.setText(contato.getTelefone());
        edtEmailEditar.setText(contato.getEmail());
        edtLinkedinEditar.setText(contato.getLinkedin());
        edtEnderecoEditar.setText(contato.getEndereco());
        chkFavoritoEditar.setChecked(contato.isContatoFavorito());

        if (contato.getFoto() != null && !contato.getFoto().isEmpty()) {
            // Carrega a foto usando uma biblioteca (ex: Glide)
        } else {
            imgFotoContatoEditar.setImageResource(R.drawable.ic_contact_placeholder);
        }
    }
}

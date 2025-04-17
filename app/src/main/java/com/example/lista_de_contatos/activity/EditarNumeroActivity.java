package com.example.lista_de_contatos.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.lista_de_contatos.R;
import com.example.lista_de_contatos.db.ContatoDAO;
import com.example.lista_de_contatos.models.Contato;

public class EditarNumeroActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_PICK_IMAGE = 1;
    private ImageView imgFotoContatoEditar;
    private Button btnEditarFoto, btnAtualizarContato;
    private EditText edtNomeEditar, edtTelefoneEditar, edtEmailEditar, edtLinkedinEditar, edtEnderecoEditar;
    private CheckBox chkFavoritoEditar;
    private Contato contato;
    private ContatoDAO contatoDAO;
    private String fotoUriString = ""; // Armazena o URI da foto selecionada

    // Inicializa a UI, carrega dados e configura botões
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_numero);

        contatoDAO = new ContatoDAO(this);

        imgFotoContatoEditar = findViewById(R.id.imgFotoContatoEditar);
        btnEditarFoto = findViewById(R.id.btnEditarFoto);
        edtNomeEditar = findViewById(R.id.edtNomeEditar);
        edtTelefoneEditar = findViewById(R.id.edtTelefoneEditar);
        edtEmailEditar = findViewById(R.id.edtEmailEditar);
        edtLinkedinEditar = findViewById(R.id.edtLinkedinEditar);
        edtEnderecoEditar = findViewById(R.id.edtEnderecoEditar);
        chkFavoritoEditar = findViewById(R.id.chkFavoritoEditar);
        btnAtualizarContato = findViewById(R.id.btnAtualizarContato);

        // Recupera o contato e preenche os campos
        contato = (Contato) getIntent().getSerializableExtra("EXTRA_CONTATO");
        if (contato != null) {
            loadContatoData();
        }

        btnEditarFoto.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                // Abre galeria para selecionar imagem
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_CODE_PICK_IMAGE);
            }
        });

        btnAtualizarContato.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                // Atualiza contato e retorna para MainActivity
                contato.setNome(edtNomeEditar.getText().toString().trim());
                contato.setTelefone(edtTelefoneEditar.getText().toString().trim());
                contato.setEmail(edtEmailEditar.getText().toString().trim());
                contato.setLinkedin(edtLinkedinEditar.getText().toString().trim());
                contato.setEndereco(edtEnderecoEditar.getText().toString().trim());
                contato.setContatoFavorito(chkFavoritoEditar.isChecked());
                if (!fotoUriString.isEmpty()){
                    contato.setFoto(fotoUriString);
                }
                contatoDAO.atualizarContato(contato);
                Intent intent = new Intent(EditarNumeroActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                finish();
            }
        });
    }

    // Trata o resultado da galeria e exibe a imagem selecionada
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_PICK_IMAGE
                && resultCode == RESULT_OK
                && data != null
                && data.getData() != null) {
            Uri imageUri = data.getData();
            fotoUriString = imageUri.toString();
            imgFotoContatoEditar.setImageURI(imageUri);
        }
    }

    // Preenche os campos de edição com os dados do contato
    private void loadContatoData() {
        edtNomeEditar.setText(contato.getNome());
        edtTelefoneEditar.setText(contato.getTelefone());
        edtEmailEditar.setText(contato.getEmail());
        edtLinkedinEditar.setText(contato.getLinkedin());
        edtEnderecoEditar.setText(contato.getEndereco());
        chkFavoritoEditar.setChecked(contato.isContatoFavorito());
        fotoUriString = contato.getFoto();
        if (fotoUriString != null && !fotoUriString.isEmpty()){
            imgFotoContatoEditar.setImageURI(Uri.parse(fotoUriString));
        } else {
            imgFotoContatoEditar.setImageResource(R.drawable.ic_contact_placeholder);
        }
    }

    // Fecha o DAO ao destruir a Activity
    @Override
    protected void onDestroy(){
        super.onDestroy();
        if (contatoDAO != null) {
            contatoDAO.close();
        }
    }
}

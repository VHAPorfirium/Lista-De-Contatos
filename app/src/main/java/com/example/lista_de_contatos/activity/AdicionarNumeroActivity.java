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

public class AdicionarNumeroActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_PICK_IMAGE = 1;
    private ImageView imgFotoNovoContato;
    private Button btnAdicionarFoto, btnSalvarContato;
    private EditText edtNome, edtTelefone, edtEmail, edtLinkedin, edtEndereco;
    private CheckBox chkNovoFavorito;
    private ContatoDAO contatoDAO;
    private String fotoUriString = ""; // Armazena o URI selecionado

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_numero);

        contatoDAO = new ContatoDAO(this);

        imgFotoNovoContato = findViewById(R.id.imgFotoNovoContato);
        btnAdicionarFoto = findViewById(R.id.btnAdicionarFoto);
        edtNome = findViewById(R.id.edtNome);
        edtTelefone = findViewById(R.id.edtTelefone);
        edtEmail = findViewById(R.id.edtEmail);
        edtLinkedin = findViewById(R.id.edtLinkedin);
        edtEndereco = findViewById(R.id.edtEndereco);
        chkNovoFavorito = findViewById(R.id.chkNovoFavorito);
        btnSalvarContato = findViewById(R.id.btnSalvarContato);

        // Seleção de foto: abre a galeria
        btnAdicionarFoto.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_CODE_PICK_IMAGE);
            }
        });

        btnSalvarContato.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String nome = edtNome.getText().toString().trim();
                String telefone = edtTelefone.getText().toString().trim();
                String email = edtEmail.getText().toString().trim();
                String linkedin = edtLinkedin.getText().toString().trim();
                String endereco = edtEndereco.getText().toString().trim();
                boolean favorito = chkNovoFavorito.isChecked();

                // Cria o contato com o URI da foto (pode ser vazio se não selecionado)
                Contato novoContato = new Contato(nome, telefone, endereco, email, linkedin, fotoUriString, favorito);
                contatoDAO.inserirContato(novoContato);
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_PICK_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri imageUri = data.getData();
            fotoUriString = imageUri.toString();
            imgFotoNovoContato.setImageURI(imageUri);
        }
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        if (contatoDAO != null){
            contatoDAO.close();
        }
    }
}

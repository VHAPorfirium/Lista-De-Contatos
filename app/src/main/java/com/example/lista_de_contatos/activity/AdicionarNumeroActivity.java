package com.example.lista_de_contatos.activity;

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

    private ImageView imgFotoNovoContato;
    private Button btnAdicionarFoto, btnSalvarContato;
    private EditText edtNome, edtTelefone, edtEmail, edtLinkedin, edtEndereco;
    private CheckBox chkNovoFavorito;
    private ContatoDAO contatoDAO;

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

        btnAdicionarFoto.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                // Implementar seleção de foto se necessário.
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

                Contato novoContato = new Contato(nome, telefone, endereco, email, linkedin, "", favorito);
                // Inserir o contato (nota: se o email for "meuemail@exemplo.com", ele será interpretado como owner)
                contatoDAO.inserirContato(novoContato);
                finish();
            }
        });
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        if (contatoDAO != null){
            contatoDAO.close();
        }
    }
}

package com.example.Evacina.ui.acitivity;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.Evacina.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class ActivityInformacoesPessoiasEdicao extends AppCompatActivity {

    private String usario_key = FirebaseAuth.getInstance().getUid();
    private Button Activity_informacoes_pessoais_edicao_button_salva;
    private EditText Activit_informacoes_pessoais_edicao_input_nome;
    private EditText Activity_informacoes_pessoais_edicao_input_sobrenome;
    private EditText Activity_informacoes_pessoais_edicao_input_data_aniversario;
    private TextView Activity_informacoes_pessoais_edicao_input_CPF;
    private EditText Activity_informacoes_pessoais_edicao_input_Endereco;


    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informacoes_pessoais_edicao);
        ActivityInformacoesPessoaisEdicaoBuscaId();
    }

    protected void onStart() {
        super.onStart();
        db.collection("users").document(usario_key)
                .addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
                    @Override
                    public void onEvent(DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {

                        String dbNome = documentSnapshot.getString("Pessoais.Nome");
                        String dbSobrenome = documentSnapshot.getString("Pessoais.Sobrenome");
                        String dbCPF = documentSnapshot.getString("Pessoais.CPF");
                        String dbAniversario = documentSnapshot.getString("Pessoais.Aniversario");
                        String dbEndereco = documentSnapshot.getString("Pessoais.Endereco");

                        Activit_informacoes_pessoais_edicao_input_nome.setText(dbNome);
                        Activity_informacoes_pessoais_edicao_input_sobrenome.setText(dbSobrenome);
                        Activity_informacoes_pessoais_edicao_input_CPF.setText(dbCPF);
                        Activity_informacoes_pessoais_edicao_input_data_aniversario.setText(dbAniversario);
                        Activity_informacoes_pessoais_edicao_input_Endereco.setText(dbEndereco);

                    }

                });
        Activity_informacoes_pessoais_edicao_button_salva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Busca informacoes da tela
                String telaNome = Activit_informacoes_pessoais_edicao_input_nome.getText().toString().trim();
                String telaSobrenome = Activity_informacoes_pessoais_edicao_input_sobrenome.getText().toString().trim();
                String telaAnivesario = Activity_informacoes_pessoais_edicao_input_data_aniversario.getText().toString().trim();
                String telaEndereco = Activity_informacoes_pessoais_edicao_input_Endereco.getText().toString().trim();

                ValidarInformacoes(telaNome, telaSobrenome, telaAnivesario, telaEndereco);
                AcitivityInformacoesPessoaisEdicao2ActivityHomePage();

            }

        });
    }

    private void ValidarInformacoes(String telaNome, String telaSobrenome, String telaAnivesario, String telaEndereco) {
        if(!(telaNome.isEmpty())){
            db.collection("users").document(usario_key)
                    .update("Pessoais.Nome", telaNome);

        }
        if(!(telaSobrenome.isEmpty())){
            db.collection("users").document(usario_key)
                    .update("Pessoais.Sobrenome", telaSobrenome);

        }
        if(!(telaAnivesario.isEmpty())){
            db.collection("users").document(usario_key)
                    .update("Pessoais.Aniversario", telaAnivesario);

        }
        if(!(telaEndereco.isEmpty())){
            db.collection("users").document(usario_key)
                    .update("Pessoais.Endereco", telaEndereco);

        }
    }

    private void ActivityInformacoesPessoaisEdicaoBuscaId() {
        Activit_informacoes_pessoais_edicao_input_nome = findViewById(R.id.Activit_informacoes_pessoais_edicao_input_nome);
        Activity_informacoes_pessoais_edicao_input_sobrenome = findViewById(R.id.Activity_informacoes_pessoais_edicao_input_sobrenome);
        Activity_informacoes_pessoais_edicao_input_CPF = findViewById(R.id.Activity_informacoes_pessoais_edicao_input_CPF);
        Activity_informacoes_pessoais_edicao_input_data_aniversario = findViewById(R.id.Activity_informacoes_pessoais_edicao_input_data_aniversario);
        Activity_informacoes_pessoais_edicao_input_Endereco = findViewById(R.id.Activity_informacoes_pessoais_edicao_input_Endereco);
        Activity_informacoes_pessoais_edicao_button_salva = findViewById(R.id.Activity_informacoes_pessoais_edicao_button_salva);
    }

    private void AcitivityInformacoesPessoaisEdicao2ActivityHomePage(){
        Intent intent = new Intent(this, ActivityHomePage.class);
        startActivity(intent);
        finish();

    }

}

package com.example.evacina4.ui.acitivity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.evacina4.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Activit_edita_informacoesPessoais extends AppCompatActivity {

    private String usario_key = FirebaseAuth.getInstance().getUid();
    private Button salvarEditcao;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activit_edita_informacoes_pessoais);

        EditText editaNome = findViewById(R.id.editor_Informacoes_Pessoais_input_nome);
        EditText editaSobrenome = findViewById(R.id.editor_informacoes_Pessoais_input_sobreNome);
        TextView CPF = findViewById(R.id.editor_informacoes_Pessoais_input_CPF);
        EditText editaAniversario = findViewById(R.id.editor_informacoes_Pessoais_input_data_aniversario);
        EditText editaEndereco = findViewById(R.id.editor_informacoes_Pessoais_input_Endereco);




        salvarEditcao = findViewById(R.id.editor_informacoes_button_salva);

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

                        editaNome.setText(dbNome);
                        editaSobrenome.setText(dbSobrenome);
                        CPF.setText(dbCPF);
                        editaAniversario.setText(dbAniversario);
                        editaEndereco.setText(dbEndereco);

                    }

                });
        salvarEditcao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String telaNome = editaNome.getText().toString().trim();
                String telaSobrenome = editaSobrenome.getText().toString().trim();
                String telaAnivesario = editaAniversario.getText().toString().trim();
                String telaEndereco = editaEndereco.getText().toString().trim();

                if(!(telaNome.isEmpty())){
                    db.collection("users").document(usario_key)
                            .update("Pessoais.Ntesteome", telaNome);

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
                toHome();



            }

        });

    }
    private void toHome(){
        Intent intent = new Intent(this, Activity_homepage.class);
        startActivity(intent);
        finish();

    }

}

package com.example.Evacina.ui.acitivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.Evacina.R;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;


import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class ActivityEnfermeiro extends AppCompatActivity {

    private EditText activity_enfermeiro_nome_enfermeiro;
    private EditText activity_enfermeiro_coren_enfermeiro;
    private EditText activity_enfermeiro_codigo_enfermeiro;
    private Button activity_enfermeiro_button_enfermeiro;

    private  FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enfermeiro);
        //Busca id no layout
        ActivityEnfermeiroBuscaId();
    }

    protected  void onStart() {
        super.onStart();
        // ao clicar no botao para cadastrar enfermeiro

        activity_enfermeiro_button_enfermeiro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nomeEnfermeiro = activity_enfermeiro_nome_enfermeiro.getText().toString();
                String coren = activityEnfermeiroBuscarId().getText().toString();
                String codigoEnfermeiro = activity_enfermeiro_codigo_enfermeiro.getText().toString();

                if (!TextUtils.isEmpty(nomeEnfermeiro) && !TextUtils.isEmpty(coren) && !TextUtils.isEmpty(codigoEnfermeiro) && nomeEnfermeiro.length()>=3){
                    // Criar banco
                    Map<String, Object> enfermeiros = CriarBancoEnfermeiros(nomeEnfermeiro, coren, codigoEnfermeiro);

                    //Envia banco
                    db.collection("enfermeiros").add(enfermeiros).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Toast.makeText(ActivityEnfermeiro.this, "Cadastrado com sucesso!", Toast.LENGTH_SHORT).show();
                            ActivityEnfermeiro2MainActivity();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(ActivityEnfermeiro.this, "Falha, tente novamente com dados validos!", Toast.LENGTH_LONG).show();
                        }
                    });

                }
                else{
                    Toast.makeText(ActivityEnfermeiro.this, "Existem campos em branco ou nome menor que 3 letras!", Toast.LENGTH_LONG).show();
                }
            }
        });


    }

    private void ActivityEnfermeiro2MainActivity() {
        Intent intent = new Intent(ActivityEnfermeiro.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @NotNull
    private Map<String, Object> CriarBancoEnfermeiros(String nomeEnfermeiro, String coren, String codigoEnfermeiro) {
        Map<String, Object> enfermeiros = new HashMap<>();
        enfermeiros.put("Nome", nomeEnfermeiro);
        enfermeiros.put("Codigo", codigoEnfermeiro);
        enfermeiros.put("COREN", coren);
        return enfermeiros;
    }

    private void ActivityEnfermeiroBuscaId() {
        activity_enfermeiro_nome_enfermeiro = findViewById(R.id.activity_enfermeiro_nome_enfermeiro);
        activity_enfermeiro_coren_enfermeiro = findViewById(R.id.activity_enfermeiro_coren_enfermeiro);
        activity_enfermeiro_codigo_enfermeiro = findViewById(R.id.activity_enfermeiro_codigo_enfermeiro);
        activity_enfermeiro_button_enfermeiro = findViewById(R.id.activity_enfermeiro_button_enfermeiro);
    }

    private EditText activityEnfermeiroBuscarId() {
        return activity_enfermeiro_coren_enfermeiro;
    }
}
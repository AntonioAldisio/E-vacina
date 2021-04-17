package com.example.evacina4.ui.acitivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.evacina4.R;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;



import java.util.HashMap;
import java.util.Map;

public class ActivityEnfermeiro extends AppCompatActivity {

    private EditText nome_enfermeiro;
    private EditText coren_enfermeiro;
    private EditText codigo_enfermeiro;
    private Button button_enfermeiro;

    private  FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enfermeiro);

        nome_enfermeiro= findViewById(R.id.nome_enfermeiro);
        coren_enfermeiro= findViewById(R.id.coren_enfermeiro);
        codigo_enfermeiro= findViewById(R.id.codigo_enfermeiro);
        button_enfermeiro= findViewById(R.id.button_enfermeiro);

        // ao clicar no botao para cadastrar enfermeiro

        button_enfermeiro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nomeEnfermeiro = nome_enfermeiro.getText().toString();
                String coren = coren_enfermeiro.getText().toString();
                String codigoEnfermeiro = codigo_enfermeiro.getText().toString();
                if (!TextUtils.isEmpty(nomeEnfermeiro) || !TextUtils.isEmpty(coren) || !TextUtils.isEmpty(codigoEnfermeiro)){
                    Map<String, Object> enfermeiros = new HashMap<>();
                enfermeiros.put("Nome", nomeEnfermeiro);
                enfermeiros.put("Codigo", codigoEnfermeiro);
                enfermeiros.put("COREN", coren);

                db.collection("enfermeiros").add(enfermeiros).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(ActivityEnfermeiro.this, "Cadastrado com sucesso!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(ActivityEnfermeiro.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ActivityEnfermeiro.this, "Falha, tente novamente com dados validos!", Toast.LENGTH_LONG).show();
                    }
                });

            }
                else{
                    Toast.makeText(ActivityEnfermeiro.this, "Existem campos em branco!", Toast.LENGTH_LONG).show();
                }
            }
        });











    }
}
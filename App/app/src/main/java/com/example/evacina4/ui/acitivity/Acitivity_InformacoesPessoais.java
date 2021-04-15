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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import static com.example.evacina4.regras.CPFValido.isCPF;

public class Acitivity_InformacoesPessoais extends AppCompatActivity {

    private String usario_key = FirebaseAuth.getInstance().getUid();
    private Button salvar;
    private  FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acitivity__informacoes_pessoais);

        EditText nome = (EditText) findViewById(R.id.Informacoes_Pessoais_input_nome);
        EditText sobrenome = (EditText) findViewById(R.id.informacoes_Pessoais_input_sobreNome);
        EditText CPF = (EditText) findViewById(R.id.informacoes_Pessoais_input_CPF);
        EditText endereco = (EditText) findViewById(R.id.informacoes_Pessoais_input_Endereco);
        EditText data = (EditText) findViewById(R.id.informacoes_Pessoais_input_data_aniversario);

        salvar = findViewById(R.id.informacoes_button_salva);

        salvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String registarnome = nome.getText().toString().trim();
                String registarsobrenome = sobrenome.getText().toString().trim();
                String registarCPF = CPF.getText().toString().trim();
                String registarendereco = endereco.getText().toString().trim();
                String registradata = data.getText().toString().trim();

                if(!TextUtils.isEmpty(registarnome)|| !TextUtils.isEmpty(registarsobrenome) ||
                        !TextUtils.isEmpty(registarCPF) || !TextUtils.isEmpty(registarendereco) ||!TextUtils.isEmpty(registradata)){
                    if(isCPF(registarCPF)){

                                Map<String, Object> docData = new HashMap<>();
                                Map<String, Object> nestedData = new HashMap<>();
                                Map<String, Object> TipoVacina = new HashMap<>();
                                Map<String, Object> Infvacina = new HashMap<>();

                                nestedData.put("Nome", registarnome);
                                nestedData.put("Sobrenome", registarsobrenome);
                                nestedData.put("CPF", registarCPF);
                                nestedData.put("Endereco", registarendereco);
                                nestedData.put("Aniversario", registradata);

                                TipoVacina.put("Lote", "null");
                                TipoVacina.put("Data", "null");
                                TipoVacina.put("Aplicado", "null");


                                Infvacina.put("BGC",TipoVacina);
                                Infvacina.put("HepB",TipoVacina);
                                Infvacina.put("Penta",TipoVacina);
                                Infvacina.put("Vip",TipoVacina);
                                Infvacina.put("10V",TipoVacina);
                                Infvacina.put("Rotavirus",TipoVacina);
                                Infvacina.put("Men",TipoVacina);
                                Infvacina.put("FebreA",TipoVacina);
                                Infvacina.put("HepA",TipoVacina);
                                Infvacina.put("Triplice",TipoVacina);
                                Infvacina.put("Tetra",TipoVacina);
                                Infvacina.put("HPV",TipoVacina);
                                Infvacina.put("Dupla",TipoVacina);
                                Infvacina.put("DTpa",TipoVacina);

                                docData.put("Pessoais", nestedData);
                                docData.put("Vacinas", Infvacina);

                                db.collection("users").document(usario_key)
                                        .set(docData)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                toHome();
                                                //Log.d(TAG, "DocumentSnapshot successfully written!");
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                //Log.w(TAG, "Error writing document", e);
                                            }
                                        });




                            }else{
                        Toast.makeText(Acitivity_InformacoesPessoais.this,"CPF inválido",Toast.LENGTH_LONG).show();
                    }
                }

            }
        });

    }

    private void toHome(){
        Intent intent = new Intent(this, Activity_homepage.class);
        startActivity(intent);
        finish();
    }



}


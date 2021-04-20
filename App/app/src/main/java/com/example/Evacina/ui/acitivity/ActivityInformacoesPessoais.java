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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

import static com.example.Evacina.regras.CPFValido.isCPF;

public class ActivityInformacoesPessoais extends AppCompatActivity {

    private String usario_key = FirebaseAuth.getInstance().getUid();
    private Button Acitivity_Informacoes_Pessoais_button_salva;
    private EditText Acitivity_Informacoes_Pessoais_input_nome;
    private EditText Acitivity_Informacoes_Pessoais_input_sobrenome;
    private EditText Acitivity_Informacoes_Pessoais_input_CPF;
    private EditText Acitivity_Informacoes_Pessoais_input_Endereco;
    private EditText Acitivity_Informacoes_Pessoais_input_data_aniversario;
    private  FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitivity_informacoes_pessoais);
        ActivityInformacoesPessoaisBuscaId();
    }

    protected  void onStart() {
        super.onStart();
        Acitivity_Informacoes_Pessoais_button_salva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Busca informacoes na tela
                String registarnome = Acitivity_Informacoes_Pessoais_input_nome.getText().toString().trim();
                String registarsobrenome = Acitivity_Informacoes_Pessoais_input_sobrenome.getText().toString().trim();
                String registarCPF = Acitivity_Informacoes_Pessoais_input_CPF.getText().toString().trim();
                String registarendereco = Acitivity_Informacoes_Pessoais_input_Endereco.getText().toString().trim();
                String registradata = Acitivity_Informacoes_Pessoais_input_data_aniversario.getText().toString().trim();

                if (!TextUtils.isEmpty(registarnome) || !TextUtils.isEmpty(registarsobrenome) ||
                        !TextUtils.isEmpty(registarCPF) || !TextUtils.isEmpty(registarendereco) || !TextUtils.isEmpty(registradata)) {
                    if (isCPF(registarCPF)) {
                        //Cria banco de dados
                        Map<String, Object> docData = criaBanco(registarnome, registarsobrenome, registarCPF, registarendereco, registradata);

                        //Envia banco de dados para a nuvem
                        db.collection("users").document(usario_key)
                                .set(docData)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        AcitivityInformacoesPessoais2ActivityHomePage();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                    }
                                });

                    } else {
                        Toast.makeText(ActivityInformacoesPessoais.this, "CPF inválido", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }

    @NotNull
    private Map<String, Object> criaBanco(String registarnome, String registarsobrenome, String registarCPF, String registarendereco, String registradata) {
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
        return docData;
    }

    private void ActivityInformacoesPessoaisBuscaId() {
        Acitivity_Informacoes_Pessoais_input_nome =  findViewById(R.id.Acitivity_Informacoes_Pessoais_input_nome);
        Acitivity_Informacoes_Pessoais_input_sobrenome =  findViewById(R.id.Acitivity_Informacoes_Pessoais_input_sobrenome);
        Acitivity_Informacoes_Pessoais_input_CPF =  findViewById(R.id.Acitivity_Informacoes_Pessoais_input_CPF);
        Acitivity_Informacoes_Pessoais_input_Endereco =  findViewById(R.id.Acitivity_Informacoes_Pessoais_input_Endereco);
        Acitivity_Informacoes_Pessoais_input_data_aniversario = findViewById(R.id.Acitivity_Informacoes_Pessoais_input_data_aniversario);
        Acitivity_Informacoes_Pessoais_button_salva = findViewById(R.id.Acitivity_Informacoes_Pessoais_button_salva);
    }

    private void AcitivityInformacoesPessoais2ActivityHomePage(){
        Intent intent = new Intent(this, ActivityHomePage.class);
        startActivity(intent);
        finish();
    }



}


package com.example.Evacina.ui.acitivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.text.TextUtils;
import  android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.os.Bundle;

import com.example.Evacina.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class ActivityCadastro extends AppCompatActivity {


    private EditText AcitivityCadastro_input_email;
    private EditText AcitivityCadastro_input_senha;
    private EditText AcitivityCadastro_input_confirmar_senha;
    private Button AcitivityCadastro_button_confrimar;
    private ProgressBar progressBar;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);
        // Inicaliza firebase auteticador
        mAuth = FirebaseAuth.getInstance();
        // Busaca ID do layout
        ActivityCadastroBuscaId();
    }

    protected void onStart() {
        super.onStart();
        AcitivityCadastro_button_confrimar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                realizaCadastro(view);
            }
        });


    }

    private void realizaCadastro(View view) {
        String registrarEmail= AcitivityCadastro_input_email.getText().toString().trim();
        String senha= AcitivityCadastro_input_senha.getText().toString().trim();
        String confirmarSenha= AcitivityCadastro_input_confirmar_senha.getText().toString().trim();
        if(!TextUtils.isEmpty(registrarEmail) || !TextUtils.isEmpty(senha) || !TextUtils.isEmpty(confirmarSenha)){
            if(senha.equals(confirmarSenha)){
                progressBar.setVisibility(view.VISIBLE);
                mAuth.createUserWithEmailAndPassword(registrarEmail,senha).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){

                            abrirTelaPrincipal();
                        } else{
                            String error= task.getException().getMessage();
                            Toast.makeText(ActivityCadastro.this, ""+error, Toast.LENGTH_SHORT).show();
                        }
                        progressBar.setVisibility(View.INVISIBLE);
                    }
                });
            }
            else{
                Toast.makeText(ActivityCadastro.this, "A senha deve ser a mesma em ambos os campos", Toast.LENGTH_SHORT).show();

            }

        }
    }

    private void ActivityCadastroBuscaId() {
        AcitivityCadastro_input_email =(EditText) findViewById(R.id.acitivityCadastro_input_email);
        AcitivityCadastro_input_senha =  (EditText) findViewById(R.id.acitivityCadastro_input_password);
        AcitivityCadastro_input_confirmar_senha = (EditText) findViewById(R.id.acitivityCadastro_input_senha_confirmar);
        AcitivityCadastro_button_confrimar = findViewById(R.id.acitivityCadastro_button_confirmar);
        progressBar= findViewById(R.id.progressBar);
    }

    private void abrirTelaPrincipal() {
        Intent intent = new Intent(ActivityCadastro.this, ActivityInformacoesPessoais.class);
        startActivity(intent);
        finish();
    }
}
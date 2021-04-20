package com.example.Evacina.ui.acitivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.Evacina.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import  com.google.firebase.auth.FirebaseAuth ;

public class MainActivity extends AppCompatActivity {

private EditText MainAcitivity_input_email;
private EditText MainAcitivity_input_senha;
private Button MainAcitivity_button_cadastrar;
private Button MainAcitivity_button_entrar;
private ProgressBar progressBar;
private FirebaseAuth mAuth;
private Button MainAcitivity_button_recuperar_senha;
private Button MainAcitivty_button_sou_enfermeiro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Inicializa firebase auteticador
        mAuth= FirebaseAuth.getInstance();
        // Busaca ID do layout
        MainActivityBsucaId();
    }

    protected void onStart() {
        super.onStart();
        // Click para entrar
        MainAcitivity_button_entrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmarLogin();
            }
        });

        MainAcitivity_button_cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainAcitivity2AcitivityCadastro();
            }
        });
        //para recuperar senha perdida
        MainAcitivity_button_recuperar_senha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recuperarSenha();
            }
        });
        //
//botao para cadastrar enfermeiro
        MainAcitivty_button_sou_enfermeiro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainAcitivity2ActivityEnfermeiro();
            }
        });

    }

    private void mainAcitivity2ActivityEnfermeiro() {
        Intent intent = new Intent(MainActivity.this, ActivityEnfermeiro.class);
        startActivity(intent);
        finish();
    }

    private void mainAcitivity2AcitivityCadastro() {
        Intent intent = new Intent(MainActivity.this, ActivityCadastro.class);
        startActivity(intent);
        finish();
    }

    private void confirmarLogin() {
        String email= MainAcitivity_input_email.getText().toString();
        String senha = MainAcitivity_input_senha.getText().toString();
        if(!TextUtils.isEmpty(email) || !TextUtils.isEmpty(senha)){
            progressBar.setVisibility(View.VISIBLE);
            mAuth.signInWithEmailAndPassword(email,senha).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        mainActivity2ActivityHomePage();
                    }
                    else {
                        String error= task.getException().getMessage();
                        Toast.makeText(MainActivity.this,""+error,Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.INVISIBLE);
                    }
                }
            });
        }
    }

    private void MainActivityBsucaId() {
        MainAcitivity_input_email = findViewById(R.id.ActivityMain_input_email);
        MainAcitivity_input_senha = findViewById(R.id.ActivityMain_input_senha);
        MainAcitivity_button_entrar = findViewById(R.id.ActivityMain_button_entrar);
        MainAcitivity_button_cadastrar = findViewById(R.id.ActivityMain_button_cadastrar);
        progressBar=findViewById(R.id.progressBarLogin);
        MainAcitivty_button_sou_enfermeiro = findViewById(R.id.ActivityMain_button_sou_enfermeiro);
        MainAcitivity_button_recuperar_senha = findViewById(R.id.ActivityMain_button_recuperar_senha);
    }

    //para recuperar senha perdida
    private void recuperarSenha(){
        String email= MainAcitivity_input_email.getText().toString().trim();
        if(email.isEmpty()){
            Toast.makeText(getBaseContext(), "Insira seu E-mail para poder recuperar", Toast.LENGTH_LONG).show();
        }
        else{
            enviarEmail(email);
        }


    }
    //

    //enviar email para recuperar
    private void enviarEmail(String email){
        mAuth.sendPasswordResetEmail(email).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(getBaseContext(), "Enviamos uma mensagem para o seu email com um link para redefinir senha", Toast.LENGTH_LONG).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getBaseContext(), "Erro ao enviar Email, escreva um E-mail cadastrado", Toast.LENGTH_LONG).show();
            }
        });


    }
//

    private void mainActivity2ActivityHomePage() {
        Intent intent = new Intent(MainActivity.this, ActivityHomePage.class);
        startActivity(intent);
        finish();
    }




}
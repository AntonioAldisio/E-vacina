package com.example.evacina4.ui.acitivity;

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

import com.example.evacina4.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import  com.google.firebase.auth.FirebaseAuth ;

public class MainActivity extends AppCompatActivity {

private EditText activity_login_input_email;
private EditText activity_login_input_password;
private Button activity_login_button_cadastrar;
private Button button_entrar_inicial;
private ProgressBar progressBar;
private FirebaseAuth mAuth;
private  Button recuperar_senha;
private  Button button_sou_enfermeiro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth= FirebaseAuth.getInstance();
        activity_login_input_email= findViewById(R.id.activity_login_input_email);
        activity_login_input_password = findViewById(R.id.activity_login_input_password);
        button_entrar_inicial= findViewById(R.id.button_entrar_inicial);
        activity_login_button_cadastrar= findViewById(R.id.activity_login_button_cadastrar);
        progressBar=findViewById(R.id.progressBarLogin);
        button_sou_enfermeiro= findViewById(R.id.button_sou_enfermeiro);
        recuperar_senha= findViewById(R.id.edit_recuperar_senha);

        button_entrar_inicial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email= activity_login_input_email.getText().toString();
                String senha = activity_login_input_password.getText().toString();
                if(!TextUtils.isEmpty(email) || !TextUtils.isEmpty(senha)){
                    progressBar.setVisibility(View.VISIBLE);
                    mAuth.signInWithEmailAndPassword(email,senha).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                abrirTelaPrincipal();
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
        });

        activity_login_button_cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ActivityCadastro.class);
                startActivity(intent);
                finish();
            }
        });
    //para recuperar senha perdida
        recuperar_senha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recuperarSenha();
            }
        });
    //
//botao para cadastrar enfermeiro
        button_sou_enfermeiro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ActivityEnfermeiro.class);
                startActivity(intent);
                finish();
            }
        });




    }
    //para recuperar senha perdida
    private void recuperarSenha(){
        String email= activity_login_input_email.getText().toString().trim();
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

    private void abrirTelaPrincipal() {
        Intent intent = new Intent(MainActivity.this, Activity_homepage.class);
        startActivity(intent);
        finish();
    }




}
package com.example.evacina4.ui.acitivity;

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

import com.example.evacina4.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class ActivityCadastro extends AppCompatActivity {


    private EditText Email;
    private EditText Senha;
    private EditText ConfirmarSenha;
    private Button cadastrar;
    private Button entrar;
    private ProgressBar progressBar;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);
        mAuth = FirebaseAuth.getInstance();
        Email =(EditText) findViewById(R.id.activity_cadastro_input_email);
        Senha =  (EditText) findViewById(R.id.activity_cadastro_input_password);
        ConfirmarSenha = (EditText) findViewById(R.id.activity_cadastro_input_password_confirmar);
        cadastrar= findViewById(R.id.activity_login_button_confirmar);
        entrar=findViewById(R.id.button_entrar_inicial);
        progressBar= findViewById(R.id.progressBar);

    cadastrar.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String registrarEmail= Email.getText().toString().trim();
            String senha= Senha.getText().toString().trim();
            String confirmarSenha= ConfirmarSenha.getText().toString().trim();
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
    });
    entrar.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(ActivityCadastro.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    });

    }

    private void abrirTelaPrincipal() {
        Intent intent = new Intent(ActivityCadastro.this, Acitivity_InformacoesPessoais.class);
        startActivity(intent);
        finish();
    }
}
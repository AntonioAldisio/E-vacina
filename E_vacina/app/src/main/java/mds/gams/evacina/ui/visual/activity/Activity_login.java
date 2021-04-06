package mds.gams.evacina.ui.visual.activity;




import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import mds.gams.evacina.R;

public class Activity_login extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        Cadastrar();
        //Entrar();
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            currentUser.reload();
        }
    }

    private void Cadastrar(){
        Button botaoCadastro;
        botaoCadastro = (Button) findViewById(R.id.activity_login_button_cadastrar);
        botaoCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abreCadastro();
            }
        });


    }

    private void abreCadastro() {
        Intent intent = new Intent(this, Activity_Cadastro.class);
        startActivity(intent);
    }

/*
    private void Entrar(){
        Button botacEntrar = (Button) findViewById(R.id.activity_login_button_confirmar);
        botacEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmalogin();
            }
        });
    }

    private void confirmalogin() {
        startActivity(new Intent(this, ActivityHomepage.class));

    }

*/
}
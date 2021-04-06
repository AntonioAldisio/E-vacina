package mds.gams.evacina.ui.visual.activity;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import mds.gams.evacina.R;

public class Activity_Cadastro extends AppCompatActivity {

    private EditText Email;
    private EditText Senha;
    private EditText ConfirmarSenha;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);
        ConfirmarCadastro();

        Email =(EditText) findViewById(R.id.activity_cadastro_input_email);
        Senha =  (EditText) findViewById(R.id.activity_cadastro_input_password);
        ConfirmarSenha = (EditText) findViewById(R.id.activity_cadastro_input_password_confirmar);

    }

    private void ConfirmarCadastro(){
        Button ConfirmarCadastro;
        ConfirmarCadastro = (Button) findViewById(R.id.activity_login_button_confirmar);
        ConfirmarCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enviaFirebase();
            }

            private void enviaFirebase() {
                FirebaseAuth firebaseauth = FirebaseAuth.getInstance();
                String email = Email.getText().toString();
                String senha= Senha.getText().toString();
                //String verificasenha = ConfirmarSenha.getText().toString();

                firebaseauth.createUserWithEmailAndPassword(email, senha);

            }
        });

    }


}
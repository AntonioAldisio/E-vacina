package com.example.evacina4.ui.acitivity;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.evacina4.R;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ActivityCadastroVacina extends AppCompatActivity {

    private String usario_key = FirebaseAuth.getInstance().getUid();
    private EditText codigo_cadastro_vacinas;
    private EditText lote_cadastro_vacinas;
    private EditText aplicador_cadastro_vacinas;
    private Button salvarVacina;

    FirebaseFirestore db = FirebaseFirestore.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_vacina);

        salvarVacina = findViewById(R.id.botao_cadastro_vacinas);
        codigo_cadastro_vacinas= findViewById(R.id.codigo_cadastro_vacinas);
        Spinner nome_cadastro_vacinas= (Spinner) findViewById(R.id.planets_spinner);
        lote_cadastro_vacinas= findViewById(R.id.lote_cadastro_vacinas);
        aplicador_cadastro_vacinas= findViewById(R.id.aplicador_cadastro_vacinas);



        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.nome_vacinas, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinne
        nome_cadastro_vacinas.setAdapter(adapter);

        salvarVacina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            String vacinaParaCadastrar = nome_cadastro_vacinas.getSelectedItem().toString().trim();
            String codigo = codigo_cadastro_vacinas.getText().toString().trim();;
            String nomeAplicador = aplicador_cadastro_vacinas.getText().toString().trim();;
            String loteVacina = lote_cadastro_vacinas.getText().toString().trim();;



            boolean vazio =(Boolean) ( nome_cadastro_vacinas.getSelectedItemPosition() == 0);

            boolean bgc =(Boolean) ( nome_cadastro_vacinas.getSelectedItemPosition() == 1);
            boolean Dupla =(Boolean) ( nome_cadastro_vacinas.getSelectedItemPosition() == 2);
            boolean Febre  =(Boolean) ( nome_cadastro_vacinas.getSelectedItemPosition() == 3);
            boolean HepA  =(Boolean) ( nome_cadastro_vacinas.getSelectedItemPosition() == 4);
            boolean HepB  =(Boolean) ( nome_cadastro_vacinas.getSelectedItemPosition() == 5);
            boolean HPV   =(Boolean) ( nome_cadastro_vacinas.getSelectedItemPosition() == 6);
            boolean MegC  =(Boolean) ( nome_cadastro_vacinas.getSelectedItemPosition() == 7);
            boolean Penta  =(Boolean) ( nome_cadastro_vacinas.getSelectedItemPosition() == 8);
            boolean Penv  =(Boolean) ( nome_cadastro_vacinas.getSelectedItemPosition() == 9);
            boolean Rotavirus  =(Boolean) ( nome_cadastro_vacinas.getSelectedItemPosition() == 10);
            boolean Tetra  =(Boolean) ( nome_cadastro_vacinas.getSelectedItemPosition() == 11);
            boolean Triplice  =(Boolean) ( nome_cadastro_vacinas.getSelectedItemPosition() == 12);
            boolean Vip  =(Boolean) ( nome_cadastro_vacinas.getSelectedItemPosition() == 13);



            if(vazio|| codigo.isEmpty()
            ||nomeAplicador.isEmpty() || loteVacina.isEmpty()){
                Toast.makeText(ActivityCadastroVacina.this,
                        vacinaParaCadastrar+"Aqui",
                        Toast.LENGTH_LONG).show();

            }

                if(bgc|| (codigo == "2")
                        ||nomeAplicador.isEmpty() || loteVacina.isEmpty()){
                    Toast.makeText(ActivityCadastroVacina.this,
                            "Aqui",
                            Toast.LENGTH_LONG).show();
                    db.collection("users").document(usario_key)
                            .update("Vacinas.BGC.Nome",nomeAplicador);
                    db.collection("users").document(usario_key)
                            .update("Vacinas.BGC.Lote",loteVacina);
                    db.collection("users").document(usario_key)
                            .update("Vacinas.BGC.Data",  new Timestamp(new Date()));


                }
                
            }

        });





    }
}
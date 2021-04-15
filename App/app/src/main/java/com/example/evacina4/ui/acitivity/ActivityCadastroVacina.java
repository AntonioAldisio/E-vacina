package com.example.evacina4.ui.acitivity;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

import com.example.evacina4.R;

public class ActivityCadastroVacina extends AppCompatActivity {


    private EditText codigo_cadastro_vacinas;
    private EditText nome_cadastro_vacinas;
    private EditText lote_cadastro_vacinas;
    private EditText aplicador_cadastro_vacinas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        codigo_cadastro_vacinas= findViewById(R.id.codigo_cadastro_vacinas);
        nome_cadastro_vacinas= findViewById(R.id.nome_cadastro_vacinas);
        lote_cadastro_vacinas= findViewById(R.id.lote_cadastro_vacinas);
        aplicador_cadastro_vacinas= findViewById(R.id.aplicador_cadastro_vacinas);





        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_vacina);
    }
}
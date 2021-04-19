package com.example.evacina4.ui.acitivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.evacina4.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ActivityCadastroVacina extends AppCompatActivity {

    private String usario_key = FirebaseAuth.getInstance().getUid();
    private EditText codigo_cadastro_vacinas;
    private EditText lote_cadastro_vacinas;

    //private Map<String, Object> informacoesAplicador = new HashMap<String, Object>();
    private String funcionario_key;
    private String nomeAplicador;
    private boolean codigoValido = false;

    private Button salvarVacina;

    private static final String TAG = "Aqui";


    FirebaseFirestore db = FirebaseFirestore.getInstance();

    private void toHome() {
        Intent intent = new Intent(this, Activity_homepage.class);
        startActivity(intent);
        finish();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_vacina);


    }

    protected void onStart() {
        super.onStart();

        salvarVacina = findViewById(R.id.botao_cadastro_vacinas);
        codigo_cadastro_vacinas = findViewById(R.id.codigo_cadastro_vacinas);
        Spinner nome_cadastro_vacinas = (Spinner) findViewById(R.id.planets_spinner);
        lote_cadastro_vacinas = findViewById(R.id.lote_cadastro_vacinas);


        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.nome_vacinas, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinne
        nome_cadastro_vacinas.setAdapter(adapter);


        salvarVacina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String codigo = codigo_cadastro_vacinas.getText().toString().trim();
                String loteVacina = lote_cadastro_vacinas.getText().toString().trim();


                boolean vazio = (Boolean) (nome_cadastro_vacinas.getSelectedItemPosition() == 0);
                //boolean camposVazio = (Boolean) (codigo.isEmpty() || loteVacina.isEmpty());
                boolean camposVazio = false;

                boolean bgc = (Boolean) (nome_cadastro_vacinas.getSelectedItemPosition() == 1);
                boolean dTpa = (Boolean) (nome_cadastro_vacinas.getSelectedItemPosition() == 2);
                boolean Dupla = (Boolean) (nome_cadastro_vacinas.getSelectedItemPosition() == 3);
                boolean Febre = (Boolean) (nome_cadastro_vacinas.getSelectedItemPosition() == 4);
                boolean HepA = (Boolean) (nome_cadastro_vacinas.getSelectedItemPosition() == 5);
                boolean HepB = (Boolean) (nome_cadastro_vacinas.getSelectedItemPosition() == 6);
                boolean HPV = (Boolean) (nome_cadastro_vacinas.getSelectedItemPosition() == 7);
                boolean MegC = (Boolean) (nome_cadastro_vacinas.getSelectedItemPosition() == 8);
                boolean Penta = (Boolean) (nome_cadastro_vacinas.getSelectedItemPosition() == 9);
                boolean Penv = (Boolean) (nome_cadastro_vacinas.getSelectedItemPosition() == 10);
                boolean Rotavirus = (Boolean) (nome_cadastro_vacinas.getSelectedItemPosition() == 11);
                boolean Tetra = (Boolean) (nome_cadastro_vacinas.getSelectedItemPosition() == 12);
                boolean Triplice = (Boolean) (nome_cadastro_vacinas.getSelectedItemPosition() == 13);
                boolean Vip = (Boolean) (nome_cadastro_vacinas.getSelectedItemPosition() == 14);


                if (!codigo.isEmpty()) {
                    db.collection("enfermeiros")
                            .whereEqualTo("Codigo", codigo)
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful()) {
                                        for (QueryDocumentSnapshot document : task.getResult()) {
                                            //Log.d(TAG, document.getId() + " => " + document.getData());
                                            funcionario_key = document.getId();
                                            nomeAplicador = document.get("Nome").toString();
                                            codigoValido = true;
                                        }

                                    } else {
                                        Toast.makeText(ActivityCadastroVacina.this,
                                                "Codigo invalido",
                                                Toast.LENGTH_LONG).show();
                                    }
                                }
                            });

                    // Verifica se tem campo vazio
                    if (vazio || camposVazio) {
                        Toast.makeText(ActivityCadastroVacina.this,
                                "Prencha todos os campos",
                                Toast.LENGTH_LONG).show();
                    }

                    // Vacina BCG
                    if (bgc && codigoValido && !camposVazio) {
                        db.collection("users").document(usario_key)
                                .update("Vacinas.BGC.Aplicado", nomeAplicador);
                        db.collection("users").document(usario_key)
                                .update("Vacinas.BGC.Lote", loteVacina);
                        db.collection("users").document(usario_key)
                                .update("Vacinas.BGC.Data", getDateTime());

                        Toast.makeText(ActivityCadastroVacina.this,
                                "Vacina cadastra com sucesso",
                                Toast.LENGTH_LONG).show();
                        toHome();
                    }

                    if (dTpa && codigoValido && !camposVazio) {
                        db.collection("users").document(usario_key)
                                .update("Vacinas.DTpa.Aplicado", nomeAplicador);
                        db.collection("users").document(usario_key)
                                .update("Vacinas.DTpa.Lote", loteVacina);
                        db.collection("users").document(usario_key)
                                .update("Vacinas.DTpa.Data", getDateTime());

                        Toast.makeText(ActivityCadastroVacina.this,
                                "Vacina cadastra com sucesso",
                                Toast.LENGTH_LONG).show();
                        toHome();
                    }

                    if (Dupla && codigoValido && !camposVazio) {
                        db.collection("users").document(usario_key)
                                .update("Vacinas.Dupla.Aplicado", nomeAplicador);
                        db.collection("users").document(usario_key)
                                .update("Vacinas.Dupla.Lote", loteVacina);
                        db.collection("users").document(usario_key)
                                .update("Vacinas.Dupla.Data", getDateTime());

                        Toast.makeText(ActivityCadastroVacina.this,
                                "Vacina cadastra com sucesso",
                                Toast.LENGTH_LONG).show();
                        toHome();
                    }

                    if (Febre && codigoValido && !camposVazio) {
                        db.collection("users").document(usario_key)
                                .update("Vacinas.FebreA.Aplicado", nomeAplicador);
                        db.collection("users").document(usario_key)
                                .update("Vacinas.FebreA.Lote", loteVacina);
                        db.collection("users").document(usario_key)
                                .update("Vacinas.FebreA.Data", getDateTime());

                        Toast.makeText(ActivityCadastroVacina.this,
                                "Vacina cadastra com sucesso",
                                Toast.LENGTH_LONG).show();
                        toHome();
                    }

                    if (HepA && codigoValido && !camposVazio) {
                        db.collection("users").document(usario_key)
                                .update("Vacinas.HepA.Aplicado", nomeAplicador);
                        db.collection("users").document(usario_key)
                                .update("Vacinas.HepA.Lote", loteVacina);
                        db.collection("users").document(usario_key)
                                .update("Vacinas.HepA.Data", getDateTime());

                        Toast.makeText(ActivityCadastroVacina.this,
                                "Vacina cadastra com sucesso",
                                Toast.LENGTH_LONG).show();
                        toHome();
                    }

                    if (HepB && codigoValido && !camposVazio) {
                        db.collection("users").document(usario_key)
                                .update("Vacinas.HepB.Aplicado", nomeAplicador);
                        db.collection("users").document(usario_key)
                                .update("Vacinas.HepB.Lote", loteVacina);
                        db.collection("users").document(usario_key)
                                .update("Vacinas.HepB.Data", getDateTime());

                        Toast.makeText(ActivityCadastroVacina.this,
                                "Vacina cadastra com sucesso",
                                Toast.LENGTH_LONG).show();
                        toHome();
                    }

                    if (HPV && codigoValido && !camposVazio) {
                        db.collection("users").document(usario_key)
                                .update("Vacinas.HPV.Aplicado", nomeAplicador);
                        db.collection("users").document(usario_key)
                                .update("Vacinas.HPV.Lote", loteVacina);
                        db.collection("users").document(usario_key)
                                .update("Vacinas.HPV.Data", getDateTime());

                        Toast.makeText(ActivityCadastroVacina.this,
                                "Vacina cadastra com sucesso",
                                Toast.LENGTH_LONG).show();
                        toHome();
                    }

                    if (MegC && codigoValido && !camposVazio) {
                        db.collection("users").document(usario_key)
                                .update("Vacinas.Men.Aplicado", nomeAplicador);
                        db.collection("users").document(usario_key)
                                .update("Vacinas.Men.Lote", loteVacina);
                        db.collection("users").document(usario_key)
                                .update("Vacinas.Men.Data", getDateTime());

                        Toast.makeText(ActivityCadastroVacina.this,
                                "Vacina cadastra com sucesso",
                                Toast.LENGTH_LONG).show();
                        toHome();
                    }

                    if (Penta && codigoValido && !camposVazio) {
                        db.collection("users").document(usario_key)
                                .update("Vacinas.Penta.Aplicado", nomeAplicador);
                        db.collection("users").document(usario_key)
                                .update("Vacinas.Penta.Lote", loteVacina);
                        db.collection("users").document(usario_key)
                                .update("Vacinas.Penta.Data", getDateTime());

                        Toast.makeText(ActivityCadastroVacina.this,
                                "Vacina cadastra com sucesso",
                                Toast.LENGTH_LONG).show();
                        toHome();
                    }

                    if (Penv && codigoValido && !camposVazio) {
                        db.collection("users").document(usario_key)
                                .update("Vacinas.10V.Aplicado", nomeAplicador);
                        db.collection("users").document(usario_key)
                                .update("Vacinas.10V.Lote", loteVacina);
                        db.collection("users").document(usario_key)
                                .update("Vacinas.10V.Data", getDateTime());

                        Toast.makeText(ActivityCadastroVacina.this,
                                "Vacina cadastra com sucesso",
                                Toast.LENGTH_LONG).show();
                        toHome();
                    }

                    if (Rotavirus && codigoValido && !camposVazio) {
                        db.collection("users").document(usario_key)
                                .update("Vacinas.Rotavirus.Aplicado", nomeAplicador);
                        db.collection("users").document(usario_key)
                                .update("Vacinas.Rotavirus.Lote", loteVacina);
                        db.collection("users").document(usario_key)
                                .update("Vacinas.Rotavirus.Data", getDateTime());

                        Toast.makeText(ActivityCadastroVacina.this,
                                "Vacina cadastra com sucesso",
                                Toast.LENGTH_LONG).show();
                        toHome();
                    }

                    if (Tetra && codigoValido && !camposVazio) {
                        db.collection("users").document(usario_key)
                                .update("Vacinas.Tetra.Aplicado", nomeAplicador);
                        db.collection("users").document(usario_key)
                                .update("Vacinas.Tetra.Lote", loteVacina);
                        db.collection("users").document(usario_key)
                                .update("Vacinas.Tetra.Data", getDateTime());

                        Toast.makeText(ActivityCadastroVacina.this,
                                "Vacina cadastra com sucesso",
                                Toast.LENGTH_LONG).show();
                        toHome();
                    }

                    if (Triplice && codigoValido && !camposVazio) {
                        db.collection("users").document(usario_key)
                                .update("Vacinas.Triplice.Aplicado", nomeAplicador);
                        db.collection("users").document(usario_key)
                                .update("Vacinas.Triplice.Lote", loteVacina);
                        db.collection("users").document(usario_key)
                                .update("Vacinas.Triplice.Data", getDateTime());

                        Toast.makeText(ActivityCadastroVacina.this,
                                "Vacina cadastra com sucesso",
                                Toast.LENGTH_LONG).show();
                        toHome();
                    }
                    if (Vip && codigoValido && !camposVazio) {
                        db.collection("users").document(usario_key)
                                .update("Vacinas.Vip.Aplicado", nomeAplicador);
                        db.collection("users").document(usario_key)
                                .update("Vacinas.Vip.Lote", loteVacina);
                        db.collection("users").document(usario_key)
                                .update("Vacinas.Vip.Data", getDateTime());

                        Toast.makeText(ActivityCadastroVacina.this,
                                "Vacina cadastra com sucesso",
                                Toast.LENGTH_LONG).show();
                        toHome();
                    }


                }

            }

        });
    }
    private String getDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        return dateFormat.format(date);
    }

}
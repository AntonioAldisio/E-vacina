package com.example.Evacina.ui.acitivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.Evacina.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ActivityCadastroVacina extends AppCompatActivity {

    private String usario_key = FirebaseAuth.getInstance().getUid();
    private EditText activity_cadastro_vacina_input_codigo;
    private EditText activity_cadastro_vacina_input_lote;
    private Spinner activity_cadastro_vacina_input_spineer;

    //private Map<String, Object> informacoesAplicador = new HashMap<String, Object>();
    private String funcionario_key;
    private String nomeAplicador;
    private boolean codigoValido = false;

    private Button activity_cadastro_vacina_button_cadastro_vacinas;

    private static final String TAG = "Aqui";


    FirebaseFirestore db = FirebaseFirestore.getInstance();

    private void ActivityCadastroVacina2ActivityHomePage() {
        Intent intent = new Intent(this, ActivityHomePage.class);
        startActivity(intent);
        finish();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_vacina);
        ActivityCadastroVacinaBuscaId();


    }

    protected void onStart() {
        super.onStart();

        //Organizar Spinner
        SteupVacina();

        activity_cadastro_vacina_button_cadastro_vacinas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String codigo = activity_cadastro_vacina_input_codigo.getText().toString().trim();
                String loteVacina = activity_cadastro_vacina_input_lote.getText().toString().trim();

                boolean camposVazio = false;

                boolean vazio = (Boolean) (activity_cadastro_vacina_input_spineer.getSelectedItemPosition() == 0);
                boolean bgc = (Boolean) (activity_cadastro_vacina_input_spineer.getSelectedItemPosition() == 1);
                boolean dTpa = (Boolean) (activity_cadastro_vacina_input_spineer.getSelectedItemPosition() == 2);
                boolean Dupla = (Boolean) (activity_cadastro_vacina_input_spineer.getSelectedItemPosition() == 3);
                boolean Febre = (Boolean) (activity_cadastro_vacina_input_spineer.getSelectedItemPosition() == 4);
                boolean HepA = (Boolean) (activity_cadastro_vacina_input_spineer.getSelectedItemPosition() == 5);
                boolean HepB = (Boolean) (activity_cadastro_vacina_input_spineer.getSelectedItemPosition() == 6);
                boolean HPV = (Boolean) (activity_cadastro_vacina_input_spineer.getSelectedItemPosition() == 7);
                boolean MegC = (Boolean) (activity_cadastro_vacina_input_spineer.getSelectedItemPosition() == 8);
                boolean Penta = (Boolean) (activity_cadastro_vacina_input_spineer.getSelectedItemPosition() == 9);
                boolean Penv = (Boolean) (activity_cadastro_vacina_input_spineer.getSelectedItemPosition() == 10);
                boolean Rotavirus = (Boolean) (activity_cadastro_vacina_input_spineer.getSelectedItemPosition() == 11);
                boolean Tetra = (Boolean) (activity_cadastro_vacina_input_spineer.getSelectedItemPosition() == 12);
                boolean Triplice = (Boolean) (activity_cadastro_vacina_input_spineer.getSelectedItemPosition() == 13);
                boolean Vip = (Boolean) (activity_cadastro_vacina_input_spineer.getSelectedItemPosition() == 14);


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

                        VacinaCadastrada();
                    }

                    if (dTpa && codigoValido && !camposVazio) {
                        db.collection("users").document(usario_key)
                                .update("Vacinas.DTpa.Aplicado", nomeAplicador);
                        db.collection("users").document(usario_key)
                                .update("Vacinas.DTpa.Lote", loteVacina);
                        db.collection("users").document(usario_key)
                                .update("Vacinas.DTpa.Data", getDateTime());

                        VacinaCadastrada();
                    }

                    if (Dupla && codigoValido && !camposVazio) {
                        db.collection("users").document(usario_key)
                                .update("Vacinas.Dupla.Aplicado", nomeAplicador);
                        db.collection("users").document(usario_key)
                                .update("Vacinas.Dupla.Lote", loteVacina);
                        db.collection("users").document(usario_key)
                                .update("Vacinas.Dupla.Data", getDateTime());

                        VacinaCadastrada();
                    }

                    if (Febre && codigoValido && !camposVazio) {
                        db.collection("users").document(usario_key)
                                .update("Vacinas.FebreA.Aplicado", nomeAplicador);
                        db.collection("users").document(usario_key)
                                .update("Vacinas.FebreA.Lote", loteVacina);
                        db.collection("users").document(usario_key)
                                .update("Vacinas.FebreA.Data", getDateTime());

                        VacinaCadastrada();
                    }

                    if (HepA && codigoValido && !camposVazio) {
                        db.collection("users").document(usario_key)
                                .update("Vacinas.HepA.Aplicado", nomeAplicador);
                        db.collection("users").document(usario_key)
                                .update("Vacinas.HepA.Lote", loteVacina);
                        db.collection("users").document(usario_key)
                                .update("Vacinas.HepA.Data", getDateTime());

                        VacinaCadastrada();
                    }

                    if (HepB && codigoValido && !camposVazio) {
                        db.collection("users").document(usario_key)
                                .update("Vacinas.HepB.Aplicado", nomeAplicador);
                        db.collection("users").document(usario_key)
                                .update("Vacinas.HepB.Lote", loteVacina);
                        db.collection("users").document(usario_key)
                                .update("Vacinas.HepB.Data", getDateTime());

                        VacinaCadastrada();
                    }

                    if (HPV && codigoValido && !camposVazio) {
                        db.collection("users").document(usario_key)
                                .update("Vacinas.HPV.Aplicado", nomeAplicador);
                        db.collection("users").document(usario_key)
                                .update("Vacinas.HPV.Lote", loteVacina);
                        db.collection("users").document(usario_key)
                                .update("Vacinas.HPV.Data", getDateTime());

                        VacinaCadastrada();
                    }

                    if (MegC && codigoValido && !camposVazio) {
                        db.collection("users").document(usario_key)
                                .update("Vacinas.Men.Aplicado", nomeAplicador);
                        db.collection("users").document(usario_key)
                                .update("Vacinas.Men.Lote", loteVacina);
                        db.collection("users").document(usario_key)
                                .update("Vacinas.Men.Data", getDateTime());

                        VacinaCadastrada();
                    }

                    if (Penta && codigoValido && !camposVazio) {
                        db.collection("users").document(usario_key)
                                .update("Vacinas.Penta.Aplicado", nomeAplicador);
                        db.collection("users").document(usario_key)
                                .update("Vacinas.Penta.Lote", loteVacina);
                        db.collection("users").document(usario_key)
                                .update("Vacinas.Penta.Data", getDateTime());

                        VacinaCadastrada();
                    }

                    if (Penv && codigoValido && !camposVazio) {
                        db.collection("users").document(usario_key)
                                .update("Vacinas.10V.Aplicado", nomeAplicador);
                        db.collection("users").document(usario_key)
                                .update("Vacinas.10V.Lote", loteVacina);
                        db.collection("users").document(usario_key)
                                .update("Vacinas.10V.Data", getDateTime());

                        VacinaCadastrada();
                    }

                    if (Rotavirus && codigoValido && !camposVazio) {
                        db.collection("users").document(usario_key)
                                .update("Vacinas.Rotavirus.Aplicado", nomeAplicador);
                        db.collection("users").document(usario_key)
                                .update("Vacinas.Rotavirus.Lote", loteVacina);
                        db.collection("users").document(usario_key)
                                .update("Vacinas.Rotavirus.Data", getDateTime());

                        VacinaCadastrada();
                    }

                    if (Tetra && codigoValido && !camposVazio) {
                        db.collection("users").document(usario_key)
                                .update("Vacinas.Tetra.Aplicado", nomeAplicador);
                        db.collection("users").document(usario_key)
                                .update("Vacinas.Tetra.Lote", loteVacina);
                        db.collection("users").document(usario_key)
                                .update("Vacinas.Tetra.Data", getDateTime());

                        VacinaCadastrada();
                    }

                    if (Triplice && codigoValido && !camposVazio) {
                        db.collection("users").document(usario_key)
                                .update("Vacinas.Triplice.Aplicado", nomeAplicador);
                        db.collection("users").document(usario_key)
                                .update("Vacinas.Triplice.Lote", loteVacina);
                        db.collection("users").document(usario_key)
                                .update("Vacinas.Triplice.Data", getDateTime());

                        VacinaCadastrada();
                    }
                    if (Vip && codigoValido && !camposVazio) {
                        db.collection("users").document(usario_key)
                                .update("Vacinas.Vip.Aplicado", nomeAplicador);
                        db.collection("users").document(usario_key)
                                .update("Vacinas.Vip.Lote", loteVacina);
                        db.collection("users").document(usario_key)
                                .update("Vacinas.Vip.Data", getDateTime());

                        VacinaCadastrada();
                    }


                }

            }

        });
    }

    private void VacinaCadastrada() {
        Toast.makeText(ActivityCadastroVacina.this,
                "Vacina cadastra com sucesso",
                Toast.LENGTH_LONG).show();
        ActivityCadastroVacina2ActivityHomePage();
    }

    private void SteupVacina() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.nome_vacinas, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinne
        activity_cadastro_vacina_input_spineer.setAdapter(adapter);
    }

    private void ActivityCadastroVacinaBuscaId() {
        activity_cadastro_vacina_button_cadastro_vacinas = findViewById(R.id.activity_cadastro_vacina_button_cadastro_vacinas);
        activity_cadastro_vacina_input_codigo = findViewById(R.id.activity_cadastro_vacina_input_codigo);
        activity_cadastro_vacina_input_spineer = findViewById(R.id.activity_cadastro_vacina_input_spineer);
        activity_cadastro_vacina_input_lote = findViewById(R.id.activity_cadastro_vacina_input_lote);
    }

    private String getDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        return dateFormat.format(date);
    }

}
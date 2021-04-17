package com.example.evacina4.ui.acitivity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.evacina4.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Activity_homepage extends AppCompatActivity {

    private FirebaseAuth mAuth;
    public static final String NOME_KEY = "Nome";
    private String usario_key = FirebaseAuth.getInstance().getUid();
    private  FirebaseFirestore db = FirebaseFirestore.getInstance();


    private Button button_sair;
    private TextView nomeUsario;
    private ListView listViewVacina;
    private ListView listViewData;
    private ListView listViewLote;
    private ListView listViewAplicador;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        mAuth=FirebaseAuth.getInstance();
        button_sair = findViewById(R.id.button_logout);
        nomeUsario = findViewById(R.id.home_activity_teste);

        listViewVacina = findViewById(R.id.acitiviy_list_view_vacina);
        listViewAplicador = findViewById(R.id.acitiviy_list_view_aplicador);
        listViewData = findViewById(R.id.acitiviy_list_view_data);
        listViewLote= findViewById(R.id.acitiviy_list_view_lote);

        button_sair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                Intent intent= new Intent(Activity_homepage.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        db.collection("users").document(usario_key)
                .addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
                    @Override
                    public void onEvent(DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
                        String dbNome = documentSnapshot.getString("Pessoais.Nome");
                        nomeUsario.setText(dbNome);

                        String dbAplicador10V = documentSnapshot.getString("Vacinas.10V.Aplicado");
                        String dbAplicadorBCG = documentSnapshot.getString("Vacinas.BGC.Aplicado");

                        if ((dbAplicador10V == "null")) {
                            List<String> dbAplicador = new ArrayList<>(
                                    Arrays.asList(dbAplicador10V, dbAplicadorBCG));

                            listViewAplicador.setAdapter(new ArrayAdapter<>(
                                    Activity_homepage.this,
                                    android.R.layout.simple_list_item_1,
                                    dbAplicador));
                        }
                    }

                });



    }
    //parte para os itens para o menu e tratamento de menu-bianca
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_principal,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {


        if(  item.getItemId()==R.id.item_editar){
            Intent intent = new Intent(this, Activit_edita_informacoesPessoais.class);
            startActivity(intent);


        }
        if(  item.getItemId()==R.id.item_vacinas){
            //nada

        }
        if(  item.getItemId()==R.id.item_cadastrar){
            Intent intent = new Intent(this, ActivityCadastroVacina.class);
            startActivity(intent);



        }
        if(item.getItemId()==R.id.item_excluir){
            AlertDialog.Builder dialog= new AlertDialog.Builder(Activity_homepage.this);
            dialog.setTitle("Tem certeza?");
            dialog.setMessage("Excluir sua conta significa que nao podera acessar novamente");
            dialog.setPositiveButton("Exluir", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    FirebaseUser user= mAuth.getCurrentUser();
                    user.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(Activity_homepage.this, "Conta deletada", Toast.LENGTH_SHORT).show();
                            mAuth.signOut();
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            finish();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                       Toast.makeText(Activity_homepage.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            }).setNegativeButton("Cancelar", null).create().show();
        }

        return super.onOptionsItemSelected(item);
    }

    // fim do menu
    @Override
    protected void onStart(){
        super.onStart();
        FirebaseUser currentuser = FirebaseAuth.getInstance().getCurrentUser();

        if(currentuser==null){
            Intent intent = new Intent(Activity_homepage.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }




}
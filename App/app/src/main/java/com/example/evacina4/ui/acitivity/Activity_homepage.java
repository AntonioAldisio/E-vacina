package com.example.evacina4.ui.acitivity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
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
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


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
    private List<String> dbAplicador;
    private List<String> dbData;
    private List<String> dbALote;
    private List<String> dbVacina;
    //para pdf
    private Button button_PDF;
    private TextView textVacina;
    private TextView textData;
    private TextView textLote;
    private TextView textAplicador;
    //


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        mAuth=FirebaseAuth.getInstance();
        button_sair = findViewById(R.id.button_logout);
        nomeUsario = findViewById(R.id.home_activity_teste);
        button_PDF= findViewById(R.id.button_PDF);
        //text view para exportar para pdf
        textVacina = findViewById(R.id.textVacina);
        textData = findViewById(R.id.textData);
        textLote = findViewById(R.id.textLote);
        textAplicador = findViewById(R.id.textAplicador);
        //

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
//


        db.collection("users").document(usario_key)
                .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot snapshot, @Nullable FirebaseFirestoreException error) {
                        if (error != null) {
                            Log.w("Erro", "Listen failed.", error);
                            return;
                        }
                        dbVacina = new ArrayList<>();
                        dbData = new ArrayList<>();
                        dbAplicador = new ArrayList<>();
                        dbALote = new ArrayList<>();


                        if (snapshot != null && snapshot.exists()) {
                            if(snapshot.get("Vacinas.Dupla.Lote").toString() == "null"){
                                Log.d("Erro", "Estou aqui");
                            }else {
                                Log.d("Erro","Nao pegue nada null");

                                dbVacina.add("Dupla");
                                dbData.add(snapshot.get("Vacinas.Dupla.Data").toString());
                                dbALote.add(snapshot.get("Vacinas.Dupla.Lote").toString());
                                dbAplicador.add(snapshot.get("Vacinas.Dupla.Aplicado").toString());

                                listViewVacina.setAdapter(new ArrayAdapter<>(
                                        Activity_homepage.this,
                                        android.R.layout.simple_list_item_1,
                                        dbVacina
                                ));

                                listViewData.setAdapter(new ArrayAdapter<>(
                                        Activity_homepage.this,
                                        android.R.layout.simple_list_item_1,
                                        dbData
                                ));

                                listViewLote.setAdapter(new ArrayAdapter<>(
                                        Activity_homepage.this,
                                        android.R.layout.simple_list_item_1,
                                        dbALote
                                ));

                                listViewAplicador.setAdapter(new ArrayAdapter<>(
                                        Activity_homepage.this,
                                        android.R.layout.simple_list_item_1,
                                        dbAplicador
                                ));

                            }
                            if(snapshot.get("Vacinas.BGC.Lote").toString() == "null"){
                                Log.d("Erro", "Estou aqui");
                            }else {
                                Log.d("Erro","Nao pegue nada null");

                                dbVacina.add("BGC");
                                dbData.add(snapshot.get("Vacinas.BGC.Data").toString());
                                dbALote.add(snapshot.get("Vacinas.BGC.Lote").toString());
                                dbAplicador.add(snapshot.get("Vacinas.BGC.Aplicado").toString());

                                listViewVacina.setAdapter(new ArrayAdapter<>(
                                        Activity_homepage.this,
                                        android.R.layout.simple_list_item_1,
                                        dbVacina
                                ));

                                listViewData.setAdapter(new ArrayAdapter<>(
                                        Activity_homepage.this,
                                        android.R.layout.simple_list_item_1,
                                        dbData
                                ));

                                listViewLote.setAdapter(new ArrayAdapter<>(
                                        Activity_homepage.this,
                                        android.R.layout.simple_list_item_1,
                                        dbALote
                                ));

                                listViewAplicador.setAdapter(new ArrayAdapter<>(
                                        Activity_homepage.this,
                                        android.R.layout.simple_list_item_1,
                                        dbAplicador
                                ));
                            }

                            if (snapshot != null && snapshot.exists()) {
                                if (snapshot.get("Vacinas.DTpa.Lote").toString() == "null") {
                                    Log.d("Erro", "Estou aqui");
                                } else {
                                    Log.d("Erro", "Nao pegue nada null");

                                    dbVacina.add("DTpa");
                                    dbData.add(snapshot.get("Vacinas.DTpa.Data").toString());
                                    dbALote.add(snapshot.get("Vacinas.DTpa.Lote").toString());
                                    dbAplicador.add(snapshot.get("Vacinas.DTpa.Aplicado").toString());

                                    listViewVacina.setAdapter(new ArrayAdapter<>(
                                            Activity_homepage.this,
                                            android.R.layout.simple_list_item_1,
                                            dbVacina
                                    ));

                                    listViewData.setAdapter(new ArrayAdapter<>(
                                            Activity_homepage.this,
                                            android.R.layout.simple_list_item_1,
                                            dbData
                                    ));

                                    listViewLote.setAdapter(new ArrayAdapter<>(
                                            Activity_homepage.this,
                                            android.R.layout.simple_list_item_1,
                                            dbALote
                                    ));

                                    listViewAplicador.setAdapter(new ArrayAdapter<>(
                                            Activity_homepage.this,
                                            android.R.layout.simple_list_item_1,
                                            dbAplicador
                                    ));

                                }
                                if (snapshot != null && snapshot.exists()) {
                                    if (snapshot.get("Vacinas.FebreA.Lote").toString() == "null") {
                                        Log.d("Erro", "Estou aqui");
                                    } else {
                                        Log.d("Erro", "Nao pegue nada null");

                                        dbVacina.add("Febre Amarela");
                                        dbData.add(snapshot.get("Vacinas.FebreA.Data").toString());
                                        dbALote.add(snapshot.get("Vacinas.FebreA.Lote").toString());
                                        dbAplicador.add(snapshot.get("Vacinas.FebreA.Aplicado").toString());

                                        listViewVacina.setAdapter(new ArrayAdapter<>(
                                                Activity_homepage.this,
                                                android.R.layout.simple_list_item_1,
                                                dbVacina
                                        ));

                                        listViewData.setAdapter(new ArrayAdapter<>(
                                                Activity_homepage.this,
                                                android.R.layout.simple_list_item_1,
                                                dbData
                                        ));

                                        listViewLote.setAdapter(new ArrayAdapter<>(
                                                Activity_homepage.this,
                                                android.R.layout.simple_list_item_1,
                                                dbALote
                                        ));

                                        listViewAplicador.setAdapter(new ArrayAdapter<>(
                                                Activity_homepage.this,
                                                android.R.layout.simple_list_item_1,
                                                dbAplicador
                                        ));

                                    }
                                }
                                if (snapshot != null && snapshot.exists()) {
                                    if (snapshot.get("Vacinas.HepA.Lote").toString() == "null") {
                                        Log.d("Erro", "Estou aqui");
                                    } else {
                                        Log.d("Erro", "Nao pegue nada null");

                                        dbVacina.add("HepA");
                                        dbData.add(snapshot.get("Vacinas.HepA.Data").toString());
                                        dbALote.add(snapshot.get("Vacinas.HepA.Lote").toString());
                                        dbAplicador.add(snapshot.get("Vacinas.HepA.Aplicado").toString());

                                        listViewVacina.setAdapter(new ArrayAdapter<>(
                                                Activity_homepage.this,
                                                android.R.layout.simple_list_item_1,
                                                dbVacina
                                        ));

                                        listViewData.setAdapter(new ArrayAdapter<>(
                                                Activity_homepage.this,
                                                android.R.layout.simple_list_item_1,
                                                dbData
                                        ));

                                        listViewLote.setAdapter(new ArrayAdapter<>(
                                                Activity_homepage.this,
                                                android.R.layout.simple_list_item_1,
                                                dbALote
                                        ));

                                        listViewAplicador.setAdapter(new ArrayAdapter<>(
                                                Activity_homepage.this,
                                                android.R.layout.simple_list_item_1,
                                                dbAplicador
                                        ));

                                    }
                                }
                                if (snapshot != null && snapshot.exists()) {
                                    if (snapshot.get("Vacinas.HepB.Lote").toString() == "null") {
                                        Log.d("Erro", "Estou aqui");
                                    } else {
                                        Log.d("Erro", "Nao pegue nada null");

                                        dbVacina.add("HepB");
                                        dbData.add(snapshot.get("Vacinas.HepB.Data").toString());
                                        dbALote.add(snapshot.get("Vacinas.HepB.Lote").toString());
                                        dbAplicador.add(snapshot.get("Vacinas.HepB.Aplicado").toString());

                                        listViewVacina.setAdapter(new ArrayAdapter<>(
                                                Activity_homepage.this,
                                                android.R.layout.simple_list_item_1,
                                                dbVacina
                                        ));

                                        listViewData.setAdapter(new ArrayAdapter<>(
                                                Activity_homepage.this,
                                                android.R.layout.simple_list_item_1,
                                                dbData
                                        ));

                                        listViewLote.setAdapter(new ArrayAdapter<>(
                                                Activity_homepage.this,
                                                android.R.layout.simple_list_item_1,
                                                dbALote
                                        ));

                                        listViewAplicador.setAdapter(new ArrayAdapter<>(
                                                Activity_homepage.this,
                                                android.R.layout.simple_list_item_1,
                                                dbAplicador
                                        ));

                                    }
                                }
                                if (snapshot != null && snapshot.exists()) {
                                    if (snapshot.get("Vacinas.HPV.Lote").toString() == "null") {
                                        Log.d("Erro", "Estou aqui");
                                    } else {
                                        Log.d("Erro", "Nao pegue nada null");

                                        dbVacina.add("HPV");
                                        dbData.add(snapshot.get("Vacinas.HPV.Data").toString());
                                        dbALote.add(snapshot.get("Vacinas.HPV.Lote").toString());
                                        dbAplicador.add(snapshot.get("Vacinas.HPV.Aplicado").toString());

                                        listViewVacina.setAdapter(new ArrayAdapter<>(
                                                Activity_homepage.this,
                                                android.R.layout.simple_list_item_1,
                                                dbVacina
                                        ));

                                        listViewData.setAdapter(new ArrayAdapter<>(
                                                Activity_homepage.this,
                                                android.R.layout.simple_list_item_1,
                                                dbData
                                        ));

                                        listViewLote.setAdapter(new ArrayAdapter<>(
                                                Activity_homepage.this,
                                                android.R.layout.simple_list_item_1,
                                                dbALote
                                        ));

                                        listViewAplicador.setAdapter(new ArrayAdapter<>(
                                                Activity_homepage.this,
                                                android.R.layout.simple_list_item_1,
                                                dbAplicador
                                        ));

                                    }
                                }

                                if (snapshot != null && snapshot.exists()) {
                                    if (snapshot.get("Vacinas.Men.Lote").toString() == "null") {
                                        Log.d("Erro", "Estou aqui");
                                    } else {
                                        Log.d("Erro", "Nao pegue nada null");

                                        dbVacina.add("Men");
                                        dbData.add(snapshot.get("Vacinas.Men.Data").toString());
                                        dbALote.add(snapshot.get("Vacinas.Men.Lote").toString());
                                        dbAplicador.add(snapshot.get("Vacinas.Men.Aplicado").toString());

                                        listViewVacina.setAdapter(new ArrayAdapter<>(
                                                Activity_homepage.this,
                                                android.R.layout.simple_list_item_1,
                                                dbVacina
                                        ));

                                        listViewData.setAdapter(new ArrayAdapter<>(
                                                Activity_homepage.this,
                                                android.R.layout.simple_list_item_1,
                                                dbData
                                        ));

                                        listViewLote.setAdapter(new ArrayAdapter<>(
                                                Activity_homepage.this,
                                                android.R.layout.simple_list_item_1,
                                                dbALote
                                        ));

                                        listViewAplicador.setAdapter(new ArrayAdapter<>(
                                                Activity_homepage.this,
                                                android.R.layout.simple_list_item_1,
                                                dbAplicador
                                        ));

                                    }
                                }

                                if (snapshot != null && snapshot.exists()) {
                                    if (snapshot.get("Vacinas.Penta.Lote").toString() == "null") {
                                        Log.d("Erro", "Estou aqui");
                                    } else {
                                        Log.d("Erro", "Nao pegue nada null");

                                        dbVacina.add("Penta");
                                        dbData.add(snapshot.get("Vacinas.Penta.Data").toString());
                                        dbALote.add(snapshot.get("Vacinas.Penta.Lote").toString());
                                        dbAplicador.add(snapshot.get("Vacinas.Penta.Aplicado").toString());

                                        listViewVacina.setAdapter(new ArrayAdapter<>(
                                                Activity_homepage.this,
                                                android.R.layout.simple_list_item_1,
                                                dbVacina
                                        ));

                                        listViewData.setAdapter(new ArrayAdapter<>(
                                                Activity_homepage.this,
                                                android.R.layout.simple_list_item_1,
                                                dbData
                                        ));

                                        listViewLote.setAdapter(new ArrayAdapter<>(
                                                Activity_homepage.this,
                                                android.R.layout.simple_list_item_1,
                                                dbALote
                                        ));

                                        listViewAplicador.setAdapter(new ArrayAdapter<>(
                                                Activity_homepage.this,
                                                android.R.layout.simple_list_item_1,
                                                dbAplicador
                                        ));

                                    }
                                }

                                if (snapshot != null && snapshot.exists()) {
                                    if (snapshot.get("Vacinas.10V.Lote").toString() == "null") {
                                        Log.d("Erro", "Estou aqui");
                                    } else {
                                        Log.d("Erro", "Nao pegue nada null");

                                        dbVacina.add("10V");
                                        dbData.add(snapshot.get("Vacinas.10V.Data").toString());
                                        dbALote.add(snapshot.get("Vacinas.10V.Lote").toString());
                                        dbAplicador.add(snapshot.get("Vacinas.10V.Aplicado").toString());

                                        listViewVacina.setAdapter(new ArrayAdapter<>(
                                                Activity_homepage.this,
                                                android.R.layout.simple_list_item_1,
                                                dbVacina
                                        ));

                                        listViewData.setAdapter(new ArrayAdapter<>(
                                                Activity_homepage.this,
                                                android.R.layout.simple_list_item_1,
                                                dbData
                                        ));

                                        listViewLote.setAdapter(new ArrayAdapter<>(
                                                Activity_homepage.this,
                                                android.R.layout.simple_list_item_1,
                                                dbALote
                                        ));

                                        listViewAplicador.setAdapter(new ArrayAdapter<>(
                                                Activity_homepage.this,
                                                android.R.layout.simple_list_item_1,
                                                dbAplicador
                                        ));

                                    }
                                }
                                if (snapshot != null && snapshot.exists()) {
                                    if (snapshot.get("Vacinas.Rotavirus.Lote").toString() == "null") {
                                        Log.d("Erro", "Estou aqui");
                                    } else {
                                        Log.d("Erro", "Nao pegue nada null");

                                        dbVacina.add("Rotavirus");
                                        dbData.add(snapshot.get("Vacinas.Rotavirus.Data").toString());
                                        dbALote.add(snapshot.get("Vacinas.Rotavirus.Lote").toString());
                                        dbAplicador.add(snapshot.get("Vacinas.Rotavirus.Aplicado").toString());

                                        listViewVacina.setAdapter(new ArrayAdapter<>(
                                                Activity_homepage.this,
                                                android.R.layout.simple_list_item_1,
                                                dbVacina
                                        ));

                                        listViewData.setAdapter(new ArrayAdapter<>(
                                                Activity_homepage.this,
                                                android.R.layout.simple_list_item_1,
                                                dbData
                                        ));

                                        listViewLote.setAdapter(new ArrayAdapter<>(
                                                Activity_homepage.this,
                                                android.R.layout.simple_list_item_1,
                                                dbALote
                                        ));

                                        listViewAplicador.setAdapter(new ArrayAdapter<>(
                                                Activity_homepage.this,
                                                android.R.layout.simple_list_item_1,
                                                dbAplicador
                                        ));

                                    }
                                }
                                if (snapshot != null && snapshot.exists()) {
                                    if (snapshot.get("Vacinas.Tetra.Lote").toString() == "null") {
                                        Log.d("Erro", "Estou aqui");
                                    } else {
                                        Log.d("Erro", "Nao pegue nada null");

                                        dbVacina.add("Tetra");
                                        dbData.add(snapshot.get("Vacinas.Tetra.Data").toString());
                                        dbALote.add(snapshot.get("Vacinas.Tetra.Lote").toString());
                                        dbAplicador.add(snapshot.get("Vacinas.Tetra.Aplicado").toString());

                                        listViewVacina.setAdapter(new ArrayAdapter<>(
                                                Activity_homepage.this,
                                                android.R.layout.simple_list_item_1,
                                                dbVacina
                                        ));

                                        listViewData.setAdapter(new ArrayAdapter<>(
                                                Activity_homepage.this,
                                                android.R.layout.simple_list_item_1,
                                                dbData
                                        ));

                                        listViewLote.setAdapter(new ArrayAdapter<>(
                                                Activity_homepage.this,
                                                android.R.layout.simple_list_item_1,
                                                dbALote
                                        ));

                                        listViewAplicador.setAdapter(new ArrayAdapter<>(
                                                Activity_homepage.this,
                                                android.R.layout.simple_list_item_1,
                                                dbAplicador
                                        ));

                                    }
                                }
                                if (snapshot != null && snapshot.exists()) {
                                    if (snapshot.get("Vacinas.Triplice.Lote").toString() == "null") {
                                        Log.d("Erro", "Estou aqui");
                                    } else {
                                        Log.d("Erro", "Nao pegue nada null");

                                        dbVacina.add("Triplice");
                                        dbData.add(snapshot.get("Vacinas.Triplice.Data").toString());
                                        dbALote.add(snapshot.get("Vacinas.Triplice.Lote").toString());
                                        dbAplicador.add(snapshot.get("Vacinas.Triplice.Aplicado").toString());

                                        listViewVacina.setAdapter(new ArrayAdapter<>(
                                                Activity_homepage.this,
                                                android.R.layout.simple_list_item_1,
                                                dbVacina
                                        ));

                                        listViewData.setAdapter(new ArrayAdapter<>(
                                                Activity_homepage.this,
                                                android.R.layout.simple_list_item_1,
                                                dbData
                                        ));

                                        listViewLote.setAdapter(new ArrayAdapter<>(
                                                Activity_homepage.this,
                                                android.R.layout.simple_list_item_1,
                                                dbALote
                                        ));

                                        listViewAplicador.setAdapter(new ArrayAdapter<>(
                                                Activity_homepage.this,
                                                android.R.layout.simple_list_item_1,
                                                dbAplicador
                                        ));

                                    }
                                }
                                if (snapshot != null && snapshot.exists()) {
                                    if (snapshot.get("Vacinas.Vip.Lote").toString() == "null") {
                                        Log.d("Erro", "Estou aqui");
                                    } else {
                                        Log.d("Erro", "Nao pegue nada null");

                                        dbVacina.add("Vip");
                                        dbData.add(snapshot.get("Vacinas.Vip.Data").toString());
                                        dbALote.add(snapshot.get("Vacinas.Vip.Lote").toString());
                                        dbAplicador.add(snapshot.get("Vacinas.Vip.Aplicado").toString());

                                        listViewVacina.setAdapter(new ArrayAdapter<>(
                                                Activity_homepage.this,
                                                android.R.layout.simple_list_item_1,
                                                dbVacina
                                        ));

                                        listViewData.setAdapter(new ArrayAdapter<>(
                                                Activity_homepage.this,
                                                android.R.layout.simple_list_item_1,
                                                dbData
                                        ));

                                        listViewLote.setAdapter(new ArrayAdapter<>(
                                                Activity_homepage.this,
                                                android.R.layout.simple_list_item_1,
                                                dbALote
                                        ));

                                        listViewAplicador.setAdapter(new ArrayAdapter<>(
                                                Activity_homepage.this,
                                                android.R.layout.simple_list_item_1,
                                                dbAplicador
                                        ));

                                    }
                                }
                            }
                        }
                    }
                });



        //
    //parte de criar pdf-PERMISSAO
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);
        createPDF();
        //

    }
    //criar pagina PDF
    private void createPDF() {
   button_PDF.setOnClickListener(new View.OnClickListener() {

       @RequiresApi(api = Build.VERSION_CODES.KITKAT)
       @Override
       public void onClick(View view) {
           PdfDocument pdfDocument= new PdfDocument();
           Paint paint= new Paint();
           PdfDocument.PageInfo pageInfo= new PdfDocument.PageInfo.Builder(250,400,1).create();
           PdfDocument.Page page= pdfDocument.startPage(pageInfo);
           Canvas canvas= page.getCanvas();
           canvas.drawText("Vacinas tomadas", 80,42,paint);
           paint.setStyle(Paint.Style.STROKE);
           paint.setStrokeWidth(2);
           canvas.drawRect(12,55,245,380,paint);
           canvas.drawLine(12, 75, 245, 75, paint);
           paint.setTextAlign(Paint.Align.LEFT);
           paint.setStyle(Paint.Style.FILL);
           canvas.drawText("Vacinas",22,70,paint);
           canvas.drawText("Data",92,70,paint);
           canvas.drawText("Lote",138,70,paint);
           canvas.drawText("Aplicador",180,70,paint);

            paint.setStrokeWidth(1);
           canvas.drawLine(82,55,82,380, paint);
           canvas.drawLine(128,55,128,380, paint);
           canvas.drawLine(170,55,170,380, paint);


           pdfDocument.finishPage(page);
           File file = new File(Environment.getExternalStorageDirectory(), "/VacinasPDF.pdf");
           try {
               pdfDocument.writeTo(new FileOutputStream(file));
               Toast.makeText(Activity_homepage.this, "Baixado em seus arquivos- armazenamento interno", Toast.LENGTH_LONG).show();
           } catch (IOException e) {
               Toast.makeText(Activity_homepage.this, "Erro", Toast.LENGTH_LONG).show();
               e.printStackTrace();
           }
            pdfDocument.close();
       }
   });


    }
    //

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
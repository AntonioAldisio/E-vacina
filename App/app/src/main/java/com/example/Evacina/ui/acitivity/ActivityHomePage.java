package com.example.Evacina.ui.acitivity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;

import android.Manifest;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

import com.example.Evacina.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class ActivityHomePage extends AppCompatActivity {

    private FirebaseAuth mAuth;
    public static final String NOME_KEY = "Nome";
    private String usario_key = FirebaseAuth.getInstance().getUid();
    private  FirebaseFirestore db = FirebaseFirestore.getInstance();



    private Button Activity_homepage_button_sair;
    private TextView Activity_homepage_textview_nome_cliente;
    private ListView Activity_homepage_listview_vacina;
    private ListView Activity_homepage_listview_data;
    private ListView Activity_homepage_listview_lote;
    private ListView Activity_homepage_listview_aplicador;
    private List<String> dbAplicador;
    private List<String> dbData;
    private List<String> dbALote;
    private List<String> dbVacina;
    //para pdf
    private Button Activity_homepage_button_PDF;
    private Bitmap imagem, iamgemEscala;

    private String mensagem;
    //


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        mAuth=FirebaseAuth.getInstance();

        imagem= BitmapFactory.decodeResource(getResources(), R.drawable.logo);
        iamgemEscala= Bitmap.createScaledBitmap(imagem, 92,92,false);

        ActivityHomePageBuscaId();

        Activity_homepage_button_sair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                Intent intent= new Intent(ActivityHomePage.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
//

// Mostrar vacinas
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

                                imprimeTela();

                            }
                            if(snapshot.get("Vacinas.BGC.Lote").toString() == "null"){
                                Log.d("Erro", "Estou aqui");
                            }else {
                                Log.d("Erro","Nao pegue nada null");

                                dbVacina.add("BGC");
                                dbData.add(snapshot.get("Vacinas.BGC.Data").toString());
                                dbALote.add(snapshot.get("Vacinas.BGC.Lote").toString());
                                dbAplicador.add(snapshot.get("Vacinas.BGC.Aplicado").toString());

                                imprimeTela();
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

                                    imprimeTela();

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

                                        imprimeTela();

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

                                        imprimeTela();

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

                                        imprimeTela();

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

                                        imprimeTela();

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

                                        imprimeTela();

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

                                        imprimeTela();

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

                                        imprimeTela();

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

                                        imprimeTela();

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

                                        imprimeTela();

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

                                        imprimeTela();

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

                                        imprimeTela();

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
        //
        // Notifição de inicio de vacinação

        db.collection("notificacoes").document("Inicio")
                .addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
                    @Override
                    public void onEvent(DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
                         mensagem = documentSnapshot.getString("Mensagem");
                    }

                });

    }

        @Override
    protected void onStart(){
        super.onStart();
        FirebaseUser currentuser = FirebaseAuth.getInstance().getCurrentUser();

        if(currentuser==null){
            Intent intent = new Intent(ActivityHomePage.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    private void ActivityHomePageBuscaId() {
        Activity_homepage_button_sair = findViewById(R.id.Activity_homepage_button_sair);
        Activity_homepage_textview_nome_cliente = findViewById(R.id.Activity_homepage_textview_nome_cliente);
        Activity_homepage_button_PDF = findViewById(R.id.Activity_homepage_button_PDF);


        Activity_homepage_listview_vacina = findViewById(R.id.Activity_homepage_listview_vacina);
        Activity_homepage_listview_aplicador = findViewById(R.id.Activity_homepage_listview_aplicador);
        Activity_homepage_listview_data = findViewById(R.id.Activity_homepage_listview_data);
        Activity_homepage_listview_lote = findViewById(R.id.Activity_homepage_listview_lote);
    }

    private void imprimeTela() {
        Activity_homepage_listview_vacina.setAdapter(new ArrayAdapter<>(
                ActivityHomePage.this,
                android.R.layout.simple_list_item_1,
                dbVacina
        ));

        Activity_homepage_listview_data.setAdapter(new ArrayAdapter<>(
                ActivityHomePage.this,
                android.R.layout.simple_list_item_1,
                dbData
        ));

        Activity_homepage_listview_lote.setAdapter(new ArrayAdapter<>(
                ActivityHomePage.this,
                android.R.layout.simple_list_item_1,
                dbALote
        ));

        Activity_homepage_listview_aplicador.setAdapter(new ArrayAdapter<>(
                ActivityHomePage.this,
                android.R.layout.simple_list_item_1,
                dbAplicador
        ));
    }

    //criar cartao PDF
    private void createPDF() {
        Activity_homepage_button_PDF.setOnClickListener(new View.OnClickListener() {

            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View view) {
                PdfDocument pdfDocument= new PdfDocument();
                Paint paint= new Paint();
                PdfDocument.PageInfo pageInfo= new PdfDocument.PageInfo.Builder(400,500,1).create();
                PdfDocument.Page page= pdfDocument.startPage(pageInfo);
                Canvas canvas= page.getCanvas();
                paint.setColor(Color.rgb(253, 141, 39));
                canvas.drawRect(0,0,400,500,paint);
                canvas.drawBitmap(iamgemEscala,290,400,paint);
                paint.setTextSize(22);
                canvas.drawText("Vacinas tomadas", 100,42,paint);
                paint.setStyle(Paint.Style.STROKE);
                paint.setStrokeWidth(2);
                paint.setColor(Color.BLACK);
                canvas.drawRect(12,55,395,380,paint);
                canvas.drawLine(12, 75, 395, 75, paint);
                paint.setTextAlign(Paint.Align.LEFT);
                paint.setStyle(Paint.Style.FILL);
                paint.setTextSize(18);

                canvas.drawText("Vacinas",22,70,paint);
                canvas.drawText("Data",112,70,paint);
                canvas.drawText("Lote",178,70,paint);
                canvas.drawText("Aplicador",245,70,paint);
                paint.setTextSize(13);
                //coluna das vacinas
                paint.setColor(Color.BLACK);

                canvas.drawText("Dupla", 22, 90,paint);
                canvas.drawText("BGC", 22, 110,paint);
                canvas.drawText("DTPA", 22, 130,paint);
                canvas.drawText("Febre A", 22, 150,paint);
                canvas.drawText("HepA", 22, 170,paint);
                canvas.drawText("HepB", 22, 190,paint);
                canvas.drawText("HPV", 22, 210,paint);
                canvas.drawText("Meningite", 22, 230,paint);
                canvas.drawText("Penta", 22, 250,paint);
                canvas.drawText("10V", 22, 270,paint);
                canvas.drawText("Rotavirus", 22, 290,paint);
                canvas.drawText("Tetra", 22, 310,paint);
                canvas.drawText("Triplice", 22, 330,paint);
                canvas.drawText("Vip", 22, 350,paint);
                paint.setStrokeWidth(1);
                canvas.drawLine(110,55,110,380, paint);
                canvas.drawLine(170,55,170,380, paint);
                canvas.drawLine(243,55,243,380, paint);
                //coluna do lote
                paint.setTextSize(10);
                canvas.drawText(dbALote.get(0),178,90,paint);
                canvas.drawText(dbALote.get(1),178, 110, paint);
                canvas.drawText(dbALote.get(2),178, 130, paint);
                canvas.drawText(dbALote.get(3),178, 150, paint);
                canvas.drawText(dbALote.get(4),178, 170, paint);
                canvas.drawText(dbALote.get(5),178, 190, paint);
                canvas.drawText(dbALote.get(6),178, 210, paint);
                canvas.drawText(dbALote.get(7),178, 230, paint);
                canvas.drawText(dbALote.get(8),178, 250, paint);
                canvas.drawText(dbALote.get(9),178, 270, paint);
                canvas.drawText(dbALote.get(10),178, 290, paint);
                canvas.drawText(dbALote.get(11),178, 310, paint);
                canvas.drawText(dbALote.get(12),178, 330, paint);
                canvas.drawText(dbALote.get(13),178, 350, paint);
                //coluna da data

                canvas.drawText(dbData.get(0),112,90,paint);
                canvas.drawText(dbData.get(1),112,110,paint);
                canvas.drawText(dbData.get(2),112,130,paint);
                canvas.drawText(dbData.get(3),112,150,paint);
                canvas.drawText(dbData.get(4),112,170,paint);
                canvas.drawText(dbData.get(5),112,190,paint);
                canvas.drawText(dbData.get(6),112,210,paint);
                canvas.drawText(dbData.get(7),112,230,paint);
                canvas.drawText(dbData.get(8),112,250,paint);
                canvas.drawText(dbData.get(9),112,270,paint);
                canvas.drawText(dbData.get(10),112,290,paint);
                canvas.drawText(dbData.get(11),112,310,paint);
                canvas.drawText(dbData.get(12),112,330,paint);
                canvas.drawText(dbData.get(13),112,350,paint);
                //coluna do aplicador
                paint.setTextSize(10);
                canvas.drawText(dbAplicador.get(0),245,90 ,paint);
                canvas.drawText(dbAplicador.get(1),245,110,paint);
                canvas.drawText(dbAplicador.get(2),245,130 ,paint);
                canvas.drawText(dbAplicador.get(3),245,150 ,paint);
                canvas.drawText(dbAplicador.get(4),245,170 ,paint);
                canvas.drawText(dbAplicador.get(5),245,190 ,paint);
                canvas.drawText(dbAplicador.get(6),245,210 ,paint);
                canvas.drawText(dbAplicador.get(7),245,230 ,paint);
                canvas.drawText(dbAplicador.get(8),245,250 ,paint);
                canvas.drawText(dbAplicador.get(9),245,270 ,paint);
                canvas.drawText(dbAplicador.get(10),245,290 ,paint);
                canvas.drawText(dbAplicador.get(11),245,310 ,paint);
                canvas.drawText(dbAplicador.get(12),245,330 ,paint);
                canvas.drawText(dbAplicador.get(13),245,350 ,paint);


                pdfDocument.finishPage(page);
                File file = new File(Environment.getExternalStorageDirectory(), "/VacinasPDF.pdf");
                try {
                    pdfDocument.writeTo(new FileOutputStream(file));
                    Toast.makeText(ActivityHomePage.this, "Baixado em seus arquivos- armazenamento interno", Toast.LENGTH_LONG).show();
                } catch (IOException e) {
                    Toast.makeText(ActivityHomePage.this, "Erro!, verifique as permissoes e se possui armazenamento interno suficiente", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
                pdfDocument.close();
            }
        });


    }
    //fim do cartao

    //parte para os itens para o menu e tratamento de menu-bianca
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_principal,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {


        if(  item.getItemId()==R.id.item_editar){
            Intent intent = new Intent(this, ActivityInformacoesPessoiasEdicao.class);
            startActivity(intent);


        }
        if(  item.getItemId()==R.id.item_vacinas){ }
        if(  item.getItemId()==R.id.item_cadastrar){
            Intent intent = new Intent(this, ActivityCadastroVacina.class);
            startActivity(intent);



        }
        if(item.getItemId()==R.id.item_excluir){
            AlertDialog.Builder dialog= new AlertDialog.Builder(ActivityHomePage.this);
            dialog.setTitle("Tem certeza?");
            dialog.setMessage("Excluir sua conta significa que nao podera acessar novamente");
            dialog.setPositiveButton("Exluir", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    FirebaseUser user= mAuth.getCurrentUser();
                    user.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(ActivityHomePage.this, "Conta deletada", Toast.LENGTH_SHORT).show();
                            mAuth.signOut();
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            finish();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(ActivityHomePage.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            }).setNegativeButton("Cancelar", null).create().show();
        }

        return super.onOptionsItemSelected(item);
    }

    // fim do menu


}



















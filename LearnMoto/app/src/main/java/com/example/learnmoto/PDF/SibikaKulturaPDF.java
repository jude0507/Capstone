package com.example.learnmoto.PDF;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;

import com.example.learnmoto.Adapter.PDFAdapter;
import com.example.learnmoto.ItemClickListener;
import com.example.learnmoto.Model.PDFModel;
import com.example.learnmoto.CheckConnection.NetworkChangeListener;
import com.example.learnmoto.Preparatory.SibikaKultura.PreparatorySibKulRead;
import com.example.learnmoto.R;
import com.example.learnmoto.Student.StudentLogin;

import java.util.ArrayList;

public class SibikaKulturaPDF extends AppCompatActivity {

    private ArrayList<PDFModel> pdfModel;
    private RecyclerView rv_pdf;
    private ItemClickListener listener;
    String checkLevel = StudentLogin.sLevel;
    NetworkChangeListener networkChangeListener = new NetworkChangeListener();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sibika_kultura_pdf);

        rv_pdf = findViewById(R.id.pdfItem);
        pdfModel = new ArrayList<>();
        setPDFTitle();
        setAdapter();

    }
    private void setPDFTitle() {
        if (checkLevel.equals("Preparatory")){
            pdfModel.add(new PDFModel("Anyong Tubig"));
            pdfModel.add(new PDFModel("Anyong Lupa"));
        }

    }

    //https://drive.google.com/drive/u/0/folders/1l3MjROypnTaL21xa_175W57xd644Yxpz

    private void setAdapter() {
        setOnclickListener();
        PDFAdapter myadapter = new PDFAdapter(pdfModel,listener);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        rv_pdf.setLayoutManager(layoutManager);
        rv_pdf.setItemAnimator(new DefaultItemAnimator());
        rv_pdf.setAdapter(myadapter);
    }

    private void setOnclickListener() {
        listener = (view, position) -> {
            String item = pdfModel.get(position).getPdftitle();
            Intent intent = new Intent(getApplicationContext(), SibikaKulturaViewPDF.class);
            intent.putExtra("SibikaKulturaPDF",item);
            startActivity(intent);

        };
    }

    public void BacktoSibKulClass(View view) {
        startActivity(new Intent(this, PreparatorySibKulRead.class));
    }

    @Override
    protected void onStart() {
        IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkChangeListener, intentFilter);
        super.onStart();
    }

    @Override
    protected void onStop() {
        unregisterReceiver(networkChangeListener);
        super.onStop();
    }

}
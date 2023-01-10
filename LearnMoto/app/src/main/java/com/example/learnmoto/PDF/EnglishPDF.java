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

import com.example.learnmoto.Interface.ItemClickListener;
import com.example.learnmoto.Kinder.English.KinderEnglish;
import com.example.learnmoto.Model.PDFModel;
import com.example.learnmoto.CheckConnection.NetworkChangeListener;
import com.example.learnmoto.Nursery.English.NurseryEnglish;
import com.example.learnmoto.Adapter.PDFAdapter;
import com.example.learnmoto.Preparatory.English.PreparatoryEnglish;
import com.example.learnmoto.R;
import com.example.learnmoto.Student.StudentHomeView;

import java.util.ArrayList;

public class EnglishPDF extends AppCompatActivity {

    private ArrayList<PDFModel> pdfModel;
    private RecyclerView rv_pdf;
    private ItemClickListener listener;

    NetworkChangeListener networkChangeListener = new NetworkChangeListener();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_english_pdf);

        rv_pdf = findViewById(R.id.pdfItem);
        pdfModel = new ArrayList<>();

        setPDFTitle();
        setAdapter();

    }

    private void setPDFTitle() {
        if (StudentHomeView.level.equals("Nursery")){
            pdfModel.add(new PDFModel("colors"));
            pdfModel.add(new PDFModel("Alphabets"));
        }else if (StudentHomeView.level.equals("Kinder")){
            pdfModel.add(new PDFModel("Real or Make"));
            pdfModel.add(new PDFModel("Different Colors 2"));
            pdfModel.add(new PDFModel("Reading Objects"));
        }else{
            pdfModel.add(new PDFModel("Alphabets"));
            pdfModel.add(new PDFModel("Real or Make"));
            pdfModel.add(new PDFModel("Reading Objects"));
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
            Intent intent = new Intent(getApplicationContext(), EnglishViewPDF.class);
            intent.putExtra("EnglishPDF",item);
            startActivity(intent);

        };
    }

    public void BacktoEnglishClass(View view) {
        BackPressed();
    }

    private void BackPressed(){
        if (StudentHomeView.level.equals("Nursery")){
            startActivity(new Intent(this, NurseryEnglish.class));
        } else if (StudentHomeView.level.equals("Kinder")){
            startActivity(new Intent(this, KinderEnglish.class));
        }else{
            startActivity(new Intent(this, PreparatoryEnglish.class));
        }
    }

    @Override
    public void onBackPressed() {
        BackPressed();
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
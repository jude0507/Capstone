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
import com.example.learnmoto.Kinder.ChristianLiving.KinderChristianLivingRead;
import com.example.learnmoto.Model.PDFModel;
import com.example.learnmoto.CheckConnection.NetworkChangeListener;
import com.example.learnmoto.Nursery.ChristianLiving.NurseryChristianLivingRead;
import com.example.learnmoto.Preparatory.ChristianLiving.PreparatoryChristianLivingRead;
import com.example.learnmoto.R;
import com.example.learnmoto.Student.StudentHomeView;
import com.example.learnmoto.Student.StudentLogin;

import java.util.ArrayList;

public class ChristianLivingPDF extends AppCompatActivity {

    private ArrayList<PDFModel> pdfModel;
    private RecyclerView rv_pdf;
    private ItemClickListener listener;

    NetworkChangeListener networkChangeListener = new NetworkChangeListener();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_christian_living_pdf);
        rv_pdf = findViewById(R.id.pdfItem);
        pdfModel = new ArrayList<>();
        setPDFTitle();
        setAdapter();
    }
    private void setPDFTitle() {
        if (StudentHomeView.level.equals("Nursery")){
            pdfModel.add(new PDFModel("The Creation"));
            pdfModel.add(new PDFModel("Who is God"));
        }else if (StudentHomeView.level.equals("Kinder")){
            pdfModel.add(new PDFModel("John 316"));
            pdfModel.add(new PDFModel("Plasm 23"));
        }else{
            pdfModel.add(new PDFModel("Faith"));
            pdfModel.add(new PDFModel("How to Pray"));

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
            Intent intent = new Intent(getApplicationContext(), ChristianLivingViewPDF.class);
            intent.putExtra("ChristianLivingPDF",item);
            startActivity(intent);

        };
    }

    public void BacktoChristianLivingClass(View view) {
        if (StudentHomeView.level.equals("Nursery")){
            startActivity(new Intent(this, NurseryChristianLivingRead.class));
        } else if (StudentHomeView.level.equals("Kinder")){
            startActivity(new Intent(this, KinderChristianLivingRead.class));
        }else{
            startActivity(new Intent(this, PreparatoryChristianLivingRead.class));
        }
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
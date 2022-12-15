package com.example.learnmoto.PDF;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.learnmoto.Adapter.PDFAdapter;
import com.example.learnmoto.ItemClickListener;
import com.example.learnmoto.Kinder.Science.KinderScienceRead;
import com.example.learnmoto.Model.PDFModel;
import com.example.learnmoto.Nursery.Science.NurseryScienceRead;
import com.example.learnmoto.PDF.EnglishViewPDF;
import com.example.learnmoto.Preparatory.Science.PreparatoryScienceRead;
import com.example.learnmoto.R;
import com.example.learnmoto.Student.StudentLogin;

import java.util.ArrayList;

public class SciencePDF extends AppCompatActivity {

    private ArrayList<PDFModel> pdfModel;
    private RecyclerView rv_pdf;
    private ItemClickListener listener;
    String checkLevel = StudentLogin.sLevel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_science_pdf);

        rv_pdf = findViewById(R.id.pdfItem);
        pdfModel = new ArrayList<>();

        setPDFTitle();
        setAdapter();
    }
    private void setPDFTitle() {
        if (checkLevel.equals("Nursery")){
            pdfModel.add(new PDFModel("About Dinosaurs"));
            pdfModel.add(new PDFModel("LifeCycle of Plants"));
        }else if (checkLevel.equals("Kinder")){
            pdfModel.add(new PDFModel("About Galaxies"));
            pdfModel.add(new PDFModel("Solar System"));
        }else{
            pdfModel.add(new PDFModel("About Dinosaurs"));
            pdfModel.add(new PDFModel("About Galaxies"));
            pdfModel.add(new PDFModel("LifeCycle of Plants"));
            pdfModel.add(new PDFModel("Solar System"));
            pdfModel.add(new PDFModel("Body Parts"));
            pdfModel.add(new PDFModel("Know about Seasons"));
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
            intent.putExtra("SciencePDF",item);
            startActivity(intent);

        };
    }

    public void BacktoEnglishClass(View view) {
        if (checkLevel.equals("Nursery")){
            startActivity(new Intent(this, NurseryScienceRead.class));
        } else if (checkLevel.equals("Kinder")){
            startActivity(new Intent(this, KinderScienceRead.class));
        }else{
            startActivity(new Intent(this, PreparatoryScienceRead.class));
        }
    }
}
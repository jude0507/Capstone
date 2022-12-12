package com.example.learnmoto;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.learnmoto.Model.PDFModel;
import com.example.learnmoto.Nursery.NurseryEnglish;
import com.example.learnmoto.Student.StudentLogin;

import java.util.ArrayList;

public class PDF extends AppCompatActivity {

    private ArrayList<PDFModel> pdfModel;
    private RecyclerView rv_pdf;
    private ItemClickListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf);

        rv_pdf = findViewById(R.id.pdfItem);
        pdfModel = new ArrayList<>();

        setPDFTitle();
        setAdapter();

    }

    private void setPDFTitle() {

        String checkLevel = StudentLogin.sLevel;

        if (checkLevel.equals("Nursery")){
            pdfModel.add(new PDFModel("colors"));
            pdfModel.add(new PDFModel("alphabets"));
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
            Intent intent = new Intent(getApplicationContext(), ViewPDF.class);
            intent.putExtra("EnglishPDF",item);
            startActivity(intent);

        };
    }

    public void BacktoEnglishClass(View view) {
        startActivity(new Intent(this, NurseryEnglish.class));
    }
}
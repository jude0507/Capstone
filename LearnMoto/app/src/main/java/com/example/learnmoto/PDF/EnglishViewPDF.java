package com.example.learnmoto.PDF;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.learnmoto.R;
import com.github.barteksc.pdfviewer.PDFView;

public class EnglishViewPDF extends AppCompatActivity {

    PDFView pdfviewer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pdf);

        pdfviewer = findViewById(R.id.pdfView);

        String getItem = getIntent().getStringExtra("EnglishPDF");

//        if (getItem.equals("colors")){
//            pdfviewer.fromAsset("colors.pdf").load();
//        }
        if (getItem.equals("Alphabets")){
            pdfviewer.fromAsset("Alphabet 1.pdf").load();
        }
        if (getItem.equals("Real or Make")){
            pdfviewer.fromAsset("Real or Make.pdf").load();
        }
        if (getItem.equals("Different Colors 2")){
            pdfviewer.fromAsset("Different Colors 2.pdf").load();
        }
        if (getItem.equals("Reading Objects")){
            pdfviewer.fromAsset("Reading Objects.pdf").load();
        }

    }

    public void BacktoPDF(View view) {
        startActivity(new Intent(this, EnglishPDF.class));
    }
}
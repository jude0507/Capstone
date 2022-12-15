package com.example.learnmoto.PDF;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.learnmoto.PDF.EnglishPDF;
import com.example.learnmoto.R;
import com.github.barteksc.pdfviewer.PDFView;

public class ChristianLivingViewPDF extends AppCompatActivity {

    PDFView pdfviewer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_christian_living_view_pdf);
        pdfviewer = findViewById(R.id.pdfView);
        String getItem = getIntent().getStringExtra("ChristianLivingPDF");

        if (getItem.equals("The Creation")){
            pdfviewer.fromAsset("The Creation.pdf").load();
        }
        if (getItem.equals("Who is God")){
            pdfviewer.fromAsset("Who is God.pdf").load();
        }
        if (getItem.equals("John 316")){
            pdfviewer.fromAsset("John 316.pdf").load();
        }
        if (getItem.equals("Plasm 23")){
            pdfviewer.fromAsset("PLASM 23.pdf").load();
        }
        if (getItem.equals("Faith")){
            pdfviewer.fromAsset("FAITH.pdf").load();
        }
        if (getItem.equals("How to Pray")){
            pdfviewer.fromAsset("How to Pray.pdf").load();
        }
    }
    public void BacktoPDF(View view) {
        startActivity(new Intent(this, ChristianLivingPDF.class));
    }
}
package com.example.learnmoto.PDF;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.learnmoto.PDF.EnglishPDF;
import com.example.learnmoto.R;
import com.github.barteksc.pdfviewer.PDFView;

public class ScienceViewPDF extends AppCompatActivity {

    PDFView pdfviewer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_science_view_pdf);

        pdfviewer = findViewById(R.id.pdfView);

        String getItem = getIntent().getStringExtra("SciencePDF");

        if (getItem.equals("About Dinosaurs")){
            pdfviewer.fromAsset("It's all about Dinosaurs.pdf").load();
        }
        if (getItem.equals("LifeCycle of Plants")){
            pdfviewer.fromAsset("LifeCycle of Plants.pdf").load();
        }
        if (getItem.equals("About Galaxies")){
            pdfviewer.fromAsset("About Galaxies.pdf").load();
        }
        if (getItem.equals("Body Parts")){
            pdfviewer.fromAsset("Body Parts.pdf").load();
        }
        if (getItem.equals("Know about Seasons")){
            pdfviewer.fromAsset("Seasons.pdf").load();
        }
        if (getItem.equals("Solar System")){
            pdfviewer.fromAsset("Solar System.pdf").load();
        }

    }

    public void BacktoPDF(View view) {
        startActivity(new Intent(this, SciencePDF.class));
    }

}
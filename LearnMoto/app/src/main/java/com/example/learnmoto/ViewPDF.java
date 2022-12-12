package com.example.learnmoto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.github.barteksc.pdfviewer.PDFView;

public class ViewPDF extends AppCompatActivity {

    PDFView pdfviewer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pdf);

        pdfviewer = findViewById(R.id.pdfView);

        String getItem = getIntent().getStringExtra("EnglishPDF");

        if (getItem.equals("colors")){
            pdfviewer.fromAsset("colors.pdf").load();
        }
        if (getItem.equals("alphabets")){
            pdfviewer.fromAsset("alphabets.pdf").load();
        }
        if (getItem.equals("Real or Make")){
            pdfviewer.fromAsset("Real or Make.pdf").load();
        }
        if (getItem.equals("Different Colors")){
            pdfviewer.fromAsset("Different Colors.pdf").load();
        }
        if (getItem.equals("Reading Objects")){
            pdfviewer.fromAsset("Reading Objects.pdf").load();
        }

    }

    public void BacktoPDF(View view) {
        startActivity(new Intent(this, PDF.class));
    }
}
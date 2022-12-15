package com.example.learnmoto.PDF;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.learnmoto.R;
import com.github.barteksc.pdfviewer.PDFView;

public class MathViewPDF extends AppCompatActivity {

    PDFView pdfviewer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_math_view_pdf);

        pdfviewer = findViewById(R.id.pdfView);

        String getItem = getIntent().getStringExtra("MathPDF");

        if (getItem.equals("Adding Dice")){
            pdfviewer.fromAsset("Adding Dice.pdf").load();
        }
        if (getItem.equals("Counting Objects")){
            pdfviewer.fromAsset("Counting Objects.pdf").load();
        }
        if (getItem.equals("Let's Count")){
            pdfviewer.fromAsset("Let's Count.pdf").load();
        }
        if (getItem.equals("Different Shapes")){
            pdfviewer.fromAsset("Different Shapes.pdf").load();
        }
        if (getItem.equals("Add and Subtract")){
            pdfviewer.fromAsset("Let's Add and Subtract.pdf").load();
        }
        if (getItem.equals("Learn to subtract")){
            pdfviewer.fromAsset("Learn to subtract.pdf").load();
        }
    }

    public void BacktoPDF(View view) {
        startActivity(new Intent(this, MathPDF.class));
    }

}
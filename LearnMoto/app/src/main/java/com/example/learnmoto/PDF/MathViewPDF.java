package com.example.learnmoto.PDF;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;

import com.example.learnmoto.CheckConnection.NetworkChangeListener;
import com.example.learnmoto.R;
import com.github.barteksc.pdfviewer.PDFView;

public class MathViewPDF extends AppCompatActivity {

    PDFView pdfviewer;
    NetworkChangeListener networkChangeListener = new NetworkChangeListener();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_math_view_pdf);

        pdfviewer = findViewById(R.id.pdfView);

        String getItem = getIntent().getStringExtra("MathPDF");

        if (getItem.equals("Adding Dice")){
            pdfviewer.fromAsset("Adding dice.pdf").load();
        }
        if (getItem.equals("Counting Objects")){
            pdfviewer.fromAsset("counting objects.pdf").load();
        }
        if (getItem.equals("Bilang")){
            pdfviewer.fromAsset("BILANG.pdf").load();
        }
        if (getItem.equals("Iba't- ibang hugis")){
            pdfviewer.fromAsset("HUGIS.pdf").load();
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

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, MathPDF.class));
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
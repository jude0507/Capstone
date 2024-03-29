package com.example.learnmoto.PDF;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.learnmoto.CheckConnection.NetworkChangeListener;
import com.example.learnmoto.R;
import com.github.barteksc.pdfviewer.PDFView;

public class ScienceViewPDF extends AppCompatActivity {

    PDFView pdfviewer;
    NetworkChangeListener networkChangeListener = new NetworkChangeListener();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_science_view_pdf);

        pdfviewer = findViewById(R.id.pdfView);

        String getItem = getIntent().getStringExtra("SciencePDF");

        if (getItem.equals("About Dinosaurs")){
            pdfviewer.fromAsset("ITS ALL ABOUT DINOSAURS.pdf").load();
        }
        if (getItem.equals("About Galaxies")){
            pdfviewer.fromAsset("About Galaxies.pdf").load();
        }
        if (getItem.equals("Body Parts")){
            pdfviewer.fromAsset("PARTS OF THE BODY.pdf").load();
        }
        if (getItem.equals("Weather")){
            pdfviewer.fromAsset("Weather.pdf").load();
        }
        if (getItem.equals("Solar System")){
            pdfviewer.fromAsset("Solar System.pdf").load();
        }

    }

    public void BacktoPDF(View view) {
        startActivity(new Intent(this, SciencePDF.class));
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, SciencePDF.class));
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
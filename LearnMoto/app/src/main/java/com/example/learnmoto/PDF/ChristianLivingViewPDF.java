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

public class ChristianLivingViewPDF extends AppCompatActivity {

    PDFView pdfviewer;
    NetworkChangeListener networkChangeListener = new NetworkChangeListener();

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
            pdfviewer.fromAsset("FaithPrep.pdf").load();
        }
        if (getItem.equals("How to Pray")){
            pdfviewer.fromAsset("How to Pray.pdf").load();
        }
    }
    public void BacktoPDF(View view) {
        startActivity(new Intent(this, ChristianLivingPDF.class));
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, ChristianLivingPDF.class));
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
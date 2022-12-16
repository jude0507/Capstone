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

public class SibikaKulturaViewPDF extends AppCompatActivity {

    PDFView pdfviewer;
    NetworkChangeListener networkChangeListener = new NetworkChangeListener();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sibika_kultura_view_pdf);

        pdfviewer = findViewById(R.id.pdfView);
        String getItem = getIntent().getStringExtra("SibikaKulturaPDF");

        if (getItem.equals("Anyong Tubig")){
            pdfviewer.fromAsset("Anyong Tubig.pdf").load();
        }
        if (getItem.equals("Anyong Lupa")){
            pdfviewer.fromAsset("Anyong Lupa.pdf").load();
        }
    }
    public void BacktoPDF(View view) {
        startActivity(new Intent(this, SibikaKulturaPDF.class));
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
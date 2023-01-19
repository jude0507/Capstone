package com.example.learnmoto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;

import com.example.learnmoto.CheckConnection.NetworkChangeListener;
import com.example.learnmoto.Kinder.Filipino.KinderFilipinoQuiz;
import com.example.learnmoto.Preparatory.Filipino.PreparatoryFilipinoQuiz;
import com.example.learnmoto.Student.StudentHomeView;
import com.google.firebase.firestore.FirebaseFirestore;

public class KinderFilipinoActivity extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    NetworkChangeListener networkChangeListener = new NetworkChangeListener();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kinder_filipino);
    }
    public void btnStart(View view) {
        startActivity(new Intent(getApplicationContext(),KinderFilStart.class));
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

    @Override
    public void onBackPressed() {
        if(StudentHomeView.level.equals("Preparatory")){
            startActivity(new Intent(getApplicationContext(), PreparatoryFilipinoQuiz.class));
        }else if(StudentHomeView.level.equals("Kinder")){
            startActivity(new Intent(getApplicationContext(), KinderFilipinoQuiz.class));
        }
    }
}
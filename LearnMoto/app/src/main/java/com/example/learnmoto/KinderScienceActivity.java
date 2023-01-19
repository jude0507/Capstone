package com.example.learnmoto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;

import com.example.learnmoto.CheckConnection.NetworkChangeListener;
import com.example.learnmoto.Kinder.English.KinderEnglishQuiz;
import com.example.learnmoto.Kinder.Filipino.KinderFilipinoQuiz;
import com.example.learnmoto.Kinder.Science.KinderScienceQuiz;
import com.example.learnmoto.Preparatory.English.PreparatoryEnglish;
import com.example.learnmoto.Preparatory.English.PreparatoryEnglishQuiz;
import com.example.learnmoto.Preparatory.Filipino.PreparatoryFilipinoQuiz;
import com.example.learnmoto.Preparatory.Science.PreparatoryScienceQuiz;
import com.example.learnmoto.Student.StudentHomeView;
import com.google.firebase.firestore.FirebaseFirestore;

public class KinderScienceActivity extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    NetworkChangeListener networkChangeListener = new NetworkChangeListener();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kinder_science_acitivity);
    }
    public void btnStart(View view) {
        startActivity(new Intent(getApplicationContext(),KinderSciStart.class));
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
            startActivity(new Intent(getApplicationContext(), PreparatoryScienceQuiz.class));
        }else if(StudentHomeView.level.equals("Kinder")){
            startActivity(new Intent(getApplicationContext(), KinderScienceQuiz.class));
        }
    }
}
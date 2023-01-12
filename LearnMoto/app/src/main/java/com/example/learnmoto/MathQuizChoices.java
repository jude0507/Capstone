package com.example.learnmoto;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.learnmoto.CheckConnection.NetworkChangeListener;
import com.example.learnmoto.Kinder.Math.KinderMathQuiz;
import com.example.learnmoto.Nursery.Math.NurseryMathQuiz;
import com.example.learnmoto.Preparatory.Math.PreparatoryMathQuiz;
import com.example.learnmoto.Student.StudentHomeView;
import com.google.firebase.firestore.FirebaseFirestore;

public class MathQuizChoices extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    NetworkChangeListener networkChangeListener = new NetworkChangeListener();
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_math_quiz_choices);
    }

    public void additionBtn(View view) {
        Intent intent= new Intent(getApplicationContext(),MathAddition.class);
        startActivity(intent);
    }

    public void subtractionBtn(View view) {
        Intent intent= new Intent(getApplicationContext(),MathSubtraction.class);
        startActivity(intent);

    }

    @Override
    public void onBackPressed() {
        if(StudentHomeView.level.equals("Nursery")){
            startActivity(new Intent(getApplicationContext(), NurseryMathQuiz.class));
        }else if(StudentHomeView.level.equals("Kinder")){
            startActivity(new Intent(getApplicationContext(), KinderMathQuiz.class));
        }else {
            startActivity(new Intent(getApplicationContext(), PreparatoryMathQuiz.class));
        }

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
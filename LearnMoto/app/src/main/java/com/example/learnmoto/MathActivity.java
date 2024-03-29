package com.example.learnmoto;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.learnmoto.Adapter.MathQuizAdapter;
import com.example.learnmoto.CheckConnection.NetworkChangeListener;
import com.example.learnmoto.Kinder.Math.KinderMathQuiz;
import com.example.learnmoto.Model.MathModel;
import com.example.learnmoto.Nursery.Math.NurseryMathQuiz;
import com.example.learnmoto.Preparatory.Math.PreparatoryMathQuiz;
import com.example.learnmoto.Student.StudentHomeView;
import com.example.learnmoto.Teacher.TeacherView;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Locale;

public class MathActivity extends AppCompatActivity {

    RecyclerView mathRecycler;
    MathQuizAdapter mathQuizAdapter;
    ArrayList<MathModel> mathModelArrayList;
    int val1, val2;
    int output;
    int[] answers;
    Button Submit;
    public static TextToSpeech textToSpeech;
    public static int score;
    public static AlertDialog.Builder builder;
    String mathscore;
    AlertDialog.Builder mybuilder;
    AlertDialog alertDialog;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    NetworkChangeListener networkChangeListener = new NetworkChangeListener();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_math);

        mathRecycler = findViewById(R.id.mathRecycler);
        Submit = findViewById(R.id.Submit);

        score = 0;

        builder = new AlertDialog.Builder(this);

        mathModelArrayList = new ArrayList<>();
        mathQuizAdapter = new MathQuizAdapter(MathActivity.this, mathModelArrayList);
        mathRecycler.setAdapter(mathQuizAdapter);

        textToSpeech();

        answers = new int[]{2, 10, 7, 11, 5};

        for (int i = 0; i < answers.length; i++) {
            AddDices(i, 2, R.drawable.dice1, R.drawable.dice2);
            AddDices(i, 10, R.drawable.dice4, R.drawable.dice6);
            AddDices(i, 7, R.drawable.dice3, R.drawable.dice4);
            AddDices(i, 11, R.drawable.dice5, R.drawable.dice6);
            AddDices(i, 5, R.drawable.dice4, R.drawable.dice1);
            mathModelArrayList.add(new MathModel(val1, "+", val2));
        }

        mathQuizAdapter.notifyDataSetChanged();

        Submit.setOnClickListener(v -> {

            mathscore = String.valueOf(score);
            DocumentReference documentReference = db.collection("Student").document(StudentHomeView.userID);
            documentReference.update("mathScoreAct", mathscore);
            textToSpeech.speak("Your final score is " + mathscore + "over five",TextToSpeech.QUEUE_ADD, null);
            Toast.makeText(v.getContext(), "Score has been saved", Toast.LENGTH_SHORT).show();
            if (StudentHomeView.level.equals("Kinder")){
                startActivity(new Intent(v.getContext(), KinderMathQuiz.class));
            }else if (StudentHomeView.level.equals("Nursery")){
                startActivity(new Intent(v.getContext(), NurseryMathQuiz.class));
            }else{
                startActivity(new Intent(v.getContext(), PreparatoryMathQuiz.class));
            }


        });

    }

    private void AddDices(int i , int val, int dice1, int dice2){
        if (answers[i] == val){
            val1 = dice1;
            val2 = dice2;
        }
    }

    private void textToSpeech() {
        textToSpeech = new TextToSpeech(this, i -> {
            if (i == TextToSpeech.SUCCESS) {
                output = textToSpeech.setLanguage(Locale.ENGLISH);
            }
        });
    }

    public void BackToQuiz(View view) {
        BackPressed();
    }

    private void BackPressed(){
        if (StudentHomeView.level.equals("Kinder")){
            startActivity(new Intent(this, KinderMathQuiz.class));
            finish();
        }else if (StudentHomeView.level.equals("Nursery")){
            startActivity(new Intent(this, NurseryMathQuiz.class));
            finish();
        }else{
            startActivity(new Intent(this, PreparatoryMathQuiz.class));
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        BackPressed();
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
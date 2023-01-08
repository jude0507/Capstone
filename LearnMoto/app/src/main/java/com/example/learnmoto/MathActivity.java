package com.example.learnmoto;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;

import com.example.learnmoto.Adapter.MathQuizAdapter;
import com.example.learnmoto.Kinder.Math.KinderMathQuiz;
import com.example.learnmoto.Model.MathModel;
import com.example.learnmoto.Nursery.Math.NurseryMathQuiz;
import com.example.learnmoto.Preparatory.Math.PreparatoryMathQuiz;
import com.example.learnmoto.Student.StudentHomeView;

import java.util.ArrayList;
import java.util.Locale;

public class MathActivity extends AppCompatActivity {

    RecyclerView mathRecycler;
    MathQuizAdapter mathQuizAdapter;
    ArrayList<MathModel> mathModelArrayList;
    int outputDice;
    int val1, val2;
    int output;
    public static TextToSpeech textToSpeech;
    public static AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_math);

        mathRecycler = findViewById(R.id.mathRecycler);

        builder = new AlertDialog.Builder(this);

        mathModelArrayList = new ArrayList<>();
        mathQuizAdapter = new MathQuizAdapter(MathActivity.this, mathModelArrayList);
        mathRecycler.setAdapter(mathQuizAdapter);

        textToSpeech();

        int[] answers = {2, 10, 7, 11, 5, 8};

        for (int i = 0; i < answers.length; i++) {
            if (answers[i] == 2) {
                val1 = R.drawable.dice1;
                val2 = R.drawable.dice1;
                outputDice = answers[0];
            } else if (answers[i] == 10) {
                val1 = R.drawable.dice4;
                val2 = R.drawable.dice6;
                outputDice = answers[1];
            } else if (answers[i] == 7) {
                val1 = R.drawable.dice5;
                val2 = R.drawable.dice2;
                outputDice = answers[2];
            } else if (answers[i] == 11) {
                val1 = R.drawable.dice6;
                val2 = R.drawable.dice5;
                outputDice = answers[3];
            } else if (answers[i] == 5) {
                val1 = R.drawable.dice3;
                val2 = R.drawable.dice2;
                outputDice = answers[4];
            } else if (answers[i] == 8) {
                val1 = R.drawable.dice4;
                val2 = R.drawable.dice4;
                outputDice = answers[5];
            }
            mathModelArrayList.add(new MathModel(val1, "+", val2, outputDice));
        }
        mathQuizAdapter.notifyDataSetChanged();

    }

    private void textToSpeech() {
        textToSpeech = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                if (i == TextToSpeech.SUCCESS) {
                    output = textToSpeech.setLanguage(Locale.ENGLISH);
                }
            }
        });
    }

    public void BackToQuiz(View view) {
        if (StudentHomeView.level.equals("Kinder")){
            startActivity(new Intent(this, KinderMathQuiz.class));
        }else if (StudentHomeView.level.equals("Nursery")){
            startActivity(new Intent(this, NurseryMathQuiz.class));
        }else{
            startActivity(new Intent(this, PreparatoryMathQuiz.class));
        }

    }
}
package com.example.learnmoto;

import static com.example.learnmoto.MathActivity.textToSpeech;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;

import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import android.widget.TextView;
import android.widget.Toast;

import com.example.learnmoto.Kinder.English.KinderEnglishQuiz;
import com.example.learnmoto.Kinder.Math.KinderMathQuiz;
import com.example.learnmoto.Nursery.English.NurseryEnglishQuiz;

import com.example.learnmoto.Nursery.Math.NurseryMathQuiz;
import com.example.learnmoto.Preparatory.English.PreparatoryEnglishQuiz;

import com.example.learnmoto.Preparatory.Math.PreparatoryMathQuiz;
import com.example.learnmoto.Student.StudentHomeView;
import com.example.learnmoto.Student.StudentLogin;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Locale;

public class EnglishQuiz extends AppCompatActivity {

    private static final int REQUEST_CODE_SPEECH_INPUT = 1000;
    AlertDialog.Builder builder;
    TextView speak, shuffle, txtInput, txtRandom;
    Button btnsubmit;
    int index=0;
    int score = 0;
    int output;
    TextToSpeech textToSpeech;
    String[] arrayList = {"red", "fun", "joy", "sign", "color","leaves"
            ,"paper","bag","ball","bread","medal","paint","tape","shoes","slipper"};

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    SharedPreferences sharedPreferences;
    String id = StudentLogin.studID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_english_quiz);

        speak = findViewById(R.id.speak);
        shuffle = findViewById(R.id.shuffle);
        txtInput = findViewById(R.id.txtInput);
        btnsubmit = findViewById(R.id.Submit);
        txtRandom = findViewById(R.id.txtRandom);

        speak.setEnabled(false);
        txtInput.setEnabled(false);
        txtInput.setVisibility(View.INVISIBLE);
        speak.setVisibility(View.INVISIBLE);

       //sharedPreferences = getApplicationContext().getSharedPreferences("Preferences", MODE_PRIVATE);
        //id = sharedPreferences.getString(StudentLogin.studID, "");

        builder = new AlertDialog.Builder(this);

        textToSpeech = new TextToSpeech(this, i -> {
            if (i == TextToSpeech.SUCCESS) {
                output = textToSpeech.setLanguage(Locale.ENGLISH);
            }
        });

        speak.setOnClickListener(v -> {
            Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
            intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Hi Say Something");

            try {
                startActivityForResult(intent, REQUEST_CODE_SPEECH_INPUT);
            } catch (Exception e) {
                Toast.makeText(EnglishQuiz.this,"" +  e.getMessage(), Toast.LENGTH_SHORT).show();
            }

            shuffle.setClickable(true);

        });

        shuffle.setOnClickListener(v -> {
            onShuffle();
        });

        txtInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final EditText inputDialog = new EditText(EnglishQuiz.this);
                inputDialog.setHint("Enter your answer");
                inputDialog.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                AlertDialog alertDialog = builder.setView(inputDialog).setCancelable(true)
                        .setPositiveButton("Okay", (dialog, which) -> {
                        }).show();
                Button positiveButton = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                positiveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String inputmsg = inputDialog.getText().toString();
                        Log.d("TAG", "onClick: " + inputmsg);
                        Log.d("TAG", "onClick: " + txtRandom.getText().toString());
                        if (inputmsg.isEmpty() || inputmsg.equals("")){
                            inputDialog.setError("Required");
                        }else{
                            alertDialog.dismiss();
//                            speak.setEnabled(false);
//                            txtInput.setEnabled(false);
                            shuffle.setEnabled(true);

                            if (inputmsg.equals(txtRandom.getText().toString())){
                                score++;
                                textToSpeech.speak("Correct! Current Score is " + score, TextToSpeech.QUEUE_ADD, null);
                                onShuffle();
                            }else{
                                textToSpeech.speak("Incorrect answer", TextToSpeech.QUEUE_ADD, null);
                                onShuffle();
                            }
                        }
                    }
                });

            }
        });

        btnsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(EnglishQuiz.this, "Final Score: " + score, Toast.LENGTH_SHORT).show();
                String engscore = String.valueOf(score);
                Toast.makeText(EnglishQuiz.this, id, Toast.LENGTH_SHORT).show();
                DocumentReference documentReference = db.collection("Student").document(id);
                documentReference.update("engScore", engscore);
                textToSpeech.speak("Your final score is " + engscore + "over six",TextToSpeech.QUEUE_ADD, null);
                Toast.makeText(EnglishQuiz.this, "Score has been saved", Toast.LENGTH_SHORT).show();

                if (StudentHomeView.level.equals("Kinder")){
                    startActivity(new Intent(EnglishQuiz.this, KinderMathQuiz.class));
                }else if (StudentHomeView.level.equals("Nursery")){
                    startActivity(new Intent(EnglishQuiz.this, NurseryMathQuiz.class));
                }else{
                    startActivity(new Intent(EnglishQuiz.this, PreparatoryMathQuiz.class));
                }

            }
        });
    }

    //For voice input
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_SPEECH_INPUT) {
            if (resultCode == RESULT_OK && null != data) {
                ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                if (txtRandom.getText().equals(result.get(0))) {
                    score++;
                    textToSpeech.speak("Correct! Current Score is " + score, TextToSpeech.QUEUE_ADD, null);
                    onShuffle();
                } else {
                    speak.setClickable(false);
                    textToSpeech.speak("Incorrect answer", TextToSpeech.QUEUE_ADD, null);
                    onShuffle();
                }

            }
        }

    }

    private void onShuffle(){
        index++;
        speak.setClickable(true);
        speak.setEnabled(true);
        txtInput.setEnabled(true);
        txtInput.setVisibility(View.VISIBLE);
        speak.setVisibility(View.VISIBLE);
        if(index!=11){
            Toast.makeText(EnglishQuiz.this, index+" out of 10", Toast.LENGTH_SHORT).show();
            Collections.shuffle(Arrays.asList(arrayList));
            txtRandom.setText(arrayList[0]);
            Log.e("Array", arrayList[0]);
            //randomBtn.setBackgroundColor(Color.RED);
//            Toast.makeText(EnglishQuiz.this, "Press Shuffle to proceed", Toast.LENGTH_SHORT).show();
        }else{
            txtInput.setVisibility(View.INVISIBLE);
            speak.setVisibility(View.INVISIBLE);
            txtRandom.setText("You are Done, Thank You!");
            Toast.makeText(EnglishQuiz.this, "You are Done", Toast.LENGTH_SHORT).show();
            //randomBtn.setBackgroundColor(Color.RED);
            shuffle.setClickable(false);
            speak.setClickable(false);
        }
    }

    public void BackToQuiz(View view) {
        if (StudentHomeView.level.equals("Kinder")){
            startActivity(new Intent(this, KinderEnglishQuiz.class));
        }else if (StudentHomeView.level.equals("Nursery")){
            startActivity(new Intent(this, NurseryEnglishQuiz.class));
        }else{
            startActivity(new Intent(this, PreparatoryEnglishQuiz.class));
        }

    }

}
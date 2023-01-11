package com.example.learnmoto.Student;

import static android.content.ContentValues.TAG;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.learnmoto.LoadingActivity;
import com.example.learnmoto.MainActivity;
import com.example.learnmoto.R;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;

public class Alphabet extends AppCompatActivity {

    private static final int REQUEST_CODE_SPEECH = 1;
    private SpeechRecognizer speechRecognizer;
    String letters;
    AlertDialog.Builder builder;
    AlertDialog alertDialog;
    TextView output;
    ImageView mic;
    int outputSpeak;
    TextToSpeech textToSpeech;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alphabet);
        ImageView imageLetters = findViewById(R.id.imageLetter);
        mic = findViewById(R.id.mic);
        output = findViewById(R.id.resultSpeach);
        letters = getIntent().getStringExtra("alphabets").toLowerCase(Locale.ROOT);

        textToSpeech();

        int drawableLetter = getResources().getIdentifier(letters,"drawable",getPackageName());
        imageLetters.setImageResource(drawableLetter);

        int SoundLetter = getResources().getIdentifier(letters,"raw",getPackageName());
        MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(), SoundLetter);
        mediaPlayer.start();


    }

    private void textToSpeech() {
        textToSpeech = new TextToSpeech(this, i -> {
            if (i == TextToSpeech.SUCCESS) {
                outputSpeak = textToSpeech.setLanguage(Locale.ENGLISH);
            }
        });
    }
    
    private void checkLetters(){
        switch (letters){
            case "a":
                if (output.getText().toString().equalsIgnoreCase("apple")){
                    GreatJobDialog();
                    //Toast.makeText(this, "Excellent", Toast.LENGTH_SHORT).show();
                    textToSpeech.speak("Great Job", TextToSpeech.QUEUE_ADD,null);
                    Delay();
                }else{
                    Toast.makeText(this, "Keep Going! Good Try!", Toast.LENGTH_SHORT).show();
                    textToSpeech.speak("Keep Going! Good Try", TextToSpeech.QUEUE_ADD,null);
                }
                break;
            case "b":
                if (output.getText().toString().equalsIgnoreCase("bicycle")){
                    GreatJobDialog();
                    textToSpeech.speak("Great Job", TextToSpeech.QUEUE_ADD,null);
                    Delay();
                }else{
                    Toast.makeText(this, "Keep Going! Good Try!", Toast.LENGTH_SHORT).show();
                    textToSpeech.speak("Keep Going! Good Try", TextToSpeech.QUEUE_ADD,null);                }
                break;
            case "c":
                if (output.getText().toString().equalsIgnoreCase("car")){
                    GreatJobDialog();
                    textToSpeech.speak("Great Job", TextToSpeech.QUEUE_ADD,null);
                    Delay();
                }else{
                    Toast.makeText(this, "Keep Going! Good Try!", Toast.LENGTH_SHORT).show();
                    Delay();
                }
                break;
            case "d":
                if (output.getText().toString().equalsIgnoreCase("dog")){
                    GreatJobDialog();
                    textToSpeech.speak("Great Job", TextToSpeech.QUEUE_ADD,null);
                    Delay();
                }else{
                    Toast.makeText(this, "Keep Going! Good Try!", Toast.LENGTH_SHORT).show();
                }
                break;
            case "e":
                if (output.getText().toString().equalsIgnoreCase("eyes")){
                    GreatJobDialog();
                    textToSpeech.speak("Great Job", TextToSpeech.QUEUE_ADD,null);
                    Delay();
                }else{
                    Toast.makeText(this, "Keep Going! Good Try!", Toast.LENGTH_SHORT).show();
                }
                break;

            case "f":
                if (output.getText().toString().equalsIgnoreCase("frog")){
                    GreatJobDialog();
                    textToSpeech.speak("Great Job", TextToSpeech.QUEUE_ADD,null);
                    Delay();
                }else{
                    Toast.makeText(this, "Keep Going! Good Try!", Toast.LENGTH_SHORT).show();
                }
                break;

            case "g":
                if (output.getText().toString().equalsIgnoreCase("gizmo")){
                    GreatJobDialog();
                    textToSpeech.speak("Great Job", TextToSpeech.QUEUE_ADD,null);
                    Delay();
                }else{
                    Toast.makeText(this, "Keep Going! Good Try!", Toast.LENGTH_SHORT).show();
                }
                break;

            case "h":
                if (output.getText().toString().equalsIgnoreCase("horse")){
                    GreatJobDialog();
                    textToSpeech.speak("Great Job", TextToSpeech.QUEUE_ADD,null);
                    Delay();
                }else{
                    Toast.makeText(this, "Keep Going! Good Try!", Toast.LENGTH_SHORT).show();
                }
                break;

            case "i":
                if (output.getText().toString().equalsIgnoreCase("ice cream")){
                    GreatJobDialog();
                    textToSpeech.speak("Great Job", TextToSpeech.QUEUE_ADD,null);
                    Delay();
                }else{
                    Toast.makeText(this, "Keep Going! Good Try!", Toast.LENGTH_SHORT).show();
                }
                break;

            case "j":
                if (output.getText().toString().equalsIgnoreCase("jeepney")){
                    GreatJobDialog();
                    textToSpeech.speak("Great Job", TextToSpeech.QUEUE_ADD,null);
                    Delay();
                }else{
                    Toast.makeText(this, "Keep Going! Good Try!", Toast.LENGTH_SHORT).show();
                }
                break;

            case "k":
                if (output.getText().toString().equalsIgnoreCase("kangaroo")){
                    GreatJobDialog();
                    textToSpeech.speak("Great Job", TextToSpeech.QUEUE_ADD,null);
                    Delay();
                }else{
                    Toast.makeText(this, "Keep Going! Good Try!", Toast.LENGTH_SHORT).show();
                    textToSpeech.speak("Keep Going! Good Try", TextToSpeech.QUEUE_ADD,null);                }
                break;

            case "l":
                if (output.getText().toString().equalsIgnoreCase("lion")){
                    GreatJobDialog();
                    textToSpeech.speak("Great Job", TextToSpeech.QUEUE_ADD,null);
                    Delay();
                }else{
                    Toast.makeText(this, "Keep Going! Good Try!", Toast.LENGTH_SHORT).show();
                    textToSpeech.speak("Keep Going! Good Try", TextToSpeech.QUEUE_ADD,null);                }
                break;

            case "m":
                if (output.getText().toString().equalsIgnoreCase("moon")){
                    GreatJobDialog();
                    textToSpeech.speak("Great Job", TextToSpeech.QUEUE_ADD,null);
                    Delay();
                }else{
                    Toast.makeText(this, "Keep Going! Good Try!", Toast.LENGTH_SHORT).show();
                    textToSpeech.speak("Keep Going! Good Try", TextToSpeech.QUEUE_ADD,null);                }
                break;

            case "n":
                if (output.getText().toString().equalsIgnoreCase("nose")){
                    GreatJobDialog();
                    textToSpeech.speak("Great Job", TextToSpeech.QUEUE_ADD,null);
                    Delay();
                }else{
                    Toast.makeText(this, "Keep Going! Good Try!", Toast.LENGTH_SHORT).show();
                    textToSpeech.speak("Keep Going! Good Try", TextToSpeech.QUEUE_ADD,null);                }
                break;


            case "o":
                if (output.getText().toString().equalsIgnoreCase("orange")){
                    GreatJobDialog();
                    textToSpeech.speak("Great Job", TextToSpeech.QUEUE_ADD,null);
                    Delay();
                }else{
                    Toast.makeText(this, "Keep Going! Good Try!", Toast.LENGTH_SHORT).show();
                    textToSpeech.speak("Keep Going! Good Try", TextToSpeech.QUEUE_ADD,null);                }
                break;

            case "p":
                if (output.getText().toString().equalsIgnoreCase("pancake") ||
                        output.getText().toString().equalsIgnoreCase("pancakes")){
                    GreatJobDialog();
                    textToSpeech.speak("Great Job", TextToSpeech.QUEUE_ADD,null);
                    Delay();
                }else{
                    Toast.makeText(this, "Keep Going! Good Try!", Toast.LENGTH_SHORT).show();
                    textToSpeech.speak("Keep Going! Good Try", TextToSpeech.QUEUE_ADD,null);                }
                break;

            case "q":
                if (output.getText().toString().equalsIgnoreCase("queen")){
                    GreatJobDialog();
                    textToSpeech.speak("Great Job", TextToSpeech.QUEUE_ADD,null);
                    Delay();
                }else{
                    Toast.makeText(this, "Keep Going! Good Try!", Toast.LENGTH_SHORT).show();
                    textToSpeech.speak("Keep Going! Good Try", TextToSpeech.QUEUE_ADD,null);                }
                break;

            case "r":
                if (output.getText().toString().equalsIgnoreCase("rabbit")){
                    GreatJobDialog();
                    textToSpeech.speak("Great Job", TextToSpeech.QUEUE_ADD,null);
                    Delay();
                }else{
                    Toast.makeText(this, "Keep Going! Good Try!", Toast.LENGTH_SHORT).show();
                    textToSpeech.speak("Keep Going! Good Try", TextToSpeech.QUEUE_ADD,null);                }
                break;

            case "s":
                if (output.getText().toString().equalsIgnoreCase("spoon")){
                    GreatJobDialog();
                    textToSpeech.speak("Great Job", TextToSpeech.QUEUE_ADD,null);
                    Delay();
                }else{
                    Toast.makeText(this, "Keep Going! Good Try!", Toast.LENGTH_SHORT).show();
                    textToSpeech.speak("Keep Going! Good Try", TextToSpeech.QUEUE_ADD,null);                }
                break;

            case "t":
                if (output.getText().toString().equalsIgnoreCase("tongue")){
                    GreatJobDialog();
                    textToSpeech.speak("Great Job", TextToSpeech.QUEUE_ADD,null);
                    Delay();
                }else{
                    Toast.makeText(this, "Keep Going! Good Try!", Toast.LENGTH_SHORT).show();
                    textToSpeech.speak("Keep Going! Good Try", TextToSpeech.QUEUE_ADD,null);                }
                break;

            case "u":
                if (output.getText().toString().equalsIgnoreCase("umbrella")){
                    GreatJobDialog();
                    textToSpeech.speak("Great Job", TextToSpeech.QUEUE_ADD,null);
                    Delay();
                }else{
                    Toast.makeText(this, "Keep Going! Good Try!", Toast.LENGTH_SHORT).show();
                    textToSpeech.speak("Keep Going! Good Try", TextToSpeech.QUEUE_ADD,null);                }
                break;
            case "v":
                if (output.getText().toString().equalsIgnoreCase("violin")){
                    GreatJobDialog();
                    textToSpeech.speak("Great Job", TextToSpeech.QUEUE_ADD,null);
                    Delay();
                }else{
                    Toast.makeText(this, "Keep Going! Good Try!", Toast.LENGTH_SHORT).show();
                    textToSpeech.speak("Keep Going! Good Try", TextToSpeech.QUEUE_ADD,null);                }
                break;

            case "w":
                if (output.getText().toString().equalsIgnoreCase("window")){
                    GreatJobDialog();
                    textToSpeech.speak("Great Job", TextToSpeech.QUEUE_ADD,null);
                    Delay();
                }else{
                    Toast.makeText(this, "Keep Going! Good Try!", Toast.LENGTH_SHORT).show();
                    textToSpeech.speak("Keep Going! Good Try", TextToSpeech.QUEUE_ADD,null);                }
                break;
            case "x":
                if (output.getText().toString().equalsIgnoreCase("x-ray")){
                    GreatJobDialog();
                    textToSpeech.speak("Great Job", TextToSpeech.QUEUE_ADD,null);
                    Delay();
                }else{
                    Toast.makeText(this, "Keep Going! Good Try!", Toast.LENGTH_SHORT).show();
                    textToSpeech.speak("Keep Going! Good Try", TextToSpeech.QUEUE_ADD,null);                }
                break;

            case "y":
                if (output.getText().toString().equalsIgnoreCase("yarn")){
                    GreatJobDialog();
                    textToSpeech.speak("Great Job", TextToSpeech.QUEUE_ADD,null);
                    Delay();
                }else{
                    Toast.makeText(this, "Keep Going! Good Try!", Toast.LENGTH_SHORT).show();
                    textToSpeech.speak("Keep Going! Good Try", TextToSpeech.QUEUE_ADD,null);                }
                break;
            case "z":
                if (output.getText().toString().equalsIgnoreCase("zebra")){
                    GreatJobDialog();
                    textToSpeech.speak("Great Job", TextToSpeech.QUEUE_ADD,null);
                    Delay();
                }else{
                    Toast.makeText(this, "Keep Going! Good Try!", Toast.LENGTH_SHORT).show();
                    textToSpeech.speak("Keep Going! Good Try", TextToSpeech.QUEUE_ADD,null);                }
                break;
        }
    }

    private void GreatJobDialog(){
        builder = new AlertDialog.Builder(Alphabet.this);
        final View view = getLayoutInflater().inflate(R.layout.word_matched_dialog,null);
        builder.setView(view);
        alertDialog = builder.create();
        alertDialog.show();
    }

    private void Delay(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(Alphabet.this, PronounceAlphabet.class));
                finish();
            }
        }, 2000);
    }


    public void PronounceAlphabet(View view) {
        startActivity(new Intent(this, PronounceAlphabet.class));
    }

    public void PronounceAgain(View view) {
        int SoundLetter = getResources().getIdentifier(letters,"raw",getPackageName());
        MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(), SoundLetter);
        mediaPlayer.start();
    }

    public void Speak(View view) {

        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak Now");

        try{
            startActivityForResult(intent, REQUEST_CODE_SPEECH);
        }catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            output.setText(e.getMessage());
            Log.e(TAG, e.getMessage());
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_SPEECH) {
            if (resultCode == RESULT_OK && data != null) {
                ArrayList<String> result = data.getStringArrayListExtra(
                        RecognizerIntent.EXTRA_RESULTS);
                output.setText(
                        Objects.requireNonNull(result).get(0));
                checkLetters();
            }
        }

    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, PronounceAlphabet.class));
    }
}
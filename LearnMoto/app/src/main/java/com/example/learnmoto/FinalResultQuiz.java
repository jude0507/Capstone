package com.example.learnmoto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.learnmoto.Kinder.Filipino.KinderFilipinoQuiz;
import com.example.learnmoto.Preparatory.Filipino.PreparatoryFilipinoQuiz;
import com.example.learnmoto.Student.StudentHomeView;

public class FinalResultQuiz extends AppCompatActivity {
    TextView finalScore,statusView;
    MediaPlayer mediaPlayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_result_quiz);
        finalScore = findViewById(R.id.finalScore);
        statusView = findViewById(R.id.statusView);

        Intent intent= new Intent();
        int finalScores = Integer.parseInt(intent.getStringExtra("score"));

        if(finalScores>=5){
            finalScore.setText(finalScores);
            statusView.setText("Passed");
            statusView.setTextColor(Color.GREEN);
            mediaPlayer= MediaPlayer.create(getApplicationContext(),R.raw.yehey);
            mediaPlayer.setLooping(false);
            mediaPlayer.start();
        }else{
            finalScore.setText(finalScores);
            statusView.setText("Failed");
            statusView.setTextColor(Color.RED);
        }

    }

    public void exitBtn(View view) {
        exitActivity();
    }

    @Override
    public void onBackPressed() {
        exitActivity();
    }

    private void exitActivity() {
        if(StudentHomeView.level.equals("Preparatory")){
            startActivity(new Intent(getApplicationContext(), StudentHomeView.class));
        }else if(StudentHomeView.level.equals("Kinder")){
            startActivity(new Intent(getApplicationContext(), StudentHomeView.class));
        }
    }


}
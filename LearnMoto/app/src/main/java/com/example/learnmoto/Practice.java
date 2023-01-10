package com.example.learnmoto;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class Practice extends AppCompatActivity{

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practice);

        textView = findViewById(R.id.clock);

        long duration = TimeUnit.MINUTES.toMillis(2);

        new CountDownTimer(duration, 1000){

            @Override
            public void onTick(long millisUntilFinished) {
                String sDuration = String.format(Locale.ENGLISH, "%02d : %02d",
                        TimeUnit.MILLISECONDS.toMinutes(1),
                        TimeUnit.MILLISECONDS.toSeconds(1) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(1)));

                textView.setText(sDuration);
            }

            @Override
            public void onFinish() {

                Toast.makeText(Practice.this, "Done", Toast.LENGTH_SHORT).show();

            }
        }.start();



    }
}
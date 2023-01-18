package com.example.learnmoto;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.learnmoto.CheckConnection.NetworkChangeListener;
import com.example.learnmoto.Nursery.Math.NurseryMathQuiz;
import com.example.learnmoto.Student.StudentHomeView;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;

public class MathSubtraction extends AppCompatActivity {
    private MediaPlayer mediaPlayer;
    Button startBtn;
    TextView TimeTextView;
    TextView ScoreTextView;
    TextView AlertTextView;
    TextView QuestionTextView;
    TextView FinalScoreTextView;
    Button button0;
    Button button1;
    Button button2;
    Button button3;
    CountDownTimer countDownTimer;
    ConstraintLayout constraintLayout;
    ConstraintLayout lastLayout;
    Button buttonPlayAgain;
    AlertDialog.Builder builder;
    AlertDialog alertDialog;
    TextToSpeech textToSpeech;
    int speakOutput;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    NetworkChangeListener networkChangeListener = new NetworkChangeListener();


    Random random = new Random();
    int a;
    int b;
    int indexOfCorrectAnswer;
    ArrayList<Integer> answers = new ArrayList<Integer>();
    int points = 0;
    int totalQuestions = 0;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_math_subtraction);
        startBtn = (Button) findViewById(R.id.btnStart);
        TimeTextView = findViewById(R.id.TimeTextView);
        ScoreTextView = findViewById(R.id.ScoreTextView);
        FinalScoreTextView = findViewById(R.id.FinalscoreTextView);
        AlertTextView = findViewById(R.id.AlertTextView);
        QuestionTextView = findViewById(R.id.QuestionTextView);
        button0 = findViewById(R.id.button0);
        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);
        buttonPlayAgain = findViewById(R.id.buttonPlayAgain);
        constraintLayout = findViewById(R.id.quizUi);
        lastLayout = findViewById(R.id.lastUi);
        lastLayout.setVisibility(View.INVISIBLE);
        constraintLayout.setVisibility(View.INVISIBLE);


        GoodJob();
    }

    @SuppressLint("SetTextI18n")
    public void NextQuestion() {
        a = random.nextInt(6);
        b = random.nextInt(6);
        QuestionTextView.setText(a + "-" + b);
        indexOfCorrectAnswer = random.nextInt(4);
        int answer = Math.abs(a - b);
        answers.clear();
        for (int i = 0; i < 4; i++) {

            if (indexOfCorrectAnswer == i) {

                answers.add(answer);

            } else {
                int wrongAnswer = random.nextInt(12);
                while (wrongAnswer == answer) {

                    wrongAnswer = random.nextInt(12);
                }
                answers.add(wrongAnswer);
            }
        }

        button0.setText(Integer.toString(answers.get(0)));
        button1.setText(Integer.toString(answers.get(1)));
        button2.setText(Integer.toString(answers.get(2)));
        button3.setText(Integer.toString(answers.get(3)));

    }

    public void optionSelect(View view) {
        if (Integer.toString(indexOfCorrectAnswer).equals(view.getTag().toString())) {
            textToSpeech.speak("Great Job Kid!", TextToSpeech.QUEUE_ADD, null);
            Dialog();
            points++;
            ScoreTextView.setText(Integer.toString(points) + "/10");
            AlertTextView.setText("Correct");

        } else {
            AlertTextView.setText("Wrong");
            textToSpeech.speak("Good Try Kid!", TextToSpeech.QUEUE_ADD, null);
            incorrectDialog();
        }
        if (++totalQuestions < 10) {
            NextQuestion();
        } else {
            endGame();
        }


    }

    public void endGame() {
        TimeTextView.setText("Time Up!");
        FinalScoreTextView.setText(Integer.toString(points) + "/10");
        constraintLayout.setVisibility(View.INVISIBLE);
        lastLayout.setVisibility(View.VISIBLE);
        if(points>=5){
            mediaPlayer= MediaPlayer.create(getApplicationContext(),R.raw.yehey);
            mediaPlayer.setLooping(false);
            mediaPlayer.start();
        }
    }

    public void playAgain(View view) {
        points = 0;
        totalQuestions = 0;
        ScoreTextView.setText(Integer.toString(points) + "/10");
        countDownTimer.start();
        lastLayout.setVisibility(View.INVISIBLE);
        constraintLayout.setVisibility(View.VISIBLE);

    }


    public void start(View view) {
        startBtn.setVisibility(View.INVISIBLE);
        constraintLayout.setVisibility(View.VISIBLE);
        NextQuestion();
        countDownTimer = new CountDownTimer(300000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                int minutes = (int) (millisUntilFinished / 1000) / 60;
                int seconds = (int) (millisUntilFinished / 1000) % 60;
                String sDuration = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
                TimeTextView.setText(sDuration);
            }

            @Override
            public void onFinish() {
                TimeTextView.setText("Time Up!");
                FinalScoreTextView.setText(Integer.toString(points) + "/10");
                constraintLayout.setVisibility(View.INVISIBLE);
                lastLayout.setVisibility(View.VISIBLE);

            }
        }.start();


    }

    private void Dialog() {
        builder = new AlertDialog.Builder(MathSubtraction.this);
        final View view = getLayoutInflater().inflate(R.layout.dialog_model, null);
        builder.setView(view);
        alertDialog = builder.create();
        alertDialog.show();
        alertDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                alertDialog.dismiss();
            }
        }, 2000);
    }

    private void GoodJob() {
        textToSpeech = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                if (i == TextToSpeech.SUCCESS) {
                    speakOutput = textToSpeech.setLanguage(Locale.CANADA);
                }
            }
        });
    }

    private void incorrectDialog() {
        builder = new AlertDialog.Builder(MathSubtraction.this);
        final View view = getLayoutInflater().inflate(R.layout.incorrect_model, null);
        builder.setView(view);
        alertDialog = builder.create();
        alertDialog.show();
        alertDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                alertDialog.dismiss();
            }
        }, 2000);
    }


    public void closeBtn(View view) {
        closeChoices();
    }

    private void closeChoices() {
        //Toast.makeText(this, StudentLogin.studID, Toast.LENGTH_SHORT).show();
        DocumentReference documentReference = db.collection("Student").document(StudentHomeView.userID);
        documentReference.update("mathScore", String.valueOf(points));
        textToSpeech.speak("Your final score is " + points + "over ten",TextToSpeech.QUEUE_ADD, null);
        Toast.makeText(this, "Score has been saved", Toast.LENGTH_SHORT).show();
        if(StudentHomeView.level.equals("Nursery")){
            startActivity(new Intent(getApplicationContext(), NurseryMathQuiz.class));
        }else{
            startActivity(new Intent(getApplicationContext(), MathQuizChoices.class));
        }
    }

    @Override
    public void onBackPressed() {
        if(StudentHomeView.level.equals("Nursery")){
            startActivity(new Intent(getApplicationContext(), NurseryMathQuiz.class));
        }else{
            startActivity(new Intent(getApplicationContext(), MathQuizChoices.class));
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
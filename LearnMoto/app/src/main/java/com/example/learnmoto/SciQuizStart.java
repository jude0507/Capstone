package com.example.learnmoto;

import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.learnmoto.CheckConnection.NetworkChangeListener;
import com.example.learnmoto.Kinder.Science.KinderScienceQuiz;
import com.example.learnmoto.Model.QuizzesModel;
import com.example.learnmoto.Nursery.Science.NurseryScienceQuiz;
import com.example.learnmoto.Preparatory.Science.PreparatoryScienceQuiz;
import com.example.learnmoto.Student.StudentHomeView;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SciQuizStart extends AppCompatActivity {
    private MediaPlayer mediaPlayer;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    NetworkChangeListener networkChangeListener = new NetworkChangeListener();
    private List<QuizzesModel> quizzesModelList;
    private TextView questionView, scoreView, questionnaireView, timeView;
    private RadioGroup radioGroup;
    private RadioButton rb1, rb2, rb3, rb4;
    private Button nextBtn;
    int totalQuestions;
    int qCounter = 0;
    int score = 0;
    ColorStateList dfRbColor;
    boolean answered;
    private QuizzesModel currentQuestion;
    CountDownTimer countDownTimer;
    int outputSpeak;
    TextToSpeech textToSpeech;
    AlertDialog.Builder builder;
    AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sci_quiz_start);
        questionnaireView = findViewById(R.id.questionnaireView);
        questionView = findViewById(R.id.questionView);
        scoreView = findViewById(R.id.scoreView);
        timeView = findViewById(R.id.timeView);
        radioGroup = findViewById(R.id.radioGroup);
        rb1 = findViewById(R.id.ansA);
        rb2 = findViewById(R.id.ansB);
        rb3 = findViewById(R.id.ansC);
        rb4 = findViewById(R.id.ansD);
        nextBtn = findViewById(R.id.nextBtn);
        dfRbColor = rb1.getTextColors();


        textSpeak();


        quizzesModelList = new ArrayList<>();

        addQuestions();
        totalQuestions = quizzesModelList.size();
        showNextQuestions();

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (answered == false) {
                    if (rb1.isChecked() || rb2.isChecked() || rb3.isChecked() || rb4.isChecked()) {
                        checkAnswer();
                        countDownTimer.cancel();
                    } else {
                        Toast.makeText(SciQuizStart.this, "Please Select an option", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    showNextQuestions();
                }
            }


        });


    }

    private void textSpeak() {
        textToSpeech = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                if (i == TextToSpeech.SUCCESS) {
                    outputSpeak = textToSpeech.setLanguage(Locale.getDefault());
                }
            }
        });
    }

    private void checkAnswer() {
        answered = true;
        RadioButton rbSelected = findViewById(radioGroup.getCheckedRadioButtonId());
        int answerNo = radioGroup.indexOfChild(rbSelected) + 1;
        if (answerNo == currentQuestion.getCorrectAnsNo()) {
            score++;
            scoreView.setText("Score: " + score);
            textToSpeech.speak("Good Job Kid!", TextToSpeech.QUEUE_ADD, null);
            correctDialog();
        } else {
            textToSpeech.speak("Good try Kid!", TextToSpeech.QUEUE_ADD, null);
            incorrectDialog();
        }
        rb1.setTextColor(Color.RED);
        rb2.setTextColor(Color.RED);
        rb3.setTextColor(Color.RED);
        rb4.setTextColor(Color.RED);
        switch (currentQuestion.getCorrectAnsNo()) {
            case 1:
                rb1.setTextColor(Color.GREEN);
                break;
            case 2:
                rb2.setTextColor(Color.GREEN);
                break;
            case 3:
                rb3.setTextColor(Color.GREEN);
                break;
            case 4:
                rb4.setTextColor(Color.GREEN);
                break;
        }

        if (qCounter < totalQuestions) {
            nextBtn.setText("Next");
        } else {
            String sciscore = String.valueOf(score);
            //Toast.makeText(this, StudentLogin.studID, Toast.LENGTH_SHORT).show();
            DocumentReference documentReference = db.collection("Student").document(StudentHomeView.userID);
            documentReference.update("scienceScore", sciscore);
            textToSpeech.speak("Your final score is " + sciscore + "over 5", TextToSpeech.QUEUE_ADD, null);
            Toast.makeText(this, "Score has been saved", Toast.LENGTH_SHORT).show();

            if (StudentHomeView.level.equals("Preperatory")) {
                startActivity(new Intent(this, PreparatoryScienceQuiz.class));
            } else if (StudentHomeView.level.equals("Kinder")) {
                startActivity(new Intent(this, KinderScienceQuiz.class));
            } else {
                startActivity(new Intent(this, NurseryScienceQuiz.class));
            }
            mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.yehey);
            mediaPlayer.setLooping(false);
            mediaPlayer.start();
            nextBtn.setText("Finish");

        }

    }

    private void incorrectDialog() {
        builder = new AlertDialog.Builder(SciQuizStart.this);
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

    private void correctDialog() {
        builder = new AlertDialog.Builder(SciQuizStart.this);
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

    private void showNextQuestions() {
        radioGroup.clearCheck();
        rb1.setTextColor(dfRbColor);
        rb2.setTextColor(dfRbColor);
        rb3.setTextColor(dfRbColor);
        rb4.setTextColor(dfRbColor);
        if (qCounter < totalQuestions) {
            timer();
            currentQuestion = quizzesModelList.get(qCounter);
            questionnaireView.setText(currentQuestion.getQuestion());
            rb1.setText(currentQuestion.getAnsA());
            rb2.setText(currentQuestion.getAnsB());
            rb3.setText(currentQuestion.getAnsC());
            rb4.setText(currentQuestion.getAnsD());

            qCounter++;
            nextBtn.setText("Submit");
            questionView.setText("Question: " + qCounter + "/" + totalQuestions);
            answered = false;
        } else {
            finish();
        }
    }

    private void timer() {
        countDownTimer = new CountDownTimer(30000, 1000) {
            @Override
            public void onTick(long l) {
                timeView.setText("00:" + l / 1000);

            }

            @Override
            public void onFinish() {
                showNextQuestions();
            }
        }.start();
    }

    private void addQuestions() {
        quizzesModelList.add(new QuizzesModel("What is the hottest star in our solar system","Earth","Moon","Sun","Star",4));
        quizzesModelList.add(new QuizzesModel("What is the 3rd planet in our solar system","Mercury","Venus","Earth","Mars",3));
        quizzesModelList.add(new QuizzesModel("What is the 2nd planet in our Solar system.","Mercury","Venus","Earth","Mars",2));
        quizzesModelList.add(new QuizzesModel("What is the 4th planet in our solar system","Mercury","Venus","Earth","Mars",4));
        quizzesModelList.add(new QuizzesModel("What is the hottest planet in our solar system","Mercury","Venus","Earth","Mars",1));
        quizzesModelList.add(new QuizzesModel("What is the 7th planet in our solar system?","Mars","Saturn","Earth","Uranus",4));
        quizzesModelList.add(new QuizzesModel("What is the type of galaxy that forms when two galaxies are collided?","Seedling","Saturn","Milky way","Galaxy merger",4));
        quizzesModelList.add(new QuizzesModel("What is the 5th planet in our solar system?","Jupiter","Mars","Earth","Venus",1));
        quizzesModelList.add(new QuizzesModel("What part of our solar system that gives light during daylight?","Moonlight","Flashlight","Sun"," Moon",4));
        quizzesModelList.add(new QuizzesModel("What body part that has a sense of sight?","Eye","Lips","Teeth","Nose",1));
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
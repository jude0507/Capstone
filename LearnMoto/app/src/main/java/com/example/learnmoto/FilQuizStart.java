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
import com.example.learnmoto.Kinder.Filipino.KinderFilipinoQuiz;
import com.example.learnmoto.Model.QuizzesModel;
import com.example.learnmoto.Preparatory.Filipino.PreparatoryFilipinoQuiz;
import com.example.learnmoto.Student.StudentHomeView;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class FilQuizStart extends AppCompatActivity {
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
        setContentView(R.layout.activity_fil_quiz_start);
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
                        Toast.makeText(FilQuizStart.this, "Please Select an option", Toast.LENGTH_SHORT).show();
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



            String filscore = String.valueOf(score);
            //Toast.makeText(this, StudentLogin.studID, Toast.LENGTH_SHORT).show();
            DocumentReference documentReference = db.collection("Student").document(StudentHomeView.userID);
            documentReference.update("filipinoScore", filscore);
            textToSpeech.speak("Your final score is " + filscore + "over 10", TextToSpeech.QUEUE_ADD, null);
            Toast.makeText(this, "Score has been saved", Toast.LENGTH_SHORT).show();

            if (StudentHomeView.level.equals("Preperatory")) {
                startActivity(new Intent(this, PreparatoryFilipinoQuiz.class));
            } else if (StudentHomeView.level.equals("Kinder")) {
                startActivity(new Intent(this, KinderFilipinoQuiz.class));
            }
            mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.yehey);
            mediaPlayer.setLooping(false);
            mediaPlayer.start();
            nextBtn.setText("Finish");

        }

    }

    private void incorrectDialog() {
        builder = new AlertDialog.Builder(FilQuizStart.this);
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
        builder = new AlertDialog.Builder(FilQuizStart.this);
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
        quizzesModelList.add(new QuizzesModel("Alin sa mga sumusunod na salita ang nagsisimula sa Patinig?", "Dagat", "Ibon", "Halaman", "Kasuy", 2));
        quizzesModelList.add(new QuizzesModel("Alin sa mga sumusunod na salita ang nagsisimula sa Katinig", "Aso", "Bata", "Ibon", "Oso", 2));
        quizzesModelList.add(new QuizzesModel("Ano ang kulang sa mga Patinig? A,E,_,O,U", "A", "R", "I", "O", 3));
        quizzesModelList.add(new QuizzesModel("Ano sa mga halimbawa ng katinig ang naiba? K,L,P,Y,A", "K", "A", "Y", "P", 2));
        quizzesModelList.add(new QuizzesModel("Ano sa mga halimbawa ng patinig ang naiba? G,A,I,O,U", "G", "A", "O", "U", 1));
        quizzesModelList.add(new QuizzesModel("Magbigay ng halimbawa ng bagay na nagsisimula sa katinig at nagtatapos sa patinig.", "Baso", "Bahay", "Telibisyon", "Punit", 1));
        quizzesModelList.add(new QuizzesModel("Ano ang pinagkaiba ng mga letra?,A,B,E,I,O,U", "May pagkakapareho", "May pagkakapantay", "May katinig na kasama sa patinig", "May patinig na nasama sa patinig", 3));
        quizzesModelList.add(new QuizzesModel("Magbigay ng patinig. A,B,C,D", "B", "C", "D", "A", 4));
        quizzesModelList.add(new QuizzesModel("Magbigay ng katinig, A,E,I,O,N", "E", "C", "D", "N", 4));
        quizzesModelList.add(new QuizzesModel("Magbigay ng patinig. B,A,E,I", "A", "B", "C", "G", 1));
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

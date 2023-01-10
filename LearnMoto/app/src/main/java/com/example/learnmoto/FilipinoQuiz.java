package com.example.learnmoto;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.learnmoto.Kinder.ChristianLiving.KinderChristianLivingQuiz;
import com.example.learnmoto.Kinder.Filipino.KinderFilipinoQuiz;
import com.example.learnmoto.Nursery.ChristianLiving.NurseryChristianLivingQuiz;
import com.example.learnmoto.Preparatory.ChristianLiving.PreparatoryChristianLivingQuiz;
import com.example.learnmoto.Preparatory.Filipino.PreparatoryFilipinoQuiz;
import com.example.learnmoto.Student.StudentHomeView;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class FilipinoQuiz extends AppCompatActivity implements View.OnClickListener{

    TextView totalQuestions;
    TextView questionnaire;
    Button AnsA, AnsB, AnsC, AnsD;
    Button submitBtn;
    ImageView back;
    int score = 0;
    int totalQuestion = FiliQuestionaire.questions.length;
    int currentQuestionIndex = 0;
    String selectedAnswer = "";
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filipino_quiz);

        totalQuestions = findViewById(R.id.totalQuestions);
        questionnaire = findViewById(R.id.questionnaire);
        AnsA = findViewById(R.id.AnsA);
        AnsB = findViewById(R.id.AnsB);
        AnsC = findViewById(R.id.AnsC);
        AnsD = findViewById(R.id.AnsD);
        submitBtn = findViewById(R.id.submitBtn);
        AnsA.setOnClickListener(this);
        AnsB.setOnClickListener(this);
        AnsC.setOnClickListener(this);
        AnsD.setOnClickListener(this);
        submitBtn.setOnClickListener(this);
        AnsA.setBackgroundColor(Color.WHITE);
        AnsB.setBackgroundColor(Color.WHITE);
        AnsC.setBackgroundColor(Color.WHITE);
        AnsD.setBackgroundColor(Color.WHITE);

        totalQuestions.setText("Total Questions: " + totalQuestion);

        loadNewQuestions();

    }

    private void loadNewQuestions() {
        if(currentQuestionIndex==totalQuestion){
            finishQuiz();
            return;
        }
        questionnaire.setText(FiliQuestionaire.questions[currentQuestionIndex]);
        AnsA.setText(FiliQuestionaire.questionChoices[currentQuestionIndex][0]);
        AnsB.setText(FiliQuestionaire.questionChoices[currentQuestionIndex][1]);
        AnsC.setText(FiliQuestionaire.questionChoices[currentQuestionIndex][2]);
        AnsD.setText(FiliQuestionaire.questionChoices[currentQuestionIndex][3]);
    }

    private void finishQuiz() {
        String passStatus ="";
        if(score>totalQuestion*0.60){
            passStatus="Passed";
        }else{
            passStatus="Failed";
        }
        new AlertDialog.Builder(this)
                .setTitle(passStatus)
                .setMessage("Score is: "+score+" out of "+totalQuestion)
                .setPositiveButton("EXit",(dialogInterface, i) -> exitQuiz())
                .setCancelable(false)
                .show();
    }

    private void exitQuiz() {

        String filiScore = String.valueOf(score);

        DocumentReference documentReference = db.collection("Student").document(StudentHomeView.userID);
        documentReference.update("filipinoScore", filiScore);
        Toast.makeText(FilipinoQuiz.this, "Score has been saved", Toast.LENGTH_SHORT).show();

        if (StudentHomeView.level.equals("Kinder")){
            startActivity(new Intent(this, KinderFilipinoQuiz.class));
        }else if (StudentHomeView.level.equals("Preparatory")){
            startActivity(new Intent(this, PreparatoryFilipinoQuiz.class));
        }
    }

    @Override
    public void onClick(View v) {
        AnsA.setBackgroundColor(Color.WHITE);
        AnsB.setBackgroundColor(Color.WHITE);
        AnsC.setBackgroundColor(Color.WHITE);
        AnsD.setBackgroundColor(Color.WHITE);
        Button clickedButton=(Button) v;
        if(clickedButton.getId()==R.id.submitBtn){
            if (selectedAnswer.equals(FiliQuestionaire.correctAnswer[currentQuestionIndex])) {
                score++;
            }
            currentQuestionIndex++;
            Toast.makeText(this, "Question Number " + currentQuestionIndex, Toast.LENGTH_SHORT).show();
            loadNewQuestions();


        }else{
            selectedAnswer=clickedButton.getText().toString();
            clickedButton.setBackgroundColor(Color.DKGRAY);
        }
    }

}
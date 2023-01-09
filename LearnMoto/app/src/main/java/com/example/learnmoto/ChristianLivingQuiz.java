package com.example.learnmoto;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.learnmoto.Kinder.ChristianLiving.KinderChristianLivingQuiz;
import com.example.learnmoto.Kinder.English.KinderEnglishQuiz;
import com.example.learnmoto.Nursery.ChristianLiving.NurseryChristianLivingQuiz;
import com.example.learnmoto.Nursery.English.NurseryEnglishQuiz;
import com.example.learnmoto.Preparatory.ChristianLiving.PreparatoryChristianLivingQuiz;
import com.example.learnmoto.Preparatory.English.PreparatoryEnglishQuiz;
import com.example.learnmoto.Student.StudentHomeView;

public class ChristianLivingQuiz extends AppCompatActivity  implements View.OnClickListener {

    TextView totalQuestions;
    TextView questionnaire;
    Button AnsA, AnsB, AnsC, AnsD;
    Button submitBtn;
    ImageView back;
    int score = 0;
    int totalQuestion = CLQuestionaire.questions.length;
    int currentQuestionIndex = 0;
    String selectedAnswer = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_christian_living_quiz);

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
            questionnaire.setText(CLQuestionaire.questions[currentQuestionIndex]);
            AnsA.setText(CLQuestionaire.questionChoices[currentQuestionIndex][0]);
            AnsB.setText(CLQuestionaire.questionChoices[currentQuestionIndex][1]);
            AnsC.setText(CLQuestionaire.questionChoices[currentQuestionIndex][2]);
            AnsD.setText(CLQuestionaire.questionChoices[currentQuestionIndex][3]);
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
            score=0;
            currentQuestionIndex=0;
            if (StudentHomeView.level.equals("Kinder")){
                startActivity(new Intent(this, KinderChristianLivingQuiz.class));
            }else if (StudentHomeView.level.equals("Nursery")){
                startActivity(new Intent(this, NurseryChristianLivingQuiz.class));
            }else{
                startActivity(new Intent(this, PreparatoryChristianLivingQuiz.class));
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

            if (selectedAnswer.equals(CLQuestionaire.correctAnswer[currentQuestionIndex])) {
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
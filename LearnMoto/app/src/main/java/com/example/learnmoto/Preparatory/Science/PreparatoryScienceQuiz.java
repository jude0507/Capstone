package com.example.learnmoto.Preparatory.Science;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.learnmoto.CheckConnection.NetworkChangeListener;
import com.example.learnmoto.Nursery.Science.NurseryScienceQuiz;
import com.example.learnmoto.R;
import com.example.learnmoto.SciQuiz;
import com.example.learnmoto.Student.StudentHomeView;

public class PreparatoryScienceQuiz extends AppCompatActivity {
    DrawerLayout drawerLayout;
    NetworkChangeListener networkChangeListener = new NetworkChangeListener();
    TextView subjectlevel;
    LinearLayout expandableView1, expandableLinear1;
    Button quizArrow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preparatory_science_quiz);
        drawerLayout = findViewById(R.id.mydrawer_layout);
        subjectlevel = findViewById(R.id.SubjectLabel);
        quizArrow = findViewById(R.id.arrow_pronounce);
        expandableView1 = findViewById(R.id.expandableLayout3);
        expandableLinear1 = findViewById(R.id.layout3);

        subjectlevel.setText(NurseryScienceQuiz.subjectName);

        quizArrow.setOnClickListener(v -> {
            StudentHomeView.textToSpeech.speak("Quiz", TextToSpeech.QUEUE_ADD, null);

            if (expandableView1.getVisibility() == View.GONE) {
                TransitionManager.beginDelayedTransition(expandableLinear1, new AutoTransition());
                expandableView1.setVisibility(View.VISIBLE);
                quizArrow.setBackgroundResource(R.drawable.ic_arrow_up);
            }else{
                TransitionManager.beginDelayedTransition(expandableLinear1, new AutoTransition());
                expandableView1.setVisibility(View.GONE);
                quizArrow.setBackgroundResource(R.drawable.ic_arrow_down);
            }
        });

        expandableLinear1.setOnClickListener(v -> {
            StudentHomeView.textToSpeech.speak("Quiz", TextToSpeech.QUEUE_ADD, null);

            if (expandableView1.getVisibility() == View.GONE) {
                TransitionManager.beginDelayedTransition(expandableLinear1, new AutoTransition());
                expandableView1.setVisibility(View.VISIBLE);
                quizArrow.setBackgroundResource(R.drawable.ic_arrow_up);
            }else{
                TransitionManager.beginDelayedTransition(expandableLinear1, new AutoTransition());
                expandableView1.setVisibility(View.GONE);
                quizArrow.setBackgroundResource(R.drawable.ic_arrow_down);
            }
        });
    }
    public void clickmenu(View view) {
        openDrawer(drawerLayout);
    }

    public static void openDrawer(DrawerLayout drawerLayout) {
        drawerLayout.openDrawer(GravityCompat.START);
    }

    public void clicklayout(View view) {
        closeDrawer(drawerLayout);
    }

    public static void closeDrawer(DrawerLayout drawerLayout) {

        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }

    public void readme(View view){
        redirectActivity(this, PreparatoryScienceRead.class);
    }
    public void watchme(View view){
        redirectActivity(this, PreparatoryScienceWatch.class);
    }
    public void takeaquiz(View view){
        recreate();
    }

    public static void redirectActivity(Activity activity, Class myclass) {

        Intent intent = new Intent(activity,myclass);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);

    }

    protected void onPause() {
        super.onPause();

        closeDrawer(drawerLayout);
    }

    public void BacktoStudentHome(View view){
        startActivity(new Intent(this, StudentHomeView.class));
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

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, StudentHomeView.class));
    }

    public void StartQuiz(View view) {
        startActivity(new Intent(this, SciQuiz.class));
    }

}
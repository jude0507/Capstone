package com.example.learnmoto.Kinder.English;

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

import com.example.learnmoto.AudioService;
import com.example.learnmoto.CheckConnection.NetworkChangeListener;
import com.example.learnmoto.EnglishQuiz;
import com.example.learnmoto.R;
import com.example.learnmoto.Student.StudentHomeView;

public class KinderEnglishQuiz extends AppCompatActivity {

    DrawerLayout drawerLayout;
    NetworkChangeListener networkChangeListener = new NetworkChangeListener();
    TextView subjectlevel;
    LinearLayout expandableView1, expandableLinear1;
    Button quizArrow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kinder_english_quiz);

        drawerLayout = findViewById(R.id.mydrawer_layout);
        quizArrow = findViewById(R.id.arrow_pronounce);
        subjectlevel = findViewById(R.id.SubjectLabel);
        expandableView1 = findViewById(R.id.expandableLayout3);
        expandableLinear1 = findViewById(R.id.layout3);

        subjectlevel.setText("English");

        expandableLinear1.setOnClickListener(v -> {
            if (expandableView1.getVisibility() == View.GONE) {
                TransitionManager.beginDelayedTransition(expandableLinear1, new AutoTransition());
                expandableView1.setVisibility(View.VISIBLE);
                quizArrow.setBackgroundResource(R.drawable.ic_arrow_up);
            }else{
                StudentHomeView.textToSpeech.speak("Quiz", TextToSpeech.QUEUE_ADD, null);
                TransitionManager.beginDelayedTransition(expandableLinear1, new AutoTransition());
                expandableView1.setVisibility(View.GONE);
                quizArrow.setBackgroundResource(R.drawable.ic_arrow_down);
            }
        });

        quizArrow.setOnClickListener(v -> {
            if (expandableView1.getVisibility() == View.GONE) {
                TransitionManager.beginDelayedTransition(expandableLinear1, new AutoTransition());
                expandableView1.setVisibility(View.VISIBLE);
                quizArrow.setBackgroundResource(R.drawable.ic_arrow_up);
            }else{
                StudentHomeView.textToSpeech.speak("Quiz", TextToSpeech.QUEUE_ADD, null);
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
        redirectActivity(this, KinderEnglish.class);
    }
    public void watchme(View view){
        redirectActivity(this, KinderEnglishWatch.class);

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

    public void StartQuiz(View view) {
        startActivity(new Intent(this, EnglishQuiz.class));
        stopService(new Intent(this, AudioService.class));
    }
}
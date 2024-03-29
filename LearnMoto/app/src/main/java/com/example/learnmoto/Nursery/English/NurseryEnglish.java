package com.example.learnmoto.Nursery.English;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
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
import com.example.learnmoto.Nursery.NStories;
import com.example.learnmoto.PDF.EnglishPDF;
import com.example.learnmoto.R;
import com.example.learnmoto.Student.PronounceAlphabet;
import com.example.learnmoto.Student.StudentHomeView;

public class NurseryEnglish extends AppCompatActivity {

    DrawerLayout drawerLayout;
    NetworkChangeListener networkChangeListener = new NetworkChangeListener();
    TextView subjectlevel;
    LinearLayout expandableView1, expandableLinear1, expandableView2, expandableLinear2, expandableView3, expandableLinear3;
    Button pdfArrow, storyArrow, pronounceArrow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nursery_english);

        drawerLayout = findViewById(R.id.mydrawer_layout);
        subjectlevel = findViewById(R.id.SubjectLabel);

        storyArrow = findViewById(R.id.arrow_story);
        pdfArrow = findViewById(R.id.arrow_pdf);
        pronounceArrow = findViewById(R.id.arrow_pronounce);
        expandableView1 = findViewById(R.id.expandableLayout1);
        expandableLinear1 = findViewById(R.id.layout1);
        expandableView2 = findViewById(R.id.expandableLayout2);
        expandableLinear2 = findViewById(R.id.layout2);
        expandableView3 = findViewById(R.id.expandableLayout3);
        expandableLinear3 = findViewById(R.id.layout3);


        subjectlevel.setText("English");
        stopService(new Intent(this, AudioService.class));

        pdfArrow.setOnClickListener(v -> {
            if (expandableView2.getVisibility() == View.GONE) {
                MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.lessons);
                mediaPlayer.start();
                TransitionManager.beginDelayedTransition(expandableLinear2, new AutoTransition());
                expandableView2.setVisibility(View.VISIBLE);
                pdfArrow.setBackgroundResource(R.drawable.ic_arrow_up);
            }else{
                TransitionManager.beginDelayedTransition(expandableLinear2, new AutoTransition());
                expandableView2.setVisibility(View.GONE);
                pdfArrow.setBackgroundResource(R.drawable.ic_arrow_down);
            }
        });

        expandableLinear2.setOnClickListener(v -> {
            if (expandableView2.getVisibility() == View.GONE) {
                MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.lessons);
                mediaPlayer.start();
                TransitionManager.beginDelayedTransition(expandableLinear2, new AutoTransition());
                expandableView2.setVisibility(View.VISIBLE);
                pdfArrow.setBackgroundResource(R.drawable.ic_arrow_up);
            }else{
                TransitionManager.beginDelayedTransition(expandableLinear2, new AutoTransition());
                expandableView2.setVisibility(View.GONE);
                pdfArrow.setBackgroundResource(R.drawable.ic_arrow_down);
            }
        });

        expandableLinear1.setOnClickListener(v -> {
            if (expandableView1.getVisibility() == View.GONE) {
                MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.story);
                mediaPlayer.start();
                TransitionManager.beginDelayedTransition(expandableLinear1, new AutoTransition());
                expandableView1.setVisibility(View.VISIBLE);
                storyArrow.setBackgroundResource(R.drawable.ic_arrow_up);
            }else{
                TransitionManager.beginDelayedTransition(expandableLinear1, new AutoTransition());
                expandableView1.setVisibility(View.GONE);
                storyArrow.setBackgroundResource(R.drawable.ic_arrow_down);
            }
        });

        storyArrow.setOnClickListener(v -> {
            if (expandableView1.getVisibility() == View.GONE) {
                MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.story);
                mediaPlayer.start();
                TransitionManager.beginDelayedTransition(expandableLinear1, new AutoTransition());
                expandableView1.setVisibility(View.VISIBLE);
                storyArrow.setBackgroundResource(R.drawable.ic_arrow_up);
            }else{
                TransitionManager.beginDelayedTransition(expandableLinear1, new AutoTransition());
                expandableView1.setVisibility(View.GONE);
                storyArrow.setBackgroundResource(R.drawable.ic_arrow_down);
            }
        });

        expandableLinear3.setOnClickListener(v -> {
            if (expandableView3.getVisibility() == View.GONE) {
                MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.pronounce);
                mediaPlayer.start();
                TransitionManager.beginDelayedTransition(expandableLinear3, new AutoTransition());
                expandableView3.setVisibility(View.VISIBLE);
                pronounceArrow.setBackgroundResource(R.drawable.ic_arrow_up);
            }else{
                TransitionManager.beginDelayedTransition(expandableLinear3, new AutoTransition());
                expandableView3.setVisibility(View.GONE);
                pronounceArrow.setBackgroundResource(R.drawable.ic_arrow_down);
            }
        });

        pronounceArrow.setOnClickListener(v -> {
            if (expandableView3.getVisibility() == View.GONE) {
                MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.pronounce);
                mediaPlayer.start();
                TransitionManager.beginDelayedTransition(expandableLinear3, new AutoTransition());
                expandableView3.setVisibility(View.VISIBLE);
                pronounceArrow.setBackgroundResource(R.drawable.ic_arrow_up);
            }else{
                TransitionManager.beginDelayedTransition(expandableLinear3, new AutoTransition());
                expandableView3.setVisibility(View.GONE);
                pronounceArrow.setBackgroundResource(R.drawable.ic_arrow_down);
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
        recreate();
    }
    public void watchme(View view){
        redirectActivity(this, NurseryEnglishWatch.class);
    }
    public void takeaquiz(View view){
        redirectActivity(this, NurseryEnglishQuiz.class);
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
        startService(new Intent(this, AudioService.class));
    }

    public void story(View view) {
        startActivity(new Intent(this, NStories.class));
        stopService(new Intent(this, AudioService.class));
    }

    public void pdf(View view) {
        startActivity(new Intent(this, EnglishPDF.class));
        stopService(new Intent(this, AudioService.class));
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

    public void PronounceAlphabet(View view) {
        stopService(new Intent(this, AudioService.class));
        startActivity(new Intent(this, PronounceAlphabet.class));
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, StudentHomeView.class));
        startService(new Intent(this, AudioService.class));
    }

}
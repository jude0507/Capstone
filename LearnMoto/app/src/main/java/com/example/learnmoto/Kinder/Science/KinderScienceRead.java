package com.example.learnmoto.Kinder.Science;

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
import com.example.learnmoto.PDF.SciencePDF;
import com.example.learnmoto.R;
import com.example.learnmoto.Student.StudentHomeView;

public class KinderScienceRead extends AppCompatActivity {

    DrawerLayout drawerLayout;
    NetworkChangeListener networkChangeListener = new NetworkChangeListener();
    TextView subjectlevel;
    LinearLayout expandableView2, expandableLinear2;
    Button pdfArrow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kinder_science_read);
        drawerLayout = findViewById(R.id.mydrawer_layout);
        subjectlevel = findViewById(R.id.SubjectLabel);
        pdfArrow = findViewById(R.id.arrow_pdf);
        expandableView2 = findViewById(R.id.expandableLayout2);
        expandableLinear2 = findViewById(R.id.layout2);

        subjectlevel.setText("Science");
        stopService(new Intent(this, AudioService.class));

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
        redirectActivity(this, KinderScienceWatch.class);
    }
    public void takeaquiz(View view){
        redirectActivity(this, KinderScienceQuiz.class);
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

    public void pdf(View view) {
        stopService(new Intent(this, AudioService.class));
        startActivity(new Intent(this, SciencePDF.class));
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
        startService(new Intent(this, AudioService.class));
    }
}
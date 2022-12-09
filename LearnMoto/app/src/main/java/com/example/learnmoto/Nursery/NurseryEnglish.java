package com.example.learnmoto.Nursery;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.learnmoto.AlphabetContent.AlphabetQuiz;
import com.example.learnmoto.AlphabetContent.AlphabetWatch;
import com.example.learnmoto.R;
import com.example.learnmoto.Student.StudentHomeView;

public class NurseryEnglish extends AppCompatActivity {

    DrawerLayout drawerLayout;
    String Story;
    TextView storyTelling;
    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nursery_english);

        Story = "A lion was once sleeping in the jungle when a mouse started running up and down his body just for fun. This disturbed the lion’s sleep, and he woke up quite angry. He was about to eat the mouse when the mouse desperately requested the lion to set him free. “I promise you, I will be of great help to you someday if you save me.” The lion laughed at the mouse’s confidence and let him go.\n" +
                "\n" +
                "One day, a few hunters came into the forest and took the lion with them. They tied him up against a tree. The lion was struggling to get out and started to whimper. Soon, the mouse walked past and noticed the lion in trouble. Quickly, he ran and gnawed on the ropes to set the lion free. Both of them sped off into the jungle.\n";


        drawerLayout = findViewById(R.id.mydrawer_layout);
        storyTelling = findViewById(R.id.storytelling);

        storyTelling.setText(Story);

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
        redirectActivity(this, AlphabetWatch.class);
    }
    public void takeaquiz(View view){
        redirectActivity(this, AlphabetQuiz.class);
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

    public void Play(View view) {
        if (mediaPlayer == null){
            mediaPlayer = MediaPlayer.create(this, R.raw.lion_and_mouse1);
            mediaPlayer.setOnCompletionListener(mp -> stopMediaPlayer());
        }
        mediaPlayer.start();
    }

    public void Pause(View view) {
        if (mediaPlayer != null){
            mediaPlayer.pause();
        }
    }

    public void Stop(View view) {
        stopMediaPlayer();
    }

    private void stopMediaPlayer(){
        if (mediaPlayer != null){
            mediaPlayer.release();
            mediaPlayer = null;
            //Toast.makeText(this, "MediaPlayer Release", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onStop() {
        stopMediaPlayer();
        super.onStop();
    }

}
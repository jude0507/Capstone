package com.example.learnmoto.Nursery.Story;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.learnmoto.CheckConnection.NetworkChangeListener;
import com.example.learnmoto.Nursery.NStories;
import com.example.learnmoto.R;

import me.biubiubiu.justifytext.library.JustifyTextView;

public class NFourthStory extends AppCompatActivity {

    String Story4;
    MediaPlayer mediaPlayer;
    JustifyTextView jtv;
    TextView TitleStory;
    ImageView ImageStory;

    NetworkChangeListener networkChangeListener = new NetworkChangeListener();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fourth_story);

        ImageStory = findViewById(R.id.ImageStory);
        TitleStory = findViewById(R.id.TitleStory);
        jtv = findViewById(R.id.justifyfield);

        Story4 = "A boy named Raj was upset because he had done poorly in his English test. " +
                "He was sitting in his room when his grandmother came and comforted him. " +
                "His grandmother sat beside him and gave him a pencil. " +
                "Raj looked at his grandma puzzled, and said he didn’t deserve a pencil after his performance in the test.\n" +
                "\n" +
                "His grandma explained, " +
                "“You can learn many things from this pencil because it is just like you. " +
                "It experiences a painful sharpening, " +
                "just the way you have experienced the pain of not doing well on your test. " +
                "However, it will help you be a better student. Just as all the good that comes from the pencil is from within itself, " +
                "you will also find the strength to overcome this hurdle. And finally, just as this pencil will make its mark on any surface, " +
                "you too shall leave your mark on anything you choose to.” Raj was immediately consoled and promised himself that he would do better.\n";

        jtv.setText(Story4);
        TitleStory.setText("The tale of the pencil");
    }

    public void Play(View view) {

        if (mediaPlayer == null){
            mediaPlayer = MediaPlayer.create(this, R.raw.pencil);
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
        stopMediaPlayer();
        unregisterReceiver(networkChangeListener);
        super.onStop();
    }

    public void BackToStories(View view) {
        startActivity(new Intent(this, NStories.class));
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, NStories.class));
    }
}
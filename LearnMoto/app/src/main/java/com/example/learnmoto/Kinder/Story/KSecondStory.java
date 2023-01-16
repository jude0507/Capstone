package com.example.learnmoto.Kinder.Story;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.learnmoto.AudioService;
import com.example.learnmoto.CheckConnection.NetworkChangeListener;
import com.example.learnmoto.Kinder.KStories;
import com.example.learnmoto.R;

import me.biubiubiu.justifytext.library.JustifyTextView;

public class KSecondStory extends AppCompatActivity {

    NetworkChangeListener networkChangeListener = new NetworkChangeListener();
    String Story2;
    MediaPlayer mediaPlayer;
    JustifyTextView jtv;
    TextView TitleStory;
    ImageView ImageStory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ksecond_story);
        ImageStory = findViewById(R.id.ImageStory);
        TitleStory = findViewById(R.id.TitleStory);
        jtv = findViewById(R.id.justifyfield);

        Story2 = "One day, a selfish fox invited a stork for dinner. Stork was very happy with the invitation – \n" +
                "she reached the fox’s home on time and knocked at the door with her long beak. \n" +
                "The fox took her to the dinner table and served some soup in shallow bowls for both of them. \n" +
                "As the bowl was too shallow for the stork, she couldn’t have soup at all. But, the fox licked up his soup quickly.\n" +
                "\n" +
                "The stork was angry and upset, but she didn’t show her anger and behaved politely. \n" +
                "To teach a lesson to the fox, she then invited him for dinner the next day. \n" +
                "She too served soup, but this time the soup was served in two tall narrow vases. \n" +
                "The stork devoured the soup from her vase, but the fox couldn’t drink any of it because of his narrow neck. \n" +
                "The fox realised his mistake and went home famished.";

        jtv.setText(Story2);
        TitleStory.setText(KStories.title2);
    }

    public void BackToStories(View view) {
        startActivity(new Intent(this, KStories.class));
    }

    public void Play(View view) {
        if (mediaPlayer == null){
            mediaPlayer = MediaPlayer.create(this, R.raw.fox_stork);
            mediaPlayer.setOnCompletionListener(mp -> {
                stopMediaPlayer();
            });
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

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, KStories.class));
    }

}
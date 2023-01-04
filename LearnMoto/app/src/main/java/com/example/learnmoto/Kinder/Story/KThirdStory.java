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

import com.example.learnmoto.CheckConnection.NetworkChangeListener;
import com.example.learnmoto.Kinder.KStories;
import com.example.learnmoto.R;

import me.biubiubiu.justifytext.library.JustifyTextView;

public class KThirdStory extends AppCompatActivity {

    NetworkChangeListener networkChangeListener = new NetworkChangeListener();
    String Story3;
    MediaPlayer mediaPlayer;
    JustifyTextView jtv;
    TextView TitleStory;
    ImageView ImageStory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kthird_story);
        ImageStory = findViewById(R.id.ImageStory);
        TitleStory = findViewById(R.id.TitleStory);
        jtv = findViewById(R.id.justifyfield);

        Story3 = "Once there lived a greedy man in a small town. He was very rich, and he loved gold and all things fancy. \n" +
                "But he loved his daughter more than anything. One day, he chanced upon a fairy. The fairy’s hair was caught in a few tree branches. He helped her out, but as his greediness took over, he realised that he had an opportunity to become richer by asking for a wish in return (by helping her out). \n" +
                "The fairy granted him a wish. He said, “All that I touch should turn to gold.” And his wish was granted by the grateful fairy.\n" +
                "\n" +
                "The greedy man rushed home to tell his wife and daughter about his wish, \n" +
                "all the while touching stones and pebbles and watching them convert into gold. \n" +
                "Once he got home, his daughter rushed to greet him. \n" +
                "As soon as he bent down to scoop her up in his arms, she turned into a gold statue. \n" +
                "He was devastated and started crying and trying to bring his daughter back to life. \n" +
                "He realised his folly and spent the rest of his days searching for the fairy to take away his wish.";

        jtv.setText(Story3);
        TitleStory.setText(KStories.title3);
    }

    public void BackToStories(View view) {
        startActivity(new Intent(this, KStories.class));
    }

    public void Play(View view) {
        if (mediaPlayer == null){
            mediaPlayer = MediaPlayer.create(this, R.raw.golden_touch);
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

}
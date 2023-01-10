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

public class KFirstStory extends AppCompatActivity {

    NetworkChangeListener networkChangeListener = new NetworkChangeListener();
    String Story1;
    MediaPlayer mediaPlayer;
    JustifyTextView jtv;
    TextView TitleStory;
    ImageView ImageStory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kfirst_story);

        ImageStory = findViewById(R.id.ImageStory);
        TitleStory = findViewById(R.id.TitleStory);
        jtv = findViewById(R.id.justifyfield);

        Story1 = "In a village, lived a carefree boy with his father. The boy’s father told him that he was old enough to watch over the sheep while they graze in the fields. \n" +
                "Every day, he had to take the sheep to the grassy fields and watch them as they graze. \n" +
                "However, the boy was unhappy and didn’t want to take the sheep to the fields. He wanted to run and play, not watch the boring sheep graze in the field. \n" +
                "So, he decided to have some fun. He cried, “Wolf! Wolf!” until the entire village came running with stones to chase away the wolf before it could eat any of the sheep.\n" +
                " When the villagers saw that there was no wolf, they left muttering under their breath about how the boy had wasted their time. The next day, the boy cried once more, \n" +
                "“Wolf! Wolf!” and, again, the villagers rushed there to chase the wolf away.\n" +
                "\n" +
                "The boy laughed at the fright he had caused. This time, the villagers left angrily. \n" +
                "The third day, as the boy went up the small hill, he suddenly saw a wolf attacking his sheep. \n" +
                "He cried as hard as he could, “Wolf! Wolf! Wolf!”, but not a single villager came to help him. \n" +
                "The villagers thought that he was trying to fool them again and did not come to rescue him or his sheep. \n" +
                "The little boy lost many sheep that day, all because of his foolishness.";

        jtv.setText(Story1);
        TitleStory.setText(KStories.title1);

    }

    public void BackToStories(View view) {
        startActivity(new Intent(this, KStories.class));
    }

    public void Play(View view) {
        if (mediaPlayer == null){
            mediaPlayer = MediaPlayer.create(this, R.raw.boy_wolf);
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
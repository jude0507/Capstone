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

public class NSecondStory extends AppCompatActivity {

    public static String Story2;
    MediaPlayer mediaPlayer;
    JustifyTextView jtv;
    TextView TitleStory;
    ImageView ImageStory;

    NetworkChangeListener networkChangeListener = new NetworkChangeListener();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_story);

        ImageStory = findViewById(R.id.ImageStory);
        TitleStory = findViewById(R.id.TitleStory);
        jtv = findViewById(R.id.justifyfield);

        Story2= "On a hot scorching day of summer, an ant was walking around in search of water. After walking around for some time, she saw a river and was delighted to see it. She climbed up on a small rock to drink the water, but she slipped and fell into the river. She was drowning but a dove who was sitting on a nearby tree helped her. Seeing the ant in trouble, the dove quickly dropped a leaf into the water. The ant moved towards the leaf and climbed up on it. The dove then carefully pulled the leaf out and placed it on the land. This way, the ant’s life was saved and she was forever indebted to the dove.\n" +
                "\n" +
                "The ant and the dove became the best of friends and days passed happily. However, one day, a hunter arrived at the forest. He saw the beautiful dove sitting on the tree and aimed his gun at the dove. The ant, who was saved the dove saw this and bit on the heel of the hunter. He shouted from the pain and dropped the gun. The dove was alarmed by the voice of the hunter and realised what could have happened with him. He flew away!\n";

        jtv.setText(Story2);
        TitleStory.setText("The Ant and The Dove");

    }

    public void Play(View view) {

        if (mediaPlayer == null){
            mediaPlayer = MediaPlayer.create(this, R.raw.ant_and_dove);
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
        unregisterReceiver(networkChangeListener);
        stopMediaPlayer();
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
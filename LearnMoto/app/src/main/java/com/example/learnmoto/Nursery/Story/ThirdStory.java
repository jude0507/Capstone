package com.example.learnmoto.Nursery.Story;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.learnmoto.Nursery.Stories;
import com.example.learnmoto.R;

import me.biubiubiu.justifytext.library.JustifyTextView;

public class ThirdStory extends AppCompatActivity {

    String Story3;
    MediaPlayer mediaPlayer;
    JustifyTextView jtv;
    TextView TitleStory;
    ImageView ImageStory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third_story);

        ImageStory = findViewById(R.id.ImageStory);
        TitleStory = findViewById(R.id.TitleStory);
        jtv = findViewById(R.id.justifyfield);

        Story3 ="One summer’s day, in a field, a Grasshopper was hopping about, chirping and singing to its heart's content. An Ant passed by, bearing along with great effort an ear of corn he was taking to his nest.\n" +
                "\"Why don’t you come and chat with me,\" asked the Grasshopper, \"instead of toiling your life away?\"\n" +
                "\"I am helping to store up food for the winter,\" said the Ant, \"and I recommend you to do the same.\"\n" +
                "\"Why bother about winter?\" said the Grasshopper. \"We have got plenty of food at present.\"\n" +
                "\n" +
                "But the Ant went on its way and continued its toil.\n" +
                "When winter came, the Grasshopper found itself dying of hunger, while it saw the ants distributing, every day, corn and grain from the stores they had collected in summer.\n" +
                "Then the Grasshopper knew...\n";

        jtv.setText(Story3);
        TitleStory.setText("The ant and the grasshopper");

    }

    public void Play(View view) {

        if (mediaPlayer == null){
            mediaPlayer = MediaPlayer.create(this, R.raw.ant_and_grasshopper);
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
    protected void onStop() {
        stopMediaPlayer();
        super.onStop();
    }

    public void BackToStories(View view) {
        startActivity(new Intent(this, Stories.class));
    }

}
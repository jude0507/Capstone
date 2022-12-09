package com.example.learnmoto.Nursery;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.learnmoto.R;
import com.example.learnmoto.StoriesAdapter;

import java.util.ArrayList;
import java.util.List;

import me.biubiubiu.justifytext.library.JustifyTextView;

public class Stories extends AppCompatActivity {


    public static String title1, title2, title3, title4;
    RecyclerView rv_stories;
    List<String> storyTitles;
    List<Integer> storyImages;
    StoriesAdapter storiesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stories);

        rv_stories = findViewById(R.id.stories);

        title1 = "The lion and the mouse";
        title2 = "The ant and the dove";
        title3 = "The ant and the grasshopper";
        title4 = "The tale of the pencil";

        storyTitles = new ArrayList<>();
        storyImages = new ArrayList<>();
        storiesAdapter = new StoriesAdapter(this, storyTitles, storyImages);

        storyTitles.add(title1);
        storyTitles.add(title2);
        storyTitles.add(title3);
        storyTitles.add(title4);

        storyImages.add(R.drawable.lionmouse);
        storyImages.add(R.drawable.ant_dove);
        storyImages.add(R.drawable.ant_grasshopper);
        storyImages.add(R.drawable.the_tale);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rv_stories.setLayoutManager(linearLayoutManager);
        rv_stories.setAdapter(storiesAdapter);

    }

    public void BacktoEnglishClass(View view) {
        startActivity(new Intent(this, NurseryEnglish.class));
    }
}
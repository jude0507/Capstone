package com.example.learnmoto.Nursery;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;

import com.example.learnmoto.CheckConnection.NetworkChangeListener;
import com.example.learnmoto.Nursery.English.NurseryEnglish;
import com.example.learnmoto.R;
import com.example.learnmoto.Adapter.StoriesAdapter;

import java.util.ArrayList;
import java.util.List;

public class Stories extends AppCompatActivity {


    public final static String title1 = "The lion and the mouse";
    public final static String title2 = "The ant and the dove";
    public final static String title3 = "The ant and the grasshopper";
    public final static String title4 = "The tale of the pencil";
    RecyclerView rv_stories;
    List<String> storyTitles;
    List<Integer> storyImages;
    StoriesAdapter storiesAdapter;
    NetworkChangeListener networkChangeListener = new NetworkChangeListener();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stories);

        rv_stories = findViewById(R.id.stories);

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

}
package com.example.learnmoto.Kinder;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.learnmoto.Adapter.KinderStoriesAdapter;
import com.example.learnmoto.AudioService;
import com.example.learnmoto.CheckConnection.NetworkChangeListener;
import com.example.learnmoto.Kinder.English.KinderEnglish;
import com.example.learnmoto.R;

import java.util.ArrayList;
import java.util.List;

public class KStories extends AppCompatActivity {

    public final static String title1 = "The boy who cried wolf";
    public final static String title2 = "The Fox and The Stork";
    public final static String title3 = "The Golden Touch";
    RecyclerView rvStory;
    List<String> storyTitles;
    List<Integer> storyImages;
    KinderStoriesAdapter storiesAdapter;
    NetworkChangeListener networkChangeListener = new NetworkChangeListener();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story);
        rvStory = findViewById(R.id.stories);
        storyTitles = new ArrayList<>();
        storyImages = new ArrayList<>();
        storiesAdapter = new KinderStoriesAdapter(this, storyTitles, storyImages);

        storyTitles.add(title1);
        storyTitles.add(title2);
        storyTitles.add(title3);

        storyImages.add(R.drawable.boy_wolf);
        storyImages.add(R.drawable.fox_stork);
        storyImages.add(R.drawable.golden_touch);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvStory.setLayoutManager(linearLayoutManager);
        rvStory.setAdapter(storiesAdapter);
    }



    public void BacktoClass(View view) {
        startActivity(new Intent(this, KinderEnglish.class));
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
        startActivity(new Intent(this, KinderEnglish.class));
        startService(new Intent(this, AudioService.class));
    }

}
package com.example.learnmoto.Preparatory;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;

import com.example.learnmoto.Adapter.PrepStoriesAdapter;
import com.example.learnmoto.AudioService;
import com.example.learnmoto.CheckConnection.NetworkChangeListener;
import com.example.learnmoto.Preparatory.Filipino.PreparatoryFilipinoRead;
import com.example.learnmoto.R;

import java.util.ArrayList;
import java.util.List;

public class PStories extends AppCompatActivity {

    public final static String title1 = "Ang batang espesyal";
    public final static String title2 = "Ang matalinong pintor";
    public final static String title3 = "Si Lino at Si Tomas";
    private RecyclerView rvStory;
    List<String> storyTitles;
    List<Integer> storyImages;
    PrepStoriesAdapter storiesAdapter;
    NetworkChangeListener networkChangeListener = new NetworkChangeListener();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pstory);
        rvStory = findViewById(R.id.stories);
        storyTitles = new ArrayList<>();
        storyImages = new ArrayList<>();
        storiesAdapter = new PrepStoriesAdapter(this, storyTitles, storyImages);

        storyTitles.add(title1);
        storyTitles.add(title2);
        storyTitles.add(title3);

        storyImages.add(R.drawable.ang_batang_espesyal);
        storyImages.add(R.drawable.ang_matalinong_pintor);
        storyImages.add(R.drawable.lino_tomas);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvStory.setLayoutManager(linearLayoutManager);
        rvStory.setAdapter(storiesAdapter);
    }



    public void BacktoFilipinoClass(View view) {
        startActivity(new Intent(this, PreparatoryFilipinoRead.class));
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, PreparatoryFilipinoRead.class));
        startService(new Intent(this, AudioService.class));
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
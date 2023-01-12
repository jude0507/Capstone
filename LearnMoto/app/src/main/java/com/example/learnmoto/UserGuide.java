package com.example.learnmoto;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.learnmoto.Adapter.GuideAdapter;

import java.util.ArrayList;
import java.util.List;

public class UserGuide extends AppCompatActivity {
    ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.guide_dialog);
        viewPager=findViewById(R.id.viewPager);

        List<Integer> imageList= new ArrayList<>();
        imageList.add(R.drawable.userguide1);
        imageList.add(R.drawable.userguide2);
        imageList.add(R.drawable.userguide3);
        imageList.add(R.drawable.userguide4);
        imageList.add(R.drawable.userguide5);
        GuideAdapter guideAdapter= new GuideAdapter(imageList);
        viewPager.setAdapter(guideAdapter);

    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(),MainActivity.class));
    }
}

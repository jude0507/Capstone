package com.example.learnmoto.StoryContent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.learnmoto.R;
import com.example.learnmoto.Student.StudentHomeView;

public class StoryQuiz extends AppCompatActivity {

    DrawerLayout drawerLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story_quiz);
        drawerLayout = findViewById(R.id.mydrawer_layout);
    }

    public void clickmenu(View view) {
        StoryRead.openDrawer(drawerLayout);
    }
    public void clicklayout(View view) {
        StoryRead.closeDrawer(drawerLayout);
    }
    public void readme(View view){
        StoryRead.redirectActivity(this, StoryRead.class);
    }
    public void watchme(View view){
        StoryRead.redirectActivity(this, StoryWatch.class);
    }
    public void takeaquiz(View view){
        recreate();
    }

    protected void onPause() {
        super.onPause();

        StoryRead.closeDrawer(drawerLayout);
    }

    public void BacktoStudentHome(View view){
        startActivity(new Intent(this, StudentHomeView.class));
    }
}
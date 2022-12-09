package com.example.learnmoto.AnimalContent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.learnmoto.R;
import com.example.learnmoto.Student.StudentHomeView;

public class AnimalsWatch extends AppCompatActivity {

    DrawerLayout drawerLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animals_watch);

        drawerLayout = findViewById(R.id.mydrawer_layout);
    }

    public void clickmenu(View view) {
        AnimalsRead.openDrawer(drawerLayout);
    }
    public void clicklayout(View view) {
        AnimalsRead.closeDrawer(drawerLayout);
    }
    public void readme(View view){
        AnimalsRead.redirectActivity(this, AnimalsRead.class);
    }
    public void watchme(View view){
        recreate();
    }
    public void takeaquiz(View view){
        AnimalsRead.redirectActivity(this, AnimalsQuiz.class);
    }

    protected void onPause() {
        super.onPause();

        AnimalsRead.closeDrawer(drawerLayout);
    }

    public void BacktoStudentHome(View view){
        startActivity(new Intent(this, StudentHomeView.class));
    }

}
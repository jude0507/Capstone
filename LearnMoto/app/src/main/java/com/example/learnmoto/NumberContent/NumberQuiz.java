package com.example.learnmoto.NumberContent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.learnmoto.R;
import com.example.learnmoto.Student.StudentHomeView;

public class NumberQuiz extends AppCompatActivity {

    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_number_quiz);

        drawerLayout = findViewById(R.id.mydrawer_layout);

    }

    public void clickmenu(View view) {
        NumberRead.openDrawer(drawerLayout);
    }
    public void clicklayout(View view) {
        NumberRead.closeDrawer(drawerLayout);
    }
    public void readme(View view){
        NumberRead.redirectActivity(this, NumberRead.class);
    }
    public void watchme(View view){
        NumberRead.redirectActivity(this, NumberWatch.class);
    }
    public void takeaquiz(View view){
        recreate();
    }

    protected void onPause() {
        super.onPause();

        NumberRead.closeDrawer(drawerLayout);
    }

    public void BacktoStudentHome(View view){
        startActivity(new Intent(this, StudentHomeView.class));
    }
}
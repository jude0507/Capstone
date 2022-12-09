package com.example.learnmoto.AlphabetContent;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.learnmoto.R;
import com.example.learnmoto.Student.StudentHomeView;

public class AlphabetQuiz extends AppCompatActivity {

    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alphabet_quiz);

        drawerLayout = findViewById(R.id.mydrawer_layout);
    }

    public void clickmenu(View view) {
        AlphabetRead.openDrawer(drawerLayout);
    }
    public void clicklayout(View view) {
        AlphabetRead.closeDrawer(drawerLayout);
    }
    public void readme(View view){
        AlphabetRead.redirectActivity(this, AlphabetRead.class);
    }
    public void watchme(View view){
        AlphabetRead.redirectActivity(this, AlphabetWatch.class);
    }
    public void takeaquiz(View view){
        recreate();
    }

    protected void onPause() {
        super.onPause();

        AlphabetRead.closeDrawer(drawerLayout);
    }

    public void BacktoStudentHome(View view){
        startActivity(new Intent(this, StudentHomeView.class));
    }
}

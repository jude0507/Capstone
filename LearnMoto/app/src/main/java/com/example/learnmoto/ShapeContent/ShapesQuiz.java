package com.example.learnmoto.ShapeContent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.learnmoto.R;
import com.example.learnmoto.Student.StudentHomeView;

public class ShapesQuiz extends AppCompatActivity {

    DrawerLayout drawerLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shapes_quiz);
        drawerLayout = findViewById(R.id.mydrawer_layout);
    }

    public void clickmenu(View view) {
        ShapesRead.openDrawer(drawerLayout);
    }
    public void clicklayout(View view) {
        ShapesRead.closeDrawer(drawerLayout);
    }
    public void readme(View view){
        ShapesRead.redirectActivity(this, ShapesRead.class);
    }
    public void watchme(View view){
        ShapesRead.redirectActivity(this, ShapesWatch.class);
    }
    public void takeaquiz(View view){
        recreate();
    }

    protected void onPause() {
        super.onPause();

        ShapesRead.closeDrawer(drawerLayout);
    }

    public void BacktoStudentHome(View view){
        startActivity(new Intent(this, StudentHomeView.class));
    }
}
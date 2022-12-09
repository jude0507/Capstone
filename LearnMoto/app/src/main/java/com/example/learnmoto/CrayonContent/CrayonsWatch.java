package com.example.learnmoto.CrayonContent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.learnmoto.R;
import com.example.learnmoto.Student.StudentHomeView;

public class CrayonsWatch extends AppCompatActivity {

    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crayons_watch);

        drawerLayout = findViewById(R.id.mydrawer_layout);
    }

    public void clickmenu(View view) {
        CrayonsRead.openDrawer(drawerLayout);
    }
    public void clicklayout(View view) {
        CrayonsRead.closeDrawer(drawerLayout);
    }
    public void readme(View view){
        CrayonsRead.redirectActivity(this, CrayonsRead.class);
    }
    public void watchme(View view){
        recreate();
    }
    public void takeaquiz(View view){
        CrayonsRead.redirectActivity(this, CrayonsWatch.class);
    }

    protected void onPause() {
        super.onPause();

        CrayonsRead.closeDrawer(drawerLayout);
    }

    public void BacktoStudentHome(View view){
        startActivity(new Intent(this, StudentHomeView.class));
    }

}
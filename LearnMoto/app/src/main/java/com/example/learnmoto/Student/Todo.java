package com.example.learnmoto.Student;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.learnmoto.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Todo extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo);

        bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setSelectedItemId(R.id.todo);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(), StudentHomeView.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.todo:
                        return true;

                    case R.id.settings:
                        startActivity(new Intent(getApplicationContext(), StudentSettings.class));
                        overridePendingTransition(0,0);
                        return true;
                }

                return false;
            }
        });

    }
}
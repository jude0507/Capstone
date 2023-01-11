package com.example.learnmoto.Student;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;

import com.example.learnmoto.Kinder.English.KinderEnglish;
import com.example.learnmoto.Nursery.English.NurseryEnglish;
import com.example.learnmoto.Preparatory.English.PreparatoryEnglish;
import com.example.learnmoto.R;
import com.example.learnmoto.Student.Alphabet;
import com.example.learnmoto.Student.StudentHomeView;

public class PronounceAlphabet extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pronounce_alphabet);
        
        String[] alphabet = new String[26];
        for (int i = 0, j = 65; i < 26; i++, j++){
            alphabet[i] = Character.toString((char)j);
        }

        GridView gridView = findViewById(R.id.grid);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(),
                android.R.layout.simple_list_item_1, alphabet);
        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), Alphabet.class);
                intent.putExtra("alphabets", alphabet[position]);
                startActivity(intent);
            }
        });

    }

    public void English(View view) {
        BackPressed();
    }

    private void BackPressed(){
        if (StudentHomeView.level.equals("Nursery")){
            startActivity(new Intent(this, NurseryEnglish.class));
        }else if (StudentHomeView.level.equals("Kinder")){
            startActivity(new Intent(this, KinderEnglish.class));
        }else{
            startActivity(new Intent(this, PreparatoryEnglish.class));
        }
    }

    @Override
    public void onBackPressed() {
        BackPressed();
    }
}
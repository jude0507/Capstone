package com.example.learnmoto.Student;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;

import com.example.learnmoto.AlphabetArrayList;
import com.example.learnmoto.GridViewAdapter;
import com.example.learnmoto.Kinder.English.KinderEnglish;
import com.example.learnmoto.Model.WordAlphabetModel;
import com.example.learnmoto.Nursery.English.NurseryEnglish;
import com.example.learnmoto.Preparatory.English.PreparatoryEnglish;
import com.example.learnmoto.R;
import com.example.learnmoto.Student.Alphabet;
import com.example.learnmoto.Student.StudentHomeView;

public class PronounceAlphabet extends AppCompatActivity implements AdapterView.OnItemClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pronounce_alphabet);

        GridView gridView = findViewById(R.id.grid);
        GridViewAdapter adapter = new GridViewAdapter(PronounceAlphabet.this,
                new AlphabetArrayList().setListData());
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(this);

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

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        WordAlphabetModel wordAlphabetModel = (WordAlphabetModel) parent.getItemAtPosition(position);
        Intent intent = new Intent(getApplicationContext(), Alphabet.class);
        intent.putExtra("alphabets", wordAlphabetModel.getLetterName());
        startActivity(intent);
    }
}
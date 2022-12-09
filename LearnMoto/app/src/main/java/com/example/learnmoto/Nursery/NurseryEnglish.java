package com.example.learnmoto.Nursery;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.learnmoto.AlphabetContent.AlphabetQuiz;
import com.example.learnmoto.AlphabetContent.AlphabetWatch;
import com.example.learnmoto.MainActivity;
import com.example.learnmoto.R;
import com.example.learnmoto.Student.StudentHomeView;

import me.biubiubiu.justifytext.library.JustifyTextView;

public class NurseryEnglish extends AppCompatActivity {

    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nursery_english);



        drawerLayout = findViewById(R.id.mydrawer_layout);


    }
    public void clickmenu(View view) {
        openDrawer(drawerLayout);
    }

    public static void openDrawer(DrawerLayout drawerLayout) {
        drawerLayout.openDrawer(GravityCompat.START);
    }

    public void clicklayout(View view) {
        closeDrawer(drawerLayout);
    }

    public static void closeDrawer(DrawerLayout drawerLayout) {

        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }

    public void readme(View view){
        //DialogWindows();
        recreate();


    }
    public void watchme(View view){
        redirectActivity(this, AlphabetWatch.class);
    }
    public void takeaquiz(View view){
        redirectActivity(this, AlphabetQuiz.class);
    }

    public static void redirectActivity(Activity activity, Class myclass) {

        Intent intent = new Intent(activity,myclass);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);

    }

    protected void onPause() {
        super.onPause();

        closeDrawer(drawerLayout);
    }

    public void BacktoStudentHome(View view){
        startActivity(new Intent(this, StudentHomeView.class));
    }

    public void story(View view) {
        startActivity(new Intent(this, Stories.class));
    }

    public void pdf(View view) {
    }
}
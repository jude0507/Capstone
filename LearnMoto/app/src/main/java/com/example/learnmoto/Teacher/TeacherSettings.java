package com.example.learnmoto.Teacher;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.learnmoto.CheckConnection.NetworkChangeListener;
import com.example.learnmoto.Parent.ParentLogin;
import com.example.learnmoto.Parent.ParentView;
import com.example.learnmoto.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class TeacherSettings extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    NetworkChangeListener networkChangeListener = new NetworkChangeListener();
    Button logoutbtn, confirmLogout;
    LinearLayout expandableView2, expandableLinear2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_settings);

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        logoutbtn = findViewById(R.id.arrow_btn_logout);
        confirmLogout = findViewById(R.id.confirmLogout);
        expandableView2 = findViewById(R.id.expandableLayout2);
        expandableLinear2 = findViewById(R.id.layout2);
        bottomNavigationView.setSelectedItemId(R.id.settings);

        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {

            //Condition of what activity is selected
            switch (item.getItemId()){
                case R.id.home:
                    startActivity(new Intent(getApplicationContext(), TeacherView.class));
                    overridePendingTransition(0,0);
                    return true;

                case R.id.announcements:
                    startActivity(new Intent(getApplicationContext(), Announcement.class));
                    overridePendingTransition(0,0);
                    return true;

                case R.id.profile:
                    startActivity(new Intent(getApplicationContext(), TeacherProfile.class));
                    overridePendingTransition(0,0);
                    return true;

                case R.id.settings:
                    return true;
            }

            return false;
        });

        logoutbtn.setOnClickListener(v -> {
            if (expandableView2.getVisibility() == View.GONE) {
                TransitionManager.beginDelayedTransition(expandableLinear2, new AutoTransition());
                expandableView2.setVisibility(View.VISIBLE);
                logoutbtn.setBackgroundResource(R.drawable.ic_arrow_up);
            }else{
                TransitionManager.beginDelayedTransition(expandableLinear2, new AutoTransition());
                expandableView2.setVisibility(View.GONE);
                logoutbtn.setBackgroundResource(R.drawable.ic_arrow_down);
            }
        });

        confirmLogout.setOnClickListener(v -> {
            Toast.makeText(TeacherSettings.this, "Logout Success", Toast.LENGTH_SHORT).show();
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(TeacherSettings.this, TeacherLogin.class));
        });

    }
    @Override
    protected void onStart() {
        IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkChangeListener, intentFilter);
        super.onStart();
    }

    @Override
    protected void onStop() {
        unregisterReceiver(networkChangeListener);
        super.onStop();
    }

    public void CancelFunction(View view) {
        TransitionManager.beginDelayedTransition(expandableLinear2, new AutoTransition());
        expandableView2.setVisibility(View.GONE);
        logoutbtn.setBackgroundResource(R.drawable.ic_arrow_down);
    }
}
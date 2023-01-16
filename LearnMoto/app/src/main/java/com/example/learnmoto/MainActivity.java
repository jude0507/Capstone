package com.example.learnmoto;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.learnmoto.CheckConnection.NetworkChangeListener;
import com.example.learnmoto.Parent.ParentLogin;
import com.example.learnmoto.Student.StudentLogin;
import com.example.learnmoto.Teacher.TeacherLogin;

public class MainActivity extends AppCompatActivity {
    LinearLayout clickableLayout;
    Button cancel,ok;
    RadioGroup radioGroup;
    RadioButton  radioid;
    androidx.appcompat.app.AlertDialog.Builder builder;
    androidx.appcompat.app.AlertDialog alertDialog;
    //Button tryagain;
    ViewPager viewPager;

    NetworkChangeListener networkChangeListener = new NetworkChangeListener();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        clickableLayout = findViewById(R.id.userType);

    }

    private void UserType(){
        AlertDialog.Builder myBuilder = new AlertDialog.Builder(MainActivity.this);
        LayoutInflater myinflater = getLayoutInflater();
        View mydialogView = myinflater.inflate(R.layout.user_type,null);
        myBuilder.setCancelable(false);
        myBuilder.setView(mydialogView);

        radioGroup = mydialogView.findViewById(R.id.typeofUsers);
        cancel = mydialogView.findViewById(R.id.Cancel);
        ok = mydialogView.findViewById(R.id.Ok);
        final AlertDialog alertDialog = myBuilder.create();
        alertDialog.show();

        cancel.setOnClickListener(v -> alertDialog.dismiss());

        ok.setOnClickListener(v -> {

            int id = radioGroup.getCheckedRadioButtonId();
            radioid = mydialogView.findViewById(id);

            if (radioid.getText().toString().equals("Teacher")){
                Toast.makeText(MainActivity.this, "You've login as Teacher", Toast.LENGTH_SHORT).show();
                alertDialog.dismiss();
                stopService(new Intent(this, AudioService.class));
                startActivity(new Intent(MainActivity.this, TeacherLogin.class));
                finish();
            }else if (radioid.getText().toString().equals("Student")){
                Toast.makeText(MainActivity.this, "You've login as Student", Toast.LENGTH_SHORT).show();
                alertDialog.dismiss();
                startService(new Intent(MainActivity.this, AudioService.class));
                startActivity(new Intent(MainActivity.this, StudentLogin.class));
                finish();
            }else{
                Toast.makeText(MainActivity.this, "You've login as Parent", Toast.LENGTH_SHORT).show();
                alertDialog.dismiss();
                stopService(new Intent(this, AudioService.class));
                startActivity(new Intent(MainActivity.this, ParentLogin.class));
                finish();
            }
        });
    }

    @Override
    protected void onStart() {
        IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkChangeListener, intentFilter);
        clickableLayout.setOnClickListener(v -> {
            UserType();

        });
        //startService(new Intent(getApplicationContext(), AudioService.class));
        super.onStart();
    }

    @Override
    protected void onStop() {
        //stopService(new Intent(getApplicationContext(), AudioService.class));
        unregisterReceiver(networkChangeListener);
        super.onStop();
    }

    int pressed = 0;
    @Override
    public void onBackPressed() {
        pressed++;
        if (pressed == 1)
            super.onBackPressed();
    }

    public void guideBtn(View view) {
        viewPager();
    }

    private void viewPager(){
        startActivity(new Intent(getApplicationContext(),UserGuide.class));
    }
}
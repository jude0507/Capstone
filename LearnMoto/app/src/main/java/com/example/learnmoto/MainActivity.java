package com.example.learnmoto;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.learnmoto.Parent.ParentLogin;
import com.example.learnmoto.Student.StudentLogin;
import com.example.learnmoto.Teacher.TeacherLogin;

public class MainActivity extends AppCompatActivity {

    LinearLayout clickableLayout;
    Button cancel,ok;
    RadioGroup radioGroup;
    RadioButton  radioid;
    Button tryagain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        clickableLayout = findViewById(R.id.userType);

        CheckConnection();

    }

    public void CheckConnection(){
        ConnectivityManager connectivityManager = (ConnectivityManager) getApplicationContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if(networkInfo == null || !networkInfo.isConnected() || !networkInfo.isAvailable()){

            Dialog dialog = new Dialog(this);
            //content view
            dialog.setContentView(R.layout.check_internet_dialog);
            //outside touch
            dialog.setCanceledOnTouchOutside(false);
            //set width and height
            dialog.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT,
                    WindowManager.LayoutParams.WRAP_CONTENT);
            //transparent background
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            //set animation
            dialog.getWindow().getAttributes().windowAnimations = android.R.style.Animation_Dialog;

            //initialize the button
            tryagain = dialog.findViewById(R.id.try_again);
            //perform action
            tryagain.setOnClickListener(v -> {
                //startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                recreate();
                dialog.dismiss();
                //UserType();
            });
            dialog.show();
        }else{
            clickableLayout.setOnClickListener(v -> UserType());
        }
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
                startActivity(new Intent(MainActivity.this, TeacherLogin.class));
            }else if (radioid.getText().toString().equals("Student")){
                Toast.makeText(MainActivity.this, "You've login as Student", Toast.LENGTH_SHORT).show();
                alertDialog.dismiss();
                startActivity(new Intent(MainActivity.this, StudentLogin.class));
            }else{
                Toast.makeText(MainActivity.this, "You've login as Parent", Toast.LENGTH_SHORT).show();
                alertDialog.dismiss();
                startActivity(new Intent(MainActivity.this, ParentLogin.class));
            }
        });
    }

}
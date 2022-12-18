package com.example.learnmoto.Teacher;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.learnmoto.MainActivity;
import com.example.learnmoto.CheckConnection.NetworkChangeListener;
import com.example.learnmoto.R;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Arrays;

public class TeacherLogin extends AppCompatActivity {

    EditText Teacher_ID, Teacher_Pass;
    public static String teacher_ID, teacher_pass, teacher_name, teacher_address, teacher_assignLevel;

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    NetworkChangeListener networkChangeListener = new NetworkChangeListener();

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_login);

        Teacher_ID = findViewById(R.id.teacherID);
        Teacher_Pass = findViewById(R.id.teacherPassword);

        Teacher_Pass.setOnTouchListener((v, event) -> {
            final int DrawableRight = 2;
            if (event.getAction() == MotionEvent.ACTION_UP){
                if (event.getRawX() >= (Teacher_Pass.getRight() - Teacher_Pass.getCompoundDrawables()
                [DrawableRight].getBounds().width())){
                    Teacher_Pass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
            }else{
                Teacher_Pass.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }
            return false;
        });


    }

    public void TeacherReg(View view) {
        startActivity(new Intent(this, TeacherRegistration.class));
    }

    public void BacktoMain(View view) {
        startActivity(new Intent(this, MainActivity.class));
    }

    public void TeacherLoginFunction(View view) {
        LoginTeacher();
    }

    private void LoginTeacher() {
        if (!Teacher_ID.getText().toString().trim().isEmpty() && !Teacher_Pass.getText().toString().trim().isEmpty()){
            DocumentReference documentReference = db.collection("Teacher").document(Teacher_ID.getText().toString());
            documentReference.get().addOnSuccessListener(documentSnapshot -> {
                if (documentSnapshot.exists()) {
                    teacher_ID = documentSnapshot.getString("teacher_ID").toString();
                    teacher_pass = documentSnapshot.getString("teacher_pass").toString();
                    teacher_name = documentSnapshot.getString("teacher_name").toString();
                    teacher_address = documentSnapshot.getString("teacher_address").toString();
                    //teacher_assignLevel = documentSnapshot.getString("assignLevel").toString();
                    //teacher_assignLevel = String.valueOf(documentSnapshot.getString("assignLevel"));
                    //teacher_address = documentSnapshot.getString("teacher_address").toString();
                    if (Teacher_ID.getText().toString().equals(teacher_ID) && Teacher_Pass.getText().toString().equals(teacher_pass)){
                        Toast.makeText(TeacherLogin.this, "Login Successfully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(TeacherLogin.this, TeacherView.class));
                    }else {
                        Toast.makeText(TeacherLogin.this, "Credential not match", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(TeacherLogin.this, "You don't have an account", Toast.LENGTH_SHORT).show();
                }
            });
        }else{
            if (Teacher_ID.getText().toString().isEmpty() && Teacher_Pass.getText().toString().isEmpty()){
                Teacher_ID.setError("Required Field");
                Teacher_Pass.setError("Required Field");
                Toast.makeText(TeacherLogin.this, "Username and Password Required", Toast.LENGTH_LONG).show();
            }else if (Teacher_ID.getText().toString().isEmpty()){
                Teacher_ID.setError("Required Field");
            }else{
                Teacher_Pass.setError("Required Field");
            }

        }
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
}
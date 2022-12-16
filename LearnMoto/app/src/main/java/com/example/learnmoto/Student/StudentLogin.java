package com.example.learnmoto.Student;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.learnmoto.MainActivity;
import com.example.learnmoto.CheckConnection.NetworkChangeListener;
import com.example.learnmoto.R;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class StudentLogin extends AppCompatActivity {

    public static String studID, studPass, sName, sBirthday, sGender, sLevel, sGuardian, sAddress;
    //public static final String PREFS_NAME = "Preferences";
    public static final String Username = "sID";
    public static final String Password = "sPassword";
    public static final String StudentName = "sName";
    public static final String StudentLevel = "sLevel";

    NetworkChangeListener networkChangeListener = new NetworkChangeListener();

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference collectionReference = db.collection("Student");

    EditText studentID, studentPass;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    LinearLayout loginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_login);

        studentID = findViewById(R.id.my_studID);
        studentPass = findViewById(R.id.my_studpass);
        loginBtn = findViewById(R.id.studentHome);


        sharedPreferences = getSharedPreferences("Preferences", MODE_PRIVATE);

        if (sharedPreferences.contains(Username)){
            startActivity(new Intent(StudentLogin.this, StudentHomeView.class));
        }

        //stayLogin();

    }

    public void studentReg(View view) {
        startActivity(new Intent(this, StudentRegistration.class));
    }

    public void studentHomeViewLogin(View view) {
//        sharedPreferences = getSharedPreferences("Preferences", MODE_PRIVATE);
//        editor = sharedPreferences.edit();
//        editor.putBoolean("hasLoggedIn", true);
//        editor.apply();
        LoginFunction();
    }

    private void LoginFunction(){
        if (!studentID.getText().toString().trim().isEmpty() && !studentPass.getText().toString().trim().isEmpty()){
            DocumentReference documentReference = db.collection("Student").document(studentID.getText().toString());
            documentReference.get().addOnSuccessListener(documentSnapshot -> {
                if (documentSnapshot.exists()) {
                    //String fetchLevel;
                    studID = documentSnapshot.getString("sID").toString();
                    studPass = documentSnapshot.getString("sPassword").toString();
                    sLevel = documentSnapshot.getString("sLevel").toString();
                    sName = documentSnapshot.getString("sName").toString();
                    sBirthday = documentSnapshot.getString("sBirthday").toString();
                    sGuardian = documentSnapshot.getString("sGuardian").toString();
                    sGender = documentSnapshot.getString("sGender").toString();
                    sAddress = documentSnapshot.getString("sAddress").toString();

                    if (studentID.getText().toString().equals(studID) && studentPass.getText().toString().equals(studPass)){
                        editor = sharedPreferences.edit();
                        editor.putString(Username, studID);
                        editor.putString(Password, studPass);
                        editor.putString(StudentName, sName);
                        editor.putString("sLevel", sLevel);
                        editor.putString("sBirthday", sBirthday);
                        editor.putString("sGuardian", sGuardian);
                        editor.putString("sGender", sGender);
                        editor.putString("sAddress", sAddress);
                        editor.commit();
                        if (sLevel.equals("Nursery")){
                            Toast.makeText(this, "Nursery", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(this, StudentHomeView.class));
                        }else if(sLevel.equals("Kinder")){
                            Toast.makeText(this, "Kinder", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(this, StudentHomeView.class));
                        }else{
                            Toast.makeText(this, "Preparatory", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(this, StudentHomeView.class));
                        }


                    }else {
                        Toast.makeText(StudentLogin.this, "Credential not match", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(StudentLogin.this, "You don't have an account", Toast.LENGTH_SHORT).show();
                }
            });
        }else{
            if (studentID.getText().toString().isEmpty() && studentPass.getText().toString().isEmpty()){
                studentID.setError("Required Field");
                studentPass.setError("Required Field");
                Toast.makeText(StudentLogin.this, "Username and Password Required", Toast.LENGTH_LONG).show();
            }else if (studentID.getText().toString().isEmpty()){
                studentID.setError("Required Field");
            }else{
                studentPass.setError("Required Field");
            }

        }

    }

    public void BacktoMain(View view) {
        startActivity(new Intent(this, MainActivity.class));
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
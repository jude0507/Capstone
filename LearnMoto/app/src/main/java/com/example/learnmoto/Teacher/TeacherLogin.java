package com.example.learnmoto.Teacher;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.learnmoto.MainActivity;
import com.example.learnmoto.CheckConnection.NetworkChangeListener;
import com.example.learnmoto.R;
import com.example.learnmoto.ShowPassword;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class TeacherLogin extends AppCompatActivity {

    EditText Teacher_ID, Teacher_Pass;
    public static String teacher_ID, teacher_pass, teacher_name, teacher_address, teacher_phone, teacher_email_status, teacher_email;
    ProgressDialog progressDialog;

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    NetworkChangeListener networkChangeListener = new NetworkChangeListener();

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_login);

        Teacher_ID = findViewById(R.id.teacherID);
        Teacher_Pass = findViewById(R.id.teacherPassword);

        ShowPassword showPassword = new ShowPassword();
        showPassword.ShowPassword(Teacher_Pass);

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
            progressDialog = new ProgressDialog(this);
            progressDialog.setCanceledOnTouchOutside(false);
            final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
            progressDialog.show();
            progressDialog.setContentView(R.layout.progress_dialog_account_checking);
            progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            DocumentReference documentReference = db.collection("Teacher").document(Teacher_ID.getText().toString());
            documentReference.get().addOnSuccessListener(documentSnapshot -> {
                if (documentSnapshot.exists()) {
                    teacher_ID = documentSnapshot.getString("teacher_ID").toString();
                    teacher_pass = documentSnapshot.getString("teacher_pass").toString();
                    teacher_name = documentSnapshot.getString("teacher_name").toString();
                    teacher_address = documentSnapshot.getString("teacher_address").toString();
                    teacher_phone = documentSnapshot.getString("teacher_phone").toString();
                    teacher_email = documentSnapshot.getString("teacher_email").toString();
                    firebaseAuth.signInWithEmailAndPassword(teacher_email, teacher_pass)
                            .addOnCompleteListener(task -> {
                                if (task.isSuccessful()) {
                                    if (firebaseAuth.getCurrentUser().isEmailVerified()){
                                        if (Teacher_ID.getText().toString().equals(teacher_ID) && Teacher_Pass.getText().toString().equals(teacher_pass)){
                                            DocumentReference updateReference = db.collection("Teacher").document(TeacherLogin.teacher_ID);
                                            updateReference.update("teacher_email_status", "Verified");
                                            progressDialog.dismiss();
                                            Toast.makeText(TeacherLogin.this, "Login Successfully", Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(TeacherLogin.this, TeacherView.class));
                                        }else {
                                            progressDialog.dismiss();
                                            Toast.makeText(TeacherLogin.this, "Credential not match", Toast.LENGTH_SHORT).show();
                                        }
                                    }else{
                                        progressDialog.dismiss();
                                        Toast.makeText(TeacherLogin.this, "Please verify your email", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }else {
                    progressDialog.dismiss();
                    Toast.makeText(TeacherLogin.this, "You don't have an account", Toast.LENGTH_SHORT).show();
                }
            });
        }else{
            if (Teacher_ID.getText().toString().isEmpty() && Teacher_Pass.getText().toString().isEmpty()){
                Teacher_ID.setError("Required Field");
                Teacher_Pass.setError("Required Field");
                Toast.makeText(TeacherLogin.this, "Username and Password Required", Toast.LENGTH_LONG).show();
            }else if (Teacher_ID.getText().toString().isEmpty()){
                Teacher_ID.requestFocus();
                Teacher_ID.setError("Required Field");
            }else{
                Teacher_Pass.requestFocus();
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
package com.example.learnmoto.Teacher;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
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
import com.example.learnmoto.Model.TeacherInfo;
import com.example.learnmoto.CheckConnection.NetworkChangeListener;
import com.example.learnmoto.R;
import com.example.learnmoto.ShowPassword;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class TeacherRegistration extends AppCompatActivity {

    EditText TeacherName, TeacherAddress, TeacherPhone, TeacherID, TeacherPassword, confirmPass;
    FirebaseFirestore firestore = FirebaseFirestore.getInstance();

    NetworkChangeListener networkChangeListener = new NetworkChangeListener();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_registration);

        TeacherName = findViewById(R.id.TeacherName);
        TeacherAddress = findViewById(R.id.TeacherAddress);
        TeacherPhone = findViewById(R.id.TeacherPhone);
        TeacherID = findViewById(R.id.TeacherID);
        TeacherPassword = findViewById(R.id.TeacherPass);
        confirmPass = findViewById(R.id.ConfirmPass);

        ShowPassword showPassword = new ShowPassword();
        showPassword.ShowPassword(TeacherPassword);
        showPassword.ShowPassword(confirmPass);

    }

    public void RegistrationFunction(View view) {

        String Teacher_Name = TeacherName.getText().toString();
        String Teacher_Address = TeacherAddress.getText().toString();
        String Teacher_Phone = TeacherPhone.getText().toString();
        String Teacher_ID = TeacherID.getText().toString();
        String Teacher_Pass = TeacherPassword.getText().toString();
        String ConfirmPass = confirmPass.getText().toString();

        if (!Teacher_Name.isEmpty() && !Teacher_Address.isEmpty() &&
                !Teacher_ID.isEmpty() && !Teacher_Pass.isEmpty()){
            if (Teacher_Phone.length() == 11) {
                if (Teacher_Pass.length() >= 6){
                    if (ConfirmPass.equals(Teacher_Pass)){
                        ProgressDialog progressDialog = new ProgressDialog(this);
                        progressDialog.show();
                        progressDialog.setContentView(R.layout.progress_dialog_registration);
                        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                        DocumentReference teacherReference = firestore.collection("Teacher").document(Teacher_ID);
                        teacherReference.get().addOnSuccessListener(documentSnapshot -> {
                            if (documentSnapshot.exists()){
                                progressDialog.dismiss();
                                Toast.makeText(TeacherRegistration.this, "Teacher ID has been registered", Toast.LENGTH_SHORT).show();
                            }else{
                                TeacherInfo teacherInfo = new TeacherInfo();
                                teacherInfo.setTeacher_name(Teacher_Name);
                                teacherInfo.setTeacher_address(Teacher_Address);
                                teacherInfo.setTeacher_phone(Teacher_Phone);
                                teacherInfo.setTeacher_ID(Teacher_ID);
                                teacherInfo.setTeacher_pass(Teacher_Pass);
                                progressDialog.dismiss();
                                firestore.collection("Teacher").document(Teacher_ID).set(teacherInfo);
                                Toast.makeText(TeacherRegistration.this, "Registration Successfully", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(TeacherRegistration.this, TeacherLogin.class));
                            }
                        });
                    }else{
                        confirmPass.setError("Not Matched");
                    }
                }else{
                    Toast.makeText(this, "Password must have 6 or more characters", Toast.LENGTH_SHORT).show();
                }
            }else{
                TeacherPhone.setError("This field contain 11 digit");
            }
        }else{
            setErrors();
        }
    }

    public void TeacherLogin(View view) {
        startActivity(new Intent(this, TeacherLogin.class));
    }

    void setErrors(){
        TeacherName.setError("Required Field");
        TeacherAddress.setError("Required Field");
        TeacherPhone.setError("Required Field");
        TeacherID.setError("Required Field");
        TeacherPassword.setError("Required Field");
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
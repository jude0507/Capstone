package com.example.learnmoto.Teacher;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.example.learnmoto.Model.TeacherModel;
import com.example.learnmoto.CheckConnection.NetworkChangeListener;
import com.example.learnmoto.R;
import com.example.learnmoto.ShowPassword;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class TeacherRegistration extends AppCompatActivity {

    EditText TeacherName, TeacherAddress, TeacherPhone, TeacherEmail, TeacherID, TeacherPassword, confirmPass;
    FirebaseFirestore firestore = FirebaseFirestore.getInstance();

    NetworkChangeListener networkChangeListener = new NetworkChangeListener();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_registration);

        TeacherName = findViewById(R.id.TeacherName);
        TeacherAddress = findViewById(R.id.TeacherAddress);
        TeacherPhone = findViewById(R.id.TeacherPhone);
        TeacherEmail = findViewById(R.id.TeacherEmail);
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
        String Teacher_Email = TeacherEmail.getText().toString();
        String Teacher_ID = TeacherID.getText().toString();
        String Teacher_Pass = TeacherPassword.getText().toString();
        String ConfirmPass = confirmPass.getText().toString();

        if (!Teacher_Name.isEmpty() && !Teacher_Address.isEmpty() &&
                !Teacher_ID.isEmpty() && !Teacher_Pass.isEmpty()){
            if (Teacher_Phone.length() == 11 && Teacher_Phone.matches("^(09)\\d{9}")){
                if (!Teacher_Email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(Teacher_Email).matches()){
                    if (Teacher_Pass.length() >= 6){
                        if (ConfirmPass.equals(Teacher_Pass)){
                            AlertDialog.Builder alBuilder = new AlertDialog.Builder(this);
                            alBuilder.setTitle("Confirmation Message")
                                    .setMessage("All data has been filled correctly");
                            alBuilder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    ProgressDialog progressDialog = new ProgressDialog(TeacherRegistration.this);
                                    progressDialog.show();
                                    progressDialog.setCanceledOnTouchOutside(false);
                                    progressDialog.setContentView(R.layout.progress_dialog_registration);
                                    progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                                    final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                                    firebaseAuth.createUserWithEmailAndPassword(Teacher_Email, Teacher_Pass)
                                            .addOnCompleteListener(task -> {
                                                if (task.isSuccessful()){
                                                    firebaseAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(task1 -> {
                                                        if (task1.isSuccessful()){
                                                            DocumentReference teacherReference = firestore.collection("Teacher").document(Teacher_ID);
                                                            teacherReference.get().addOnSuccessListener(documentSnapshot -> {
                                                                if (documentSnapshot.exists()){
                                                                    progressDialog.dismiss();
                                                                    Toast.makeText(TeacherRegistration.this, "Teacher ID has been registered", Toast.LENGTH_SHORT).show();
                                                                }else{
                                                                    TeacherModel teacherInfo = new TeacherModel();
                                                                    teacherInfo.setTeacher_name(Teacher_Name);
                                                                    teacherInfo.setTeacher_address(Teacher_Address);
                                                                    teacherInfo.setTeacher_phone(Teacher_Phone);
                                                                    teacherInfo.setTeacher_email(Teacher_Email);
                                                                    teacherInfo.setTeacher_ID(Teacher_ID);
                                                                    teacherInfo.setTeacher_pass(Teacher_Pass);
                                                                    firestore.collection("Teacher").document(Teacher_ID).set(teacherInfo);
                                                                    progressDialog.dismiss();
                                                                    Toast.makeText(TeacherRegistration.this, "User Registered Successfully! Please Verify your email", Toast.LENGTH_SHORT).show();
                                                                    Toast.makeText(TeacherRegistration.this, "Please check your inbox/spam message", Toast.LENGTH_LONG).show();
                                                                    startActivity(new Intent(TeacherRegistration.this, TeacherLogin.class));
                                                                }
                                                            });
                                                        }else{
                                                            progressDialog.dismiss();
                                                            Toast.makeText(TeacherRegistration.this, task1.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                                        }
                                                    });

                                                }else{
                                                    progressDialog.dismiss();
                                                    Toast.makeText(TeacherRegistration.this, "Registration Failed! Try Again", Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                }
                            });
                            alBuilder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());
                            alBuilder.show();
                        }else{
                            confirmPass.requestFocus();
                            confirmPass.setError("Not Matched");
                        }
                    }else{
                        Toast.makeText(this, "Password must have 6 or more characters", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    TeacherEmail.requestFocus();
                    TeacherEmail.setError("Please provide valid email");

                }
            }else{
                TeacherPhone.requestFocus();
                TeacherPhone.setError("Invalid Phone Number");
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

    //https://www.youtube.com/watch?v=Z-RE1QuUWPg&list=PL65Ccv9j4eZJ_bg0TlmxA7ZNbS8IMyl5i&index=3
    //https://www.youtube.com/watch?v=15WRCpH-VG0&list=PL65Ccv9j4eZJ_bg0TlmxA7ZNbS8IMyl5i&index=6
    //https://www.youtube.com/watch?v=2r-8xXXgpfU

}
package com.example.learnmoto.Student;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.learnmoto.Model.StudentInfo;
import com.example.learnmoto.R;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class StudentRegistration extends AppCompatActivity {

    private Spinner yearlevelSpinner;
    private EditText studentID, studentPass, studentName, studentAddress, studentBday, guardianName;
    private RadioGroup radioGroup;
    Button btnStudentSignUp;
    int age;

    FirebaseFirestore firestore = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_registration);

        yearlevelSpinner = findViewById(R.id.year_level);
        studentBday = findViewById(R.id.et_birthday);
        studentName = findViewById(R.id.studentName);
        studentAddress = findViewById(R.id.studentAddress);
        radioGroup = findViewById(R.id.radiogrp);
        studentID = findViewById(R.id.studentID);
        studentPass = findViewById(R.id.studentPass);
        guardianName = findViewById(R.id.guardianName);
        btnStudentSignUp = findViewById(R.id.btnStudentSignUp);

        List<String> yearlvl = new ArrayList<>();
        yearlvl.add(0, "Choose Level");
        yearlvl.add("Kinder");
        yearlvl.add("Nursery");
        yearlvl.add("Preparatory");

        ArrayAdapter<String> yearlvlAdapter;
        yearlvlAdapter = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,yearlvl);
        yearlevelSpinner.setAdapter(yearlvlAdapter);

        //Spinner for Year Level
        yearlevelSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (parent.getItemAtPosition(position).equals("Choose Level"))
                {
                    // nothing to do
                }
                else
                {
                    //once item selected
                    String itemselect = parent.getItemAtPosition(position).toString();
                    Toast.makeText(StudentRegistration.this, itemselect, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        final Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        studentBday.setOnClickListener(v -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(v.getContext(),
                    datepickerListener, year, month, day);
            datePickerDialog.getDatePicker().setMaxDate(new Date().getTime());
            datePickerDialog.show();
        });

    }

    private final DatePickerDialog.OnDateSetListener datepickerListener = new DatePickerDialog.OnDateSetListener(){

        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            Calendar calendar = Calendar.getInstance();
            month = month + 1;
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, month);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            String dateFormat = month+"/"+dayOfMonth+"/"+ year;
            studentBday.setText(dateFormat);
            age = CalculateAge(calendar.getTimeInMillis());
        }
    };

    int CalculateAge(long date){
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(date);

        Calendar today = Calendar.getInstance();
        int computeAge = (today.get(Calendar.YEAR) - cal.get(Calendar.YEAR)) + 1;
        if (today.get(Calendar.DAY_OF_MONTH) < cal.get(Calendar.DAY_OF_MONTH)){
            computeAge--;
        }
        return computeAge;
    }

    public void StudentLoginPage(View view) {
        startActivity(new Intent(StudentRegistration.this, StudentLogin.class));
    }

    public void SignUpFunction(View view) {
        int selectedID = radioGroup.getCheckedRadioButtonId();
        RadioButton radioButton = findViewById(selectedID);
        String student_Name = studentName.getText().toString().trim();
        String student_Address = studentAddress.getText().toString().trim();
        String student_Gender = radioButton.getText().toString();
        String student_Birthday = studentBday.getText().toString().trim();
        String student_Level = yearlevelSpinner.getSelectedItem().toString();
        String student_guardian = guardianName.getText().toString().trim();
        String student_ID = studentID.getText().toString().trim();
        String student_Pass = studentPass.getText().toString().trim();
        String student_age = String.valueOf(age);

        AlertDialog.Builder alBuilder = new AlertDialog.Builder(this);
        alBuilder.setTitle("Confirmation Message")
                .setMessage("All data are set to register.");
        alBuilder.setPositiveButton("Confirm", (dialog, which) -> {

            if(!student_Name.isEmpty() && !student_Address.isEmpty() && !student_Gender.isEmpty() &&
                    !student_Birthday.isEmpty() && !student_Level.isEmpty() && !student_guardian.isEmpty()
                    && !student_ID.isEmpty() && !student_Pass.isEmpty()){
                DocumentReference documentReference = firestore.collection("Student").document(student_ID);
                documentReference.get().addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()){
                        Toast.makeText(StudentRegistration.this, "Student ID has been registered", Toast.LENGTH_SHORT).show();
                    }else{
                        if (student_Level.equals("Choose Level")){
                            Toast.makeText(this, "Chose your level", Toast.LENGTH_SHORT).show();
                        }else{
                            StudentInfo studentInfo = new StudentInfo();
                            studentInfo.setsName(student_Name);
                            studentInfo.setsAddress(student_Address);
                            studentInfo.setsAge(student_age);
                            studentInfo.setsGender(student_Gender);
                            studentInfo.setsBirthday(student_Birthday);
                            studentInfo.setsLevel(student_Level);
                            studentInfo.setsGuardian(student_guardian);
                            studentInfo.setsID(student_ID);
                            studentInfo.setsPassword(student_Pass);
                            firestore.collection("Student").document(student_ID).set(studentInfo);
                            Toast.makeText(StudentRegistration.this, "Registration Successfully", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(StudentRegistration.this, StudentLogin.class));
                        }
                    }
                });

            }else{
                studentName.setError("Required Field!");
                studentAddress.setError("Required Field!");
                studentBday.setError("Required Field!");
                guardianName.setError("Required Field!");
                studentID.setError("Required Field!");
                studentPass.setError("Required Field!");
                Toast.makeText(this, "Please Input all Fields", Toast.LENGTH_SHORT).show();
            }

        });
        alBuilder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());
        alBuilder.show();

    }
}
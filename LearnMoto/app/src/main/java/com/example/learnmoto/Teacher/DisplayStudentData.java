package com.example.learnmoto.Teacher;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.learnmoto.Kinder.KinderClassList;
import com.example.learnmoto.Model.StudentModel;
import com.example.learnmoto.Nursery.NurseryClassList;
import com.example.learnmoto.Preparatory.PreparatoryClassList;
import com.example.learnmoto.R;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import de.hdodenhof.circleimageview.CircleImageView;

public class DisplayStudentData extends AppCompatActivity {

    TextView name, level, guardianName, Phone, address, birthday;
    String id, getNameIntent;
    String getSLevel;
    String getGuardianName, getBirthday, getAddress, imageStudent, phoneNumber;
    CircleImageView StudPicture;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_student_data);

        name = findViewById(R.id.name);
        level = findViewById(R.id.level);
        guardianName = findViewById(R.id.guardianName);
        Phone = findViewById(R.id.phone);
        address = findViewById(R.id.Address);
        birthday = findViewById(R.id.bday);
        StudPicture = findViewById(R.id.picture);
        //id = findViewById(R.id.idstudent);

        getNameIntent = getIntent().getStringExtra("studentName");
        id = getIntent().getStringExtra("studentID");
        getSLevel = getIntent().getStringExtra("studentLevel");

        //id.setText(getIntent().getStringExtra("studentID"));
        DisplayData();

    }
    @SuppressLint("NotConstructor")
    public void DisplayData(){
        db.collection("Student").whereEqualTo("sID", id).get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    getGuardianName = "";
                    getBirthday = "";
                    getAddress = "";
                    imageStudent = "";
                    phoneNumber = "";
                    //imageDisplay = "";
                    for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots){
                        StudentModel studentInfo = documentSnapshot.toObject(StudentModel.class);
                        studentInfo.setMyid(documentSnapshot.getId());
                        getGuardianName += studentInfo.getsGuardian();
                        getBirthday += studentInfo.getsBirthday();
                        getAddress += studentInfo.getsAddress();
                        imageStudent += studentInfo.getImageurl();
                        phoneNumber += studentInfo.getGuardianPhone();
                    }
                    name.setText(getNameIntent);
                    level.setText(getSLevel);
                    guardianName.setText(getGuardianName);
                    birthday.setText(getBirthday);
                    address.setText(getAddress);
                    Phone.setText(phoneNumber);

                    Glide.with(DisplayStudentData.this).load(imageStudent).placeholder(R.drawable.ic_user_circle)
                            .into(StudPicture);

                });
    }



    public void BackToTeacherView(View view) {
        if (getSLevel.equals("Kinder")){
            startActivity(new Intent(this, KinderClassList.class));
        }else if (getSLevel.equals("Nursery")){
            startActivity(new Intent(this, NurseryClassList.class));
        }else{
            startActivity(new Intent(this, PreparatoryClassList.class));
        }
    }
}
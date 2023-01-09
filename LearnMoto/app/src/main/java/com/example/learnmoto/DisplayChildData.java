package com.example.learnmoto;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.learnmoto.CheckConnection.NetworkChangeListener;
import com.example.learnmoto.Kinder.KinderClassList;
import com.example.learnmoto.Model.StudentModel;
import com.example.learnmoto.Parent.ParentView;
import com.example.learnmoto.Teacher.DisplayStudentData;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import de.hdodenhof.circleimageview.CircleImageView;

public class DisplayChildData extends AppCompatActivity {

    TextView name, level, guardianName, Phone, address, birthday, math, english;
    String id, getNameIntent;
    String getGuardianName, getBirthday, getAddress, imageStudent, phoneNumber, mathScore, engScore;
    CircleImageView StudPicture;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    NetworkChangeListener networkChangeListener = new NetworkChangeListener();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_child_data);
        name = findViewById(R.id.name);
        level = findViewById(R.id.level);
        math = findViewById(R.id.math);
        english = findViewById(R.id.english);
        guardianName = findViewById(R.id.guardianName);
        Phone = findViewById(R.id.phone);
        address = findViewById(R.id.Address);
        birthday = findViewById(R.id.bday);
        StudPicture = findViewById(R.id.picture);

        getNameIntent = getIntent().getStringExtra("studentName");
        id = getIntent().getStringExtra("studentID");
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
                    mathScore = "";
                    engScore = "";
                    //imageDisplay = "";
                    for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots){
                        StudentModel studentInfo = documentSnapshot.toObject(StudentModel.class);
                        studentInfo.setMyid(documentSnapshot.getId());
                        getGuardianName += studentInfo.getsGuardian();
                        getBirthday += studentInfo.getsBirthday();
                        getAddress += studentInfo.getsAddress();
                        imageStudent += studentInfo.getImageurl();
                        phoneNumber += studentInfo.getGuardianPhone();
                        engScore += studentInfo.getEngScore();
                        mathScore += studentInfo.getMathScore();
                    }
                    name.setText(getNameIntent);
                    guardianName.setText(getGuardianName);
                    birthday.setText(getBirthday);
                    address.setText(getAddress);
                    Phone.setText(phoneNumber);
                    math.setText(mathScore);
                    english.setText(engScore);

                    Glide.with(DisplayChildData.this).load(imageStudent).placeholder(R.drawable.ic_user_circle)
                            .into(StudPicture);

                });
    }

    public void BackToParentView(View view) {
        startActivity(new Intent(this, ParentView.class));
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
package com.example.learnmoto;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.learnmoto.CheckConnection.NetworkChangeListener;
import com.example.learnmoto.Kinder.KinderClassList;
import com.example.learnmoto.Model.StudentModel;
import com.example.learnmoto.Parent.ParentView;
import com.example.learnmoto.Student.StudentHomeView;
import com.example.learnmoto.Teacher.DisplayStudentData;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import de.hdodenhof.circleimageview.CircleImageView;

public class DisplayChildData extends AppCompatActivity {

    TextView name, level, guardianName, Phone, address, birthday, math, english, fili, science, cl;
    String id, getNameIntent;
    String getlevel, getGuardianName, getBirthday, getAddress, imageStudent, phoneNumber, mathScore,
            engScore, filiScore, sciScore, clScore;
    CircleImageView StudPicture;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    LinearLayout filipinoLayout;
    NetworkChangeListener networkChangeListener = new NetworkChangeListener();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_child_data);
        name = findViewById(R.id.name);
        level = findViewById(R.id.level);
        math = findViewById(R.id.math);
        english = findViewById(R.id.english);
        fili = findViewById(R.id.filipino);
        science = findViewById(R.id.science);
        cl = findViewById(R.id.cl);
        guardianName = findViewById(R.id.guardianName);
        Phone = findViewById(R.id.phone);
        address = findViewById(R.id.Address);
        birthday = findViewById(R.id.bday);
        StudPicture = findViewById(R.id.picture);
        filipinoLayout = findViewById(R.id.filipinoLayout);

        filipinoLayout.setVisibility(View.GONE);
        getNameIntent = getIntent().getStringExtra("studentName");
        id = getIntent().getStringExtra("studentID");
        DisplayData();

    }

    @SuppressLint({"NotConstructor", "SetTextI18n"})
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
                    filiScore = "";
                    sciScore = "";
                    clScore = "";
                    getlevel = "";
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
                        filiScore += studentInfo.getFilipinoScore();
                        sciScore += studentInfo.getScienceScore();
                        clScore += studentInfo.getClScore();
                        getlevel += studentInfo.getsLevel();
                    }

                    if (!getlevel.equals("Nursery")){
                        name.setText(getNameIntent);
                        level.setText(getlevel);
                        guardianName.setText(getGuardianName);
                        birthday.setText(getBirthday);
                        address.setText(getAddress);
                        Phone.setText(phoneNumber);
                        math.setText(mathScore + "/12");
                        english.setText(engScore + "/10");
                        filipinoLayout.setVisibility(View.VISIBLE);
                        fili.setText(filiScore + "/10");
                        science.setText(sciScore + "/10");
                        cl.setText(clScore + "/10");

                    }else{

                        name.setText(getNameIntent);
                        level.setText(getlevel);
                        guardianName.setText(getGuardianName);
                        birthday.setText(getBirthday);
                        address.setText(getAddress);
                        Phone.setText(phoneNumber);
                        math.setText(mathScore + "/12");
                        english.setText(engScore + "/10");
                        filipinoLayout.setVisibility(View.GONE);
                        //fili.setText(filiScore + "/10");
                        science.setText(sciScore + "/10");
                        cl.setText(clScore + "/10");

                    }
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
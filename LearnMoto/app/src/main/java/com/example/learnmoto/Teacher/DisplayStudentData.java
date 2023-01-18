package com.example.learnmoto.Teacher;

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
import com.example.learnmoto.Nursery.NurseryClassList;
import com.example.learnmoto.Parent.ParentView;
import com.example.learnmoto.Preparatory.PreparatoryClassList;
import com.example.learnmoto.R;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import de.hdodenhof.circleimageview.CircleImageView;

public class DisplayStudentData extends AppCompatActivity {

    TextView name, level, guardianName, Phone, address, birthday, math, english, science, filipino, cl;
    String id, getNameIntent;
    String getSLevel;
    String getGuardianName, getBirthday, getAddress, imageStudent, phoneNumber, mathScore,
            engScore, filiScore, sciScore, clScore;
    CircleImageView StudPicture;
    LinearLayout filLayout;
    NetworkChangeListener networkChangeListener = new NetworkChangeListener();

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_student_data);

        name = findViewById(R.id.name);
        level = findViewById(R.id.level);
        math = findViewById(R.id.math);
        english = findViewById(R.id.english);
        filLayout = findViewById(R.id.filLayout);
        science = findViewById(R.id.science);
        cl = findViewById(R.id.cl);
        filipino = findViewById(R.id.filipino);
        guardianName = findViewById(R.id.guardianName);
        Phone = findViewById(R.id.phone);
        address = findViewById(R.id.Address);
        birthday = findViewById(R.id.bday);
        StudPicture = findViewById(R.id.picture);

        getNameIntent = getIntent().getStringExtra("studentName");
        id = getIntent().getStringExtra("studentID");
        getSLevel = getIntent().getStringExtra("studentLevel");

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
                    sciScore = "";
                    filiScore = "";
                    clScore = "";
                    //imageDisplay = "";
                    for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots){
                        StudentModel studentInfo = documentSnapshot.toObject(StudentModel.class);
                        studentInfo.setMyid(documentSnapshot.getId());
                        getGuardianName += studentInfo.getsGuardian();
                        getBirthday += studentInfo.getsBirthday();
                        getAddress += studentInfo.getsAddress();
                        imageStudent += studentInfo.getImageurl();
                        phoneNumber += studentInfo.getGuardianPhone();
                        mathScore += studentInfo.getMathScore();
                        engScore += studentInfo.getEngScore();
                        sciScore += studentInfo.getScienceScore();
                        filiScore += studentInfo.getScienceScore();
                        clScore += studentInfo.getScienceScore();

                    }

                    name.setText(getNameIntent);
                    level.setText(getSLevel);
                    guardianName.setText(getGuardianName);
                    birthday.setText(getBirthday);
                    address.setText(getAddress);
                    Phone.setText(phoneNumber);
                    if (!getSLevel.equals("Nursery")){
                        filLayout.setVisibility(View.VISIBLE);
                        if (mathScore.equals("null")){
                            math.setText("Not yet taken");
                        }if(engScore.equals("null")){
                            english.setText("Not yet taken");
                        }if(filiScore.equals("null")){
                            filipino.setText("Not yet taken");
                        }if(sciScore.equals("null")){
                            science.setText("Not yet taken");
                        }if(clScore.equals("null")){
                            cl.setText("Take the quiz");
                        }if (!mathScore.equals("null")){
                            math.setText(mathScore + "/10");
                        }if(!engScore.equals("null")){
                            english.setText(engScore + "/10");
                        }if(!filiScore.equals("null")){
                            filipino.setText(filiScore + "/10");
                        }if(!sciScore.equals("null")){
                            science.setText(sciScore + "/10");
                        }if(!clScore.equals("null")){
                            cl.setText(clScore + "/10");
                        }


                    }else {
                        filLayout.setVisibility(View.GONE);
                        if (mathScore.equals("null")) {
                            math.setText("Not yet taken");
                        }
                        if (engScore.equals("null")) {
                            english.setText("Not yet taken");
                        }
                        if (filiScore.equals("null")) {
                            filipino.setText("Not yet taken");
                        }
                        if (sciScore.equals("null")) {
                            science.setText("Not yet taken");
                        }
                        if (clScore.equals("null")) {
                            cl.setText("Not yet taken");
                        }
                        if (!mathScore.equals("null")) {
                            math.setText(mathScore + "/10");
                        }
                        if (!engScore.equals("null")) {
                            english.setText(engScore + "/10");
                        }
                        if (!filiScore.equals("null")) {
                            filipino.setText(filiScore + "/10");
                        }
                        if (!sciScore.equals("null")) {
                            science.setText(sciScore + "/10");
                        }
                        if (!clScore.equals("null")) {
                            cl.setText(clScore + "/10");
                        }

                    }

                    Glide.with(DisplayStudentData.this).load(imageStudent).placeholder(R.drawable.ic_user_circle)
                            .into(StudPicture);

                });
    }

    public void BackToTeacherView(View view) {
        BackPressed();
    }

    @Override
    public void onBackPressed() {
        BackPressed();
    }

    private void BackPressed(){
        if (getSLevel.equals("Kinder")){
            startActivity(new Intent(this, KinderClassList.class));
        }else if (getSLevel.equals("Nursery")){
            startActivity(new Intent(this, NurseryClassList.class));
        }else{
            startActivity(new Intent(this, PreparatoryClassList.class));
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
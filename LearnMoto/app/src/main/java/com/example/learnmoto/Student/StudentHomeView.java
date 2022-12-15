package com.example.learnmoto.Student;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.animation.LayoutTransition;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.learnmoto.Adapter.AnnouncementAdapter;
import com.example.learnmoto.Model.AnnouncementModel;
import com.example.learnmoto.Model.StudentInfo;
import com.example.learnmoto.R;
import com.example.learnmoto.Adapter.StudentAdapter;
import com.example.learnmoto.Adapter.TranslateAnimatioUI;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class StudentHomeView extends AppCompatActivity {

    ArrayList<AnnouncementModel> announcementArray;
    AnnouncementAdapter announcementAdapter;
    BottomNavigationView bottomNavigationView;
    RecyclerView recyclerView, rv_announcement;
    List<Integer> mImages;
    StudentAdapter studentAdapter;
    ScrollView scrollView;

    TextView textViewannounce, studentName, adviserName, textTitle;
    LinearLayout linearLayouttexts, subjectslayout, containerMessage;
    CircleImageView StudPicture;

    SharedPreferences sharedPreferences;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference collectionReference = db.collection("Student");
    CollectionReference teacherReference = db.collection("Teacher");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_view);

        bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setSelectedItemId(R.id.home);
        recyclerView = findViewById(R.id.subjectRv);
        rv_announcement = findViewById(R.id.announcement_rv);
        scrollView = findViewById(R.id.scroller);
        linearLayouttexts = findViewById(R.id.texts_layout);
        containerMessage = findViewById(R.id.AnnouncementContainer);
        subjectslayout = findViewById(R.id.subjectslayout);
        studentName = findViewById(R.id.studentName);
        StudPicture = findViewById(R.id.profile);
        adviserName = findViewById(R.id.adviserName);

        sharedPreferences = getApplicationContext().getSharedPreferences("Preferences", MODE_PRIVATE);
        String name = sharedPreferences.getString(StudentLogin.StudentName, "");
        String level = sharedPreferences.getString(StudentLogin.StudentLevel, "");

        studentName.setText(name);

        DisplayImage();

        linearLayouttexts.getLayoutTransition().enableTransitionType(LayoutTransition.CHANGING);
        subjectslayout.getLayoutTransition().enableTransitionType(LayoutTransition.CHANGING);

        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {

            //Condition of what activity is selected
            switch (item.getItemId()){
                case R.id.home:
                    return true;

                case R.id.todo:
                    startActivity(new Intent(getApplicationContext(), Todo.class));
                    overridePendingTransition(0,0);
                    return true;

                case R.id.settings:
                    startActivity(new Intent(getApplicationContext(), StudentSettings.class));
                    overridePendingTransition(0,0);
                    return true;
            }

            return false;
        });

        mImages = new ArrayList<>();

        studentAdapter = new StudentAdapter(this, mImages);

        if (level.equals("Nursery")){

            mImages.add(R.drawable.logo_english);
            mImages.add(R.drawable.logo_math);
            mImages.add(R.drawable.logo_science);
            mImages.add(R.drawable.logo_cl);

        }else if (level.equals("Kinder")){

            mImages.add(R.drawable.logo_english);
            mImages.add(R.drawable.logo_math);
            mImages.add(R.drawable.logo_science);
            mImages.add(R.drawable.logo_cl);
            mImages.add(R.drawable.logo_filipino);

        }else{

            mImages.add(R.drawable.logo_english);
            mImages.add(R.drawable.logo_math);
            mImages.add(R.drawable.logo_science);
            mImages.add(R.drawable.logo_cl);
            mImages.add(R.drawable.logo_filipino);
            mImages.add(R.drawable.logo_sibkul);

        }

        //StudentSubjects
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(studentAdapter);

        //AnnouncementArray
        rv_announcement.setHasFixedSize(true);
        rv_announcement.setLayoutManager(new LinearLayoutManager(this));
        announcementArray = new ArrayList<AnnouncementModel>();
        announcementAdapter = new AnnouncementAdapter(this, announcementArray);
        rv_announcement.setAdapter(announcementAdapter);
        EventChangeListener();


        //Access translateAnimUI Class for animation
        scrollView.setOnTouchListener(new TranslateAnimatioUI(this, bottomNavigationView));
    }

    private void EventChangeListener() {
        db.collection("Announcements")
                .addSnapshotListener((value, error) -> {
                    for (DocumentChange documentChange : value.getDocumentChanges()){
                        if (documentChange.getType() == DocumentChange.Type.ADDED){
                            announcementArray.add(documentChange.getDocument().toObject(AnnouncementModel.class));
                        }

                        announcementAdapter.notifyDataSetChanged();
                    }
                });
    }

    //Dipslay Image for student user
    public void DisplayImage(){
        collectionReference.whereEqualTo("sID", StudentLogin.studID)
                .get().addOnSuccessListener(queryDocumentSnapshots -> {
                    String imageDiplay = "";
                    for (QueryDocumentSnapshot documentSnapshot: queryDocumentSnapshots){
                        StudentInfo studentInfo = documentSnapshot.toObject(StudentInfo.class);
                        studentInfo.setMyid(documentSnapshot.getId());

                        imageDiplay += studentInfo.getImageurl();

                    }
                    Glide.with(getApplicationContext()).load(imageDiplay).placeholder(R.drawable.ic_user_circle).into(StudPicture);
                });
    }

//    public void DisplayAdviserName(){
//        teacherReference.whereEqualTo("sID", StudentLogin.studID)
//                .get().addOnSuccessListener(queryDocumentSnapshots -> {
//                    String imageDiplay = "";
//                    for (QueryDocumentSnapshot documentSnapshot: queryDocumentSnapshots){
//                        StudentInfo studentInfo = documentSnapshot.toObject(StudentInfo.class);
//                        studentInfo.setMyid(documentSnapshot.getId());
//
//                        imageDiplay += studentInfo.getImageurl();
//
//                    }
//                    Glide.with(getApplicationContext()).load(imageDiplay).placeholder(R.drawable.ic_user_circle).into(StudPicture);
//                });
//    }



    public void expand(View view) {
        int v = (containerMessage.getVisibility() == View.GONE)? View.VISIBLE: View.GONE;
        TransitionManager.beginDelayedTransition(linearLayouttexts, new AutoTransition());
        containerMessage.setVisibility(v);
    }

    public void expandsubject(View view) {
        int subjects = (recyclerView.getVisibility() == View.GONE)? View.VISIBLE: View.GONE;
        TransitionManager.beginDelayedTransition(subjectslayout, new AutoTransition());
        recyclerView.setVisibility(subjects);
    }
}
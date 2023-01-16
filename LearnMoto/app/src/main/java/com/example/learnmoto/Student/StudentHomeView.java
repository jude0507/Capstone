package com.example.learnmoto.Student;

import android.animation.LayoutTransition;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.learnmoto.Adapter.AnnouncementAdapter;
import com.example.learnmoto.Adapter.StudentSubjectAdapter;
import com.example.learnmoto.Adapter.TranslateAnimatioUI;
import com.example.learnmoto.AudioService;
import com.example.learnmoto.CheckConnection.NetworkChangeListener;
import com.example.learnmoto.DisplayImage;
import com.example.learnmoto.Model.AnnouncementModel;
import com.example.learnmoto.Model.TeacherModel;
import com.example.learnmoto.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class StudentHomeView extends AppCompatActivity {
    private MediaPlayer mediaPlayer;
    ArrayList<AnnouncementModel> announcementArray;
    AnnouncementAdapter announcementAdapter;
    BottomNavigationView bottomNavigationView;
    RecyclerView recyclerView, rv_announcement;
    List<Integer> mImages;
    StudentSubjectAdapter studentSubjectAdapter;
    ScrollView scrollView;

    TextView studentName, adviserName, displaylevel;
    LinearLayout linearLayouttexts, subjectslayout, containerMessage;
    CircleImageView StudPicture;

    SharedPreferences sharedPreferences;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    SharedPreferences.Editor editor;
    public static TextToSpeech textToSpeech;
    int output;

    NetworkChangeListener networkChangeListener = new NetworkChangeListener();
    public static String level, userID;

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
        displaylevel = findViewById(R.id.level);

        sharedPreferences = getApplicationContext().getSharedPreferences("Preferences", MODE_PRIVATE);
        String name = sharedPreferences.getString(StudentLogin.StudentName, "");
        level = sharedPreferences.getString(StudentLogin.StudentLevel, "");
        userID = sharedPreferences.getString(StudentLogin.Username, "");

        studentName.setText(name);
        displaylevel.setText(level);

//        mediaPlayer= MediaPlayer.create(getApplicationContext(),R.raw.background);
//        mediaPlayer.setLooping(false);
//        mediaPlayer.start();

        //Toast.makeText(this, "ID: " + userID, Toast.LENGTH_SHORT).show();
        DisplayImage();
        DisplayAdviserName();
        textSpeech();

        linearLayouttexts.getLayoutTransition().enableTransitionType(LayoutTransition.CHANGING);
        subjectslayout.getLayoutTransition().enableTransitionType(LayoutTransition.CHANGING);

        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {

            //Condition of what activity is selected
            switch (item.getItemId()) {
                case R.id.home:
                    return true;

                case R.id.todo:
                    startActivity(new Intent(getApplicationContext(), Todo.class));
                    overridePendingTransition(0, 0);
                    return true;

                case R.id.settings:
                    startActivity(new Intent(getApplicationContext(), StudentSettings.class));
                    overridePendingTransition(0, 0);
                    return true;
            }

            return false;
        });

        mImages = new ArrayList<>();

        studentSubjectAdapter = new StudentSubjectAdapter(this, mImages);

        if (level.equals("Nursery")) {

            mImages.add(R.drawable.logo_english);
            mImages.add(R.drawable.logo_math);
            mImages.add(R.drawable.logo_science);
            mImages.add(R.drawable.logo_cl);

        } else if (level.equals("Kinder")) {

            mImages.add(R.drawable.logo_english);
            mImages.add(R.drawable.logo_math);
            mImages.add(R.drawable.logo_science);
            mImages.add(R.drawable.logo_cl);
            mImages.add(R.drawable.logo_filipino);

        } else {

            mImages.add(R.drawable.logo_english);
            mImages.add(R.drawable.logo_math);
            mImages.add(R.drawable.logo_science);
            mImages.add(R.drawable.logo_cl);
            mImages.add(R.drawable.logo_filipino);
            //mImages.add(R.drawable.logo_sibkul);

        }

        //StudentSubjects
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setAdapter(studentSubjectAdapter);

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

    private void textSpeech(){
        textToSpeech = new TextToSpeech(this, i -> {
            if (i == TextToSpeech.SUCCESS) {
                output = textToSpeech.setLanguage(Locale.ENGLISH);
            }
        });    }

    private void DisplayAdviserName() {
        db.collection("Teacher").whereEqualTo("myAdvisoryClass", level).get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        String getAdvisoryLevel = "";
                        for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                            TeacherModel teacherModel = documentSnapshot.toObject(TeacherModel.class);
                            teacherModel.setMyid(documentSnapshot.getId());
                            getAdvisoryLevel += teacherModel.getTeacher_name();
                        }

                        adviserName.setText(getAdvisoryLevel);
                    }
                });
    }

    private void EventChangeListener() {
        db.collection("Announcements")
                .addSnapshotListener((value, error) -> {
                    for (DocumentChange documentChange : value.getDocumentChanges()) {
                        if (documentChange.getType() == DocumentChange.Type.ADDED) {
                            announcementArray.add(documentChange.getDocument().toObject(AnnouncementModel.class));
                        }

                        announcementAdapter.notifyDataSetChanged();
                    }
                });
    }

    //Dipslay Image for student user
    public void DisplayImage() {
        DisplayImage.RetrieveImageStudents(this, "Student", "sID", StudentLogin.studID, StudPicture);
    }

    public void expand(View view) {
        int v = (containerMessage.getVisibility() == View.GONE) ? View.VISIBLE : View.GONE;
        TransitionManager.beginDelayedTransition(linearLayouttexts, new AutoTransition());
        containerMessage.setVisibility(v);
    }

    public void expandsubject(View view) {
        int subjects = (recyclerView.getVisibility() == View.GONE) ? View.VISIBLE : View.GONE;
        TransitionManager.beginDelayedTransition(subjectslayout, new AutoTransition());
        recyclerView.setVisibility(subjects);
    }

    private void BackPressed(){
        AlertDialog.Builder alBuilder = new AlertDialog.Builder(this);
        alBuilder.setTitle("Confirmation Message")
                .setMessage("Do you want to logout");
        alBuilder.setPositiveButton("Confirm", (dialog, which) -> {
            editor = sharedPreferences.edit();
            editor.clear();
            editor.commit();
            startActivity(new Intent(StudentHomeView.this, StudentLogin.class));
            Toast.makeText(StudentHomeView.this, "Logout Success", Toast.LENGTH_SHORT).show();
            finish();
        });
        alBuilder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());
        alBuilder.show();
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

    @Override
    protected void onResume() {
        super.onResume();
        stopService(new Intent(getApplicationContext(), AudioService.class));
        startService(new Intent(getApplicationContext(), AudioService.class));
    }

    @Override
    public void onBackPressed() {
        BackPressed();
    }
}


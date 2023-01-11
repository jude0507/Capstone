package com.example.learnmoto.Teacher;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.learnmoto.Model.AnnouncementModel;
import com.example.learnmoto.CheckConnection.NetworkChangeListener;
import com.example.learnmoto.R;
import com.example.learnmoto.Student.StudentHomeView;
import com.example.learnmoto.Student.StudentSettings;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class Announcement extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    EditText title, message;
    TextView DisplayAnnouncement;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String authorID = TeacherLogin.teacher_ID;
    String authorName = TeacherLogin.teacher_name;

    NetworkChangeListener networkChangeListener = new NetworkChangeListener();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_announcement);

        //FirebaseMessaging.getInstance().subscribeToTopic("all");

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.announcements);
        //title = findViewById(R.id.notification_title);
        message = findViewById(R.id.notification_message);
        DisplayAnnouncement = findViewById(R.id.displayAnnouncement);


        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {

            //Condition of what activity is selected
            switch (item.getItemId()){
                case R.id.home:
                    startActivity(new Intent(getApplicationContext(), TeacherView.class));
                    overridePendingTransition(0,0);
                    return true;

                case R.id.announcements:
                    return true;

                case R.id.profile:
                    startActivity(new Intent(getApplicationContext(), TeacherProfile.class));
                    overridePendingTransition(0,0);
                    return true;

                case R.id.settings:
                    startActivity(new Intent(getApplicationContext(), TeacherSettings.class));
                    overridePendingTransition(0,0);
                    return true;
            }

            return false;
        });

        //scrollView.setOnTouchListener(new TranslateAnimatioUI(this, bottomNavigationView));

    }

    public void SendAnnouncement(View view) {

        String announce_msg = message.getText().toString();
        if (!announce_msg.isEmpty()){
            DocumentReference announcements = db.collection("Announcements").document(authorID);
            announcements.get().addOnSuccessListener(documentSnapshot -> {
                AnnouncementModel announcementModel = new AnnouncementModel();
                announcementModel.setAuthorID(authorID);
                announcementModel.setAuthorName(authorName);
                announcementModel.setMessage(announce_msg);
                db.collection("Announcements").document(authorID).set(announcementModel);
                Toast.makeText(Announcement.this, "Announcement has been posted", Toast.LENGTH_SHORT).show();
                DisplayAnnouncement.setText(announce_msg);
                message.setText("");

            });

        }else{
            message.setError("Required Field");
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
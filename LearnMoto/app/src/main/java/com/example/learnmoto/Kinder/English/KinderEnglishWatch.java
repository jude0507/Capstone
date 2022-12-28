package com.example.learnmoto.Kinder.English;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;

import com.example.learnmoto.CheckConnection.NetworkChangeListener;
import com.example.learnmoto.Kinder.ChristianLiving.KinderChristianLivingWatch;
import com.example.learnmoto.Model.VideoInfo;
import com.example.learnmoto.R;
import com.example.learnmoto.Student.StudentHomeView;
import com.example.learnmoto.VideoAdapter;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class KinderEnglishWatch extends AppCompatActivity {

    DrawerLayout drawerLayout;
    NetworkChangeListener networkChangeListener = new NetworkChangeListener();
    RecyclerView recyclerView;
    VideoAdapter videoAdapter;
    ArrayList<VideoInfo> videoNameArraylist;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String subject = "Kinder English";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kinder_english_watch);

        drawerLayout = findViewById(R.id.mydrawer_layout);
        recyclerView = findViewById(R.id.VideoRecyclerView);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        videoNameArraylist = new ArrayList<>();
        videoAdapter = new VideoAdapter(KinderEnglishWatch.this, videoNameArraylist);
        recyclerView.setAdapter(videoAdapter);
        EventChangeListener();

    }

    public void clickmenu(View view) {
        openDrawer(drawerLayout);
    }

    public static void openDrawer(DrawerLayout drawerLayout) {
        drawerLayout.openDrawer(GravityCompat.START);
    }

    public void clicklayout(View view) {
        closeDrawer(drawerLayout);
    }

    public static void closeDrawer(DrawerLayout drawerLayout) {

        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }

    public void readme(View view){
        redirectActivity(this, KinderEnglish.class);
    }
    public void watchme(View view){
        recreate();
    }
    public void takeaquiz(View view){
        redirectActivity(this, KinderEnglishQuiz.class);
    }

    public static void redirectActivity(Activity activity, Class myclass) {

        Intent intent = new Intent(activity,myclass);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);

    }

    protected void onPause() {
        super.onPause();

        closeDrawer(drawerLayout);
    }

    public void BacktoStudentHome(View view){
        startActivity(new Intent(this, StudentHomeView.class));
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

    private void EventChangeListener() {
        db.collection("Videos").whereEqualTo("videoSubject", subject)
                .addSnapshotListener((value, error) -> {

                    for (DocumentChange documentChange: value.getDocumentChanges()){
                        if (documentChange.getType() == DocumentChange.Type.ADDED){
                            videoNameArraylist.add(documentChange.getDocument().toObject(VideoInfo.class));
                        }
                        videoAdapter.notifyDataSetChanged();
                    }

                });
    }

}
package com.example.learnmoto;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.learnmoto.Model.VideoModel;
import com.example.learnmoto.Teacher.SubjectVideo;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class DeleteVideo extends AppCompatActivity {

    VideoView videoView;
    TextView textView;
    Button RemoveVideo;
    String getNameVideo;
    String VideoURL;
    Uri uri;
    ProgressDialog progressDialog;

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_video);

        videoView = findViewById(R.id.Video);
        textView = findViewById(R.id.DisplayVideoName);
        RemoveVideo = findViewById(R.id.RemoveVideo);

        getNameVideo = getIntent().getStringExtra("VideoName");
        textView.setText(getNameVideo);

        RemoveVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                videoView.start();
            }
        });


//        progressDialog = new ProgressDialog(this);
//        progressDialog.setMessage("Loading....");
//        progressDialog.setCanceledOnTouchOutside(false);
//        progressDialog.show();


    }

    private void Video() {
        db.collection("Videos").whereEqualTo("videoName", getNameVideo)
                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        VideoURL = "";
                        for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots){
                            VideoModel videoModel = documentSnapshot.toObject(VideoModel.class);
                            videoModel.setMyid(documentSnapshot.getId());
                            VideoURL += videoModel.getVideoUrl();
                        }

                        uri = Uri.parse(VideoURL);
                        videoView.setVideoURI(uri);
                        videoView.requestFocus();


                    }
                });
    }

    @Override
    protected void onStart() {
        super.onStart();

        Video();
    }

    public void BackToTeacherView(View view) {
        startActivity(new Intent(DeleteVideo.this, SubjectVideo.class));
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(DeleteVideo.this, SubjectVideo.class));
    }
}
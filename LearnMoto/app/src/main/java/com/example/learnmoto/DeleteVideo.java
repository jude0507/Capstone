package com.example.learnmoto;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.example.learnmoto.Model.VideoModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentChange;
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

        Video();

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
                        videoView.start();
                        MediaController mediaController = new MediaController(DeleteVideo.this);
                        mediaController.setAnchorView(videoView);
                        videoView.setMediaController(mediaController);

                    }
                });
    }

}
package com.example.learnmoto.Preparatory.English;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.learnmoto.AudioService;
import com.example.learnmoto.Nursery.Science.NurseryScienceWatch;
import com.example.learnmoto.Preparatory.English.PreparatoryEnglishWatch;
import com.example.learnmoto.R;

public class PrepEngVideoView extends AppCompatActivity {

    VideoView videoView;
    String videoUrl;
    Uri uri;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prep_eng_video_view);
        videoView = findViewById(R.id.videoView);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading....");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
    }
    @Override
    protected void onStart() {
        super.onStart();
        videoUrl = getIntent().getStringExtra("VideoUrl");

        uri = Uri.parse(videoUrl);
        videoView.setVideoURI(uri);
        videoView.start();
        MediaController mediaController = new MediaController(this);
        mediaController.setAnchorView(videoView);
        videoView.setMediaController(mediaController);
        videoView.setOnPreparedListener(mp -> progressDialog.dismiss());
    }

    int pressed = 0;
    @Override
    public void onBackPressed() {
        pressed++;
        if (pressed == 1){
            Toast.makeText(this, "Press back again to exit", Toast.LENGTH_SHORT).show();
        }else{
            startService(new Intent(this, AudioService.class));
            startActivity(new Intent(this, PreparatoryEnglishWatch.class));
        }
    }
}
package com.example.learnmoto;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.learnmoto.Teacher.TeacherLogin;
import com.example.learnmoto.Teacher.TeacherView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class SubjectVideo extends AppCompatActivity {

    TextView subject;
    EditText VideoName;
    Button ChooseVideo, UploadVideo;
    VideoView videoView;
    MediaController mediaController;
    Uri VideoUri;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    StorageReference storageReference;
    FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
    CollectionReference collectionReference = db.collection("Videos");
    DocumentReference documentReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject_video);

        subject = findViewById(R.id.txtSubject);
        videoView = findViewById(R.id.Video);
        ChooseVideo = findViewById(R.id.ChooseVideo);
        UploadVideo = findViewById(R.id.UploadVideo);
        VideoName = findViewById(R.id.VideoName);

        mediaController = new MediaController(this);
        storageReference = FirebaseStorage.getInstance().getReference("videos");

        videoView.setMediaController(mediaController);
        mediaController.setAnchorView(videoView);
        videoView.start();

        ChooseVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChooseVideo();
            }
        });

        UploadVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UploadVideo();
            }
        });

        String getItem = getIntent().getStringExtra("Subjects");
        switch (getItem) {
            case "Nursery English":
                Toast.makeText(this, "Nursery English", Toast.LENGTH_SHORT).show();
                subject.setText("Nursery English");
                break;
            case "Nursery Math":
                Toast.makeText(this, "Nursery Math", Toast.LENGTH_SHORT).show();
                subject.setText("Nursery Math");
                break;
            case "Nursery Science":
                Toast.makeText(this, "Nursery Science", Toast.LENGTH_SHORT).show();
                subject.setText("Nursery Science");
                break;
            case "Nursery Christian Living":
                Toast.makeText(this, "Nursery Christian Living", Toast.LENGTH_SHORT).show();
                subject.setText("Nursery Christian Living");
                break;
            case "Kinder English":
                Toast.makeText(this, "Kinder English", Toast.LENGTH_SHORT).show();
                subject.setText("Kinder English");
                break;
            case "Kinder Math":
                Toast.makeText(this, "Kinder Math", Toast.LENGTH_SHORT).show();
                subject.setText("Kinder Math");
                break;
            case "Kinder Science":
                Toast.makeText(this, "Kinder Science", Toast.LENGTH_SHORT).show();
                subject.setText("Kinder Science");
                break;
            case "Kinder Christian Living":
                Toast.makeText(this, "Kinder Christian Living", Toast.LENGTH_SHORT).show();
                subject.setText("Kinder Christian Living");
                break;
            case "Kinder Filipino":
                Toast.makeText(this, "Kinder Filipino", Toast.LENGTH_SHORT).show();
                subject.setText("Kinder Filipino");
                break;
            case "Preparatory English":
                Toast.makeText(this, "Preparatory English", Toast.LENGTH_SHORT).show();
                subject.setText("Preparatory English");
                break;
            case "Preparatory Math":
                Toast.makeText(this, "Preparatory Math", Toast.LENGTH_SHORT).show();
                subject.setText("Preparatory Math");
                break;
            case "Preparatory Science":
                Toast.makeText(this, "Preparatory Science", Toast.LENGTH_SHORT).show();
                subject.setText("Preparatory Science");
                break;
            case "Preparatory Christian Living":
                Toast.makeText(this, "Preparatory Christian Living", Toast.LENGTH_SHORT).show();
                subject.setText("Preparatory Christian Living");
                break;
            case "Preparatory Filipino":
                Toast.makeText(this, "Preparatory Filipino", Toast.LENGTH_SHORT).show();
                subject.setText("Preparatory Filipino");
                break;
            case "Preparatory Sibika at Kultura":
                Toast.makeText(this, "Preparatory Sibika at Kultura", Toast.LENGTH_SHORT).show();
                subject.setText("Preparatory Sibika at Kultura");
                break;
            default:
                Toast.makeText(this, "Not Found", Toast.LENGTH_SHORT).show();
                subject.setText("Nursery English");
                break;
        }

    }

    public void BackToTeacherView(View view) {
        startActivity(new Intent(this, TeacherView.class));
    }

    public void ChooseVideo() {
        Intent intent = new Intent();
        intent.setType("video/");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() !=null){
            VideoUri = data.getData();
            videoView.setVideoURI(VideoUri);
        }
    }

    private String getFileExt(Uri VideoUri){
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(VideoUri));
    }

    public void UploadVideo() {
        if (VideoUri != null){
            storageReference = firebaseStorage.getReference().child(System.currentTimeMillis() +
                    "," + getFileExt(VideoUri));
            storageReference.putFile(VideoUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(SubjectVideo.this, "Upload Sucesss", Toast.LENGTH_SHORT).show();
                    String videoName = VideoName.getText().toString().trim();
                    if (videoName.isEmpty()){
                        VideoName.setError("Required");
                    }else{
                        VideoInfo videoInfo = new VideoInfo();
                        videoInfo.setVideoUrl(taskSnapshot.toString());
                        videoInfo.setVideoName(VideoUri.toString());
                        videoInfo.setVideoName(videoName);
                        collectionReference.add(videoInfo);
                        Toast.makeText(SubjectVideo.this, "Success", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }else{
            Toast.makeText(this, "No video selected", Toast.LENGTH_SHORT).show();
        }
    }
}
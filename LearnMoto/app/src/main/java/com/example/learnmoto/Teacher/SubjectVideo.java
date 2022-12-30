package com.example.learnmoto.Teacher;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.learnmoto.CheckConnection.NetworkChangeListener;
import com.example.learnmoto.Model.TeacherInfo;
import com.example.learnmoto.Model.VideoInfo;
import com.example.learnmoto.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class SubjectVideo extends AppCompatActivity {

    private long backpressedTimes;
    TextView subject;
    EditText VideoName;
    Button ChooseVideo, UploadVideo;
    VideoView videoView;
    MediaController mediaController;
    Uri VideoUri;
    ProgressBar progressBar;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    StorageReference storageReference;
    FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
    CollectionReference collectionReference = db.collection("Videos");
    String getItem;
    AlertDialog.Builder builder;
    AlertDialog alertDialog;
    NetworkChangeListener networkChangeListener = new NetworkChangeListener();
    List<String> VideoNames = new ArrayList<>();
    String getName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject_video);

        subject = findViewById(R.id.txtSubject);
        videoView = findViewById(R.id.Video);
        ChooseVideo = findViewById(R.id.ChooseVideo);
        UploadVideo = findViewById(R.id.UploadVideo);
        VideoName = findViewById(R.id.VideoName);
        progressBar = findViewById(R.id.progress_bar);

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
                UploadVideo.setVisibility(View.GONE);
                VideoName.setVisibility(View.GONE);
            }
        });

        getItem = getIntent().getStringExtra("Subjects");
        subject.setText(getItem);

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
            UploadVideo.setVisibility(View.VISIBLE);
            VideoName.setVisibility(View.VISIBLE);
        }
    }

    private String getFileExt(Uri VideoUri){
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(VideoUri));
    }

    public void UploadVideo() {

        int CurrentProgress = 0;
        CurrentProgress += 10;
        progressBar.setProgress(CurrentProgress);
        progressBar.setMax(100);
        progressBar.setVisibility(View.VISIBLE);
        ChooseVideo.setEnabled(false);

        Toast.makeText(this, "Start Uploading Please Wait", Toast.LENGTH_LONG).show();
        String videoName = VideoName.getText().toString();
        if (VideoUri != null){
            if (!videoName.isEmpty()){
                storageReference = firebaseStorage.getReference().child("videos/").child(System.currentTimeMillis() +
                        "." + getFileExt(VideoUri));
                storageReference.putFile(VideoUri).addOnCompleteListener(task -> {
                    if (task.isSuccessful()){
                        storageReference.getDownloadUrl().addOnSuccessListener(uri -> {
                            VideoInfo videoInfo = new VideoInfo();
                            if (getItem.contains("Nursery")){
                                videoInfo.setVideoLevel("Nursery");
                            }else if (getItem.contains("Kinder")){
                                videoInfo.setVideoLevel("Kinder");
                            }else{
                                videoInfo.setVideoLevel("Preparatory");
                            }
                            videoInfo.setVideoName(videoName);
                            videoInfo.setVideoUrl(uri.toString());
                            videoInfo.setVideoSubject(subject.getText().toString());
                            videoInfo.setUploadedBy(TeacherLogin.teacher_name);
                            collectionReference.add(videoInfo);
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(SubjectVideo.this, "Uploaded Sucessfully", Toast.LENGTH_SHORT).show();
                            ChooseVideo.setEnabled(true);
                            VideoName.setText("");
                        });
                    }
                });
            }else{
                progressBar.setVisibility(View.GONE);
                ChooseVideo.setEnabled(true);
                VideoName.setError("Required");
            }
        }else{
            progressBar.setVisibility(View.GONE);
            ChooseVideo.setEnabled(true);
            Toast.makeText(this, "No video selected", Toast.LENGTH_SHORT).show();
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

    @Override
    public void onBackPressed() {
        if (backpressedTimes + 3000 > System.currentTimeMillis()){
            super.onBackPressed();
            return;
        }else{
            Toast.makeText(this, "Press back again to exit", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, TeacherLogin.class));
        }
        backpressedTimes = System.currentTimeMillis();

    }

    public void ViewListVideo(View view) {
        ShowVideoList();
    }

    public void ShowVideoList(){
        builder = new AlertDialog.Builder(SubjectVideo.this);
        final View view = getLayoutInflater().inflate(R.layout.view_list_video,null);

        ListView ListLevel = view.findViewById(R.id.listVideo);
        ImageView close = view.findViewById(R.id.close);
        builder.setView(view);
        alertDialog = builder.create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();

        collectionReference.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot value, FirebaseFirestoreException error) {
                VideoNames.clear();
                for (DocumentSnapshot documentSnapshot: value){
                    VideoNames.add(documentSnapshot.getString("videoName"));
                    getName = documentSnapshot.getString("videoName");
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),
                        android.R.layout.simple_list_item_1, VideoNames);
                adapter.notifyDataSetChanged();
                ListLevel.setAdapter(adapter);
            }
        });


        close.setOnClickListener(v -> alertDialog.dismiss());

    }
}
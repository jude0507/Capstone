package com.example.learnmoto.Preparatory;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.learnmoto.Adapter.ClassListAdapter;
import com.example.learnmoto.Model.StudentInfo;
import com.example.learnmoto.R;
import com.example.learnmoto.Teacher.TeacherView;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class PreparatoryClassList extends AppCompatActivity {

    ArrayList<StudentInfo> studentInfoArrayList;
    ClassListAdapter classListAdapter;
    RecyclerView prepClassRV;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    //CollectionReference collectionReference = db.collection("Student");
    String ClassLevel = "Preparatory";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preparatory_class_list);
        prepClassRV = findViewById(R.id.nurseryClass);

        prepClassRV.setHasFixedSize(true);
        prepClassRV.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        studentInfoArrayList = new ArrayList<>();
        classListAdapter = new ClassListAdapter(PreparatoryClassList.this, studentInfoArrayList);
        prepClassRV.setAdapter(classListAdapter);
        EventChangeListener();
    }
    private void EventChangeListener() {
        db.collection("Student").whereEqualTo("sLevel", ClassLevel)
                .addSnapshotListener((value, error) -> {

                    for (DocumentChange documentChange: value.getDocumentChanges()){
                        if (documentChange.getType() == DocumentChange.Type.ADDED){
                            studentInfoArrayList.add(documentChange.getDocument().toObject(StudentInfo.class));
                        }
                        classListAdapter.notifyDataSetChanged();
                    }

                });
    }
    public void BackToTeacherView(View view) {
        startActivity(new Intent(this, TeacherView.class));
    }
}
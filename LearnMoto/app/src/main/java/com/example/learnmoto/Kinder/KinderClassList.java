package com.example.learnmoto.Kinder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.learnmoto.Adapter.ClassListAdapter;
import com.example.learnmoto.Model.StudentModel;
import com.example.learnmoto.R;
import com.example.learnmoto.Teacher.TeacherView;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class KinderClassList extends AppCompatActivity {

    ArrayList<StudentModel> studentInfoArrayList;
    ClassListAdapter classListAdapter;
    RecyclerView kinderClassRV;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    //CollectionReference collectionReference = db.collection("Student");
    String ClassLevel = "Kinder";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kinder_class_list);
        kinderClassRV = findViewById(R.id.nurseryClass);

        kinderClassRV.setHasFixedSize(true);
        kinderClassRV.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        studentInfoArrayList = new ArrayList<>();
        classListAdapter = new ClassListAdapter(KinderClassList.this, studentInfoArrayList);
        kinderClassRV.setAdapter(classListAdapter);
        EventChangeListener();
    }
    private void EventChangeListener() {
        db.collection("Student").whereEqualTo("sLevel", ClassLevel)
                .addSnapshotListener((value, error) -> {

                    for (DocumentChange documentChange: value.getDocumentChanges()){
                        if (documentChange.getType() == DocumentChange.Type.ADDED){
                            studentInfoArrayList.add(documentChange.getDocument().toObject(StudentModel.class));
                        }
                        classListAdapter.notifyDataSetChanged();
                    }

                });
    }
    public void BackToTeacherView(View view) {
        startActivity(new Intent(this, TeacherView.class));
    }
}
package com.example.learnmoto.Nursery;

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

public class NurseryClassList extends AppCompatActivity {

    ArrayList<StudentModel> studentInfoArrayList;
    ClassListAdapter classListAdapter;
    RecyclerView nurseryClassRV;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    //CollectionReference collectionReference = db.collection("Student");
    String ClassLevel = "Nursery";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nursery_class_list);
        nurseryClassRV = findViewById(R.id.nurseryClass);

        nurseryClassRV.setHasFixedSize(true);
        nurseryClassRV.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        studentInfoArrayList = new ArrayList<>();
        classListAdapter = new ClassListAdapter(NurseryClassList.this, studentInfoArrayList);
        nurseryClassRV.setAdapter(classListAdapter);
        EventChangeListener();

    }

    //Get the names from StudentInfo Model
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
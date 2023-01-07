package com.example.learnmoto.Preparatory;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.learnmoto.Adapter.ClassListAdapter;
import com.example.learnmoto.Kinder.KinderClassList;
import com.example.learnmoto.Model.StudentModel;
import com.example.learnmoto.Model.ToDoModel;
import com.example.learnmoto.R;
import com.example.learnmoto.Teacher.TeacherLogin;
import com.example.learnmoto.Teacher.TeacherView;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class PreparatoryClassList extends AppCompatActivity {

    ArrayList<StudentModel> studentInfoArrayList;
    ClassListAdapter classListAdapter;
    RecyclerView prepClassRV;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    //CollectionReference collectionReference = db.collection("Student");
    String ClassLevel = "Preparatory";
    String authorID = TeacherLogin.teacher_ID;
    String authorName = TeacherLogin.teacher_name;
    LinearLayout Layout1, expandLayout1;
    Button TodoArrow;
    EditText todoMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preparatory_class_list);
        prepClassRV = findViewById(R.id.nurseryClass);
        TodoArrow = findViewById(R.id.todo_arrow_btn);
        Layout1 = findViewById(R.id.teacherTodoLayout);
        expandLayout1 = findViewById(R.id.expandToDo);
        todoMsg = findViewById(R.id.ToDoMessage);

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
                            studentInfoArrayList.add(documentChange.getDocument().toObject(StudentModel.class));
                        }
                        classListAdapter.notifyDataSetChanged();
                    }

                });
    }
    public void BackToTeacherView(View view) {
        startActivity(new Intent(this, TeacherView.class));
    }

    public void ExpandToDo(View view) {
        if (expandLayout1.getVisibility() == View.GONE) {
            TransitionManager.beginDelayedTransition(Layout1, new AutoTransition());
            expandLayout1.setVisibility(View.VISIBLE);
            TodoArrow.setBackgroundResource(R.drawable.ic_arrow_up);
        }else{
            TransitionManager.beginDelayedTransition(Layout1, new AutoTransition());
            expandLayout1.setVisibility(View.GONE);
            TodoArrow.setBackgroundResource(R.drawable.ic_arrow_down);
        }
    }

    public void SendToDo(View view) {
        String MsgTodo = todoMsg.getText().toString();
        if (!MsgTodo.isEmpty()){
            DocumentReference todo = db.collection("Todo").document(authorID);
            todo.get().addOnSuccessListener(documentSnapshot -> {
                ToDoModel toDoModel = new ToDoModel();
                toDoModel.setAuthorID(authorID);
                toDoModel.setAuthorName(authorName);
                toDoModel.setMessage(MsgTodo);
                toDoModel.setLevel(ClassLevel);
                db.collection("Todo").add(toDoModel);
                Toast.makeText(PreparatoryClassList.this, "Todo has been sent", Toast.LENGTH_SHORT).show();
                //DisplayAnnouncement.setText(announce_msg);
                todoMsg.setText("");

            });

        }else{
            todoMsg.setError("Required Field");
        }
    }

}
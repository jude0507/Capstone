package com.example.learnmoto.Teacher;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.learnmoto.Adapter.StudentSubjectAdapter;
import com.example.learnmoto.CheckConnection.NetworkChangeListener;
import com.example.learnmoto.LevelAdapter;
import com.example.learnmoto.Model.TeacherInfo;
import com.example.learnmoto.R;
import com.example.learnmoto.Adapter.TranslateAnimatioUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class TeacherView extends AppCompatActivity {

    Button viewClassLevel, viewSubjects, viewClassAdvisory, AddLevelList, savebtn;
    BottomNavigationView bottomNavigationView;
    ScrollView scrollView;
    TextView teacherName;
    RecyclerView rvAssignedSubjects, rvAssignClass;
    LinearLayout assignClassLayout, assignClassContainer, subjectContainer,expandSubjects,
            advisoryClassContainer, expandClassAdvisory;

    AlertDialog.Builder builder;
    AlertDialog alertDialog;
    ArrayList<String> addLevel = new ArrayList<String>();
    ArrayAdapter<String> arrayAdapter;
    List<String> levelArray;
    ListView ListLevel, listClass;
    EditText AssignLevelInput;
    String data = "";

    public static String GetAssignedLevel;
    public static List<Map<String, Object>> assignLevel;
    LevelAdapter levelAdapter;
    List<Integer> mImages;

    String TeacherName = TeacherLogin.teacher_name;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference collectionReference = db.collection("Teacher");
    DocumentReference documentReference = collectionReference.document(TeacherLogin.teacher_ID);

    NetworkChangeListener networkChangeListener = new NetworkChangeListener();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_view);

        teacherName = findViewById(R.id.TeacherName);
        bottomNavigationView = findViewById(R.id.bottom_nav);
        scrollView = findViewById(R.id.scroller);
        viewClassLevel = findViewById(R.id.class_arrow_btn);
        viewSubjects = findViewById(R.id.subject_arrow_btn);
        viewClassAdvisory = findViewById(R.id.advisoryClass_arrow_btn);
        rvAssignedSubjects = findViewById(R.id.rvSubjects);
        //listClass = findViewById(R.id.listClass);
        rvAssignClass = findViewById(R.id.rvClass);
        assignClassLayout = findViewById(R.id.expandMyClass);
        assignClassContainer = findViewById(R.id.assignClassLayout);
        subjectContainer = findViewById(R.id.teacherSubjectLayout);
        expandSubjects = findViewById(R.id.expandSubjects);
        advisoryClassContainer = findViewById(R.id.teacherAdvisoryClass);
        expandClassAdvisory = findViewById(R.id.expandAdvisoryClass);

        //teacherInfoArrayList = new ArrayList<>();
        //https://stackoverflow.com/questions/54711228/compare-two-arrays-with-not-the-same-order

        DisplayAssignedLevel();

        bottomNavigationView.setSelectedItemId(R.id.home);
        teacherName.setText(TeacherName);


        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {

            //Condition of what activity is selected
            switch (item.getItemId()){
                case R.id.home:
                    return true;

                case R.id.announcements:
                    startActivity(new Intent(getApplicationContext(), Announcement.class));
                    overridePendingTransition(0,0);
                    return true;

                case R.id.profile:
                    startActivity(new Intent(getApplicationContext(), TeacherProfile.class));
                    overridePendingTransition(0,0);
                    return true;

                case R.id.settings:
                    startActivity(new Intent(getApplicationContext(), TeacherSettings.class));
                    overridePendingTransition(0,0);
                    return true;
            }

            return false;
        });

        scrollView.setOnTouchListener(new TranslateAnimatioUI(this, bottomNavigationView));

    }

    public void ExpandAssignLevel(View view) {
        if (assignClassLayout.getVisibility() == View.GONE) {
            TransitionManager.beginDelayedTransition(assignClassContainer, new AutoTransition());
            assignClassLayout.setVisibility(View.VISIBLE);
            viewClassLevel.setBackgroundResource(R.drawable.ic_arrow_up);
        }else{
            TransitionManager.beginDelayedTransition(assignClassContainer, new AutoTransition());
            assignClassLayout.setVisibility(View.GONE);
            viewClassLevel.setBackgroundResource(R.drawable.ic_arrow_down);
        }
    }

    public void ExpandSubjects(View view) {
        if (expandSubjects.getVisibility() == View.GONE) {
            TransitionManager.beginDelayedTransition(subjectContainer, new AutoTransition());
            expandSubjects.setVisibility(View.VISIBLE);
            viewSubjects.setBackgroundResource(R.drawable.ic_arrow_up);
        }else{
            TransitionManager.beginDelayedTransition(subjectContainer, new AutoTransition());
            expandSubjects.setVisibility(View.GONE);
            viewSubjects.setBackgroundResource(R.drawable.ic_arrow_down);
        }
    }

    public void ExpandClassAdvisory(View view) {
        if (expandClassAdvisory.getVisibility() == View.GONE) {
            TransitionManager.beginDelayedTransition(advisoryClassContainer, new AutoTransition());
            expandClassAdvisory.setVisibility(View.VISIBLE);
            viewClassAdvisory.setBackgroundResource(R.drawable.ic_arrow_up);
        }else{
            TransitionManager.beginDelayedTransition(advisoryClassContainer, new AutoTransition());
            expandClassAdvisory.setVisibility(View.GONE);
            viewClassAdvisory.setBackgroundResource(R.drawable.ic_arrow_down);
        }
    }

    public void AddAssignLevel(View view) {
        AssignLevelWindow();
        //use the function on onClick
    }
    //function for add level window dialog
    public void AssignLevelWindow(){
        builder = new AlertDialog.Builder(TeacherView.this);
        final View view = getLayoutInflater().inflate(R.layout.assign_class_level,null);

        AssignLevelInput = view.findViewById(R.id.inputAssignLevel);
        savebtn= view.findViewById(R.id.Save);
        AddLevelList = view.findViewById(R.id.AddLevelList);
        ListLevel = view.findViewById(R.id.listlevel);

        builder.setView(view);
        alertDialog = builder.create();
        alertDialog.show();

        AddLevelList.setOnClickListener(v -> {
            String inputLevel = AssignLevelInput.getText().toString();

            if (addLevel.contains(inputLevel)){
                Toast.makeText(TeacherView.this, "Already Added", Toast.LENGTH_SHORT).show();
            }else if(inputLevel.isEmpty() || inputLevel.trim().equals("")){
                Toast.makeText(TeacherView.this, "NO value", Toast.LENGTH_SHORT).show();
            }else if (inputLevel.equals("Nursery") || inputLevel.equals("Kinder") || inputLevel.equals("Preparatory")){
                addLevel.add(inputLevel);
                arrayAdapter = new ArrayAdapter<String>(TeacherView.this,
                        android.R.layout.simple_list_item_1, addLevel);
                ListLevel.setAdapter(arrayAdapter);
                AssignLevelInput.setText("");

            }else{
                Toast.makeText(TeacherView.this, "Invalid Input", Toast.LENGTH_SHORT).show();

            }
        });

        //https://stackoverflow.com/questions/49657098/android-firestore-query-array
        //https://stackoverflow.com/questions/58113840/how-to-retrieve-an-array-from-firestore

        savebtn.setOnClickListener(v -> {
            for (String item:addLevel){
                data = data + "" + item + ",";

            }
            if (data.equals("") || data.isEmpty()){
                Toast.makeText(TeacherView.this, "no data", Toast.LENGTH_SHORT).show();
            }else{

                String [] dataArray = data.split("\\s*,\\s*");
                levelArray = Arrays.asList(dataArray);

                DocumentReference documentReference = db.collection("Teacher")
                        .document(TeacherLogin.teacher_ID);
                documentReference.update("assignLevel", levelArray);
                Toast.makeText(TeacherView.this, "Save Successfully", Toast.LENGTH_SHORT).show();
            }
            alertDialog.dismiss();
        });
    }

    public void DisplayAssignedLevel(){
        documentReference.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    assignLevel = (List<Map<String, Object>>) document.get("assignLevel");
                    GetAssignedLevel = String.valueOf(assignLevel);

                    Toast.makeText(this, GetAssignedLevel, Toast.LENGTH_SHORT).show();

                    mImages = new ArrayList<>();

                    levelAdapter = new LevelAdapter(this, mImages);

                    try {
                        if (GetAssignedLevel != "null" && !GetAssignedLevel.isEmpty()){
                            if (assignLevel.contains("Kinder") && assignLevel.contains("Preparatory") && assignLevel.contains("Nursery")){
                                Toast.makeText(this, "NKP", Toast.LENGTH_SHORT).show();
                                mImages.add(R.drawable.kinder_logo);
                                mImages.add(R.drawable.nursery_logo);
                                mImages.add(R.drawable.preparatory_logo);
                            }
                            else if (assignLevel.contains("Nursery") && assignLevel.contains("Kinder")){
                                Toast.makeText(this, "NK", Toast.LENGTH_SHORT).show();
                                mImages.add(R.drawable.kinder_logo);
                                mImages.add(R.drawable.nursery_logo);
                            }
                            else if(assignLevel.contains("Kinder") && assignLevel.contains("Preparatory")){
                                Toast.makeText(this, "KP", Toast.LENGTH_SHORT).show();
                                mImages.add(R.drawable.nursery_logo);
                                mImages.add(R.drawable.preparatory_logo);
                            }
                            else if(assignLevel.contains("Nursery") && assignLevel.contains("Preparatory")){
                                Toast.makeText(this, "NP", Toast.LENGTH_SHORT).show();
                                mImages.add(R.drawable.nursery_logo);
                                mImages.add(R.drawable.preparatory_logo);
                            }
                            else if (assignLevel.contains("Kinder")){
                                Toast.makeText(this, "K", Toast.LENGTH_SHORT).show();
                                mImages.add(R.drawable.kinder_logo);
                            }
                            else if (assignLevel.contains("Nursery")){
                                Toast.makeText(this, "N", Toast.LENGTH_SHORT).show();
                                mImages.add(R.drawable.nursery_logo);
                            }
                            else if (assignLevel.contains("Preparatory")){
                                Toast.makeText(this, "P", Toast.LENGTH_SHORT).show();
                                mImages.add(R.drawable.preparatory_logo);
                            }
                        }else{
                            Toast.makeText(this, "NONE", Toast.LENGTH_SHORT).show();
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
                    rvAssignClass.setLayoutManager(linearLayoutManager);
                    rvAssignClass.setAdapter(levelAdapter);

                }
            }
        });
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
}
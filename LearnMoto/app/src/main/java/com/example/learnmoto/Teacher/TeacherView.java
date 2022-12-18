package com.example.learnmoto.Teacher;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
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

import com.example.learnmoto.CheckConnection.NetworkChangeListener;
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
    //ArrayList<String> teacherInfoArrayList;
    List<String> levelArray;
    ListView ListLevel, listClass;
    EditText AssignLevelInput;
    String GetAssignedLevel;
    String data = "";
    List<Map<String, Object>> assignLevel;

    String [] year1 = {"Nursery"};
    String [] year2 = {"Nursery", "Kinder"};
    String [] year3 = {"Nursery", "Kinder", "Preparatory"};

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
        listClass = findViewById(R.id.listClass);
        //rvAssignClass = findViewById(R.id.rvClass);
        assignClassLayout = findViewById(R.id.expandMyClass);
        assignClassContainer = findViewById(R.id.assignClassLayout);
        subjectContainer = findViewById(R.id.teacherSubjectLayout);
        expandSubjects = findViewById(R.id.expandSubjects);
        advisoryClassContainer = findViewById(R.id.teacherAdvisoryClass);
        expandClassAdvisory = findViewById(R.id.expandAdvisoryClass);

        //teacherInfoArrayList = new ArrayList<>();
        //https://stackoverflow.com/questions/54711228/compare-two-arrays-with-not-the-same-order

        DisplayAssignedLevel();
//        if (assignLevel.containsAll(Arrays.asList(year1)) && assignLevel.equals(Arrays.asList(year1))){
//            Toast.makeText(this, "year1", Toast.LENGTH_SHORT).show();
//        }else if (assignLevel.containsAll(Arrays.asList(year2)) && assignLevel.equals(Arrays.asList(year2))){
//            Toast.makeText(this, "year2", Toast.LENGTH_SHORT).show();
//        }else if (assignLevel.containsAll(Arrays.asList(year3)) && assignLevel.equals(Arrays.asList(year3))){
//            Toast.makeText(this, "year3", Toast.LENGTH_SHORT).show();
//        }else{
//            Toast.makeText(this, "year null", Toast.LENGTH_SHORT).show();
//        }


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

                    //Di ko macompare ung nasa loob ng assignLevel para makuha ung level na nasa loob

                }
            }
        });
//        collectionReference.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                for (DocumentSnapshot document : task.getResult()) {
//                    List<String> list = (List<String>) document.get("assignLevel");
//                    for (String item : list) {
//                        Log.d("TAG", item);
//                    }
//
//                }
//            }
//        });
//        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                if (task.isSuccessful()){
//                    DocumentSnapshot documentSnapshot = task.getResult();
//                    if (documentSnapshot.exists()){
//                        List<Map<String, TeacherInfo>> teacherInfo = (List<Map<String, TeacherInfo>>) documentSnapshot.get("assignLevel");
//                    }
//                }
//            }
//        });


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
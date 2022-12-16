package com.example.learnmoto.Teacher;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
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
import com.example.learnmoto.R;
import com.example.learnmoto.Student.StudentHomeView;
import com.example.learnmoto.Student.StudentSettings;
import com.example.learnmoto.Adapter.TranslateAnimatioUI;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TeacherView extends AppCompatActivity {

    Button viewClassLevel, viewSubjects, viewClassAdvisory, ListLevelbtn, closeWindow;
    BottomNavigationView bottomNavigationView;
    ScrollView scrollView;
    TextView teacherName;
    RecyclerView rvAssignedLevel;
    LinearLayout assignClassLayout, assignClassContainer, subjectContainer,expandSubjects,
            advisoryClassContainer, expandClassAdvisory;

    AlertDialog.Builder builder;
    AlertDialog alertDialog;
    ArrayList<String> addLevel = new ArrayList<String>();
    ArrayAdapter<String> arrayAdapter;
    ListView ListLevel;
    EditText AssignLevelInput;
    String data = "";

    String TeacherName = TeacherLogin.teacher_name;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
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
        rvAssignedLevel = findViewById(R.id.assignedLevel);
        assignClassLayout = findViewById(R.id.expandMyClass);
        assignClassContainer = findViewById(R.id.assignClassLayout);
        subjectContainer = findViewById(R.id.teacherSubjectLayout);
        expandSubjects = findViewById(R.id.expandSubjects);
        advisoryClassContainer = findViewById(R.id.teacherAdvisoryClass);
        expandClassAdvisory = findViewById(R.id.expandAdvisoryClass);

        bottomNavigationView.setSelectedItemId(R.id.home);
        teacherName.setText(TeacherName);
        //assignClassLayout.getLayoutTransition().enableTransitionType(LayoutTransition.CHANGING);

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
                    startActivity(new Intent(getApplicationContext(), StudentSettings.class));
                    overridePendingTransition(0,0);
                    return true;

                case R.id.settings:
                    startActivity(new Intent(getApplicationContext(), StudentHomeView.class));
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
        closeWindow = view.findViewById(R.id.CloseWindow);
        ListLevelbtn = view.findViewById(R.id.SaveLevelList);
        ListLevel = view.findViewById(R.id.listlevel);

        builder.setView(view);
        alertDialog = builder.create();
        alertDialog.show();

        ListLevelbtn.setOnClickListener(v -> {
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

        closeWindow.setOnClickListener(v -> {
            for (String item:addLevel){
                data = data + "" + item + ",";

            }
            if (data.equals("") || data.isEmpty()){
                Toast.makeText(TeacherView.this, "no data", Toast.LENGTH_SHORT).show();
            }else{

                String [] dataArray = data.split("\\s*,\\s*");
                List<String> levelArray = Arrays.asList(dataArray);

                DocumentReference documentReference = db.collection("Teacher")
                        .document(TeacherLogin.teacher_ID);
                documentReference.update("assignLevel", levelArray);
                Toast.makeText(TeacherView.this, "Save Successfully", Toast.LENGTH_SHORT).show();
            }
            alertDialog.dismiss();
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
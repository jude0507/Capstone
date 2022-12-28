package com.example.learnmoto.Teacher;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.learnmoto.CheckConnection.NetworkChangeListener;
import com.example.learnmoto.Adapter.LevelAdapter;
import com.example.learnmoto.DisplayImage;
import com.example.learnmoto.R;
import com.example.learnmoto.Adapter.TranslateAnimatioUI;
import com.example.learnmoto.SubjectArrayClass;
import com.example.learnmoto.SubjectVideo;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class TeacherView extends AppCompatActivity {

    Button viewClassLevel, viewSubjects, viewClassAdvisory, AddLevelList, savebtn, SaveSubjectBtn;
    BottomNavigationView bottomNavigationView;
    ScrollView scrollView;
    TextView teacherName;
    CircleImageView ProfilePicture;
    RecyclerView rvAssignClass;
    LinearLayout assignClassLayout, assignClassContainer, subjectContainer,expandSubjects,
            advisoryClassContainer, expandClassAdvisory;

    AlertDialog.Builder builder;
    AlertDialog alertDialog;
    ArrayList<String> addLevel = new ArrayList<>();
    public static ArrayList<String> addSubjectToList = new ArrayList<>();
    ArrayAdapter<String> arrayAdapter, arraySubjectsAdapter;
    List<String> levelArray, subjectArray;
    ListView ListLevel, listsubject, DisplaySubjectList;
    String[] arraySubject;
    EditText AssignLevelInput;
    String DataAddLevel = "";
    String DataAssignLevel = "";
    String DataAssignSubject = "";
    String DataAddSubjectToList = "";

    public static String GetAssignedLevel;
    public static List<String> assignLevel;
    public static List<String> assignSubjects;
    LevelAdapter levelAdapter;
    List<Integer> mImages;

    String TeacherName = TeacherLogin.teacher_name;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference collectionReference = db.collection("Teacher");
    DocumentReference documentReference = collectionReference.document(TeacherLogin.teacher_ID);

    NetworkChangeListener networkChangeListener = new NetworkChangeListener();

    @SuppressLint({"NonConstantResourceId", "ClickableViewAccessibility"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_view);

        teacherName = findViewById(R.id.TeacherName);
        ProfilePicture = findViewById(R.id.profile);
        bottomNavigationView = findViewById(R.id.bottom_nav);
        scrollView = findViewById(R.id.scroller);
        viewClassLevel = findViewById(R.id.class_arrow_btn);
        viewSubjects = findViewById(R.id.subject_arrow_btn);
        viewClassAdvisory = findViewById(R.id.advisoryClass_arrow_btn);
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
        DisplayImage();
        FetchSubjects();

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

        addLevel.clear();
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

        //https://www.geeksforgeeks.org/java-program-to-merge-two-arrays/

        //check data list item from firestore then display in listview
        try{
            if (assignLevel.contains("Kinder") && assignLevel.contains("Preparatory") && assignLevel.contains("Nursery")){
                addLevel.add("Kinder");
                addLevel.add("Nursery");
                addLevel.add("Preparatory");
                arrayAdapter = new ArrayAdapter<String>(TeacherView.this,
                        android.R.layout.simple_list_item_1, addLevel);
                ListLevel.setAdapter(arrayAdapter);

            }
            else if (assignLevel.contains("Nursery") && assignLevel.contains("Kinder")){
                addLevel.add("Kinder");
                addLevel.add("Nursery");
                arrayAdapter = new ArrayAdapter<String>(TeacherView.this,
                        android.R.layout.simple_list_item_1, addLevel);
                ListLevel.setAdapter(arrayAdapter);

            }
            else if(assignLevel.contains("Kinder") && assignLevel.contains("Preparatory")){
                addLevel.add("Kinder");
                addLevel.add("Preparatory");
                arrayAdapter = new ArrayAdapter<String>(TeacherView.this,
                        android.R.layout.simple_list_item_1, addLevel);

            }
            else if(assignLevel.contains("Nursery") && assignLevel.contains("Preparatory")){
                addLevel.add("Nursery");
                addLevel.add("Preparatory");
                arrayAdapter = new ArrayAdapter<String>(TeacherView.this,
                        android.R.layout.simple_list_item_1, addLevel);
                ListLevel.setAdapter(arrayAdapter);
            }
            else if (assignLevel.contains("Kinder")){
                addLevel.add("Kinder");
                arrayAdapter = new ArrayAdapter<String>(TeacherView.this,
                        android.R.layout.simple_list_item_1, addLevel);
                ListLevel.setAdapter(arrayAdapter);
            }
            else if (assignLevel.contains("Nursery")){
                addLevel.add("Nursery");
                arrayAdapter = new ArrayAdapter<String>(TeacherView.this,
                        android.R.layout.simple_list_item_1, addLevel);
                ListLevel.setAdapter(arrayAdapter);

            }
            else if (assignLevel.contains("Preparatory")) {
                addLevel.add("Preparatory");
                arrayAdapter = new ArrayAdapter<String>(TeacherView.this,
                        android.R.layout.simple_list_item_1, addLevel);
                ListLevel.setAdapter(arrayAdapter);
            }
        }catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }


        //add data in list
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

        //LongPress for Delete item in list
        ListLevel.setOnItemLongClickListener((parent, view1, position, id) -> {
            final int selected_item = position;

            new AlertDialog.Builder(TeacherView.this)
                    .setIcon(R.drawable.ic_delete)
                    .setTitle("Delete Level")
                    .setMessage("Do you want to delete this level in the list?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            addLevel.remove(selected_item);
                            arrayAdapter.notifyDataSetChanged();
                        }
                    })
                    .setNegativeButton("Cancel", null)
                    .show();

            return true;
        });

        //save the list in firestore
        savebtn.setOnClickListener(v -> {
            for (String item:addLevel){
                DataAddLevel = DataAddLevel + "" + item + ",";

            }
            if (DataAddLevel.equals("") || DataAddLevel.isEmpty()){
                Toast.makeText(TeacherView.this, "no data", Toast.LENGTH_SHORT).show();
            }else{

                String [] dataArray = DataAddLevel.split("\\s*,\\s*");
                levelArray = Arrays.asList(dataArray);

                DocumentReference documentReference = db.collection("Teacher")
                        .document(TeacherLogin.teacher_ID);
                documentReference.update("assignLevel", levelArray);
                finish();
                overridePendingTransition(0, 0);
                startActivity(getIntent());
                overridePendingTransition(0, 0);
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
                    try{
                        assignLevel = (List<String>) document.get("assignLevel");

                        //assert assignLevel != null; (sanhi ng error)
                        String[] arrayLevel = new String[assignLevel.size()];
                        for (int i = 0; i < assignLevel.size(); i++){
                            arrayLevel[i] = assignLevel.get(i);

                            DataAssignLevel += assignLevel.get(i);
                            if (i < assignLevel.size() -1){
                                DataAssignLevel += ",";
                            }
                        }

                        Toast.makeText(this, DataAssignLevel, Toast.LENGTH_SHORT).show();
                        GetAssignedLevel = String.valueOf(assignLevel);

                        mImages = new ArrayList<>();
                        levelAdapter = new LevelAdapter(this, mImages);

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
                        Toast.makeText(this, "You have no assign level. Please add your assign level Thank you!", Toast.LENGTH_SHORT).show();
                        //e.printStackTrace();
                    }

                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
                    rvAssignClass.setLayoutManager(linearLayoutManager);
                    rvAssignClass.setAdapter(levelAdapter);

                }
            }
        });
    }

    public void DisplayImage(){
        DisplayImage.RetrieveImageTeacher(this, "Teacher", "teacher_ID" ,TeacherLogin.teacher_ID, ProfilePicture);
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

    /*@Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        AssignLevelWindow();
        super.onRestoreInstanceState(savedInstanceState);
    }*/

    public void AddSubject(View view) {
        addSubjectToList.clear();
        AssignSubject();
    }

    public void AssignSubject(){

        SubjectArrayClass subjectArrayClass = new SubjectArrayClass();

        builder = new AlertDialog.Builder(TeacherView.this);
        final View view = getLayoutInflater().inflate(R.layout.add_assign_subject,null);

        SaveSubjectBtn = view.findViewById(R.id.saveSubjects);
        listsubject = view.findViewById(R.id.listSubject);
        ImageView CloseDialog = view.findViewById(R.id.closeSubjectDialog);

        builder.setView(view);
        alertDialog = builder.create();
        alertDialog.show();

        try{
            if (assignLevel.contains("Kinder") && assignLevel.contains("Preparatory") && assignLevel.contains("Nursery")){
                arraySubjectsAdapter = new ArrayAdapter<String>(TeacherView.this,
                        android.R.layout.simple_list_item_multiple_choice, SubjectArrayClass.CombineAll);
                listsubject.setAdapter(arraySubjectsAdapter);
            }
            else if (assignLevel.contains("Nursery") && assignLevel.contains("Kinder")){
                arraySubjectsAdapter = new ArrayAdapter<String>(TeacherView.this,
                        android.R.layout.simple_list_item_multiple_choice,
                        subjectArrayClass.Combine(SubjectArrayClass.NurserySubject, SubjectArrayClass.KinderSubject));
                listsubject.setAdapter(arraySubjectsAdapter);
            }
            else if(assignLevel.contains("Kinder") && assignLevel.contains("Preparatory")){
                arraySubjectsAdapter = new ArrayAdapter<String>(TeacherView.this,
                        android.R.layout.simple_list_item_multiple_choice,
                        subjectArrayClass.Combine(SubjectArrayClass.KinderSubject, SubjectArrayClass.Preparatory));
                listsubject.setAdapter(arraySubjectsAdapter);
            }
            else if(assignLevel.contains("Nursery") && assignLevel.contains("Preparatory")){
                arraySubjectsAdapter = new ArrayAdapter<String>(TeacherView.this,
                        android.R.layout.simple_list_item_multiple_choice,
                        subjectArrayClass.Combine(SubjectArrayClass.KinderSubject, SubjectArrayClass.Preparatory));
                listsubject.setAdapter(arraySubjectsAdapter);
            }
            else if (assignLevel.contains("Kinder")){
                arraySubjectsAdapter = new ArrayAdapter<String>(TeacherView.this,
                        android.R.layout.simple_list_item_multiple_choice, SubjectArrayClass.KinderSubject);
                listsubject.setAdapter(arraySubjectsAdapter);
            }
            else if (assignLevel.contains("Nursery")){
                arraySubjectsAdapter = new ArrayAdapter<String>(TeacherView.this,
                        android.R.layout.simple_list_item_multiple_choice, SubjectArrayClass.NurserySubject);
                listsubject.setAdapter(arraySubjectsAdapter);
            }
            else if (assignLevel.contains("Preparatory")){
                arraySubjectsAdapter = new ArrayAdapter<String>(TeacherView.this,
                        android.R.layout.simple_list_item_multiple_choice, SubjectArrayClass.Preparatory);
                listsubject.setAdapter(arraySubjectsAdapter);
            }
        }catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        SaveSubjectBtn.setOnClickListener(v -> {
            String selectedItem = "";
            for (int i = 0; i < listsubject.getCount(); i++){
                if (listsubject.isItemChecked(i)){
                    selectedItem += listsubject.getItemAtPosition(i) + ",";
                }
            }
            if (selectedItem.equals("")){
                Toast.makeText(TeacherView.this, "Please select Data", Toast.LENGTH_SHORT).show();
            }else{
                String [] dataSubjectArray = selectedItem.split("\\s*,\\s*");
                subjectArray = Arrays.asList(dataSubjectArray);
                DocumentReference documentReference = db.collection("Teacher")
                        .document(TeacherLogin.teacher_ID);
                documentReference.update("assignSubject", subjectArray);
                finish();
                overridePendingTransition(0, 0);
                startActivity(getIntent());
                overridePendingTransition(0, 0);
                Toast.makeText(TeacherView.this, "Save Successfully", Toast.LENGTH_SHORT).show();
                alertDialog.dismiss();
                //Toast.makeText(TeacherView.this, selectedItem, Toast.LENGTH_SHORT).show();
            }
        });

        CloseDialog.setOnClickListener(v -> alertDialog.dismiss());

    }

    public void FetchSubjects(){
        //Get Data from Firestore (assignSubject field)
        documentReference.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()){
                DocumentSnapshot documentSnapshot = task.getResult();
                if (documentSnapshot.exists()) {
                    try{
                        if (documentSnapshot.exists()){
                            assignSubjects = (List<String>) documentSnapshot.get("assignSubject");

                            //assert assignSubjects != null;
                            arraySubject = new String[assignSubjects.size()];
                            for (int i = 0; i < assignSubjects.size(); i++){
                                arraySubject[i] = assignSubjects.get(i);
                                DataAssignSubject += assignSubjects.get(i);
                                assignSubjects.size();
                                DataAssignSubject += ",";
                            }
                            Toast.makeText(this, DataAssignSubject, Toast.LENGTH_SHORT).show();
                        }
                    }catch (Exception e){
                        Toast.makeText(this, "You have no subject. Please add your subject Thank you!", Toast.LENGTH_SHORT).show();

                    }
                }else{
                    Toast.makeText(this, "No Subject Found. Please Add Subject", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void ViewSubjects(View view) {
        addSubjectToList.clear();
        DisplaySubjectDialog();
    }

    public void DisplaySubjectDialog(){
        builder = new AlertDialog.Builder(TeacherView.this);
        final View view = getLayoutInflater().inflate(R.layout.display_subject_dialog,null);

        ImageView showInformation = view.findViewById(R.id.information);
        Button saveUpdateSubject = view.findViewById(R.id.UpdateNewSubjects);
        Button Close = view.findViewById(R.id.Close);
        DisplaySubjectList = view.findViewById(R.id.listDisplaySubject);

        builder.setView(view);
        alertDialog = builder.create();
        alertDialog.show();

        try{
            for (int i = 0; i < assignSubjects.size(); i++){
                arraySubject[i] = assignSubjects.get(i);
                DataAssignSubject += assignSubjects.get(i);
                assignSubjects.size();
                DataAssignSubject += ",";
                addSubjectToList.add(assignSubjects.get(i));
                arraySubjectsAdapter = new ArrayAdapter<>(TeacherView.this, android.R.layout.simple_list_item_1, addSubjectToList);
                DisplaySubjectList.setAdapter(arraySubjectsAdapter);
            }
        }catch (Exception e){
            e.getMessage();
        }

        saveUpdateSubject.setOnClickListener(v -> {
            for (String item : addSubjectToList){
                DataAddSubjectToList = DataAddSubjectToList + "" + item + ",";
            }

            if (DataAddSubjectToList.equals("") || DataAddSubjectToList.isEmpty()){
                Toast.makeText(TeacherView.this, "no data", Toast.LENGTH_SHORT).show();
            }else{

                String [] dataSubjectArray = DataAddSubjectToList.split("\\s*,\\s*");
                subjectArray = Arrays.asList(dataSubjectArray);
                DocumentReference documentReference = db.collection("Teacher")
                        .document(TeacherLogin.teacher_ID);
                documentReference.update("assignSubject", subjectArray);
                finish();
                overridePendingTransition(0, 0);
                startActivity(getIntent());
                overridePendingTransition(0, 0);
                Toast.makeText(TeacherView.this, "Save Successfully", Toast.LENGTH_SHORT).show();
            }
            alertDialog.dismiss();
        });

        DisplaySubjectList.setOnItemLongClickListener((parent, view1, position, id) -> {
            final int selected_item = position;

            new AlertDialog.Builder(TeacherView.this)
                    .setIcon(R.drawable.ic_delete)
                    .setTitle("Delete Subject")
                    .setMessage("Do you want to delete this subject in the list?")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        addSubjectToList.remove(selected_item);
                        arraySubjectsAdapter.notifyDataSetChanged();
                    })
                    .setNegativeButton("Cancel", null)
                    .show();

            return true;
        });

        DisplaySubjectList.setOnItemClickListener((parent, view12, position, id) -> {
            String SubjectItem = addSubjectToList.get(position);
            Toast.makeText(this, addSubjectToList.get(position), Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(), SubjectVideo.class);
            intent.putExtra("Subjects",SubjectItem);
            startActivity(intent);
            alertDialog.dismiss();
        });

        showInformation.setOnClickListener(v -> Toast.makeText(TeacherView.this, "Delete Subject: Make a long press\n" +
                "Open Subject: Single press\n\nNote: Always Save when you delete subjects ", Toast.LENGTH_SHORT).show());
        Close.setOnClickListener(v -> alertDialog.dismiss());

    }
}


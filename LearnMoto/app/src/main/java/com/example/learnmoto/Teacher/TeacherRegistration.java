package com.example.learnmoto.Teacher;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;
import com.example.learnmoto.Model.TeacherInfo;
import com.example.learnmoto.R;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class TeacherRegistration extends AppCompatActivity {

    EditText TeacherName, TeacherAddress, TeacherPhone, TeacherID, TeacherPassword, AssignLevelInput;
    //Spinner advisoryClass;
    FirebaseFirestore firestore = FirebaseFirestore.getInstance();
//    AlertDialog.Builder builder;
//    AlertDialog alertDialog;
//
//    String data = "";
//
//    Button ListLevelbtn, closeWindow;
//    ArrayList<String> addLevel = new ArrayList<String>();
//    ArrayAdapter<String> arrayAdapter;
//    ListView ListLevel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_registration);

        TeacherName = findViewById(R.id.TeacherName);
        TeacherAddress = findViewById(R.id.TeacherAddress);
        TeacherPhone = findViewById(R.id.TeacherPhone);
        TeacherID = findViewById(R.id.TeacherID);
        TeacherPassword = findViewById(R.id.TeacherPass);
        //advisoryClass = findViewById(R.id.classLevel);

//        List<String> yearlvl = new ArrayList<>();
//        yearlvl.add(0, "None");
//        yearlvl.add("Kinder");
//        yearlvl.add("Nursery");
//        yearlvl.add("Preparatory");
//
//        ArrayAdapter<String> yearlvlAdapter;
//        yearlvlAdapter = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,yearlvl);
//        advisoryClass.setAdapter(yearlvlAdapter);
//
//        //Spinner for Year Level
//        advisoryClass.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                if (parent.getItemAtPosition(position).equals("None"))
//                {
//                    // nothing to do
//                }
//                else
//                {
//                    //once item selected
//                    String itemselect = parent.getItemAtPosition(position).toString();
//                    Toast.makeText(TeacherRegistration.this, itemselect, Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });

    }

    public void RegistrationFunction(View view) {

        String Teacher_Name = TeacherName.getText().toString();
        String Teacher_Address = TeacherAddress.getText().toString();
        String Teacher_Phone = TeacherPhone.getText().toString();
        String Teacher_ID = TeacherID.getText().toString();
        String Teacher_Pass = TeacherPassword.getText().toString();

        if (!Teacher_Name.isEmpty() && !Teacher_Address.isEmpty() &&
                !Teacher_ID.isEmpty() && !Teacher_Pass.isEmpty()){
            if (!Teacher_Phone.isEmpty() && Teacher_Phone.length() == 11) {
                if (Teacher_Pass.length() >= 6){
                    DocumentReference teacherReference = firestore.collection("Teacher").document(Teacher_ID);
                    teacherReference.get().addOnSuccessListener(documentSnapshot -> {
                        if (documentSnapshot.exists()){
                            Toast.makeText(TeacherRegistration.this, "Teacher ID has been registered", Toast.LENGTH_SHORT).show();
                        }else{
                            TeacherInfo teacherInfo = new TeacherInfo();
                            teacherInfo.setTeacher_name(Teacher_Name);
                            teacherInfo.setTeacher_address(Teacher_Address);
                            teacherInfo.setTeacher_phone(Teacher_Phone);
                            teacherInfo.setTeacher_ID(Teacher_ID);
                            teacherInfo.setTeacher_pass(Teacher_Pass);
                            firestore.collection("Teacher").document(Teacher_ID).set(teacherInfo);
                            Toast.makeText(TeacherRegistration.this, "Registration Successfully", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(TeacherRegistration.this, TeacherLogin.class));
                        }
                    });
                }else{
                    Toast.makeText(this, "Password must have 6 or more characters", Toast.LENGTH_SHORT).show();
                }
            }else{
                TeacherPhone.setError("This field contain 11 digit");
            }
        }else{
            setErrors();
        }
    }

    public void TeacherLogin(View view) {
        startActivity(new Intent(this, TeacherLogin.class));
    }

    void setErrors(){
        TeacherName.setError("Required Field");
        TeacherAddress.setError("Required Field");
        TeacherPhone.setError("Required Field");
        TeacherID.setError("Required Field");
        TeacherPassword.setError("Required Field");
    }

//    public void ShowWindowLevel(View view) {
//        AssignLevelWindow();
//    }

//    public void AssignLevelWindow(){
//        builder = new AlertDialog.Builder(TeacherRegistration.this);
//        final View view = getLayoutInflater().inflate(R.layout.assign_class_level,null);
//
//        AssignLevelInput = view.findViewById(R.id.inputAssignLevel);
//        closeWindow = view.findViewById(R.id.CloseWindow);
//        ListLevelbtn = view.findViewById(R.id.SaveLevelList);
//        ListLevel = view.findViewById(R.id.listlevel);
//
//        builder.setView(view);
//        alertDialog = builder.create();
//        alertDialog.show();
//
//        ListLevelbtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String inputLevel = AssignLevelInput.getText().toString();
//
//                if (addLevel.contains(inputLevel)){
//                    Toast.makeText(TeacherRegistration.this, "Already Added", Toast.LENGTH_SHORT).show();
//                }else if(inputLevel.isEmpty() || inputLevel.trim().equals("")){
//                    Toast.makeText(TeacherRegistration.this, "NO value", Toast.LENGTH_SHORT).show();
//                }else if (inputLevel.equals("Nursery") || inputLevel.equals("Kinder") || inputLevel.equals("Preparatory")){
//                    addLevel.add(inputLevel);
//                    arrayAdapter = new ArrayAdapter<String>(TeacherRegistration.this,
//                            android.R.layout.simple_list_item_1, addLevel);
//                    ListLevel.setAdapter(arrayAdapter);
//                    AssignLevelInput.setText("");
//
//                }else{
//                    Toast.makeText(TeacherRegistration.this, "Invalid Input", Toast.LENGTH_SHORT).show();
//
//                }
//            }
//        });
//
//        closeWindow.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                for (String item:addLevel){
//                    data = data + "" + item;
//
//                }
//                Toast.makeText(TeacherRegistration.this, data, Toast.LENGTH_SHORT).show();
//                TeacherName.setText(data);
//                alertDialog.dismiss();
//            }
//        });
//
//
//    }

}
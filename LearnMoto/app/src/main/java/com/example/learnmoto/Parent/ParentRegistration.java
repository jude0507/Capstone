package com.example.learnmoto.Parent;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.learnmoto.Model.ParentInfo;
import com.example.learnmoto.CheckConnection.NetworkChangeListener;
import com.example.learnmoto.R;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class ParentRegistration extends AppCompatActivity {

   // LinearLayout captureImage;
   // CircleImageView imageViewProfile;

    private EditText ParentName, ParentAddress, ParentChildID, ParentID, ParentPassword, ParentPhone;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    NetworkChangeListener networkChangeListener = new NetworkChangeListener();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_registration);

        ParentName = findViewById(R.id.ParentName);
        ParentAddress = findViewById(R.id.ParentAddress);
        ParentChildID = findViewById(R.id.ParentChildID);
        ParentID = findViewById(R.id.ParentID);
        ParentPassword = findViewById(R.id.ParentPass);
        ParentPhone = findViewById(R.id.ParentPhone);

    }

    public void ParentLoginPage(View view) {
        startActivity(new Intent(ParentRegistration.this, ParentLogin.class));
    }

    public void RegistrationFunction(View view) {
        String Parent_Name = ParentName.getText().toString().trim();
        String Parent_Address = ParentAddress.getText().toString().trim();
        String Parent_ChildID = ParentChildID.getText().toString().trim();
        String Parent_ID = ParentID.getText().toString().trim();
        String Parent_Password = ParentPassword.getText().toString().trim();
        String Parent_Phone = ParentPhone.getText().toString().trim();

        if (!Parent_ChildID.isEmpty()){
            DocumentReference studentReference = db.collection("Student").document(Parent_ChildID);
            studentReference.get().addOnSuccessListener(documentSnapshot -> {
                String fetchStudID;
                if (documentSnapshot.exists()){
                    fetchStudID = documentSnapshot.getString("sID").toString();
                    if (!Parent_ChildID.equals(fetchStudID)){
                        ParentChildID.setError("Not Found");
                        Toast.makeText(ParentRegistration.this, "Child ID not Found", Toast.LENGTH_SHORT).show();
                    }else{
                        //Toast.makeText(ParentRegistration.this, "Equal", Toast.LENGTH_SHORT).show();
                        if(!Parent_Name.isEmpty() && !Parent_Address.isEmpty() &&
                                !Parent_ID.isEmpty() && !Parent_Password.isEmpty() && !Parent_Phone.isEmpty()){
                            DocumentReference documentReference = db.collection("Parent").document(ParentID.getText().toString());
                            documentReference.get().addOnSuccessListener(documentSnapshot1 -> {
                                if (documentSnapshot1.exists()){
                                    ParentID.setError("UserID already registered");
                                    Toast.makeText(ParentRegistration.this, "UserID already registered", Toast.LENGTH_SHORT).show();
                                }else{
                                    ParentInfo parentInfo = new ParentInfo();
                                    parentInfo.setpName(Parent_Name);
                                    parentInfo.setpAddress(Parent_Address);
                                    parentInfo.setpChildID(Parent_ChildID);
                                    parentInfo.setpID(Parent_ID);
                                    parentInfo.setpPassword(Parent_Password);
                                    parentInfo.setpPhoneNumber(String.valueOf(Parent_Phone));
                                    db.collection("Parent").document(Parent_ID).set(parentInfo);
                                    Toast.makeText(ParentRegistration.this, "Registration Successfully", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(ParentRegistration.this, ParentLogin.class));
                                }
                            });

                        }else{
                            setErrors();
                        }
                    }
                }else{
                    ParentChildID.setError("Not Match");
                }
            });
        }else{
            setErrors();
        }
    }
    void setErrors(){
        ParentName.setError("Required Field");
        ParentAddress.setError("Required Field");
        ParentChildID.setError("Required Field");
        ParentID.setError("Required Field");
        ParentPassword.setError("Required Field");
        ParentPhone.setError("Required Field");
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
package com.example.learnmoto.Parent;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.learnmoto.MainActivity;
import com.example.learnmoto.R;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class ParentLogin extends AppCompatActivity {

    EditText ParentID, ParentPass;
    public static String pID, pPass, pName, pAddress;
    SharedPreferences sharedPreferences;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference collectionReference = db.collection("Parent");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_login);

        ParentID = findViewById(R.id.parentID);
        ParentPass = findViewById(R.id.parentPassword);

    }

    public void ParentViewFunction(View view) {
//        sharedPreferences = getSharedPreferences("Preferences", MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.putBoolean("hasLoggedIn", true);
//        editor.commit();
        ParentLoginFunction();

    }

    private void ParentLoginFunction() {
        if (!ParentID.getText().toString().trim().isEmpty() && !ParentPass.getText().toString().trim().isEmpty()){
            DocumentReference documentReference = db.collection("Parent").document(ParentID.getText().toString());
            documentReference.get().addOnSuccessListener(documentSnapshot -> {
                if (documentSnapshot.exists()) {
                    pID = documentSnapshot.getString("pID").toString();
                    pPass = documentSnapshot.getString("pPassword").toString();
                    pName = documentSnapshot.getString("pName").toString();
                    pAddress = documentSnapshot.getString("pAddress").toString();
                    if (ParentID.getText().toString().equals(pID) && ParentPass.getText().toString().equals(pPass)){
                        Toast.makeText(ParentLogin.this, "Login Successfully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(ParentLogin.this, ParentView.class));
                    }else {
                        Toast.makeText(ParentLogin.this, "Credential not match", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(ParentLogin.this, "You don't have an account", Toast.LENGTH_SHORT).show();
                }
            });
        }else{
            if (ParentID.getText().toString().isEmpty() && ParentPass.getText().toString().isEmpty()){
                ParentID.setError("Required Field");
                ParentPass.setError("Required Field");
                Toast.makeText(ParentLogin.this, "Username and Password Required", Toast.LENGTH_LONG).show();
            }else if (ParentID.getText().toString().isEmpty()){
                ParentID.setError("Required Field");
            }else{
                ParentPass.setError("Required Field");
            }

        }

    }


    public void ParentRegistrationPage(View view) {
        startActivity(new Intent(this, ParentRegistration.class));
    }

    public void BacktoMain(View view) {
        startActivity(new Intent(this, MainActivity.class));
    }
}
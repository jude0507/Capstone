package com.example.learnmoto.Parent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.learnmoto.Model.ParentInfo;
import com.example.learnmoto.CheckConnection.NetworkChangeListener;
import com.example.learnmoto.R;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import de.hdodenhof.circleimageview.CircleImageView;

public class ParentSettings extends AppCompatActivity {
    public static Uri imageUri;
    String ImageURLPath;
    String ParentName = ParentLogin.pName;
    String ParentAddress = ParentLogin.pAddress;
    String ParentID = ParentLogin.pID;
    String ParentPassword = ParentLogin.pPass;

    EditText Et_Name, Et_Address, Et_ParentID, Et_Password;
    Button Update;
    LinearLayout chooseFile;
    CircleImageView ParentImage;

    FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
    StorageReference storageReference = firebaseStorage.getReference();

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference collectionReference = db.collection("Parent");

    NetworkChangeListener networkChangeListener = new NetworkChangeListener();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.parent_settings);
        Et_Name = findViewById(R.id.NameParent);
        Et_Address = findViewById(R.id.NameAddress);
        Et_ParentID = findViewById(R.id.IDParent);
        Et_Password = findViewById(R.id.PasswordParent);
        Update = findViewById(R.id.UpdateInfoBtn);
        chooseFile = findViewById(R.id.choosefile);
        ParentImage = findViewById(R.id.profile);

        chooseFile.setOnClickListener(v -> ImagePicker.Companion.with(ParentSettings.this)
                .crop()
                .maxResultSize(1080, 1080)
                .start(101));


        DisplayParentInfo();
        ReadTextOnly();

    }

    public void backtoparentview(View view) {
        startActivity(new Intent(this, ParentView.class));
    }

    public void DisplayParentInfo(){
        Et_Name.setText(ParentName);
        Et_Address.setText(ParentAddress);
        Et_ParentID.setText(ParentID);
        Et_Password.setText(ParentPassword);

        collectionReference.whereEqualTo("pID", ParentLogin.pID)
                .get().addOnSuccessListener(queryDocumentSnapshots -> {
                    String imageDiplay = "";
                    for (QueryDocumentSnapshot documentSnapshot: queryDocumentSnapshots){
                        ParentInfo parentInfo = documentSnapshot.toObject(ParentInfo.class);
                        parentInfo.setMyid(documentSnapshot.getId());

                        imageDiplay += parentInfo.getImageurl();

                    }
                    Glide.with(getApplicationContext()).load(imageDiplay).placeholder(R.drawable.ic_user_circle).into(ParentImage);
                });

    }

    public void ReadTextOnly(){
        Et_Name.setEnabled(false);
        Et_Name.setTextColor(ContextCompat.getColor(this, R.color.black));
        Et_Address.setEnabled(false);
        Et_Address.setTextColor(ContextCompat.getColor(this, R.color.black));
        Et_ParentID.setEnabled(false);
        Et_ParentID.setTextColor(ContextCompat.getColor(this, R.color.black));
        Et_Password.setEnabled(false);
        Et_Password.setTextColor(ContextCompat.getColor(this, R.color.black));
        Update.setEnabled(false);
        Update.setTextColor(ContextCompat.getColor(this, R.color.white));
        chooseFile.setEnabled(false);
    }

    public void UpdateParentInfo(View view) {
        DocumentReference documentReference = db.collection("Parent").document(ParentLogin.pID);
        documentReference.update("pName", Et_Name.getText().toString());
        documentReference.update("pAddress", Et_Address.getText().toString());
        documentReference.update("pID", Et_ParentID.getText().toString());
        documentReference.update("pPassword", Et_Password.getText().toString());
        UploadImageToStorage();
        Toast.makeText(this, "Updated Successfully", Toast.LENGTH_SHORT).show();
        ReadTextOnly();
    }

    public void EditData(View view) {
        AlertDialog.Builder alBuilder = new AlertDialog.Builder(this);
        alBuilder.setTitle("Confirmation Message")
                .setMessage("Do you want to update your data in this field? " +
                        "\n\t (Address, Password, Images)");
        alBuilder.setPositiveButton("Confirm", (dialog, which) -> {

            Et_Address.setEnabled(true);
            //studLevel.setEnabled(true);
            Et_Password.setEnabled(true);
            Update.setEnabled(true);
            chooseFile.setEnabled(true);

        });
        alBuilder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());
        alBuilder.show();
    }

    public void UploadImageToStorage(){
        if (imageUri != null) {
            storageReference = firebaseStorage.getReference().child("images/").child(imageUri.getLastPathSegment());
            storageReference.putFile(imageUri).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    storageReference.getDownloadUrl().addOnSuccessListener(uri -> {
                        ImageURLPath = uri.toString();
                        //Upload URL&ImageName to firestore
                        DocumentReference documentReference = db.collection("Parent").document(ParentLogin.pID);
                        documentReference.update("imageurl", ImageURLPath);
                        documentReference.update("imagename", imageUri.toString());

                        Toast.makeText(ParentSettings.this, "Uploaded Successfully Storage", Toast.LENGTH_LONG).show();
                    });
                }
            });
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101 && resultCode == Activity.RESULT_OK) {
            imageUri = data.getData();
            ParentImage.setImageURI(imageUri);
        }
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
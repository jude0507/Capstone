package com.example.learnmoto.Student;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.example.learnmoto.Model.StudentInfo;
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

public class StudentInfoSettings extends AppCompatActivity {

    //public static final int CAMERA_REQUEST_CODE = 1;
    //public static final int GALLERY_REQUEST_CODE = 2;
    public static Uri imageUri;
    public static Bitmap bitmap;
    public static String displayImageURL = "";
    public static String encodedString;
    String ImageURLPath;
    EditText studName, studID, studPass, studAddress, studGuardian, studLevel, studBirthday, studGender;
    Button updateButton;
    LinearLayout chooseFile;
    CircleImageView StudPicture;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    //public static String imageDiplay = "";

    FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
    StorageReference storageReference = firebaseStorage.getReference();

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference collectionReference = db.collection("Student");

    NetworkChangeListener networkChangeListener = new NetworkChangeListener();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_info_settings);

        studName = findViewById(R.id.stName);
        studID = findViewById(R.id.stID);
        studPass = findViewById(R.id.stPass);
        studAddress = findViewById(R.id.stAddress);
        studGuardian = findViewById(R.id.stGuardian);
        studLevel = findViewById(R.id.stLevel);
        studBirthday = findViewById(R.id.stBirthday);
        studGender = findViewById(R.id.stGender);
        updateButton = findViewById(R.id.updatebtn);
        chooseFile = findViewById(R.id.choosefile);
        StudPicture = findViewById(R.id.picture);

        chooseFile.setOnClickListener(v -> ImagePicker.Companion.with(StudentInfoSettings.this)
                .crop()
                .maxResultSize(1080, 1080)
                .start(101));

        //fetch data from shared references
        sharedPreferences = getApplicationContext().getSharedPreferences("Preferences", MODE_PRIVATE);
        String name = sharedPreferences.getString("sName", "");
        String address = sharedPreferences.getString("sAddress", "");
        String gender = sharedPreferences.getString("sGender", "");
        String level = sharedPreferences.getString("sLevel", "");
        String guardian = sharedPreferences.getString("sGuardian", "");
        String birthday = sharedPreferences.getString("sBirthday", "");
        String id = sharedPreferences.getString("sID", "");
        String password = sharedPreferences.getString("sPassword", "");
        //String image = sharedPreferences.getString("imageurl", "");

        studName.setText(name);
        studID.setText(id);
        studPass.setText(password);
        studAddress.setText(address);
        studGuardian.setText(guardian);
        studLevel.setText(level);
        studBirthday.setText(birthday);
        studGender.setText(gender);
        DisplayImage();
        //StudPicture.setImageURI(Uri.parse(image));
        //Glide.with(getApplicationContext()).load(image).placeholder(R.drawable.ic_user_circle).into(StudPicture);

        ReadOnlyText();

    }

    public void ReadOnlyText() {
        studName.setEnabled(false);
        studName.setBackgroundResource(R.drawable.background_edittext);
        studName.setTextColor(ContextCompat.getColor(this, R.color.black));
        studID.setEnabled(false);
        studID.setBackgroundResource(R.drawable.background_edittext);
        studID.setTextColor(ContextCompat.getColor(this, R.color.black));
        studPass.setEnabled(false);
        studPass.setBackgroundResource(R.drawable.background_edittext);
        studPass.setTextColor(ContextCompat.getColor(this, R.color.black));
        studAddress.setEnabled(false);
        studAddress.setBackgroundResource(R.drawable.background_edittext);
        studAddress.setTextColor(ContextCompat.getColor(this, R.color.black));
        studGuardian.setEnabled(false);
        studGuardian.setBackgroundResource(R.drawable.background_edittext);
        studGuardian.setTextColor(ContextCompat.getColor(this, R.color.black));
        studLevel.setEnabled(false);
        studLevel.setBackgroundResource(R.drawable.background_edittext);
        studLevel.setTextColor(ContextCompat.getColor(this, R.color.black));
        studBirthday.setEnabled(false);
        studBirthday.setBackgroundResource(R.drawable.background_edittext);
        studBirthday.setTextColor(ContextCompat.getColor(this, R.color.black));
        studGender.setEnabled(false);
        studGender.setBackgroundResource(R.drawable.background_edittext);
        studGender.setTextColor(ContextCompat.getColor(this, R.color.black));
        updateButton.setEnabled(false);
        updateButton.setTextColor(ContextCompat.getColor(this, R.color.white));
        chooseFile.setEnabled(false);


    }

    //Update the Data of the Student (Picture, address, password)
    public void UpdateStudentData() {

        //remove the saved data in preference (password, address)
        sharedPreferences = getApplicationContext().getSharedPreferences("Preferences", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.remove("sAddress");
        editor.remove("sPassword");
        //editor.remove("imageurl");
        editor.apply();

        String updateAddress = studAddress.getText().toString();
        String updatePassword = studPass.getText().toString();
        //update the data in the firestore
        DocumentReference documentReference = db.collection("Student").document(StudentLogin.studID);
        documentReference.update("sAddress", updateAddress);
        documentReference.update("sPassword", updatePassword);

        //update the data in the sharedpreference
        editor.putString("sAddress", updateAddress);
        editor.putString("sPassword", updatePassword);
        editor.commit();
        String address = sharedPreferences.getString("sAddress", "");
        String password = sharedPreferences.getString("sPassword", "");
        studPass.setText(password);
        studAddress.setText(address);
        UploadImageToStorage();
        Toast.makeText(this, "Updated Successfully", Toast.LENGTH_SHORT).show();

    }

    public void UploadImageToStorage(){
        if (imageUri != null) {
            storageReference = firebaseStorage.getReference().child("images/").child(imageUri.getLastPathSegment());
            storageReference.putFile(imageUri).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    storageReference.getDownloadUrl().addOnSuccessListener(uri -> {
                        ImageURLPath = uri.toString();
                        //Upload URL&ImageName to firestore
                        DocumentReference documentReference = db.collection("Student").document(StudentLogin.studID);
                        documentReference.update("imageurl", ImageURLPath);
                        documentReference.update("imagename", imageUri.toString());

                        Toast.makeText(StudentInfoSettings.this, "Uploaded Successfully Storage", Toast.LENGTH_LONG).show();
                    });
                }
            });
        }
    }

    public void DisplayImage(){
        collectionReference.whereEqualTo("sID", StudentLogin.studID)
                .get().addOnSuccessListener(queryDocumentSnapshots -> {
                    String imageDiplay = "";
                    for (QueryDocumentSnapshot documentSnapshot: queryDocumentSnapshots){
                        StudentInfo studentInfo = documentSnapshot.toObject(StudentInfo.class);
                        studentInfo.setMyid(documentSnapshot.getId());

                        imageDiplay += studentInfo.getImageurl();

                    }
                    Glide.with(getApplicationContext()).load(imageDiplay).placeholder(R.drawable.ic_user_circle).into(StudPicture);
                });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101 && resultCode == Activity.RESULT_OK) {
            imageUri = data.getData();
            StudPicture.setImageURI(imageUri);
//            try {
//                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
//                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//                bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
//                byte[] byteArray = outputStream.toByteArray();
//
//                //Use your Base64 String as you wish
//                encodedString = Base64.encodeToString(byteArray, Base64.DEFAULT);
//
//                StudPicture.setImageURI(imageUri);
//
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
        }
    }

    int pressed = 0;
    @Override
    public void onBackPressed() {
        pressed++;
        if (pressed == 2)
            super.onBackPressed();
    }



    //Confirmation dialog for editing data
    public void EditData(View view) {
        AlertDialog.Builder alBuilder = new AlertDialog.Builder(this);
        alBuilder.setTitle("Confirmation Message")
                .setMessage("Do you want to update your data in this field? " +
                        "\n\t (Address, Password, Images)");
        alBuilder.setPositiveButton("Confirm", (dialog, which) -> {

            studAddress.setEnabled(true);
            //studLevel.setEnabled(true);
            studPass.setEnabled(true);
            updateButton.setEnabled(true);
            chooseFile.setEnabled(true);

        });
        alBuilder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());
        alBuilder.show();

    }

    public void UpdateStudentInfo(View view) {
        UpdateStudentData();
        ReadOnlyText();
    }

    public void backtosettings(View view) {
        startActivity(new Intent(this, StudentSettings.class));
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

package com.example.learnmoto.Student;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.learnmoto.DisplayImage;
import com.example.learnmoto.CheckConnection.NetworkChangeListener;
import com.example.learnmoto.R;
import com.example.learnmoto.ShowPassword;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
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
    ImageView Camera;
    CircleImageView StudPicture;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    //public static String imageDiplay = "";

    FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
    StorageReference storageReference = firebaseStorage.getReference();

    FirebaseFirestore db = FirebaseFirestore.getInstance();

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
        StudPicture = findViewById(R.id.picture);
        Camera = findViewById(R.id.Camera);

        Camera.setOnClickListener(v -> ImagePicker.Companion.with(StudentInfoSettings.this)
                .crop()
                .maxResultSize(1080, 1080)
                .start(101));

        ShowPassword showPassword = new ShowPassword();
        showPassword.ShowPassword(studPass);

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
        //Toast.makeText(this, "Updated Successfully", Toast.LENGTH_SHORT).show();

    }

    public void UploadImageToStorage(){
        if (imageUri != null) {
            ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.setMessage("Setting your image");
            progressDialog.show();
            storageReference = firebaseStorage.getReference().child("images/").child(imageUri.getLastPathSegment());
            storageReference.putFile(imageUri).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    storageReference.getDownloadUrl().addOnSuccessListener(uri -> {
                        ImageURLPath = uri.toString();
                        //Upload URL&ImageName to firestore
                        DocumentReference documentReference = db.collection("Student").document(StudentLogin.studID);
                        documentReference.update("imageurl", ImageURLPath);
                        documentReference.update("imagename", imageUri.toString());
                        progressDialog.dismiss();
                        Toast.makeText(StudentInfoSettings.this, "Uploaded Successfully", Toast.LENGTH_LONG).show();
                        Camera.setVisibility(View.GONE);
                        finish();
                        overridePendingTransition(0, 0);
                        startActivity(getIntent());
                        overridePendingTransition(0, 0);
                    });
                }
            }).addOnProgressListener(snapshot -> {
                double progress = (100.0 * snapshot.getBytesTransferred()) / snapshot.getTotalByteCount();
                progressDialog.setMessage(((int) progress) + "% Please Wait....");
            }).addOnFailureListener(e -> Toast.makeText(StudentInfoSettings.this, e.getMessage(), Toast.LENGTH_SHORT).show());
        }else{
            Toast.makeText(this, "No image uploaded. Please Try Again!", Toast.LENGTH_SHORT).show();
        }
    }

    public void DisplayImage(){
        DisplayImage.RetrieveImageStudents(this, "Student", "sID", StudentLogin.studID, StudPicture);
    //https://www.c-sharpcorner.com/article/how-to-store-and-retrieve-the-image-using-sharedpreferences-in-android/
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101 && resultCode == Activity.RESULT_OK) {
            imageUri = data.getData();
            StudPicture.setImageURI(imageUri);

//            Bitmap photo= (Bitmap) data.getExtras().get("data");
//            StudPicture.setImageBitmap(photo);
//            ByteArrayOutputStream baos = new ByteArrayOutputStream();
//            photo.compress(Bitmap.CompressFormat.JPEG, 100, baos);
//            byte[] b = baos.toByteArray();
//            String encodedImage = Base64.encodeToString(b, Base64.DEFAULT);
//            preferenceManager.setString("image_data",encodedImage);
//            Toast.makeText(this, "Image saved in SharedPreferences", Toast.LENGTH_SHORT).show();
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
            studPass.setEnabled(true);
            updateButton.setEnabled(true);
            Camera.setVisibility(View.VISIBLE);
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

//    private final static String CAPTURED_PHOTO_URI_KEY = "imageUri";
//    private final static String CAPTURED_PHOTO_PATH_KEY = "ImageURLPath";
//    @Override
//    public void onSaveInstanceState(Bundle saveInstance){
//
//        if (ImageURLPath != null) {
//
//            saveInstance.putString(CAPTURED_PHOTO_PATH_KEY, ImageURLPath);
//        }
//        if (ImageURLPath != null) {
//
//            saveInstance.putString(CAPTURED_PHOTO_URI_KEY, imageUri.toString());
//        }
//
//        super.onSaveInstanceState(saveInstance);
//    }
//
//    @Override
//    public void onRestoreInstanceState(Bundle savedInstanceState) {
//        if (savedInstanceState.containsKey(CAPTURED_PHOTO_PATH_KEY)) {
//
//            ImageURLPath = savedInstanceState.getString(CAPTURED_PHOTO_PATH_KEY);
//        }
//        if (savedInstanceState.containsKey(CAPTURED_PHOTO_URI_KEY)) {
//
//            imageUri = Uri.parse(savedInstanceState.getString(CAPTURED_PHOTO_URI_KEY));    }
//        super.onRestoreInstanceState(savedInstanceState);
//    }

}

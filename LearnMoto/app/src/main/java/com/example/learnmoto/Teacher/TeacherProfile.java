package com.example.learnmoto.Teacher;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.method.KeyListener;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.learnmoto.Adapter.TranslateAnimatioUI;
import com.example.learnmoto.CheckConnection.NetworkChangeListener;
import com.example.learnmoto.DisplayImage;
import com.example.learnmoto.MainActivity;
import com.example.learnmoto.Model.StudentInfo;
import com.example.learnmoto.R;
import com.example.learnmoto.ShowPassword;
import com.example.learnmoto.Student.StudentInfoSettings;
import com.example.learnmoto.Student.StudentLogin;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import de.hdodenhof.circleimageview.CircleImageView;

public class TeacherProfile extends AppCompatActivity {

    Uri imageUri;
    String ImageURLPath;
    BottomNavigationView bottomNavigationView;
    NetworkChangeListener networkChangeListener = new NetworkChangeListener();
    LinearLayout ProfileLayout, ProfileContainer, AccountLayout, AccountContainer;
    EditText TeacherName, Address, PhoneNumber, TeacherUserName, Password,
            NewPass, ConfirmNewPass;
    TextView NameUser, TxtEditProfile;
    Button ProfileBtn, AccountBtn, ChangePassBtn, BasicInfoUpdateBtn, DoneUpdatePicture, ConfirmChangePassBtn;
    ImageView CameraBtn;
    ScrollView scrollView;
    CircleImageView ProfilePicture;
    ProgressDialog progressDialog;

    FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
    StorageReference storageReference = firebaseStorage.getReference();

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference collectionReference = db.collection("Teacher");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_profile);

        ProfileLayout = findViewById(R.id.BasicInformationLayout);
        ProfileContainer = findViewById(R.id.BasicInformationContainer);
        AccountLayout = findViewById(R.id.AccountInformationLayout);
        AccountContainer = findViewById(R.id.AccountInformationContainer);
        ProfileBtn = findViewById(R.id.BasicInformationArrowBtn);
        AccountBtn = findViewById(R.id.AccountInformationArrowBtn);
        scrollView = findViewById(R.id.ScrollViewProfile);
        TeacherName = findViewById(R.id.et_teacherName);
        Address = findViewById(R.id.et_teacherAddress);
        PhoneNumber = findViewById(R.id.et_PhoneNumber);
        TeacherUserName = findViewById(R.id.et_UserName);
        Password = findViewById(R.id.et_Pass);
        NewPass = findViewById(R.id.et_newPass);
        ConfirmNewPass = findViewById(R.id.et_confirmPass);
        ProfilePicture = findViewById(R.id.profile);
        CameraBtn = findViewById(R.id.EditProfilePhoto);
        ChangePassBtn = findViewById(R.id.ChangePassBtn);
        BasicInfoUpdateBtn = findViewById(R.id.UpdateBasicInfoBtn);
        DoneUpdatePicture = findViewById(R.id.DoneUpdatePicture);
        NameUser = findViewById(R.id.NameUser);
        TxtEditProfile = findViewById(R.id.txtEditProfile);
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.profile);

        ShowPassword showPassword = new ShowPassword();
        showPassword.ShowPassword(Password);
        showPassword.ShowPassword(NewPass);
        showPassword.ShowPassword(ConfirmNewPass);
        DisplayTeacherInfo();
        //TeacherName.setClickable(false);

        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {

            //Condition of what activity is selected
            switch (item.getItemId()){
                case R.id.home:
                    startActivity(new Intent(getApplicationContext(), TeacherView.class));
                    overridePendingTransition(0,0);
                    return true;

                case R.id.announcements:
                    startActivity(new Intent(getApplicationContext(), Announcement.class));
                    overridePendingTransition(0,0);
                    return true;

                case R.id.profile:
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

    public void ExpandBasicInformation(View view) {
        if (ProfileContainer.getVisibility() == View.GONE) {
            TransitionManager.beginDelayedTransition(ProfileLayout, new AutoTransition());
            ProfileContainer.setVisibility(View.VISIBLE);
            ProfileBtn.setBackgroundResource(R.drawable.ic_arrow_up);
        }else{
            TransitionManager.beginDelayedTransition(ProfileLayout, new AutoTransition());
            ProfileContainer.setVisibility(View.GONE);
            ProfileBtn.setBackgroundResource(R.drawable.ic_arrow_down);
        }
    }

    public void ExpandAccountInformation(View view) {
        if (AccountContainer.getVisibility() == View.GONE) {
            TransitionManager.beginDelayedTransition(AccountLayout, new AutoTransition());
            AccountContainer.setVisibility(View.VISIBLE);
            AccountBtn.setBackgroundResource(R.drawable.ic_arrow_up);
        }else{
            TransitionManager.beginDelayedTransition(AccountLayout, new AutoTransition());
            AccountContainer.setVisibility(View.GONE);
            AccountBtn.setBackgroundResource(R.drawable.ic_arrow_down);
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101 && resultCode == Activity.RESULT_OK) {
            imageUri = data.getData();
            ProfilePicture.setImageURI(imageUri);
            if (DoneUpdatePicture.getVisibility() == View.GONE){
                DoneUpdatePicture.setVisibility(View.VISIBLE);
                CameraBtn.setVisibility(View.GONE);
            }
        }
    }

    public void TakePhoto(View view) {
        ImagePicker.Companion.with(TeacherProfile.this)
                .crop()
                .maxResultSize(1080, 1080)
                .start(101);
    }

    public void EditProfile(View view) {
        AlertDialog.Builder alBuilder = new AlertDialog.Builder(this);
        alBuilder.setTitle("Confirmation Message")
                .setMessage("Do you want to update your data");
        alBuilder.setPositiveButton("Yes", (dialog, which) -> {

            if (CameraBtn.getVisibility() == View.GONE){
                CameraBtn.setVisibility(View.VISIBLE);

                Address.setEnabled(true);
                PhoneNumber.setEnabled(true);
                BasicInfoUpdateBtn.setEnabled(true);
                ChangePassBtn.setEnabled(true);
                Password.setEnabled(true);
                Password.setText("");
                NewPass.setVisibility(View.VISIBLE);
                ConfirmNewPass.setVisibility(View.VISIBLE);
                BasicInfoUpdateBtn.setVisibility(View.VISIBLE);
                ChangePassBtn.setVisibility(View.VISIBLE);

            }

            TxtEditProfile.setEnabled(false);

        });
        alBuilder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());
        alBuilder.show();
    }

    public void ReadOnly(){

        Address.setEnabled(false);
        Address.setBackgroundResource(R.drawable.background_edittext);
        Address.setTextColor(ContextCompat.getColor(this, R.color.black));
        PhoneNumber.setEnabled(false);
        PhoneNumber.setBackgroundResource(R.drawable.background_edittext);
        PhoneNumber.setTextColor(ContextCompat.getColor(this, R.color.black));
        Password.setEnabled(false);
        Password.setBackgroundResource(R.drawable.background_edittext);
        Password.setTextColor(ContextCompat.getColor(this, R.color.black));
        TeacherName.setFocusable(false);
        TeacherUserName.setFocusable(false);
        BasicInfoUpdateBtn.setEnabled(false);
        ChangePassBtn.setEnabled(false);
    }

    public void DisplayTeacherInfo(){
        NameUser.setText(TeacherLogin.teacher_name);
        TeacherName.setText(TeacherLogin.teacher_name);
        Address.setText(TeacherLogin.teacher_address);
        PhoneNumber.setText(TeacherLogin.teacher_phone);
        TeacherUserName.setText(TeacherLogin.teacher_ID);
        Password.setText(TeacherLogin.teacher_pass);
        DisplayImage();
        ReadOnly();

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

    int pressed = 0;
    @Override
    public void onBackPressed() {
        pressed++;
        if (pressed == 2)
            super.onBackPressed();
    }

    public void UpdateImage(View view) {
        UploadImageToStorage();
        DoneUpdatePicture.setVisibility(View.GONE);
        CameraBtn.setVisibility(View.GONE);
    }

    public void ChangePassword(View view) {
        AlertDialog.Builder alBuilder = new AlertDialog.Builder(this);
        alBuilder.setTitle("Confirmation Message")
                .setMessage("Submit your new password");
        alBuilder.setPositiveButton("Yes", (dialog, which) -> {
            UpdatePassword();
        });
        alBuilder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());
        alBuilder.show();

    }

    public void UpdatePassword(){
        String GetCurrentPassword = TeacherLogin.teacher_pass;
        String InputPassword = Password.getText().toString();
        String InputNewPassword = NewPass.getText().toString();
        String InputConfirmPassword = ConfirmNewPass.getText().toString();

        
        if (InputPassword.equals(GetCurrentPassword)){
            if (!InputNewPassword.isEmpty() && !InputNewPassword.equals(InputPassword) &&
                    InputNewPassword.length() >= 6){
                if (InputConfirmPassword.equals(InputNewPassword)){
                    DocumentReference documentReference = db.collection("Teacher").document(TeacherLogin.teacher_ID);
                    documentReference.update("teacher_pass", InputNewPassword);
                    NewPass.setVisibility(View.GONE);
                    ConfirmNewPass.setVisibility(View.GONE);
                    ChangePassBtn.setEnabled(false);
                    Toast.makeText(this, "New Password Submitted", Toast.LENGTH_SHORT).show();
                    Password.setEnabled(false);
                    Password.setText(InputNewPassword);

                }else{
                    ConfirmNewPass.setError("Not Matched");
                }
            }else{
                NewPass.setError("Error: Field is Required/Matched");
            }
        }else{
            Password.setError("Not Found");
        }

    }

    public void UpdateBasicInformation(View view) {
        AlertDialog.Builder alBuilder = new AlertDialog.Builder(this);
        alBuilder.setTitle("Confirmation Message")
                .setMessage("Are your sure you want to update your information");
        alBuilder.setPositiveButton("Yes", (dialog, which) -> {

            UpdateBasicInformation();
        });
        alBuilder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());
        alBuilder.show();
    }

    public void UpdateBasicInformation(){
        String InputAddress = Address.getText().toString();
        String InputPhoneNumber = PhoneNumber.getText().toString();

        if (InputAddress.isEmpty() && InputPhoneNumber.isEmpty()){
            Address.setError("Required Field");
            PhoneNumber.setError("Required Field");
        }else if(InputPhoneNumber.length() < 11){
            PhoneNumber.setError("Phone Number must be valid");
        }
        else{

            DocumentReference documentReference = db.collection("Teacher").document(TeacherLogin.teacher_ID);
            documentReference.update("teacher_address", InputAddress);
            documentReference.update("teacher_phone", InputPhoneNumber);

            Toast.makeText(this, "Update Sucessfully", Toast.LENGTH_SHORT).show();
            Address.setEnabled(false);
            PhoneNumber.setEnabled(false);
        }

    }

    public void UploadImageToStorage(){
        if (imageUri != null) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Setting your image");
            progressDialog.show();
            storageReference = firebaseStorage.getReference().child("images/").child(imageUri.getLastPathSegment());
            storageReference.putFile(imageUri).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    storageReference.getDownloadUrl().addOnSuccessListener(uri -> {
                        ImageURLPath = uri.toString();
                        //Upload URL&ImageName to firestore
                        DocumentReference documentReference = db.collection("Teacher").document(TeacherLogin.teacher_ID);
                        documentReference.update("imageurl", ImageURLPath);
                        documentReference.update("imagename", imageUri.toString());
                        progressDialog.dismiss();
                        Toast.makeText(TeacherProfile.this, "Image Uploaded Successfully", Toast.LENGTH_LONG).show();
                    });
                }
            }).addOnProgressListener(snapshot -> {
                double progress = (100.0 * snapshot.getBytesTransferred()) / snapshot.getTotalByteCount();
                progressDialog.setMessage(((int) progress) + "% Uploading Image....");
            }).addOnFailureListener(e -> Toast.makeText(TeacherProfile.this, e.getMessage(), Toast.LENGTH_SHORT).show());
        }else{
            Toast.makeText(this, "No image uploaded. Please Try Again!", Toast.LENGTH_SHORT).show();
        }
    }


}
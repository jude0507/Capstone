package com.example.learnmoto.Teacher;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.method.KeyListener;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.learnmoto.Adapter.TranslateAnimatioUI;
import com.example.learnmoto.CheckConnection.NetworkChangeListener;
import com.example.learnmoto.R;
import com.example.learnmoto.Student.StudentInfoSettings;
import com.example.learnmoto.Student.StudentLogin;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import de.hdodenhof.circleimageview.CircleImageView;

public class TeacherProfile extends AppCompatActivity {

    Uri imageUri;
    BottomNavigationView bottomNavigationView;
    NetworkChangeListener networkChangeListener = new NetworkChangeListener();
    LinearLayout ProfileLayout, ProfileContainer, AccountLayout, AccountContainer;
    EditText TeacherName, Address, PhoneNumber, TeacherUserName, Password;
    TextView NameUser, TxtEditProfile;
    Button ProfileBtn, AccountBtn, UpdatePicturebtn, ChangePassBtn, BasicInfoUpdateBtn, DoneUpdatePicture;
    ImageView CameraBtn;
    ScrollView scrollView;
    CircleImageView ProfilePicture;

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
        ProfilePicture = findViewById(R.id.profile);
        CameraBtn = findViewById(R.id.EditProfilePhoto);
        UpdatePicturebtn = findViewById(R.id.UpdatePicture);
        ChangePassBtn = findViewById(R.id.ChangePassBtn);
        BasicInfoUpdateBtn = findViewById(R.id.UpdateBasicInfoBtn);
        DoneUpdatePicture = findViewById(R.id.UpdatePicture);
        NameUser = findViewById(R.id.NameUser);
        TxtEditProfile = findViewById(R.id.txtEditProfile);
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.profile);


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
            if (UpdatePicturebtn.getVisibility() == View.GONE){
                UpdatePicturebtn.setVisibility(View.VISIBLE);
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
        TeacherName.setFocusable(false);
        TeacherUserName.setFocusable(false);
        Password.setFocusable(false);
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
        ReadOnly();

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
        Toast.makeText(this, "Image", Toast.LENGTH_SHORT).show();
        DoneUpdatePicture.setVisibility(View.GONE);
        CameraBtn.setVisibility(View.GONE);
    }
}
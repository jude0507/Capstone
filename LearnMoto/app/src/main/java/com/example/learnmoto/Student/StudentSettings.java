package com.example.learnmoto.Student;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.learnmoto.DisplayImage;
import com.example.learnmoto.CheckConnection.NetworkChangeListener;
import com.example.learnmoto.R;
import com.example.learnmoto.Adapter.TranslateAnimatioUI;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import de.hdodenhof.circleimageview.CircleImageView;

public class StudentSettings extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    Button arrow_btn, logoutbtn, confirmLogout;
    LinearLayout expandableView1, expandableLinear1, expandableView2, expandableLinear2;
    TextView studentName;

    CircleImageView StudPicture;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    private ScrollView scrollView;
    String fetchStudID = StudentLogin.studID;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference collectionReference = db.collection("Student");

    NetworkChangeListener networkChangeListener = new NetworkChangeListener();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        bottomNavigationView = findViewById(R.id.bottom_nav);
        //arrow_btn = findViewById(R.id.arrow_btn);
        logoutbtn = findViewById(R.id.arrow_btn_logout);
        confirmLogout = findViewById(R.id.confirmLogout);
        studentName = findViewById(R.id.studentName);
        //expandableView1 = findViewById(R.id.expandableLayout1);
        expandableView2 = findViewById(R.id.expandableLayout2);
        //expandableLinear1 = findViewById(R.id.layout1);
        expandableLinear2 = findViewById(R.id.layout2);
        scrollView = findViewById(R.id.scroller);
        StudPicture = findViewById(R.id.profile);

        sharedPreferences = getSharedPreferences("Preferences", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        String name = sharedPreferences.getString(StudentLogin.StudentName, "");
        //String image = sharedPreferences.getString("imageurl", "");

        studentName.setText(name);
        DisplayImage();
        //Glide.with(getApplicationContext()).load(StudentInfoSettings.imageDiplay).placeholder(R.drawable.ic_user_circle).into(StudPicture);

        bottomNavigationView.setSelectedItemId(R.id.settings);

        //expandableLinear1.getLayoutTransition().enableTransitionType(LayoutTransition.CHANGING);
        //expandableLinear2.getLayoutTransition().enableTransitionType(LayoutTransition.CHANGING);

        //Navigation Bar conditional statement
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {

            switch (item.getItemId()){
                case R.id.home:
                    startActivity(new Intent(getApplicationContext(), StudentHomeView.class));
                    overridePendingTransition(0,0);
                    return true;

                case R.id.todo:
                    startActivity(new Intent(getApplicationContext(), Todo.class));
                    overridePendingTransition(0,0);
                    return true;

                case R.id.settings:
                    return true;
            }

            return false;
        });

        scrollView.setOnTouchListener(new TranslateAnimatioUI(this, bottomNavigationView));
        //Perform Expandable LAYOUT
        logoutbtn.setOnClickListener(v -> {
            if (expandableView2.getVisibility() == View.GONE) {
                TransitionManager.beginDelayedTransition(expandableLinear2, new AutoTransition());
                expandableView2.setVisibility(View.VISIBLE);
                logoutbtn.setBackgroundResource(R.drawable.ic_arrow_up);
            }else{
                TransitionManager.beginDelayedTransition(expandableLinear2, new AutoTransition());
                expandableView2.setVisibility(View.GONE);
                logoutbtn.setBackgroundResource(R.drawable.ic_arrow_down);
            }
        });

        expandableLinear2.setOnClickListener(v -> {
            if (expandableView2.getVisibility() == View.GONE) {
                TransitionManager.beginDelayedTransition(expandableLinear2, new AutoTransition());
                expandableView2.setVisibility(View.VISIBLE);
                logoutbtn.setBackgroundResource(R.drawable.ic_arrow_up);
            }else{
                TransitionManager.beginDelayedTransition(expandableLinear2, new AutoTransition());
                expandableView2.setVisibility(View.GONE);
                logoutbtn.setBackgroundResource(R.drawable.ic_arrow_down);
            }
        });

        confirmLogout.setOnClickListener(v -> {
            editor.clear();
            editor.commit();
            Toast.makeText(StudentSettings.this, "Logout Success", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(StudentSettings.this, StudentLogin.class));
        });

        if(savedInstanceState != null){
            String savedName = savedInstanceState.getString(fetchStudID);
            studentName.setText(savedName);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstance) {

        savedInstance.putString(fetchStudID, studentName.getText().toString());

        super.onSaveInstanceState(savedInstance);

    }

    public void DisplayImage(){
        DisplayImage.RetrieveImageStudents(this, "Student", "sID", StudentLogin.studID, StudPicture);
    }
    public void editPersonalSettings(View view) {
        startActivity(new Intent(StudentSettings.this, StudentInfoSettings.class));
    }

    public void CancelFunction(View view) {
        TransitionManager.beginDelayedTransition(expandableLinear2, new AutoTransition());
        expandableView2.setVisibility(View.GONE);
        logoutbtn.setBackgroundResource(R.drawable.ic_arrow_down);
    }

    @Override
    protected void onStart() {
        IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkChangeListener, intentFilter);
        DisplayImage();
        super.onStart();
    }

    @Override
    protected void onStop() {
        unregisterReceiver(networkChangeListener);
        super.onStop();
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(StudentSettings.this, StudentHomeView.class));
    }

    //    @Override
//    protected void onDestroy() {
//        editor.clear();
//        editor.commit();
//        super.onDestroy();
//    }
}
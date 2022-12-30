package com.example.learnmoto.Parent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.learnmoto.Adapter.ChildListAdapter;
import com.example.learnmoto.DisplayImage;
import com.example.learnmoto.Model.ParentInfo;
import com.example.learnmoto.Model.StudentInfo;
import com.example.learnmoto.CheckConnection.NetworkChangeListener;
import com.example.learnmoto.R;
import com.example.learnmoto.Teacher.TeacherLogin;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ParentView extends AppCompatActivity {

    private long backpressedTimes;
    Button arrow_btn, logoutbtn, confirmLogout, arrow;
    TextView Parent_Name;
    RelativeLayout relativeLayout1;
    LinearLayout expandableAnnounce,childrenLayout, expandableView2, expandableLinear2;
    RecyclerView recyclerView;
    ChildListAdapter childListAdapter;
    FirebaseFirestore firebaseFirestore;
    ArrayList<StudentInfo> studentInfoArrayList;
    CircleImageView ParentImage;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference collectionReference = db.collection("Parent");

    String ParentName = ParentLogin.pName;

    NetworkChangeListener networkChangeListener = new NetworkChangeListener();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_view);

        arrow = findViewById(R.id.arrow_btn);
        arrow_btn = findViewById(R.id.arrow_btn);
        logoutbtn = findViewById(R.id.arrow_btn_logout);
        confirmLogout = findViewById(R.id.confirmLogout);
        Parent_Name = findViewById(R.id.parent_name);
        relativeLayout1 = findViewById(R.id.ParentAnnounceLayout);
        expandableAnnounce = findViewById(R.id.expandableAnnounce);
        childrenLayout = findViewById(R.id.childrenLayout);
        recyclerView = findViewById(R.id.childRecyclerview);
        expandableView2 = findViewById(R.id.expandableLayout2);
        expandableLinear2 = findViewById(R.id.layout2);
        ParentImage = findViewById(R.id.profile);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        firebaseFirestore = FirebaseFirestore.getInstance();
        studentInfoArrayList = new ArrayList<>();
        childListAdapter = new ChildListAdapter(ParentView.this, studentInfoArrayList);

        recyclerView.setAdapter(childListAdapter);

        Parent_Name.setText(ParentName);
        DisplayImage();

        if(savedInstanceState != null){
            String savedName = savedInstanceState.getString(ParentName);
            Parent_Name.setText(savedName);
        }

        EventChangeListener();

        arrow.setOnClickListener(v -> {
            if (expandableAnnounce.getVisibility() == View.GONE) {
                TransitionManager.beginDelayedTransition(relativeLayout1, new AutoTransition());
                expandableAnnounce.setVisibility(View.VISIBLE);
                arrow.setBackgroundResource(R.drawable.ic_arrow_up);
            }else{
                TransitionManager.beginDelayedTransition(relativeLayout1, new AutoTransition());
                expandableAnnounce.setVisibility(View.GONE);
                arrow.setBackgroundResource(R.drawable.ic_arrow_down);
            }
        });

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

        confirmLogout.setOnClickListener(v -> {
            Toast.makeText(ParentView.this, "Logout Success", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(ParentView.this, ParentLogin.class));
        });

    }

    //display the name of the children
    private void EventChangeListener() {
        firebaseFirestore.collection("Student").whereEqualTo("sGuardian", ParentLogin.pName)
                .get().addOnSuccessListener(queryDocumentSnapshots -> {

                    for (QueryDocumentSnapshot documentSnapshot: queryDocumentSnapshots){
                        studentInfoArrayList.add(documentSnapshot.toObject(StudentInfo.class));
                    }
                });
    }

    public void ParentSettings(View view) {
        startActivity(new Intent(this, ParentSettings.class));
    }

    public void Expand(View view) {
        int children = (recyclerView.getVisibility() == View.GONE)? View.VISIBLE: View.GONE;
        TransitionManager.beginDelayedTransition(childrenLayout, new AutoTransition());
        recyclerView.setVisibility(children);
    }

    public void CancelFunction(View view) {
        TransitionManager.beginDelayedTransition(expandableLinear2, new AutoTransition());
        expandableView2.setVisibility(View.GONE);
        logoutbtn.setBackgroundResource(R.drawable.ic_arrow_down);
    }

    public void DisplayImage(){
        DisplayImage.RetrieveImageParents(this, "Parent", "pID" , ParentLogin.pID, ParentImage);
    }


    @Override
    protected void onSaveInstanceState(Bundle savedInstance) {

        savedInstance.putString(ParentName, Parent_Name.getText().toString());

        super.onSaveInstanceState(savedInstance);

    }

    @Override
    public void onBackPressed() {
        if (backpressedTimes + 3000 > System.currentTimeMillis()){
            super.onBackPressed();
            return;
        }else{
            Toast.makeText(this, "Press back again to exit", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, ParentLogin.class));
        }
        backpressedTimes = System.currentTimeMillis();

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
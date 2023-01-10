package com.example.learnmoto.Student;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.learnmoto.Adapter.AnnouncementAdapter;
import com.example.learnmoto.Adapter.TodoAdapter;
import com.example.learnmoto.CheckConnection.NetworkChangeListener;
import com.example.learnmoto.Model.AnnouncementModel;
import com.example.learnmoto.Model.StudentModel;
import com.example.learnmoto.Model.ToDoModel;
import com.example.learnmoto.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class Todo extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    NetworkChangeListener networkChangeListener = new NetworkChangeListener();
    TodoAdapter todoAdapter;
    RecyclerView recyclerView;
    ArrayList<ToDoModel> todoArray;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo);

        bottomNavigationView = findViewById(R.id.bottom_nav);
        recyclerView = findViewById(R.id.rvTodo);
        bottomNavigationView.setSelectedItemId(R.id.todo);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(), StudentHomeView.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.todo:
                        return true;

                    case R.id.settings:
                        startActivity(new Intent(getApplicationContext(), StudentSettings.class));
                        overridePendingTransition(0,0);
                        return true;
                }

                return false;
            }
        });

        //LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        todoArray = new ArrayList<ToDoModel>();
        todoAdapter = new TodoAdapter(this, todoArray);
        recyclerView.setAdapter(todoAdapter);
        EventChangeListener();


    }

    private void EventChangeListener() {
        db.collection("Todo").whereEqualTo("level", StudentHomeView.level).get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    for (QueryDocumentSnapshot documentSnapshot: queryDocumentSnapshots){
                        todoArray.add(documentSnapshot.toObject(ToDoModel.class));
                    }
                    todoAdapter.notifyDataSetChanged();
                });
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

    @Override
    public void onBackPressed() {
        startActivity(new Intent(Todo.this, StudentHomeView.class));

    }

}
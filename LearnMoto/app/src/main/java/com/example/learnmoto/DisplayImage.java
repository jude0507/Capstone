package com.example.learnmoto;

import android.content.Context;

import androidx.lifecycle.ViewModel;

import com.bumptech.glide.Glide;
import com.example.learnmoto.Model.ParentInfo;
import com.example.learnmoto.Model.StudentInfo;
import com.example.learnmoto.Model.TeacherInfo;
import com.example.learnmoto.Teacher.TeacherLogin;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import de.hdodenhof.circleimageview.CircleImageView;

public class DisplayImage {

    public static void RetrieveImageTeacher(Context context, String CollectionPath, String field, String ObjectValue, CircleImageView circleImageView){
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection(CollectionPath).whereEqualTo(field, ObjectValue).get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    String imageDisplay = "";
                    for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots){
                        TeacherInfo teacherInfo = documentSnapshot.toObject(TeacherInfo.class);
                        teacherInfo.setMyid(documentSnapshot.getId());
                        imageDisplay += teacherInfo.getImageurl();

                    }
                    Glide.with(context.getApplicationContext()).load(imageDisplay).placeholder(R.drawable.ic_user_circle).into(circleImageView);

                });

    }

    public static void RetrieveImageStudents(Context context, String CollectionPath, String field, String ObjectValue, CircleImageView circleImageView){
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection(CollectionPath).whereEqualTo(field, ObjectValue).get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    String imageDisplay = "";
                    for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots){
                        StudentInfo studentInfo = documentSnapshot.toObject(StudentInfo.class);
                        studentInfo.setMyid(documentSnapshot.getId());
                        imageDisplay += studentInfo.getImageurl();

                    }
                    Glide.with(context.getApplicationContext()).load(imageDisplay).placeholder(R.drawable.ic_user_circle).into(circleImageView);

                });

    }

    public static void RetrieveImageParents(Context context, String CollectionPath, String field, String ObjectValue, CircleImageView circleImageView){
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection(CollectionPath).whereEqualTo(field, ObjectValue).get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    String imageDisplay = "";
                    for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots){
                        ParentInfo parentInfo = documentSnapshot.toObject(ParentInfo.class);
                        parentInfo.setMyid(documentSnapshot.getId());
                        imageDisplay += parentInfo.getImageurl();

                    }
                    Glide.with(context.getApplicationContext()).load(imageDisplay).placeholder(R.drawable.ic_user_circle).into(circleImageView);

                });
    }
}

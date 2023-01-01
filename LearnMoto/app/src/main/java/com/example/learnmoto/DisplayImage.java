package com.example.learnmoto;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.example.learnmoto.Model.ParentModel;
import com.example.learnmoto.Model.StudentModel;
import com.example.learnmoto.Model.TeacherModel;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import de.hdodenhof.circleimageview.CircleImageView;

public class DisplayImage {

    public static void RetrieveImageTeacher(Context context, String CollectionPath, String field, String ObjectValue, CircleImageView circleImageView){
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection(CollectionPath).whereEqualTo(field, ObjectValue).get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    String imageDisplay = "";
                    for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots){
                        TeacherModel teacherInfo = documentSnapshot.toObject(TeacherModel.class);
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
                        StudentModel studentInfo = documentSnapshot.toObject(StudentModel.class);
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
                        ParentModel parentInfo = documentSnapshot.toObject(ParentModel.class);
                        parentInfo.setMyid(documentSnapshot.getId());
                        imageDisplay += parentInfo.getImageurl();

                    }
                    Glide.with(context.getApplicationContext()).load(imageDisplay).placeholder(R.drawable.ic_user_circle).into(circleImageView);

                });
    }
}

package com.example.learnmoto.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.learnmoto.DisplayChildData;
import com.example.learnmoto.Model.StudentModel;
import com.example.learnmoto.R;
import com.example.learnmoto.Teacher.DisplayStudentData;

import java.util.ArrayList;

public class ChildAdapter extends RecyclerView.Adapter<ChildAdapter.MyViewHolder> {

    Context context;
    ArrayList<StudentModel> nameArraylist;

    public ChildAdapter(Context context, ArrayList<StudentModel> nameArraylist) {
        this.context = context;
        this.nameArraylist = nameArraylist;
    }

    @NonNull
    @Override
    public ChildAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.children_list, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChildAdapter.MyViewHolder holder, int position) {
        final StudentModel studentInfo = nameArraylist.get(position);
        holder.sGuardian.setText(studentInfo.getsName());
        holder.sID.setText(studentInfo.getsID());

        holder.sGuardian.setOnClickListener(v -> {
            Intent intent = new Intent(context, DisplayChildData.class);
            intent.putExtra("studentName", studentInfo.getsName());
            intent.putExtra("studentID", studentInfo.getsID());
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return nameArraylist.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView sGuardian;
        TextView sID;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            sGuardian = itemView.findViewById(R.id.childsName);
            sID = itemView.findViewById(R.id.studentID);
        }
    }
}

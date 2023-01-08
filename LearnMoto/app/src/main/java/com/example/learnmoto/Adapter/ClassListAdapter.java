package com.example.learnmoto.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.learnmoto.Teacher.DisplayStudentData;
import com.example.learnmoto.Model.StudentModel;
import com.example.learnmoto.R;

import java.util.ArrayList;

public class ClassListAdapter extends RecyclerView.Adapter<ClassListAdapter.MyViewHolder>{

    Context context;
    ArrayList<StudentModel> nameArraylist;

    public ClassListAdapter(Context context, ArrayList<StudentModel> nameArraylist) {
        this.context = context;
        this.nameArraylist = nameArraylist;
    }

    @NonNull
    @Override
    public ClassListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.student_list, parent, false);

        return new ClassListAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ClassListAdapter.MyViewHolder holder, int position) {

        final StudentModel studentInfo = nameArraylist.get(position);
        holder.sName.setText(studentInfo.sName);
        holder.sID.setText(studentInfo.sID);

        holder.sName.setOnClickListener(v -> {
            Intent intent = new Intent(context, DisplayStudentData.class);
            intent.putExtra("studentName", studentInfo.getsName());
            intent.putExtra("studentID", studentInfo.getsID());
            intent.putExtra("studentLevel", studentInfo.getsLevel());
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        });
        //holder.sName.setText(nameArraylist.get(position).getsName());
    }

    @Override
    public int getItemCount() {
        return nameArraylist.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView sName;
        TextView sID;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            sName = itemView.findViewById(R.id.Name);
            sID = itemView.findViewById(R.id.studentID);

        }
    }
}

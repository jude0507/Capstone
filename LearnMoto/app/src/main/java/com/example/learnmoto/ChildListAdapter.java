package com.example.learnmoto;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.learnmoto.Model.StudentInfo;

import java.util.ArrayList;

public class ChildListAdapter extends RecyclerView.Adapter<ChildListAdapter.MyViewHolder> {

    Context context;
    ArrayList<StudentInfo> nameArraylist;

    public ChildListAdapter(Context context, ArrayList<StudentInfo> nameArraylist) {
        this.context = context;
        this.nameArraylist = nameArraylist;
    }

    @NonNull
    @Override
    public ChildListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.children_list, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChildListAdapter.MyViewHolder holder, int position) {

        //StudentInfo studentInfo = new StudentInfo();
        //holder.sGuardian.setText(studentInfo.sGuardian);
        holder.sGuardian.setText(nameArraylist.get(position).getsName());

    }

    @Override
    public int getItemCount() {
        return nameArraylist.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView sGuardian;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            sGuardian = itemView.findViewById(R.id.childsName);

        }
    }
}

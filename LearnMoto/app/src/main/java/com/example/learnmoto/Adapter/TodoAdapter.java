package com.example.learnmoto.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.learnmoto.Model.ToDoModel;
import com.example.learnmoto.R;

import java.util.ArrayList;

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.MyViewHolder>{

    Context context;
    ArrayList<ToDoModel> todoArray;

    public TodoAdapter(Context context, ArrayList<ToDoModel> todoArray) {
        this.context = context;
        this.todoArray = todoArray;
    }

    @NonNull
    @Override
    public TodoAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.announcement_item,parent,false);

        return new TodoAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TodoAdapter.MyViewHolder holder, int position) {
        ToDoModel toDoModel = todoArray.get(position);
        holder.author_name.setText(toDoModel.getAuthorName());
        holder.announcement_msg.setText(toDoModel.getMessage());
    }

    @Override
    public int getItemCount() {
        return todoArray.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView author_name, announcement_msg;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            author_name = itemView.findViewById(R.id.announcementAuthorName);
            announcement_msg = itemView.findViewById(R.id.msg_announcement);

        }
    }
}

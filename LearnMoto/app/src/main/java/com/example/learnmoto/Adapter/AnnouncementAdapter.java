package com.example.learnmoto.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.learnmoto.Model.AnnouncementModel;
import com.example.learnmoto.R;

import java.util.ArrayList;

public class AnnouncementAdapter extends RecyclerView.Adapter<AnnouncementAdapter.MyViewHolder> {

    Context context;
    ArrayList<AnnouncementModel> announcementArray;

    public AnnouncementAdapter(Context context, ArrayList<AnnouncementModel> announcementArray) {
        this.context = context;
        this.announcementArray = announcementArray;
    }

    @NonNull
    @Override
    public AnnouncementAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.announcement_item,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AnnouncementAdapter.MyViewHolder holder, int position) {

        AnnouncementModel announcementModel = announcementArray.get(position);
        holder.author_name.setText(announcementModel.getAuthorName());
        holder.announcement_msg.setText(announcementModel.getMessage());

    }

    @Override
    public int getItemCount() {
        return announcementArray.size();
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

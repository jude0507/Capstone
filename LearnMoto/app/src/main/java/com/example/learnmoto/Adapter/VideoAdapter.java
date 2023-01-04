package com.example.learnmoto.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.learnmoto.Model.VideoModel;
import com.example.learnmoto.R;
import com.example.learnmoto.Interface.RecyclerViewInterface;

import java.util.ArrayList;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.MyViewHolder>{

    Context context;
    ArrayList<VideoModel> videoNameArraylist;
    private final RecyclerViewInterface recyclerViewInterface;

    public VideoAdapter(Context context, ArrayList<VideoModel> videoNameArraylist, RecyclerViewInterface recyclerViewInterface) {
        this.context = context;
        this.videoNameArraylist = videoNameArraylist;
        this.recyclerViewInterface = recyclerViewInterface;
    }

    @NonNull
    @Override
    public VideoAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.video_name_list, parent, false);

        return new VideoAdapter.MyViewHolder(view, recyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoAdapter.MyViewHolder holder, int position) {
        VideoModel videoInfo = videoNameArraylist.get(position);
        holder.videoName.setText(videoInfo.getVideoName());
    }

    @Override
    public int getItemCount() {
        return videoNameArraylist.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView videoName;
        public MyViewHolder(@NonNull View itemView, RecyclerViewInterface recyclerViewInterface) {
            super(itemView);
            videoName = itemView.findViewById(R.id.videoName);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (recyclerViewInterface != null){
                        int pos = getAdapterPosition();
                        if (pos != RecyclerView.NO_POSITION) {
                            recyclerViewInterface.onItemClick(pos);
                        }
                    }
                }
            });

        }
    }
}

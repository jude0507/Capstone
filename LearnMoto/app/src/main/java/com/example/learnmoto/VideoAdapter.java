package com.example.learnmoto;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.learnmoto.Adapter.ChildListAdapter;
import com.example.learnmoto.Model.StudentInfo;
import com.example.learnmoto.Model.VideoInfo;

import java.util.ArrayList;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.MyViewHolder>{

    Context context;
    ArrayList<VideoInfo> videoNameArraylist;
    private ItemClickListener listener;

    public VideoAdapter(Context context, ArrayList<VideoInfo> videoNameArraylist) {
        this.context = context;
        this.videoNameArraylist = videoNameArraylist;
    }

    @NonNull
    @Override
    public VideoAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.video_name_list, parent, false);

        return new VideoAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoAdapter.MyViewHolder holder, int position) {
        VideoInfo videoInfo = videoNameArraylist.get(position);
        holder.videoName.setText(videoInfo.getVideoName());
    }

    @Override
    public int getItemCount() {
        return videoNameArraylist.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView videoName;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            videoName = itemView.findViewById(R.id.videoName);

        }

        @Override
        public void onClick(View v) {
            listener.onItemClickLister(v, getAdapterPosition());

            Toast.makeText(context, getAdapterPosition(), Toast.LENGTH_SHORT).show();

        }
    }
}

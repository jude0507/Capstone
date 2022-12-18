package com.example.learnmoto;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.learnmoto.Adapter.StudentSubjectAdapter;

import java.util.List;


public class LevelAdapter extends RecyclerView.Adapter<LevelAdapter.MyViewHolder>{

    private Context context;
    private List<Integer> images;

    public LevelAdapter(Context context, List<Integer> images){
        this.context = context;
        this.images = images;
    }

    @NonNull
    @Override
    public LevelAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.level_list, parent,false);
        return new LevelAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LevelAdapter.MyViewHolder holder, int position) {
        holder.mImage.setImageResource(images.get(position));
    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView mImage;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mImage = itemView.findViewById(R.id.levelImage);
        }
    }
}

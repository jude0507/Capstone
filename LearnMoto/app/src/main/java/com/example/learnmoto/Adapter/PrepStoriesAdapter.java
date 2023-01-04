package com.example.learnmoto.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.learnmoto.Preparatory.Stories.PThirdStory;
import com.example.learnmoto.Preparatory.Stories.PSecondStory;
import com.example.learnmoto.Preparatory.Stories.PFirstStory;
import com.example.learnmoto.R;

import java.util.List;

public class PrepStoriesAdapter extends RecyclerView.Adapter<PrepStoriesAdapter.MyViewHolder>{
    private final Context context;
    private final List<String> storyTitles;
    private final List<Integer> storyImages;

    public PrepStoriesAdapter(Context context, List<String> storyTitles, List<Integer> storyImages) {
        this.context = context;
        this.storyTitles = storyTitles;
        this.storyImages = storyImages;
    }

    @NonNull
    @Override
    public PrepStoriesAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.story_title_layout, parent,false);
        return new PrepStoriesAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PrepStoriesAdapter.MyViewHolder holder, int position) {
        holder.storyTitles.setText(storyTitles.get(position));
        holder.storyImages.setImageResource(storyImages.get(position));
    }

    @Override
    public int getItemCount() {
        return storyImages.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView storyTitles;
        ImageView storyImages;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            storyTitles = itemView.findViewById(R.id.story_title);
            storyImages = itemView.findViewById(R.id.story_Image);
            itemView.setOnClickListener(v -> {
                switch (getAdapterPosition()){
                    case 0:
                        v.getContext().startActivity(new Intent(v.getContext(), PFirstStory.class));
                        break;
                    case 1:
                        v.getContext().startActivity(new Intent(v.getContext(), PSecondStory.class));
                        break;
                    case 2:
                        v.getContext().startActivity(new Intent(v.getContext(), PThirdStory.class));
                        break;
                }
            });
        }
    }
}

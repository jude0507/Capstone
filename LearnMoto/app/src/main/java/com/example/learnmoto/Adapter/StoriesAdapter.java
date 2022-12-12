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

import com.example.learnmoto.Nursery.Story.FirstStory;
import com.example.learnmoto.Nursery.Story.FourthStory;
import com.example.learnmoto.Nursery.Story.SecondStory;
import com.example.learnmoto.Nursery.Story.ThirdStory;
import com.example.learnmoto.R;

import java.util.List;

public class StoriesAdapter extends RecyclerView.Adapter<StoriesAdapter.MyViewHolder> {

    private final Context context;
    private final List<String> storyTitles;
    private final List<Integer> storyImages;

    public StoriesAdapter(Context context, List<String> storyTitles, List<Integer> storyImages) {
        this.context = context;
        this.storyTitles = storyTitles;
        this.storyImages = storyImages;
    }

    @NonNull
    @Override
    public StoriesAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.story_title_layout, parent,false);
        return new StoriesAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StoriesAdapter.MyViewHolder holder, int position) {
        holder.storyTitles.setText(storyTitles.get(position));
        holder.storyImages.setImageResource(storyImages.get(position));

    }

    @Override
    public int getItemCount() {
        return storyImages.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView storyTitles;
        ImageView storyImages;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            storyTitles = itemView.findViewById(R.id.story_title);
            storyImages = itemView.findViewById(R.id.story_Image);

            itemView.setOnClickListener(view -> {
                switch (getAdapterPosition()){
                    case 0:
                        view.getContext().startActivity(new Intent(view.getContext(), FirstStory.class));
                        break;
                    case 1:
                        view.getContext().startActivity(new Intent(view.getContext(), SecondStory.class));
                        break;
                    case 2:
                        view.getContext().startActivity(new Intent(view.getContext(), ThirdStory.class));
                        break;
                    case 3:
                        view.getContext().startActivity(new Intent(view.getContext(), FourthStory.class));
                        break;
                }
            });

        }
    }
}

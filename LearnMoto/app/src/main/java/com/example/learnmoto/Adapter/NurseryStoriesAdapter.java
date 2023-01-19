package com.example.learnmoto.Adapter;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.learnmoto.Nursery.Story.NFirstStory;
import com.example.learnmoto.Nursery.Story.NFourthStory;
import com.example.learnmoto.Nursery.Story.NSecondStory;
import com.example.learnmoto.Nursery.Story.NThirdStory;
import com.example.learnmoto.R;

import java.util.List;

public class NurseryStoriesAdapter extends RecyclerView.Adapter<NurseryStoriesAdapter.MyViewHolder> {

    private final Context context;
    private final List<String> storyTitles;
    private final List<Integer> storyImages;

    public NurseryStoriesAdapter(Context context, List<String> storyTitles, List<Integer> storyImages) {
        this.context = context;
        this.storyTitles = storyTitles;
        this.storyImages = storyImages;
    }

    @NonNull
    @Override
    public NurseryStoriesAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.story_title_layout, parent,false);
        return new NurseryStoriesAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NurseryStoriesAdapter.MyViewHolder holder, int position) {
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
                        MediaPlayer mediaPlayer1 = MediaPlayer.create(view.getContext(),R.raw.title_lion);
                        mediaPlayer1.start();
                        view.getContext().startActivity(new Intent(view.getContext(), NFirstStory.class));
                        break;
                    case 1:
                        MediaPlayer mediaPlayer2 = MediaPlayer.create(view.getContext(),R.raw.title_ant_dove);
                        mediaPlayer2.start();
                        view.getContext().startActivity(new Intent(view.getContext(), NSecondStory.class));
                        break;
                    case 2:
                        MediaPlayer mediaPlayer3 = MediaPlayer.create(view.getContext(),R.raw.title_ant);
                        mediaPlayer3.start();
                        view.getContext().startActivity(new Intent(view.getContext(), NThirdStory.class));
                        break;
                    case 3:
                        view.getContext().startActivity(new Intent(view.getContext(), NFourthStory.class));
                        break;
                }
            });

        }
    }
}

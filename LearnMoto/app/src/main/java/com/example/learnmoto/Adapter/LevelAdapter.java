package com.example.learnmoto.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.learnmoto.Kinder.KinderClassList;
import com.example.learnmoto.Nursery.NurseryClassList;
import com.example.learnmoto.Preparatory.PreparatoryClassList;
import com.example.learnmoto.R;
import com.example.learnmoto.Teacher.TeacherView;

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

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (getAdapterPosition()){
                        case 0:
                            if (TeacherView.assignLevel.contains("Kinder")){
                                v.getContext().startActivity(new Intent(v.getContext(), KinderClassList.class));
                                Toast.makeText(v.getContext(), "kinder 0", Toast.LENGTH_SHORT).show();
                            }else if (TeacherView.assignLevel.contains("Nursery")){
                                v.getContext().startActivity(new Intent(v.getContext(), NurseryClassList.class));
                                Toast.makeText(v.getContext(), "nursery 0", Toast.LENGTH_SHORT).show();
                            }else if (TeacherView.assignLevel.contains("Preparatory")){
                                v.getContext().startActivity(new Intent(v.getContext(), PreparatoryClassList.class));
                                Toast.makeText(v.getContext(), "preparatory 0", Toast.LENGTH_SHORT).show();
                            }
                            break;
                        case 1:
                            if (TeacherView.assignLevel.contains("Nursery")){
                                v.getContext().startActivity(new Intent(v.getContext(), NurseryClassList.class));
                                Toast.makeText(v.getContext(), "nursery 1", Toast.LENGTH_SHORT).show();
                            }else if (TeacherView.assignLevel.contains("Preparatory")){
                                v.getContext().startActivity(new Intent(v.getContext(), PreparatoryClassList.class));
                                Toast.makeText(v.getContext(), "preparatory 1", Toast.LENGTH_SHORT).show();
                            }
                            break;
                        case 2:
                            if (TeacherView.assignLevel.contains("Preparatory")){
                                v.getContext().startActivity(new Intent(v.getContext(), PreparatoryClassList.class));
                                Toast.makeText(v.getContext(), "preparatory", Toast.LENGTH_SHORT).show();
                            }
                            break;
                    }
                }
            });

        }
    }
}

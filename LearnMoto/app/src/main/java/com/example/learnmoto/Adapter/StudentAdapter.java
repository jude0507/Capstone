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
import com.example.learnmoto.Kinder.English.KinderEnglish;
import com.example.learnmoto.Kinder.Math.KinderMathRead;
import com.example.learnmoto.Nursery.English.NurseryEnglish;
import com.example.learnmoto.Nursery.Math.NurseryMathRead;
import com.example.learnmoto.Preparatory.English.PreparatoryEnglish;
import com.example.learnmoto.Preparatory.Math.PreparatoryMathRead;
import com.example.learnmoto.R;
import com.example.learnmoto.Student.StudentLogin;

import java.util.List;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.MyViewHolder> {

    private Context context;
    //private List<String> title;
    private List<Integer> images;
    //private itemClickListener listener;

    public StudentAdapter(Context context, List<Integer> images){
        this.context = context;
        //this.title = title; List<String> title
        this.images = images;
    }

    public StudentAdapter() {

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.mylayout, parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        //holder.mtxt.setText(title.get(position));
        holder.mImage.setImageResource(images.get(position));
    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView mImage;
        //TextView mtxt;

        public MyViewHolder(final View itemView) {
            super(itemView);

            int EnglishIndex = 0;
            int MathIndex = 1;

            String studentLevel = StudentLogin.sLevel;
            mImage = itemView.findViewById(R.id.imgview);
            //mtxt = itemView.findViewById(R.id.txt_label);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //papunta sa bawat index ng recyclerview
                   switch (getAdapterPosition()){
                       case 0:
                           Toast.makeText(v.getContext(), "English", Toast.LENGTH_SHORT).show();
                            if (studentLevel.equals("Nursery")){
                                Toast.makeText(v.getContext(), "Nursery", Toast.LENGTH_SHORT).show();
                                v.getContext().startActivity(new Intent(v.getContext(), NurseryEnglish.class));
                            }else if (studentLevel.equals("Kinder")){
                                Toast.makeText(v.getContext(), "Kinder", Toast.LENGTH_SHORT).show();
                                v.getContext().startActivity(new Intent(v.getContext(), KinderEnglish.class));
                            }else{
                                Toast.makeText(v.getContext(), "Preparatory", Toast.LENGTH_SHORT).show();
                                v.getContext().startActivity(new Intent(v.getContext(), PreparatoryEnglish.class));
                            }
                            //v.getContext().startActivity(new Intent(v.getContext(), AlphabetRead.class));
                            break;
                        case 1:
                            Toast.makeText(v.getContext(), "Math", Toast.LENGTH_SHORT).show();
                            if (studentLevel.equals("Nursery")){
                                Toast.makeText(v.getContext(), "Nursery", Toast.LENGTH_SHORT).show();
                                v.getContext().startActivity(new Intent(v.getContext(), NurseryMathRead.class));
                            }else if (studentLevel.equals("Kinder")){
                                Toast.makeText(v.getContext(), "Kinder", Toast.LENGTH_SHORT).show();
                                v.getContext().startActivity(new Intent(v.getContext(), KinderMathRead.class));
                            }else{
                                Toast.makeText(v.getContext(), "Preparatory", Toast.LENGTH_SHORT).show();
                                v.getContext().startActivity(new Intent(v.getContext(), PreparatoryMathRead.class));
                            }
                            //v.getContext().startActivity(new Intent(v.getContext(), CrayonsRead.class));
                            break;
                        case 2:
                            Toast.makeText(v.getContext(), "Science", Toast.LENGTH_SHORT).show();
                            //v.getContext().startActivity(new Intent(v.getContext(), NumberRead.class));
                            break;
                        case 3:
                            Toast.makeText(v.getContext(), "Christian Living", Toast.LENGTH_SHORT).show();
                            //v.getContext().startActivity(new Intent(v.getContext(), StoryRead.class));
                            break;
                        case 4:
                            Toast.makeText(v.getContext(), "Filipino", Toast.LENGTH_SHORT).show();
                            //v.getContext().startActivity(new Intent(v.getContext(), ShapesRead.class));
                            break;
                        case 5:
                            Toast.makeText(v.getContext(), "Sibika at Kultura", Toast.LENGTH_SHORT).show();
                            //v.getContext().startActivity(new Intent(v.getContext(), AnimalsRead.class));
                            break;
                    }
                }
            });
        }

//        @Override
//        public void onClick(View v) {
//          //  listener.onClick();
//        }
    }

//    public interface itemClickListener{
//        void clickListener(View view, int position);
//    }
}

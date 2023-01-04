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

import com.example.learnmoto.Kinder.ChristianLiving.KinderChristianLivingRead;
import com.example.learnmoto.Kinder.English.KinderEnglish;
import com.example.learnmoto.Kinder.Filipino.KinderFilipinoRead;
import com.example.learnmoto.Kinder.Math.KinderMathRead;
import com.example.learnmoto.Kinder.Science.KinderScienceRead;
import com.example.learnmoto.Nursery.ChristianLiving.NurseryChristianLivingRead;
import com.example.learnmoto.Nursery.English.NurseryEnglish;
import com.example.learnmoto.Nursery.Math.NurseryMathRead;
import com.example.learnmoto.Nursery.Science.NurseryScienceRead;
import com.example.learnmoto.Preparatory.ChristianLiving.PreparatoryChristianLivingRead;
import com.example.learnmoto.Preparatory.English.PreparatoryEnglish;
import com.example.learnmoto.Preparatory.Filipino.PreparatoryFilipinoRead;
import com.example.learnmoto.Preparatory.Math.PreparatoryMathRead;
import com.example.learnmoto.Preparatory.Science.PreparatoryScienceRead;
import com.example.learnmoto.Preparatory.SibikaKultura.PreparatorySibKulRead;
import com.example.learnmoto.R;
import com.example.learnmoto.Student.StudentHomeView;
import com.example.learnmoto.Student.StudentLogin;

import java.util.List;

public class StudentSubjectAdapter extends RecyclerView.Adapter<StudentSubjectAdapter.MyViewHolder> {

    private Context context;
    private List<Integer> images;

    public StudentSubjectAdapter(Context context, List<Integer> images){
        this.context = context;
        this.images = images;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.mylayout, parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.mImage.setImageResource(images.get(position));
    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView mImage;

        public MyViewHolder(final View itemView) {
            super(itemView);

            String level = StudentHomeView.level;
            mImage = itemView.findViewById(R.id.imgview);
            itemView.setOnClickListener(v -> {
                //papunta sa bawat index ng recyclerview
               switch (getAdapterPosition()){
                   case 0:
                       //Toast.makeText(v.getContext(), "English", Toast.LENGTH_SHORT).show();
                        if (level.equals("Nursery")){
                            //Toast.makeText(v.getContext(), "Nursery", Toast.LENGTH_SHORT).show();
                            v.getContext().startActivity(new Intent(v.getContext(), NurseryEnglish.class));
                        }else if (level.equals("Kinder")){
                            //Toast.makeText(v.getContext(), "Kinder", Toast.LENGTH_SHORT).show();
                            v.getContext().startActivity(new Intent(v.getContext(), KinderEnglish.class));
                        }else{
                            //Toast.makeText(v.getContext(), "Preparatory", Toast.LENGTH_SHORT).show();
                            v.getContext().startActivity(new Intent(v.getContext(), PreparatoryEnglish.class));
                        }
                        //v.getContext().startActivity(new Intent(v.getContext(), AlphabetRead.class));
                        break;
                    case 1:
                        //Toast.makeText(v.getContext(), "Math", Toast.LENGTH_SHORT).show();
                        if (level.equals("Nursery")){
                            //Toast.makeText(v.getContext(), "Nursery", Toast.LENGTH_SHORT).show();
                            v.getContext().startActivity(new Intent(v.getContext(), NurseryMathRead.class));
                        }else if (level.equals("Kinder")){
                            //Toast.makeText(v.getContext(), "Kinder", Toast.LENGTH_SHORT).show();
                            v.getContext().startActivity(new Intent(v.getContext(), KinderMathRead.class));
                        }else{
                            //Toast.makeText(v.getContext(), "Preparatory", Toast.LENGTH_SHORT).show();
                            v.getContext().startActivity(new Intent(v.getContext(), PreparatoryMathRead.class));
                        }
                        //v.getContext().startActivity(new Intent(v.getContext(), CrayonsRead.class));
                        break;
                    case 2:
                        //Toast.makeText(v.getContext(), "Science", Toast.LENGTH_SHORT).show();
                        if (level.equals("Nursery")){
                           // Toast.makeText(v.getContext(), "Nursery", Toast.LENGTH_SHORT).show();
                            v.getContext().startActivity(new Intent(v.getContext(), NurseryScienceRead.class));
                        }else if (level.equals("Kinder")){
                           // Toast.makeText(v.getContext(), "Kinder", Toast.LENGTH_SHORT).show();
                            v.getContext().startActivity(new Intent(v.getContext(), KinderScienceRead.class));
                        }else{
                            //Toast.makeText(v.getContext(), "Preparatory", Toast.LENGTH_SHORT).show();
                            v.getContext().startActivity(new Intent(v.getContext(), PreparatoryScienceRead.class));
                        }
                        //v.getContext().startActivity(new Intent(v.getContext(), NumberRead.class));
                        break;
                    case 3:
                        //Toast.makeText(v.getContext(), "Christian Living", Toast.LENGTH_SHORT).show();
                        if (level.equals("Nursery")){
                           // Toast.makeText(v.getContext(), "Nursery", Toast.LENGTH_SHORT).show();
                            v.getContext().startActivity(new Intent(v.getContext(), NurseryChristianLivingRead.class));
                        }else if (level.equals("Kinder")){
                            //Toast.makeText(v.getContext(), "Kinder", Toast.LENGTH_SHORT).show();
                            v.getContext().startActivity(new Intent(v.getContext(), KinderChristianLivingRead.class));
                        }else{
                            //Toast.makeText(v.getContext(), "Preparatory", Toast.LENGTH_SHORT).show();
                            v.getContext().startActivity(new Intent(v.getContext(), PreparatoryChristianLivingRead.class));
                        }
                        //v.getContext().startActivity(new Intent(v.getContext(), StoryRead.class));
                        break;
                    case 4:
                        //Toast.makeText(v.getContext(), "Filipino", Toast.LENGTH_SHORT).show();
                        if (level.equals("Kinder")){
                          //  Toast.makeText(v.getContext(), "Kinder", Toast.LENGTH_SHORT).show();
                            v.getContext().startActivity(new Intent(v.getContext(), KinderFilipinoRead.class));
                        }else{
                            //Toast.makeText(v.getContext(), "Preparatory", Toast.LENGTH_SHORT).show();
                            v.getContext().startActivity(new Intent(v.getContext(), PreparatoryFilipinoRead.class));
                        }
                        //v.getContext().startActivity(new Intent(v.getContext(), ShapesRead.class));
                        break;
                    case 5:
                        //Toast.makeText(v.getContext(), "Sibika at Kultura", Toast.LENGTH_SHORT).show();
                        v.getContext().startActivity(new Intent(v.getContext(), PreparatorySibKulRead.class));
                        break;
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

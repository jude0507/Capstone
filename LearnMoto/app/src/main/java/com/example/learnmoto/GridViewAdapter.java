package com.example.learnmoto;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.learnmoto.Model.WordAlphabetModel;

import java.util.ArrayList;

public class GridViewAdapter extends ArrayAdapter<WordAlphabetModel> {
    public GridViewAdapter(@NonNull Context context, ArrayList<WordAlphabetModel> alphabetModelArrayList) {
        super(context, 0, alphabetModelArrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        HolderView holderView;
        if (convertView == null) {

            convertView = LayoutInflater.from(getContext()).inflate(R.layout.grid_view_item,
                    parent, false);
            holderView = new HolderView(convertView);
            convertView.setTag(holderView);
        }else{
            holderView = (HolderView) convertView.getTag();
        }

        WordAlphabetModel wordAlphabetModel = getItem(position);
        holderView.icons.setImageResource(wordAlphabetModel.getIconId());
        holderView.letter.setText(wordAlphabetModel.getLetterName());


        return convertView;
    }

    private static class HolderView{
        private final ImageView icons;
        private final TextView letter;

        public HolderView(View view){
            icons = view.findViewById(R.id.icon_id);
            letter = view.findViewById(R.id.letterName);
        }

    }

}

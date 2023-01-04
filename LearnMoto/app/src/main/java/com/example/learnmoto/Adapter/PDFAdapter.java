package com.example.learnmoto.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.learnmoto.Interface.ItemClickListener;
import com.example.learnmoto.Model.PDFModel;
import com.example.learnmoto.R;

import java.util.ArrayList;

public class PDFAdapter extends RecyclerView.Adapter<PDFAdapter.MyViewHolder> {

    private ArrayList<PDFModel> pdfModel;
    private ItemClickListener listener;

    public PDFAdapter(ArrayList<PDFModel> pdfModel, ItemClickListener listener) {
        this.pdfModel = pdfModel;
        this.listener = listener;
    }

    @NonNull
    @Override
    public PDFAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemview = LayoutInflater.from(parent.getContext()).inflate(R.layout.pdf_list, parent, false);
        return new MyViewHolder(itemview);
    }

    @Override
    public void onBindViewHolder(@NonNull PDFAdapter.MyViewHolder holder, int position) {
        String nametitle = pdfModel.get(position).getPdftitle();
        holder.pdfTitle.setText(nametitle);

    }

    @Override
    public int getItemCount() {
        return pdfModel.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView pdfTitle;

        public MyViewHolder(final View view){
            super(view);

            pdfTitle = view.findViewById(R.id.pdfTitle);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listener.onItemClickLister(v, getAdapterPosition());
        }
    }

}

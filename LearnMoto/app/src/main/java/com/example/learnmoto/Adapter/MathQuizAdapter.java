package com.example.learnmoto.Adapter;

import static com.example.learnmoto.MathActivity.builder;
import static com.example.learnmoto.MathActivity.textToSpeech;

import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.learnmoto.MathActivity;
import com.example.learnmoto.Model.MathModel;
import com.example.learnmoto.R;

import java.util.ArrayList;

public class MathQuizAdapter extends RecyclerView.Adapter<MathQuizAdapter.MyViewHolder> {

    Context context;
    ArrayList<MathModel> mathModelArrayList;

    int dice1Val = 0, dice2Val = 0;



    public MathQuizAdapter(Context context, ArrayList<MathModel> mathModelArrayList) {
        this.context = context;
        this.mathModelArrayList = mathModelArrayList;
    }

    @NonNull
    @Override
    public MathQuizAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_math, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MathQuizAdapter.MyViewHolder holder, int position) {
        MathModel mathModel = mathModelArrayList.get(position);

        holder.img1.setImageResource(mathModel.getDice1());
        holder.operator.setText(mathModel.getOperator());
        holder.img2.setImageResource(mathModel.getDice2());
        holder.equal.setText("=");

        holder.cardboard.setOnClickListener(view -> {

            if (mathModel.getDice1() == R.drawable.dice1) {
                dice1Val = 1;
            } else if (mathModel.getDice1() == R.drawable.dice2) {
                dice1Val = 2;
            } else if (mathModel.getDice1() == R.drawable.dice3) {
                dice1Val = 3;
            } else if (mathModel.getDice1() == R.drawable.dice4) {
                dice1Val = 4;
            } else if (mathModel.getDice1() == R.drawable.dice5) {
                dice1Val = 5;
            } else if (mathModel.getDice1() == R.drawable.dice6) {
                dice1Val = 6;
            }

            if (mathModel.getDice2() == R.drawable.dice1) {
                dice2Val = 1;
            } else if (mathModel.getDice2() == R.drawable.dice2) {
                dice2Val = 2;
            } else if (mathModel.getDice2() == R.drawable.dice3) {
                dice2Val = 3;
            } else if (mathModel.getDice2() == R.drawable.dice4) {
                dice2Val = 4;
            } else if (mathModel.getDice2() == R.drawable.dice5) {
                dice2Val = 5;
            } else if (mathModel.getDice2() == R.drawable.dice6) {
                dice2Val = 6;
            }

            MathActivity.textToSpeech.speak(dice1Val + " + " + dice2Val + "?", TextToSpeech.QUEUE_ADD, null);

            final EditText inputDialog = new EditText(context);
            inputDialog.setInputType(InputType.TYPE_CLASS_NUMBER);
            inputDialog.setHint(dice1Val + " + " + dice2Val);

            AlertDialog dialog = builder.setTitle("ANSWER").setView(inputDialog).setCancelable(false).setPositiveButton("OK", (dialogInterface, i) -> {
            }).show();

            Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
            positiveButton.setOnClickListener(view1 -> {
                String answer = inputDialog.getText().toString();
                int key = dice1Val + dice2Val;

                if (answer.isEmpty()){
                    textToSpeech.speak("No answer found", TextToSpeech.QUEUE_ADD, null);
                    inputDialog.setError("Required Field");
                }else{
                    holder.answerInput.setText(answer);
                    dialog.dismiss();
                    if (key == Integer.parseInt(answer)) {
                        MathActivity.score = MathActivity.score + 2;
                        textToSpeech.speak("CORRECT!", TextToSpeech.QUEUE_ADD, null);
                        holder.cardboard.setEnabled(false);
                    } else {
                        textToSpeech.speak("Incorrect answer!", TextToSpeech.QUEUE_ADD, null);
                        holder.cardboard.setEnabled(false);
                    }
                }
            });

        });

    }
    @Override
    public int getItemCount() {
        return mathModelArrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView img1, img2;
        TextView operator, equal, answerInput;
        CardView cardboard;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            cardboard = (CardView) itemView.findViewById(R.id.cardboard);
            img1 = (ImageView) itemView.findViewById(R.id.img1);
            operator = (TextView) itemView.findViewById(R.id.operator);
            img2 = (ImageView) itemView.findViewById(R.id.img2);
            equal = (TextView) itemView.findViewById(R.id.equal);
            answerInput = (TextView) itemView.findViewById(R.id.answerInput);
        }
    }
}

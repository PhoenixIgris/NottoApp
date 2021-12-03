package com.phoenixigris.noteapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;

public class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.ViewHolder> {
    private ArrayList<NoteContent> noteContentArrayList = new ArrayList<>();
    private Context context;
private String color;
    public RecycleViewAdapter(Context context) {
        this.context = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private CardView cardView;
        private TextView title;
        private TextView desc;
        private String color;

        public ViewHolder(@NonNull View itemView) {

            super(itemView);
            cardView = itemView.findViewById(R.id.card);
            title = itemView.findViewById(R.id.note_title);
            desc = itemView.findViewById(R.id.note_desc);



        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.title.setText(noteContentArrayList.get(position).getNote_title());
        holder.desc.setText(noteContentArrayList.get(position).getNote_desc());
   color = noteContentArrayList.get(position).getNote_background();
        if (color == null) {
            System.out.println("Value of color" + color);
        } else {
            switch (color) {
                case "color_one":
                    holder.cardView.setCardBackgroundColor(context.getResources().getColor(R.color.color_one));
                    break;
                case "color_two":
                    holder.cardView.setCardBackgroundColor(context.getResources().getColor(R.color.color_two));
                    break;
                case "color_three":
                    holder.cardView.setCardBackgroundColor(context.getResources().getColor(R.color.color_three));
                    break;
                case "color_four":
                    holder.cardView.setCardBackgroundColor(context.getResources().getColor(R.color.color_four));
                    break;
                case "color_five":
                    holder.cardView.setCardBackgroundColor(context.getResources().getColor(R.color.color_five));
                    break;
                case "color_six":
                    holder.cardView.setCardBackgroundColor(context.getResources().getColor(R.color.color_six));
                    break;
                case "color_seven":
                    holder.cardView.setCardBackgroundColor(context.getResources().getColor(R.color.color_seven));
                    break;
                case "color_eight":
                    holder.cardView.setCardBackgroundColor(context.getResources().getColor(R.color.color_eight));
                    break;

            }


        }

            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, NoteActivity.class);

                    intent.putExtra("NOTE_ID", noteContentArrayList.get(position).getNote_id());
                    intent.setClass(context, NoteActivity.class);
                    context.startActivity(intent);

                }
            });
        }

        @Override
        public int getItemCount () {
            return noteContentArrayList.size();
        }


        public void setNoteContentArrayList (ArrayList < NoteContent > noteContentArrayList) {
            this.noteContentArrayList = noteContentArrayList;
            notifyDataSetChanged();

        }


    }
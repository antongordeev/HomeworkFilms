package com.example.homework_v2;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class FilmsItemViewHolder extends RecyclerView.ViewHolder {

    public TextView cardName;
    public ImageView cardImage;
    public ImageView likeButton;


    //во viewholder приходит view
    public FilmsItemViewHolder(@NonNull View itemView) {
        super(itemView);
        cardName = itemView.findViewById(R.id.cardName);
        cardImage = itemView.findViewById(R.id.cardImage);
        likeButton = itemView.findViewById(R.id.like);


        //itemView - это наша CardView, при клике вызывается метод onDetailItemClick
        //click the itemview - get the position - pass the position to interface method - to the Fragment
//        itemView.setOnClickListener(v -> {
//            if (listener != null) {
//                    //get the position of Adapter
//                    int position = getAdapterPosition();
//                    //check we did not click on deleted card
//                    if (position != RecyclerView.NO_POSITION) {
//                        listener.onDetailItemClick(position);
//                    }
//                }
//        });
    }
}

package com.example.homework_v2;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class FilmsItemsAdaptor extends RecyclerView.Adapter<FilmsItemViewHolder> {

    private List<FilmItem> items;
    private OnDetailClickListener detailListener;

    public interface OnDetailClickListener {
        void onDetailItemClick(int filmId);
        void onLikeClick(int filmId);
    }

    public FilmsItemsAdaptor(List<FilmItem> items, OnDetailClickListener listener) {
        this.items = items;
        this.detailListener = listener;
    }

    @NonNull
    @Override
    public FilmsItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //передаем ViewHolder
        return new FilmsItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_film, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull FilmsItemViewHolder holder, int position) {
        //get the position we look at
        FilmItem currentItem = items.get(position);
        //pass the data to cardImage which is in ViewHolder by position
        holder.cardImage.setImageResource(currentItem.getImageResourceId());
        holder.cardName.setText(currentItem.getTitle());
        //listen the clicks on item and like button
        holder.itemView.setOnClickListener(v -> {
            if (detailListener != null)
            detailListener.onDetailItemClick(items.get(position).getFilmId());
        });

        holder.likeButton.setOnClickListener(v -> {
            if (detailListener != null)
                detailListener.onLikeClick(items.get(position).getFilmId());
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}

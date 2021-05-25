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

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.squareup.picasso.Picasso;

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
//        holder.cardImage.setImageResource(currentItem.getImageResourceId());

//        Picasso.get()
//                //откуда
//                .load(currentItem.imageResourceId)
//                .fit() //что бы влез в контейнер, который задаем в XML
//                .centerCrop() // растянуть
//                .placeholder(R.drawable.ic_launcher_foreground) //типо заглушки
//                .error(R.drawable.ic_baseline_error_outline_24) //если ошибка
//                //куда
//                .into(holder.cardImage);
        //use Glide
        Glide.with(holder.cardImage.getContext())
                .load(currentItem.imageResourceId)
//                .centerCrop()
                .placeholder(R.drawable.ic_baseline_image_24)
                .into(holder.cardImage);

        holder.cardName.setText(currentItem.getTitle());
        if (currentItem.getIsLiked()) {
            holder.likeButton.setImageResource(R.drawable.ic_baseline_favorite_like);
        } else {
            holder.likeButton.setImageResource(R.drawable.ic_baseline_favorite_border_24);
        }
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

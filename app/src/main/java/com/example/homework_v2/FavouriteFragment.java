package com.example.homework_v2;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class FavouriteFragment extends Fragment implements FilmsItemsAdaptor.OnDetailClickListener {

    public static final String TAG = "FavouriteFragment";

    RecyclerView recyclerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public void onDetailItemClick(int filmId) {
        //open the fragment with method newInstance to pass the position
        Fragment fragment = DetailFragment.newInstance(filmId);
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }

    @Override
    public void onLikeClick(int choiceFilmId) {

        FilmItem choiceFilm = null;
        for (FilmItem filmItem : FilmsItemsRepository.getInstance().getFavoriteItems()) {
            if (filmItem.filmId == choiceFilmId) {
                choiceFilm = filmItem;
            }
        }
        //remove from the list of favorites
        FilmsItemsRepository.getInstance().getFavoriteItems().remove(choiceFilm);
        recyclerView.getAdapter().notifyDataSetChanged();
        //set disLike
        choiceFilm.isLiked = false;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_favourite, container, false);

        Toolbar mToolbar = (Toolbar) view.findViewById(R.id.simple_toolbar);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(mToolbar);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewFav);
        FilmsItemsAdaptor adaptor = new FilmsItemsAdaptor(FilmsItemsRepository.getInstance().getFavoriteItems(), this);
        recyclerView.setAdapter(adaptor);
        //создание табличного LayoutManager с 2 столбцами. в параметрах контекст (активность) и колво столбцов
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.getAdapter().notifyDataSetChanged();
    }
}
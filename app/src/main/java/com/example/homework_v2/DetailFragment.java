package com.example.homework_v2;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


public class DetailFragment extends Fragment {

    private TextView description;

    private static final String EXTRA_FILMID = "EXTRA_POSITION";
    private int choiceFilmId = 0;

    public static DetailFragment newInstance(int filmId) {
        //put in the bundle the position
        DetailFragment fragment = new DetailFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(EXTRA_FILMID, filmId);
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_detail, container, false);

        Toolbar mToolbar = (Toolbar) view.findViewById(R.id.simple_toolbar);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(mToolbar);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getArguments() != null) {
            choiceFilmId = getArguments().getInt(EXTRA_FILMID, 0);

        }
        FilmItem choiceFilm = null;
        for (FilmItem filmItem : FilmsItemsRepository.getInstance().getItems()) {
            if (filmItem.filmId == choiceFilmId) {
                choiceFilm = filmItem;
            }
        }


        description = (TextView) view.findViewById(R.id.textView_detail);
        description.setText(choiceFilm.getDescription());
        description.setVisibility(View.INVISIBLE);

        view.findViewById(R.id.imageView_detail).setOnClickListener(v -> {
            description.setVisibility(View.VISIBLE);

        });

        ImageView photo = (ImageView) view.findViewById(R.id.imageView_detail);
        photo.setImageResource(choiceFilm.getImageResourceId());
        photo.setContentDescription(choiceFilm.getTitle());
    }

}
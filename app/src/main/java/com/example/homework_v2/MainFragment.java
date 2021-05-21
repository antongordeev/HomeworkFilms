package com.example.homework_v2;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.ShareActionProvider;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.core.view.MenuItemCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentResultListener;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;


public class MainFragment extends Fragment implements NavigationView.OnNavigationItemSelectedListener, FilmsItemsAdaptor.OnDetailClickListener {

    public static final String TAG = "MainFragment";

    private ShareActionProvider shareActionProvider;//переменная для share

    AddFilmFragment addFilmFragment = new AddFilmFragment();

    //объявляем слушателя
//    private OnDetailClickListener listener = null;

    //объявление интерфейса
//    public interface OnDetailClickListener {
//        void onDetailItemClick(int position);
//    }

    //конструктор Listener
//    public void setListener(OnDetailClickListener listener) {
//        this.listener = listener;
//
//    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setHasOptionsMenu(true);//говорим активити, что у фрагмента есть свое меню
        super.onCreate(savedInstanceState);
        //при пересоздании фрагмент не будет убиваться
//        setRetainInstance(true);


    }

//    @Override
//    public void onSaveInstanceState(@NonNull Bundle outState) {
//        super.onSaveInstanceState(outState);
//        //сохранение переменных типа булин
//        outState.putBoolean("django", af_cl_django);
//        outState.putBoolean("fiction", af_cl_fiction);
//        outState.putBoolean("hollywood", af_cl_hollywood);
//        outState.putBoolean("eight", af_cl_eight);
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        // подцепили toolbar
        Toolbar mToolbar = (Toolbar) view.findViewById(R.id.toolbar_main);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(mToolbar);

        DrawerLayout mDrawer = (DrawerLayout) view.findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(getActivity(),
                mDrawer, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        mDrawer.addDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();

        NavigationView navigationView = view.findViewById(R.id.nav_drawer);
        navigationView.setNavigationItemSelectedListener(this);
        return view;
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
        //find the right film by filmId
        FilmItem choiceFilm = null;
        for (FilmItem filmItem : FilmsItemsRepository.getInstance().getItems()) {
            if (filmItem.filmId == choiceFilmId) {
                choiceFilm = filmItem;
            }
        }
//        FilmsItemsRepository.getInstance().getFavoriteItems().add(FilmsItemsRepository.getInstance().getItems().get(filmId));

        //add liked item to the favorite list
        FilmsItemsRepository.getInstance().getFavoriteItems().add(choiceFilm);
        //set Like
        choiceFilm.isLiked = true;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //create recyclerview
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        //get the list from the repository
        FilmsItemsAdaptor adaptor = new FilmsItemsAdaptor(FilmsItemsRepository.getInstance().getItems(), this);
        recyclerView.setAdapter(adaptor);
        //создание табличного LayoutManager с 2 столбцами. в параметрах контекст (активность) и колво столбцов
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(gridLayoutManager);

//        adaptor.setOnDetailClickListener(new FilmsItemsAdaptor.OnDetailClickListener() {
//            @Override
//            public void onDetailItemClick(int position) {
////                if (listener != null && position < Film.films.length) {
////                    listener.onDetailItemClick(position);
////                }
//            }
//        });


        //обработка кликов и изменение цвета названия фильма
//        view.findViewById(R.id.cardview_fiction).setOnClickListener(v -> {
//            af_cl_fiction = true;
//            textView_fiction = getActivity().findViewById(R.id.fiction_name);
//            textView_fiction.setTextColor(getResources().getColor(R.color.colorAfterClick));
//            if (listener != null) {
//                listener.onDetailItemClick(v.getTag().toString());
//            }
//        });

        //получаем инфо из 2го фрагмента - название добавленного фильма в избранное
        getParentFragmentManager().setFragmentResultListener("key1", this, new FragmentResultListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle bundle) {
                String name = bundle.getString("name");
                String description = bundle.getString("description");

                //new next Id for new added film
                int size = FilmsItemsRepository.getInstance().getItems().size();

                FilmsItemsRepository.getInstance().getItems().add(new FilmItem(name, R.drawable.ic_baseline_image_24, description, size, false));
                recyclerView.getAdapter().notifyDataSetChanged();
            }
        });

//        if (savedInstanceState != null) {
//            //получение переменных булин для восстановления цвета названия фильма
//            af_cl_django = savedInstanceState.getBoolean("django");
//            af_cl_fiction = savedInstanceState.getBoolean("fiction");
//            af_cl_hollywood = savedInstanceState.getBoolean("hollywood");
//            af_cl_eight = savedInstanceState.getBoolean("eight");
//
//            textView_django.setTextColor(savedInstanceState.getInt(KEY1));
//            textView_fiction.setTextColor(savedInstanceState.getInt(KEY2));
//            textView_hollywood.setTextColor(savedInstanceState.getInt(KEY3));
//            textView_eight.setTextColor(savedInstanceState.getInt(KEY4));
//        }
    }

//    @Override
//    public void onResume() {
//        super.onResume();
//        //изменение цвета названия фильма, если он был кликнут - переменная - true
//        if (af_cl_django) {
//            Log.d("onsave", "4step");
//
//            textView_django = getActivity().findViewById(R.id.Django_name);
//            textView_django.setTextColor(getResources().getColor(R.color.colorAfterClick));
//        }
//        if (af_cl_fiction) {
//            textView_fiction = getActivity().findViewById(R.id.fiction_name);
//            textView_fiction.setTextColor(getResources().getColor(R.color.colorAfterClick));
//        }
//        if (af_cl_hollywood) {
//            textView_hollywood = getActivity().findViewById(R.id.hollywood_name);
//            textView_hollywood.setTextColor(getResources().getColor(R.color.colorAfterClick));
//        }
//        if (af_cl_eight) {
//            textView_eight = getActivity().findViewById(R.id.eight_name);
//            textView_eight.setTextColor(getResources().getColor(R.color.colorAfterClick));
//        }
//    }

//    @Override
//    public void onConfigurationChanged(@NonNull Configuration newConfig) {
//        mDrawerToggle.onConfigurationChanged(newConfig);
//        super.onConfigurationChanged(newConfig);
//
//    }

//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
////        if (mDrawerToggle.onOptionsItemSelected(item)) {
////            return true;
////        }
//        //обработка клика "Add Film"
//        switch (item.getItemId()) {
//            case R.id.add_film:
//                FragmentManager fragmentManager = getFragmentManager();
//                fragmentManager.beginTransaction()
//                        .replace(R.id.fragment_container, addFilmFragment, AddFilmFragment.TAG)
//                        .addToBackStack(null)
//                        .commit();
//                break;
//        }
//        return super.onOptionsItemSelected(item);
//    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
        MenuItem menuItem = menu.findItem(R.id.action_share);
        shareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(menuItem);
        setShareActionIntent("Пойдешь в кино?");
        super.onCreateOptionsMenu(menu, inflater);
    }

    private void setShareActionIntent(String text) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, text);
        shareActionProvider.setShareIntent(intent);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();

        if (id == R.id.main_screen) {
            Toast.makeText(getActivity(), "Home screen", Toast.LENGTH_SHORT).show();
        }
        if (id == R.id.info) {
            AlertDialog.Builder bld = new AlertDialog.Builder(getActivity());
            DialogInterface.OnClickListener lst_1 = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            };

            //показываем диалоговое окно
            DialogInterface.OnClickListener lst_2 = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(getActivity(), "Ok, don't forget", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            };
            DialogInterface.OnClickListener lst_3 = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(getActivity(), "Wait, please", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            };
            bld.setMessage("Leave feedback");
            bld.setTitle("it's us");
            bld.setNegativeButton("No", lst_1);
            bld.setNeutralButton("Later", lst_2);
            bld.setPositiveButton("Yes", lst_3);
            AlertDialog dialog = bld.create();
            dialog.show();
        }

        if (id == R.id.add_film2) {
            FragmentManager fragmentManager = getParentFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, addFilmFragment, AddFilmFragment.TAG)
                    .addToBackStack(null)
                    .commit();

        }
        DrawerLayout drawer = getActivity().findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
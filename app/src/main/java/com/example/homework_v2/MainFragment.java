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

    private RecyclerView recyclerView;

    public static final String TAG = "MainFragment";

    private ShareActionProvider shareActionProvider;//переменная для share

    AddFilmFragment addFilmFragment = new AddFilmFragment();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setHasOptionsMenu(true);//говорим активити, что у фрагмента есть свое меню
        super.onCreate(savedInstanceState);
        //при пересоздании фрагмент не будет убиваться
//        setRetainInstance(true);
    }

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
        FilmsItemsRepository.getInstance().getFavoriteItems();
        //find the right film by filmId
        FilmItem choiceFilm = null;
        for (FilmItem filmItem : FilmsItemsRepository.getInstance().getItems()) {
            if (filmItem.filmId == choiceFilmId) {
                choiceFilm = filmItem;
            }
        }
        choiceFilm.isLiked = !choiceFilm.isLiked;
        recyclerView.getAdapter().notifyDataSetChanged();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //create recyclerview
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        //get the list from the repository
        Log.d("checknow", "onViewCreated");
        FilmsItemsAdaptor adaptor = new FilmsItemsAdaptor(FilmsItemsRepository.getInstance().getItems(), this);
        recyclerView.setAdapter(adaptor);
        //создание табличного LayoutManager с 2 столбцами. в параметрах контекст (активность) и колво столбцов
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(gridLayoutManager);

        //получаем инфо из 2го фрагмента - название добавленного фильма в избранное
        getParentFragmentManager().setFragmentResultListener("key1", this, new FragmentResultListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle bundle) {
                String name = bundle.getString("name");
                String description = bundle.getString("description");

                //new next Id for new added film
                int size = FilmsItemsRepository.getInstance().getItems().size();
                //parse drawable to the String
                FilmsItemsRepository.getInstance().getItems().add(new FilmItem(name, "drawable://" + R.drawable.ic_baseline_image_24, description, size, false));
                recyclerView.getAdapter().notifyDataSetChanged();
            }
        });
    }

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
package com.example.homework_v2;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FilmsItemsRepository {

    //для хранения списка фильмов
    private static List<FilmItem> items = new ArrayList<>();
    private static List<FilmItem> favoriteItems = new ArrayList<>();

    private static FilmsItemsRepository instance;
    //конструктор. создание списка
    private FilmsItemsRepository() {
        Log.d("checknow", "FilmsItemsRepository");
        HomeworkApp.getInstance().filmApiService.getMovies().enqueue(new Callback<List<FilmJson>>() {
            @Override
            public void onResponse(Call<List<FilmJson>> call, Response<List<FilmJson>> response) {
                if (response.isSuccessful()) {
                    List<FilmJson> filmJsonList = response.body();

                    for (int i = 0; i < filmJsonList.size(); i++) {
                        items.add(new FilmItem(filmJsonList.get(i), false, i));
                    }
                    Log.d("checknow", "listisfull");
                }
            }

            @Override
            public void onFailure(Call<List<FilmJson>> call, Throwable t) {
            }
        });


//        for(int i = 0; i < Film.films.length; i++) {
//            items.add(new FilmItem(Film.films[i].getName(), Film.films[i].getImageResourceId(), Film.films[i].getDescription(), i, false));
//        }
    }

    //создание singltone
    public static FilmsItemsRepository getInstance() {
        //объект будет всего один
        Log.d("checknow", "getInstance");
        if (instance == null) {
            instance = new FilmsItemsRepository();
        }
        return instance;
    }

    // метод откуда получаем список
    public List<FilmItem> getItems() {
        Log.d("checknow", "getItems");
        return items;
    }

    //get the list of favorites items
    public List<FilmItem> getFavoriteItems() {
        favoriteItems.clear();
        for (FilmItem filmItem : items) {
            if (filmItem.isLiked) {
                favoriteItems.add(filmItem);
            }
        }
        return favoriteItems;
    }


}

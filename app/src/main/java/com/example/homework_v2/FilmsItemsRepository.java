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
    }

    //создание singltone
    public static FilmsItemsRepository getInstance() {
        //объект будет всего один
        if (instance == null) {
            instance = new FilmsItemsRepository();
        }
        return instance;
    }

    // метод откуда получаем список
    public List<FilmItem> getItems() {
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

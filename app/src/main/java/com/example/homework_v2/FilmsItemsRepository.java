package com.example.homework_v2;

import java.util.ArrayList;
import java.util.List;

public class FilmsItemsRepository {

    //для хранения списка фильмов
    private static List<FilmItem> items = new ArrayList<>();
    private static List<FilmItem> favoriteItems = new ArrayList<>();

    private static FilmsItemsRepository instance;
    //конструктор. создание списка
    private FilmsItemsRepository() {
        for(int i = 0; i < Film.films.length; i++) {
            items.add(new FilmItem(Film.films[i].getName(), Film.films[i].getImageResourceId(), Film.films[i].getDescription(), i, false));
        }
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
        return favoriteItems;
    }


}

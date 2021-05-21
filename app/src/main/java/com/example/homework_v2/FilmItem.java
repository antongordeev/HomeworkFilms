package com.example.homework_v2;


public class FilmItem {

    public String title;
    public int imageResourceId;
    public String description;
    public int filmId;
    public boolean isLiked;


    public FilmItem(String title, int imageResourceId, String description, int filmId, boolean isLiked) {
        this.title = title;
        this.imageResourceId = imageResourceId;
        this.description = description;
        this.filmId = filmId;
        this.isLiked = isLiked;
    }

    public String getTitle() {
        return title;
    }

    public int getImageResourceId() {
        return imageResourceId;
    }

    public String getDescription() {
        return description;
    }

    public int getFilmId() {
        return filmId;
    }

    public boolean getIsLiked() {
        return isLiked;
    }



}

package com.example.homework_v2;


public class FilmItem {

    public String title;
    public String imageResourceId;
    public String description;
    public int filmId;
    public boolean isLiked;


    public FilmItem(String title, String imageResourceId, String description, int filmId, boolean isLiked) {
        this.title = title;
        this.imageResourceId = imageResourceId;
        this.description = description;
        this.filmId = filmId;
        this.isLiked = isLiked;
    }

    public FilmItem(FilmJson filmJson, boolean isLiked, int filmId) {
        this.title = filmJson.title;
        this.description = filmJson.description;
        this.imageResourceId = filmJson.imageResourceId;
        this.isLiked = isLiked;
        this.filmId = filmId;
    }

    public String getTitle() {
        return title;
    }

    public String getImageResourceId() {
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

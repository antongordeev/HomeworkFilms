package com.example.homework_v2;

import com.google.gson.annotations.SerializedName;

public class FilmJson {
//    @SerializedName("name")
    public String title;
    public String description;
    @SerializedName("img")
    public String imageResourceId;
}

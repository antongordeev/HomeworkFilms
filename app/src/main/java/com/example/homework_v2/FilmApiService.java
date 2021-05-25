package com.example.homework_v2;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface FilmApiService {
    @GET("list")
    Call<List<FilmJson>> getMovies();

}

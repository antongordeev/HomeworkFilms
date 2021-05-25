package com.example.homework_v2;

import android.app.Application;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeworkApp extends Application {
    public FilmApiService filmApiService;

    private static HomeworkApp instance;

    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;
        initRetrofit();
    }

    public static HomeworkApp getInstance() {
        return instance;
    }

    private void initRetrofit() {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(chain -> {
                    Request request = chain.request().newBuilder().addHeader("X-Auth-Token", "djksjdj2hhfs").build();
                    return chain.proceed(request);
                })
                .addInterceptor(httpLoggingInterceptor)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl("https://my-json-server.typicode.com/antongordeev/json_placeholder/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        filmApiService = retrofit.create(FilmApiService.class);
    }
}

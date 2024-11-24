package com.example.earthly;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInstance {
    public static final Retrofit VideoFit = new Retrofit.Builder().
            baseUrl("https://yt-api.p.rapidapi.com/").
            addConverterFactory(GsonConverterFactory.create())
            .build();

}

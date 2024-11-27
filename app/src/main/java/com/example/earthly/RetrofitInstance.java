package com.example.earthly;

import com.example.earthly.apiIterfaces.MapApiInterface;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInstance {
    public static final Retrofit VideoFit = new Retrofit.Builder().
            baseUrl("https://yt-api.p.rapidapi.com/").
            addConverterFactory(GsonConverterFactory.create())
            .build();

    public static final Retrofit MapFit = new Retrofit.Builder().
            baseUrl("https://serpapi.com/").
            addConverterFactory(GsonConverterFactory.create())
            .build();

    public static MapApiInterface createMapApi(){
        return MapFit.create(MapApiInterface.class);
    }
}

package com.example.earthly.apiIterfaces;

import com.example.earthly.responses.YouTubeResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface VideoApi {

    @GET("search")
    Call<YouTubeResponse> searchVideos(
            @Query("query") String query,
            @Header("X-RapidAPI-Key") String apiKey,
            @Header("X-RapidAPI-Host") String apiHost
    );


}

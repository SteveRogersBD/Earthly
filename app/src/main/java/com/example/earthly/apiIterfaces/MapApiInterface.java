package com.example.earthly.apiIterfaces;

import com.example.earthly.responses.MapResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MapApiInterface {

    @GET("search.json")
    Call<MapResponse> getParkLocations(@Query("api_key") String apiKey,
                                       @Query("engine") String engine,
                                       @Query("q") String query,
                                       @Query("ll") String latLong
                                       );

}

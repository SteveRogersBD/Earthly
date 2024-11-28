package com.example.earthly.apiIterfaces;

import com.example.earthly.requests.EventRequest;
import com.example.earthly.requests.LogInRequest;
import com.example.earthly.requests.SignUpRequest;
import com.example.earthly.responses.EventResponse;
import com.example.earthly.responses.LogInResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface AuthApiInterface {

    @POST("login")
    Call<LogInResponse>logInUser(@Body LogInRequest request);

    @POST("register")
    Call<LogInResponse>registerUser(@Body SignUpRequest request);

    @POST("/location/create/user/{userId}")
    Call<EventResponse>createEvent(@Path("userId") int userId,
                                   @Body EventRequest req);
}

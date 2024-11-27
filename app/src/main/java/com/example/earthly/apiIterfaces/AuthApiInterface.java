package com.example.earthly.apiIterfaces;

import com.example.earthly.requests.LogInRequest;
import com.example.earthly.requests.SignUpRequest;
import com.example.earthly.responses.LogInResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthApiInterface {

    @POST("login")
    Call<LogInResponse>logInUser(@Body LogInRequest request);

    @POST("register")
    Call<LogInResponse>registerUser(@Body SignUpRequest request);
}

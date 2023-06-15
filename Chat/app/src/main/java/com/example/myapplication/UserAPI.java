package com.example.myapplication;


import com.example.myapplication.entities.UserLogin;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserAPI {


    @POST("Tokens")
    Call <String> login(@Body UserLogin user);
}
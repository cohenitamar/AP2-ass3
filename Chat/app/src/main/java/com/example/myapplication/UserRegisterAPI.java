package com.example.myapplication;


import com.example.myapplication.entities.RegisterUser;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserRegisterAPI {
    @POST("Users")
    Call<Void> register(@Body RegisterUser user);
}

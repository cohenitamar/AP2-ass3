package com.example.myapplication;


import com.example.myapplication.entities.ChatUser;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserRegisterAPI {
    @POST("Users")
    Call<Void> register(@Body ChatUser user);
}

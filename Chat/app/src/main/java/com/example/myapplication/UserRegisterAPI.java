package com.example.myapplication;


import com.example.myapplication.entities.ChatUser;
import com.example.myapplication.entities.RegisterUser;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface UserRegisterAPI {
    @POST("Users")
    Call<Void> register(@Body RegisterUser user);

    @GET("Users/{username}")
    Call<ChatUser> get(@Header("Authorization") String token, @Path("username") String username);
}

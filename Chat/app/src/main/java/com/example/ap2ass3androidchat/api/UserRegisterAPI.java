package com.example.ap2ass3androidchat.api;


import com.example.ap2ass3androidchat.assistingclasses.ChatUser;
import com.example.ap2ass3androidchat.assistingclasses.RegisterUser;

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

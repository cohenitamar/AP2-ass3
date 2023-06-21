package com.example.ap2ass3androidchat;


import com.example.ap2ass3androidchat.entities.UserLogin;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface UserAPI {


    @POST("Tokens")
    Call <String> login(@Body UserLogin user, @Header("phoneToken") String phoneToken);

}

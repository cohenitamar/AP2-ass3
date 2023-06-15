package com.example.myapplication;

import com.example.myapplication.entities.ChatMember;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface ChatAPI {

    @GET("Chats")
    Call <ArrayList<ChatMember>> getChats(@Header("Authorization") String token);
}


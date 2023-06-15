package com.example.myapplication;

import com.example.myapplication.entities.ChatMember;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface ChatAPI {

    @GET("Chats")
    Call <List<ChatMember>> getChats(@Header("Authorization") String token);
}


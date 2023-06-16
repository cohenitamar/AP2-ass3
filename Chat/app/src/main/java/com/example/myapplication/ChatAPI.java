package com.example.myapplication;

import com.example.myapplication.entities.ChatMember;
import com.example.myapplication.entities.ChatMessage;
import com.example.myapplication.entities.Contact;
import com.example.myapplication.entities.MessagesByID;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface ChatAPI {

    @GET("Chats")
    Call <List<Contact>> getChats(@Header("Authorization") String token);

    @GET("Chats/{id}/Messages")
    Call <List<MessagesByID>> getChatsByID(@Header("Authorization") String token, @Path("id") String id);

}


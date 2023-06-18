package com.example.myapplication;

import com.example.myapplication.entities.Contact;
import com.example.myapplication.entities.MessagesByID;
import com.example.myapplication.entities.PostChatUser;
import com.example.myapplication.entities.PostMessagesByID;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ChatAPI {

    @GET("Chats")
    Call <List<Contact>> getChats(@Header("Authorization") String token);

    @GET("Chats/{id}/Messages")
    Call <List<MessagesByID>> getChatsByID(@Header("Authorization") String token,
                                           @Path("id") String id);

    @POST("Chats")
    Call <PostChatUser> postChat(@Header("Authorization") String token,
                                 @Body Map<String, String> requestBody);

    @POST("Chats/{id}/Messages")
    Call <PostMessagesByID> postMessage(@Path("id") String id,
                                        @Header("Authorization") String token,
                                        @Body Map<String, String> requestBody);


}


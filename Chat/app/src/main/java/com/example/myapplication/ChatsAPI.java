package com.example.myapplication;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.myapplication.contacts.ContactDB;
import com.example.myapplication.entities.ChatMessage;
import com.example.myapplication.entities.Contact;
import com.example.myapplication.entities.MessagesByID;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ChatsAPI {

    Retrofit retrofit;
    ChatAPI chatApi;


    private MutableLiveData<String> responseLiveData;

    public ChatsAPI() {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor).build();

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        retrofit = new Retrofit.Builder().baseUrl("http://10.0.2.2:5000/api/")
                .addConverterFactory(GsonConverterFactory.create(gson)).client(client)
                .build();


        chatApi = retrofit.create(ChatAPI.class);

        responseLiveData = new MutableLiveData<>();

    }




    public void get(MutableLiveData<List<Contact>> contactListData, String token) {
        Call<List<Contact>> call = chatApi.getChats(token);
        call.enqueue(new Callback<List<Contact>>() {
            @Override
            public void onResponse(Call<List<Contact>> call,
                                   Response<List<Contact>> response) {
                if (response.isSuccessful()) {
                    contactListData.setValue(response.body());
                    Log.e("API Call", response.body().toString());
                } else {
                    Log.e("API Call", "faillogin");
                }
            }

            @Override
            public void onFailure(Call<List<Contact>> call, Throwable t) {
                // Handle the network or API call failure
                String errorMessage = t.getMessage();
                if (errorMessage != null) {
                    Log.e("API Call", "Error message: " + errorMessage);
                } else {
                    Log.e("API Call", "Unknown error occurred.");
                }
            }
        });
    }


    public void getChatsByID(MutableLiveData<List<MessagesByID>> messageListData, String token,
                             String id) {
        Call<List<MessagesByID>> call = chatApi.getChatsByID(token, id);
        call.enqueue(new Callback<List<MessagesByID>>() {
            @Override
            public void onResponse(Call<List<MessagesByID>> call,
                                   Response<List<MessagesByID>> response) {
                if (response.isSuccessful()) {
                    List<MessagesByID> mByID = response.body();
                    Collections.reverse(mByID);
                    messageListData.setValue(mByID);
                    Log.e("API Call", response.body().toString());
                } else {
                    Log.e("API Call", "faillogin");
                }
            }

            @Override
            public void onFailure(Call<List<MessagesByID>> call, Throwable t) {
                // Handle the network or API call failure
                String errorMessage = t.getMessage();
                if (errorMessage != null) {
                    Log.e("API Call", "Error message: " + errorMessage);
                } else {
                    Log.e("API Call", "Unknown error occurred.");
                }
            }
        });
    }


}

package com.example.myapplication;

import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.myapplication.entities.ChatMember;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
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

        responseLiveData = new MutableLiveData<String>();

    }
    public void get(String token) {
        Call<List<ChatMember>> call = chatApi.getChats(token);
        call.enqueue(new Callback<List<ChatMember>> () {
            @Override
            public void onResponse(Call<List<ChatMember>>  call,
                                   Response<List<ChatMember>> response) {


                if (response.isSuccessful()) {
//                    responseLiveData.setValue(response.body());
//                    Log.e("API Call",response.body());
                    // Handle the response string
                } else {
                    Log.e("API Call","faillogin");
                }
            }

            @Override
            public void onFailure(Call<List<ChatMember>>call, Throwable t) {
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

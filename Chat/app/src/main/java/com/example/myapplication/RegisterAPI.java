package com.example.myapplication;

import android.content.Intent;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.myapplication.entities.ChatUser;
import com.example.myapplication.entities.RegisterUser;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegisterAPI {

    Retrofit retrofit;
    static UserRegisterAPI registerAPI;

    private static MutableLiveData<String> responseLiveData;


    public RegisterAPI() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor).build();

        retrofit = new Retrofit.Builder().baseUrl("http://10.0.2.2:5000/api/")
                .addConverterFactory(GsonConverterFactory.create(gson)).client(client)
                .build();
        registerAPI = retrofit.create(UserRegisterAPI.class);

        responseLiveData = new MutableLiveData<String>();

    }

    public MutableLiveData<String> getResponseLiveData() {
        return responseLiveData;
    }


    public static void post(RegisterUser user) {
        Call<Void> call = registerAPI.register(user);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                        // Handle the non-empty response
                        responseLiveData.setValue("true");
                        // Handle the empty response
                    // Handle the response string
                } else {

                    int a = response.code();
                    if(a == 409){
                        responseLiveData.setValue("User already exist.");
                    }
                    else{

                        responseLiveData.setValue("Bad Request");
                    }
                    Log.e("API Call3", responseLiveData.toString());
                }
            }


            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                // Handle the network or API call failure
                String errorMessage = t.getMessage();
                if (errorMessage != null) {
                    Log.e("API Call1", "Error message: " + errorMessage);
                } else {
                    Log.e("API Call2", "Unknown error occurred.");
                }
            }
        });
    }


}

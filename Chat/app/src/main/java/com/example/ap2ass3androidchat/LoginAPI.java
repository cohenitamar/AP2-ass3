package com.example.ap2ass3androidchat;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.ap2ass3androidchat.entities.UserLogin;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginAPI {
    Retrofit retrofit;
    UserAPI userAPI;

    private MutableLiveData<String> responseLiveData;


    public LoginAPI() {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor).build();

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        retrofit = new Retrofit.Builder().baseUrl("http://5.39.117.103:5000/api/")
                .addConverterFactory(GsonConverterFactory.create(gson)).client(client)
                .build();
        userAPI = retrofit.create(UserAPI.class);

        responseLiveData = new MutableLiveData<>();

    }

    public MutableLiveData<String> getResponseLiveData() {
        return responseLiveData;
    }

    public void post(UserLogin userLogin, String phoneToken) {


        Call<String> call = userAPI.login(userLogin, phoneToken);
        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {

                   responseLiveData.setValue(response.body());
                    Log.e("API Call",response.body());
                    // Handle the response string
                } else {
                    responseLiveData.setValue("Not valid user/password.");
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
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

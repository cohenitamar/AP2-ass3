package com.example.ap2ass3androidchat;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.widget.TextView;

import androidx.lifecycle.MutableLiveData;

import com.example.ap2ass3androidchat.entities.ChatUser;
import com.example.ap2ass3androidchat.entities.RegisterUser;
import com.google.android.material.imageview.ShapeableImageView;
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

        SharedPreferences pref = SingletonURL.getURLInstance();
        String URL = pref.getString("URL", "http://10.0.2.2:5000");

        retrofit = new Retrofit.Builder().baseUrl(URL + "/api/")
                .addConverterFactory(GsonConverterFactory.create(gson)).client(client)
                .build();
        registerAPI = retrofit.create(UserRegisterAPI.class);

        responseLiveData = new MutableLiveData<String>();

    }


    public void setURL() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor).build();

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        SharedPreferences pref = SingletonURL.getURLInstance();
        String URL = pref.getString("URL", "http://10.0.2.2:5000");

        retrofit = new Retrofit.Builder().baseUrl(URL + "/api/")
                .addConverterFactory(GsonConverterFactory.create(gson)).client(client)
                .build();
        registerAPI = retrofit.create(UserRegisterAPI.class);

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
                    if (a == 409) {
                        responseLiveData.setValue("User already exist.");
                    } else {

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

    public static void getUser(String token, String username, ShapeableImageView img, TextView name) {
        Call<ChatUser> call = registerAPI.get(token, username);
        call.enqueue(new Callback<ChatUser>() {
            @Override
            public void onResponse(Call<ChatUser> call, Response<ChatUser> response) {
                if (response.isSuccessful()) {
                    ChatUser me = response.body();
                    String encodedProfilePic = me.getProfilePic();
                    if (encodedProfilePic.equals("/static/media/easter_egg.d0d1d09d533aee0fddf4.png")) {
                        img.setImageResource(R.drawable.easter_egg);
                    } else {

                        encodedProfilePic = encodedProfilePic
                                .replaceFirst("^data:image\\/.+;base64,", "");
                        byte[] decodedBytes = Base64.decode(encodedProfilePic, Base64.DEFAULT);
                        Bitmap decodedBitmap = BitmapFactory
                                .decodeByteArray(decodedBytes, 0, decodedBytes.length);
                        img.setImageBitmap(decodedBitmap);
                    }
                    name.setText(me.getDisplayName());
                }
            }


            @Override
            public void onFailure(Call<ChatUser> call, Throwable t) {
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

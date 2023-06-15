package com.example.myapplication;

import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.myapplication.entities.ChatMember;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;

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

    ArrayList<ChatMember> contacts;

    MutableLiveData<Integer> status;

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
        Call<ArrayList<ChatMember>> call = chatApi.getChats(token);
        call.enqueue(new Callback<ArrayList<ChatMember>> () {
            @Override
            public void onResponse(Call<ArrayList<ChatMember>>  call, Response<ArrayList<ChatMember>> response) {
                new getChatTask(response).execute();
            }
              /*  if (response.isSuccessful()) {
//                    responseLiveData.setValue(response.body());
//                    Log.e("API Call",response.body());
                    // Handle the response string
                } else {
                    Log.e("API Call","faillogin");
                }
            }*/

            @Override
            public void onFailure(Call<ArrayList<ChatMember>>call, Throwable t) {
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



    public ArrayList<ChatMember> getChatMembers() {
        return contacts;
    }
    public MutableLiveData<Integer> getStatus() {
        return status;
    }

    private class getChatTask extends AsyncTask<Void, Void, ArrayList<ChatMember>> {
        private Response<ArrayList<ChatMember>> response;

        public getChatTask(Response<ArrayList<ChatMember>> res) {
            this.response = res;
        }

        @Override
        protected  ArrayList<ChatMember> doInBackground(Void... voids) {
            if(this.response.isSuccessful()) {
                return this.response.body();
            }
            return null;
        }

        protected  void onPostExectue(ArrayList<ChatMember> res) {
            if(res != null) {
                contacts = res;
            } else {
                contacts = null;
            }
            status.setValue(response.code());
        }
    }

}

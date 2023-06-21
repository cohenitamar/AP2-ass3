package com.example.ap2ass3androidchat.api;

import android.content.SharedPreferences;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.ap2ass3androidchat.singleton.SingletonChatsAPI;
import com.example.ap2ass3androidchat.singleton.SingletonDatabase;
import com.example.ap2ass3androidchat.singleton.SingletonURL;
import com.example.ap2ass3androidchat.contacts.ContactDB;
import com.example.ap2ass3androidchat.contacts.ContactsDao;
import com.example.ap2ass3androidchat.entities.Contact;
import com.example.ap2ass3androidchat.entities.MessagesByID;
import com.example.ap2ass3androidchat.assistingclasses.PostChatUser;
import com.example.ap2ass3androidchat.assistingclasses.PostMessagesByID;
import com.example.ap2ass3androidchat.assistingclasses.Sender;
import com.example.ap2ass3androidchat.messages.MessageDB;
import com.example.ap2ass3androidchat.messages.MessagesDao;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Collections;
import java.util.List;
import java.util.Map;

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

    MutableLiveData<String> responseLiveData;

    MutableLiveData<String> responseAnswer;


    public ChatsAPI() {

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


        chatApi = retrofit.create(ChatAPI.class);

        responseAnswer = SingletonChatsAPI.getSingletonChatsAPIInstance();
        responseLiveData = new MutableLiveData<>();

    }


    public MutableLiveData<String> getResponseLiveData() {
        return responseLiveData;
    }

    public void getChats(MutableLiveData<List<Contact>> contactListData, String token) {
        Call<List<Contact>> call = chatApi.getChats(token);
        call.enqueue(new Callback<List<Contact>>() {
            @Override
            public void onResponse(Call<List<Contact>> call,
                                   Response<List<Contact>> response) {
                if (response.isSuccessful()) {
                    ContactsDao dao = SingletonDatabase
                            .getContactInstance()
                            .contactDao();
                    dao.deleteAll();
                    List<Contact> list = response.body();
                    for (Contact c : list) {
                        if (c.getLastMessage() != null) {
                            if (c.getLastMessage().getCreated() != null) {
                                c.getLastMessage()
                                        .setCreated((formatDate(
                                                c.getLastMessage().getCreated())));
                            }
                        }
                        dao.insert(c);
                    }
                    contactListData.setValue(list);
                    responseLiveData.setValue("bla");
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
                    for (MessagesByID m : mByID) {
                        m.setChatID(id);
                        m.setCreated(formatDate(m.getCreated()));
                    }
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

    public void postChats(String token, String username) {
        Call<PostChatUser> call = chatApi.postChat(token, Map.of("username", username));
        call.enqueue(new Callback<PostChatUser>() {
            @Override
            public void onResponse(Call<PostChatUser> call, Response<PostChatUser> response) {
                if (response.isSuccessful()) {
                    ContactDB db = SingletonDatabase.getContactInstance();
                    ContactsDao dao = db.contactDao();
                    PostChatUser p = response.body();
                    Contact c = new Contact(p.getId(), p.getUser(), null);
                    dao.insert(c);
                    Log.e("API Call", response.body().toString());
                    responseAnswer.setValue("ok");
                } else {
                    responseAnswer.setValue("invalid");
                }
            }

            @Override
            public void onFailure(Call<PostChatUser> call, Throwable t) {
                Log.e("API Call", "probleeeeem");
            }
        });
    }

    public void postMessages(MutableLiveData<List<MessagesByID>> messageListData, String token, String id, String msg) {
        Call<PostMessagesByID> call = chatApi.postMessage(id, token, Map.of("msg", msg));
        call.enqueue(new Callback<PostMessagesByID>() {
            @Override
            public void onResponse(Call<PostMessagesByID> call, Response<PostMessagesByID> response) {
                if (response.isSuccessful()) {
                    PostMessagesByID newMsg = response.body();
                    List<MessagesByID> list = messageListData.getValue();
                    MessagesByID insert = new MessagesByID(newMsg.getId(), formatDate(newMsg.getCreated()),
                            new Sender(newMsg.getSender().getUsername()), newMsg.getContent());
                    insert.setChatID(id);
                    list.add(insert);
                    MessageDB db = SingletonDatabase.getMessageInstance();
                    MessagesDao dao = db.messageDao();
                    dao.insert(insert);
                    messageListData.setValue(list);
                    Log.e("API Call", response.body().toString());
                }
            }

            @Override
            public void onFailure(Call<PostMessagesByID> call, Throwable t) {

            }
        });


    }

    public static String formatDate(String isoDate) {
        String[] dateParts, date, time;
        String year, month, day, hour, minute;
        dateParts = isoDate.split("T");
        date = dateParts[0].split("-");
        time = dateParts[1].split(":");
        year = date[0];
        month = date[1];
        day = date[2];
        hour = time[0];
        minute = time[1];

        // Add 3 to the hour and handle wrapping from 24 to 0
        int hourInt = Integer.parseInt(hour);
        hourInt += 3;
        if (hourInt >= 24) {
            hourInt -= 24;
        }
        // Add leading zero if necessary
        hour = String.format("%02d", hourInt);
        return day + "." + month + "." + year + " " + hour + ":" + minute;
    }

}

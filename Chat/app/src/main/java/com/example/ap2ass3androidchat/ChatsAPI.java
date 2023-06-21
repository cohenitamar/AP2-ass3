package com.example.ap2ass3androidchat;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.ap2ass3androidchat.contacts.ContactDB;
import com.example.ap2ass3androidchat.contacts.ContactsDao;
import com.example.ap2ass3androidchat.entities.Contact;
import com.example.ap2ass3androidchat.entities.MessagesByID;
import com.example.ap2ass3androidchat.entities.PostChatUser;
import com.example.ap2ass3androidchat.entities.PostMessagesByID;
import com.example.ap2ass3androidchat.entities.Sender;
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


    private MutableLiveData<String> responseAnswer;

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

        responseAnswer = new MutableLiveData<>();

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
                    for(Contact c : response.body()) {
                        dao.insert(c);
                    }
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
                    for (MessagesByID m : mByID) {
                        m.setChatID(id);
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
                    int a = response.code();
                    responseAnswer.setValue(response.errorBody().toString());
                    Log.e("API Call", "dfdfdf");
                    Log.e("API Call", responseAnswer.toString());
                    a = response.code();
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

/*                    // split the string by the "T" character
                    String[] dateTime = newMsg.getCreated().split("T");

                    // split the date into its components
                    String[] dateParts = dateTime[0].split("-");

                    // split the time into its components
                    String[] timeParts = dateTime[1].split(":|\\.");

                    // rearrange the date and time components into the desired format
                    String formattedDate = dateParts[2] + "." + dateParts[1] + "." + dateParts[0]
                            + " " + timeParts[0] + ":" + timeParts[1];*/

                    MessagesByID insert = new MessagesByID(newMsg.getId(), newMsg.getCreated(),
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


}

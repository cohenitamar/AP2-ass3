package com.example.ap2ass3androidchat.singleton;

import androidx.lifecycle.MutableLiveData;

public class SingletonChatsAPI {


    private static MutableLiveData<String> data;

    private SingletonChatsAPI() {
    }

    public static synchronized MutableLiveData<String> getSingletonChatsAPIInstance() {
        if (data == null) {
            data = new MutableLiveData<>();
        }
        return data;
    }


}
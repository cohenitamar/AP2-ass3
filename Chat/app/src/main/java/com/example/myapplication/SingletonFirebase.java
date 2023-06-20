package com.example.myapplication;

import androidx.lifecycle.MutableLiveData;

import com.example.myapplication.entities.MessagesByID;


public class SingletonFirebase {
    private static MutableLiveData<String> contactFirebase;
    private static MutableLiveData<MessagesByID> messageFirebase;

    private SingletonFirebase() {
        // Private constructor to prevent instantiation
    }

    public static synchronized MutableLiveData<String> getFirebaseContactInstance() {
        if (contactFirebase == null) {
            contactFirebase = new MutableLiveData<>();
        }
        return contactFirebase;
    }



    public static synchronized MutableLiveData<MessagesByID> getFirebaseMessageInstance() {
        if (messageFirebase == null) {
            messageFirebase =  new MutableLiveData<>();
        }
        return messageFirebase;
    }


}
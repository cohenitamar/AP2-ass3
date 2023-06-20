package com.example.myapplication;

import android.content.Context;

import androidx.room.Room;

import com.example.myapplication.contacts.ContactDB;
import com.example.myapplication.messages.MessageDB;

public class SingletonLogout {

    public static void setState(Integer state) {
        SingletonLogout.state = state;
    }

    private static Integer state;

    private SingletonLogout() {
    }

    public static synchronized Integer getLogoutInstance() {
        if (state == null) {
            state = 0;
        }
        return state;
    }


}
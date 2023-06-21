package com.example.ap2ass3androidchat;

import android.content.SharedPreferences;

public class SingletonURL {


    private static SharedPreferences preference;

    private SingletonURL() {
    }

    public static synchronized SharedPreferences getURLInstance(SharedPreferences pref) {
        if (preference == null) {
            preference = pref;
        }
        return preference;
    }


    public static synchronized SharedPreferences getURLInstance() {
        return preference;
    }

}
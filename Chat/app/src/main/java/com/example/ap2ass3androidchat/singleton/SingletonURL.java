package com.example.ap2ass3androidchat.singleton;

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
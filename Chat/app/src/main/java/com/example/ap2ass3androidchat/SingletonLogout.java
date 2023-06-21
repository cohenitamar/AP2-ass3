package com.example.ap2ass3androidchat;

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
package com.example.ap2ass3androidchat;

public class SingletonNotification {

    public static void toggleState() {
        SingletonNotification.state = !SingletonNotification.state;
    }

    private static Boolean state;

    private SingletonNotification() {
    }

    public static synchronized Boolean getLogoutInstance() {
        if (state == null) {
            state = false;
        }
        return state;
    }


}
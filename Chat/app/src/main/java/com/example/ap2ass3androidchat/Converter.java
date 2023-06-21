package com.example.ap2ass3androidchat;

import androidx.room.TypeConverter;

import com.example.ap2ass3androidchat.entities.ChatMessage;
import com.example.ap2ass3androidchat.entities.ChatUser;
import com.example.ap2ass3androidchat.entities.Sender;
import com.google.gson.Gson;


public class Converter {

    @TypeConverter
    public static String fromContactUserDetails(ChatUser userDetails) {
        // Convert the ContactUserDetails object to a string representation (e.g., JSON)
        // and return it.
        Gson gson = new Gson();
        return gson.toJson(userDetails);
    }

    @TypeConverter
    public static ChatUser toContactUserDetails(String userDetailsJson) {
        // Convert the string representation back to a ContactUserDetails object
        // and return it.
        Gson gson = new Gson();
        return gson.fromJson(userDetailsJson, ChatUser.class);

    }


    @TypeConverter
    public static String fromLastMessage(ChatMessage msg) {
        // Convert the ContactUserDetails object to a string representation (e.g., JSON)
        // and return it.
        Gson gson = new Gson();
        return gson.toJson(msg);
    }

    @TypeConverter
    public static ChatMessage toLastMessage(String msg) {
        // Convert the string representation back to a ContactUserDetails object
        // and return it.
        Gson gson = new Gson();
        return gson.fromJson(msg, ChatMessage.class);
    }


    @TypeConverter
    public static String fromSender(Sender sender) {
        // Convert the ContactUserDetails object to a string representation (e.g., JSON)
        // and return it.
        Gson gson = new Gson();
        return gson.toJson(sender);
    }

    @TypeConverter
    public static Sender toSender(String sender) {
        // Convert the string representation back to a ContactUserDetails object
        // and return it.
        Gson gson = new Gson();
        return gson.fromJson(sender, Sender.class);
    }



}

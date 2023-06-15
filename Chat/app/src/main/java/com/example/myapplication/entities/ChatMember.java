package com.example.myapplication.entities;

public class ChatMember {
    private String id;
    private user user;
    private messageChat lastMessage;

    public ChatMember(String id, user user, messageChat lastMessage) {
        this.id = id;
        this.user = user;
        this.lastMessage = lastMessage;
    }
}

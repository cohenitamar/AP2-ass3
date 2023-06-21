package com.example.ap2ass3androidchat.entities;

public class PostChatUser {
    private String id;
    private ChatUser user;

    public String getId() {
        return id;
    }

    public ChatUser getUser() {
        return user;
    }

    public PostChatUser(String id, ChatUser user) {
        this.id = id;
        this.user = user;


    }
}

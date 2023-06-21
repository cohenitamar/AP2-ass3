package com.example.ap2ass3androidchat.entities;

public class ChatMember {
    private String id;
    private ChatUser user;
    private ChatMessage lastMessage;

    public ChatMember(String id, ChatUser user, ChatMessage lastMessage) {
        this.id = id;
        this.user = user;
        this.lastMessage = lastMessage;
    }
}
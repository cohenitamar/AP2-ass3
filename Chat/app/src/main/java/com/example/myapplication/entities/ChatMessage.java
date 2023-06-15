package com.example.myapplication.entities;

public class ChatMessage {
    private  String id;
    private  String created;
    private String content;


    public ChatMessage(String id, String created, String content) {
        this.id = id;
        this.created = created;
        this.content = content;
    }
}

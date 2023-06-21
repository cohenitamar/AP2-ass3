package com.example.ap2ass3androidchat.entities;

public class ChatMessage {

    private  String id;

    public void setCreated(String created) {
        this.created = created;
    }

    private  String created;

    public String getId() {
        return id;
    }

    public String getCreated() {
        return created;
    }

    public String getContent() {
        return content;
    }

    private String content;


    public ChatMessage(String id, String created, String content) {
        this.id = id;
        this.created = created;
        this.content = content;
    }
}

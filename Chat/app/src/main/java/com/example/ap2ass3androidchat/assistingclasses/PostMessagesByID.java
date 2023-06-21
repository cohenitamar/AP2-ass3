package com.example.ap2ass3androidchat.assistingclasses;

import com.example.ap2ass3androidchat.assistingclasses.ChatUser;


public class PostMessagesByID {

    private String id;
    private String created;
    private ChatUser sender;
    private String content;

    public void setChatID(String chatID) {
        this.chatID = chatID;
    }

    public String getChatID() {
        return chatID;
    }

    private String chatID;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public ChatUser getSender() {
        return sender;
    }

    public void setSender(ChatUser sender) {
        this.sender = sender;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public PostMessagesByID(String id, String created, ChatUser sender, String content) {
        this.id = id;
        this.created = created;
        this.sender = sender;
        this.content = content;
    }


}

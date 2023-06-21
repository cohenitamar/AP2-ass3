package com.example.ap2ass3androidchat.entities;

import com.example.ap2ass3androidchat.R;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Message {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String chatID;
    private String sender;
    private String receiver;
    private String date;
    private String content;


    public Message(String chatID, String sender,
                   String receiver, String date, String content) {
        this.chatID = chatID;
        this.sender = sender;
        this.receiver = receiver;
        this.date = date;
        this.content = content;
    }

    public Message() {
    }

    public int getId() {
        return id;
    }

    public String getChatID() {
        return chatID;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSender() {
        return sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public String getDate() {
        return date;
    }

    public String getContent() {
        return content;
    }

    public void setChatID(String chatID) {
        this.chatID = chatID;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", chatID='" + chatID + '\'' +
                ", sender='" + sender + '\'' +
                ", receiver='" + receiver + '\'' +
                ", date='" + date + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}


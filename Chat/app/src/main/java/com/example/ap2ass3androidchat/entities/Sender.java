package com.example.ap2ass3androidchat.entities;

public class Sender {

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    private String username;

    public Sender(String u) {
        this.username = u;
    }
}

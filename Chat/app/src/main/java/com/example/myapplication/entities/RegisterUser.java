package com.example.myapplication.entities;

public class RegisterUser {

    private String username;

    private String password;
    private String displayName;
    private String profilePic;


    public RegisterUser(String username, String password, String displayName, String profilePic) {
        this.username = username;
        this.password = password;
        this.displayName = displayName;
        this.profilePic = profilePic;
    }
}

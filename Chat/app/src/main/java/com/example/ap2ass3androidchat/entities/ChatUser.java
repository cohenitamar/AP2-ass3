package com.example.ap2ass3androidchat.entities;

public class ChatUser {


    public String getUsername() {
        return username;
    }

    public String getDisplayName() {
        return displayName;
    }


    public String getProfilePic() {
        return profilePic;
    }
    private String username;
    private String displayName;
    private String profilePic;




    public ChatUser(String username, String displayName, String profilePic) {
        this.username = username;
        this.displayName = displayName;
        this.profilePic = profilePic;
    }







}







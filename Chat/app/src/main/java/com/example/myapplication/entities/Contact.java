package com.example.myapplication.entities;

import com.example.myapplication.R;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Contact {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String contactID;
    private int pic;
    private String username;
    private String name;
    private String lastMessage;
    private String date;

    public Contact(String contactID, int pic, String username, String name,
                   String lastMessage, String date) {
        this.contactID = contactID;
        this.pic = pic;
        this.username = username;
        this.name = name;
        this.lastMessage = lastMessage;
        this.date = date;
    }

    public Contact() {
        this.pic = R.drawable.profilepic;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContactID() {
        return contactID;
    }

    public void setContactID(String contactID) {
        this.contactID = contactID;
    }

    public int getPic() {
        return pic;
    }

    public void setPic(int pic) {
        this.pic = pic;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

}

package com.example.myapplication.contacts;


import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.myapplication.entities.Contact;

@Database(entities = {Contact.class}, version = 1)
public abstract class ContactDB extends RoomDatabase {
    public abstract ContactsDao contactDao();
}

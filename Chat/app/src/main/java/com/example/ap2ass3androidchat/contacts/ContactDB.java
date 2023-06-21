package com.example.ap2ass3androidchat.contacts;


import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.ap2ass3androidchat.Converter;
import com.example.ap2ass3androidchat.entities.Contact;

@Database(entities = {Contact.class}, version = 1)
@TypeConverters(Converter.class)
public abstract class ContactDB extends RoomDatabase {
    public abstract ContactsDao contactDao();
}

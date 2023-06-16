package com.example.myapplication.contacts;


import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.myapplication.Converter;
import com.example.myapplication.entities.Contact;

@Database(entities = {Contact.class}, version = 1)
@TypeConverters(Converter.class)
public abstract class ContactDB extends RoomDatabase {
    public abstract ContactsDao contactDao();
}

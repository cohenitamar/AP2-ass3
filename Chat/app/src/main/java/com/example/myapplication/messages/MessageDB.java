package com.example.myapplication.messages;


import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.myapplication.Converter;
import com.example.myapplication.entities.Message;
import com.example.myapplication.entities.MessagesByID;

@Database(entities = {MessagesByID.class}, version = 1)
@TypeConverters(Converter.class)
public abstract class MessageDB extends RoomDatabase {
    public abstract MessagesDao messageDao();
}

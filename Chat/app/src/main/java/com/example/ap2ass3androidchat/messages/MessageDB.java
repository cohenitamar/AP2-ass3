package com.example.ap2ass3androidchat.messages;


import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.ap2ass3androidchat.assistingclasses.Converter;
import com.example.ap2ass3androidchat.entities.MessagesByID;

@Database(entities = {MessagesByID.class}, version = 1)
@TypeConverters(Converter.class)
public abstract class MessageDB extends RoomDatabase {
    public abstract MessagesDao messageDao();
}

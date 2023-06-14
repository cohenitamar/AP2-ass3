package com.example.myapplication.messages;


import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.myapplication.entities.Message;

@Database(entities = {Message.class}, version = 1)
public abstract class MessageDB extends RoomDatabase {
    public abstract MessagesDao messageDao();
}

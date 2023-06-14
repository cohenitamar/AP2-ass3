package com.example.myapplication;


import androidx.room.Database;

import com.example.myapplication.entities.Message;

@Database(entities = {Message.class},version = 1)
public class MessageDB  extends RoomDatabase{
}

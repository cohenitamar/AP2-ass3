package com.example.myapplication.messages;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.myapplication.entities.Message;

import java.util.List;

@Dao
public interface MessagesDao {


    @Query("SELECT * FROM message")
    List<Message> index();

    @Query("SELECT * FROM message WHERE id = :id")
    Message get(int id);

    @Insert
    void insert(Message... message);

    @Update
    void update(Message... message);

    @Delete
    void delete(Message... message);

}

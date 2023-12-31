package com.example.ap2ass3androidchat.messages;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.ap2ass3androidchat.entities.MessagesByID;

import java.util.List;

@Dao
public interface MessagesDao {


    @Query("SELECT * FROM messagesByID")
    List<MessagesByID> index();

    @Query("SELECT * FROM messagesByID WHERE chatID = :id")
    List<MessagesByID> getMsgByChat(String id);

    @Query("SELECT * FROM messagesByID WHERE id = :id")
    MessagesByID getMsgByID(String id);

    @Insert
    void insert(MessagesByID... message);

    @Update
    void update(MessagesByID... message);

    @Delete
    void delete(MessagesByID... message);

    @Query("DELETE FROM messagesByID")
    void deleteAll();


}

package com.example.myapplication;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.myapplication.entities.Contact;
import com.example.myapplication.entities.MessagesByID;
import com.example.myapplication.messages.MessageDB;
import com.example.myapplication.messages.MessagesDao;

import java.util.List;

public class MessageRepository {
    MessagesDao messagesDao;
    private MessageDB db;
    private MessageListData messageListData;
    private ChatsAPI api;

    public void setId(String id) {
        this.id = id;
    }

    String id;
    String token;

    public MessageRepository(String token) {
        this.token = token;
        this.db = SingletonDatabase.getMessageInstance();
        this.messagesDao = db.messageDao();
        this.messageListData = new MessageListData();
        this.api = new ChatsAPI();
    }

    class MessageListData extends MutableLiveData<List<MessagesByID>> {

        public MessageListData() {
            super();
            setValue(messagesDao.index());
        }

        @Override
        protected void onActive() {
            super.onActive();
            new Thread(() -> {
                api.getChatsByID(this, token, id);
            }).start();
        }
    }

    public LiveData<List<MessagesByID>> getAll() {
        return messageListData;
    }
}

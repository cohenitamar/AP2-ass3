package com.example.ap2ass3androidchat;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.ap2ass3androidchat.entities.MessagesByID;
import com.example.ap2ass3androidchat.messages.MessageDB;
import com.example.ap2ass3androidchat.messages.MessagesDao;

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

    String chatId;



    public MessageRepository(String token,String ChatId) {
        this.chatId = ChatId;
        this.token = token;
        this.db = SingletonDatabase.getMessageInstance();
        this.messagesDao = db.messageDao();
        this.messageListData = new MessageListData();
        this.api = new ChatsAPI();

    }

    class MessageListData extends MutableLiveData<List<MessagesByID>> {

        public MessageListData() {
            super();
            Log.e("chatID",chatId);
            setValue(messagesDao.getMsgByChat(chatId));
        }

        @Override
        protected void onActive() {
            super.onActive();
            new Thread(() -> {
                api.getChatsByID(this, token, id);
                List<MessagesByID> list = this.getValue();
            }).start();
        }
    }

    public LiveData<List<MessagesByID>> getAll() {
        return messageListData;
    }


    public void addMsg( String id, String msg){
        api.postMessages(this.messageListData,token,id,msg);
    }
    public void onReload(){
        messagesDao.deleteAll();
    }

    public void add(MessagesByID m){
        if(messagesDao.getMsgByID(m.getId()) != null)
            messagesDao.insert(m);
        List<MessagesByID> list = this.messageListData.getValue();
        list.add(m);
        this.messageListData.postValue(list);
    }
}

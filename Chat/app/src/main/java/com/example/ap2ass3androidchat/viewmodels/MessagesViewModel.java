package com.example.ap2ass3androidchat.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.ap2ass3androidchat.repositories.MessageRepository;
import com.example.ap2ass3androidchat.entities.MessagesByID;

import java.util.List;

public class MessagesViewModel extends ViewModel {

    public MessageRepository getmRepository() {
        return mRepository;
    }

    private MessageRepository mRepository;
    private LiveData<List<MessagesByID>> messages;




    public MessagesViewModel(String token,String chatId) {
        this.mRepository = new MessageRepository(token,chatId);
        this.messages = mRepository.getAll();

    }

    public LiveData<List<MessagesByID>> get() {
        return this.messages;
    }

    public void add(MessagesByID m) {
       this.mRepository.add(m);
    }
    public void addMsg( String id, String msg){
        mRepository.addMsg(id,msg);
    }

/*    public void delete(MessageByID m) {
       // this.mRepository.delete(c);
    }*/

    public void setId( String id){
        this.mRepository.setId(id);
    }

    public void reload() {
        this.mRepository.onReload();
    }
}

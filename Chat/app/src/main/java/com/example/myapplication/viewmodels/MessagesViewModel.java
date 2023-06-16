package com.example.myapplication.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.ContactRepository;
import com.example.myapplication.MessageRepository;
import com.example.myapplication.entities.Contact;
import com.example.myapplication.entities.MessagesByID;

import java.util.List;

public class MessagesViewModel extends ViewModel {

    public MessageRepository getmRepository() {
        return mRepository;
    }

    private MessageRepository mRepository;
    private LiveData<List<MessagesByID>> messages;


    public MessagesViewModel(String token) {
        this.mRepository = new MessageRepository(token);
        this.messages = mRepository.getAll();
        int a = 4;
        int b = 6;
    }

    public LiveData<List<MessagesByID>> get() {
        return this.messages;
    }

    public void add(Contact c) {
       // this.mRepository.add(c);
    }

    public void delete(Contact c) {
       // this.mRepository.delete(c);
    }

    public void reload() {
       // this.mRepository.reload();
    }

}

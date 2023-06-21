package com.example.ap2ass3androidchat;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.ap2ass3androidchat.contacts.ContactDB;
import com.example.ap2ass3androidchat.contacts.ContactsDao;
import com.example.ap2ass3androidchat.entities.Contact;

import java.util.List;

public class ContactRepository {
    ContactsDao contactsDao;
    private ContactDB db;
    private ContactListData contactListData;
    private ChatsAPI api;

    String token;

    public ContactRepository(String token) {
        this.token = token;
        this.db = SingletonDatabase.getContactInstance();
        this.contactsDao = db.contactDao();
        this.contactListData = new ContactListData();
        this.api = new ChatsAPI();
    }
    class ContactListData extends MutableLiveData<List<Contact>> {

        public ContactListData() {
            super();
            setValue(contactsDao.index());
        }

        @Override
        protected void onActive() {
            super.onActive();
            new Thread(() -> {
                api.getChats(this, token);
            }).start();
        }
    }

    public LiveData<List<Contact>> getAll() {
        return contactListData;
    }


    public void addContact(String username){
        api.postChats(token,username);

    }
    public void onReload(){
        new Thread(() -> {
            api.getChats(this.contactListData, token);
        }).start();
    }

}

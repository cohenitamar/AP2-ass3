package com.example.myapplication;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.myapplication.contacts.ContactDB;
import com.example.myapplication.contacts.ContactsDao;
import com.example.myapplication.entities.Contact;

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
                api.get(this, token);
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
        contactsDao.deleteAll();
    }

}

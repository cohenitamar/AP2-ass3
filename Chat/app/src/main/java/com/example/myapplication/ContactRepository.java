package com.example.myapplication;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.myapplication.contacts.ContactsDao;
import com.example.myapplication.entities.Contact;

import java.util.ArrayList;
import java.util.List;

public class ContactRepository {
    ContactsDao contactsDao;
    private ContactListData contactListData;
    private ChatsAPI api;

    public ContactRepository(ContactsDao contactsDao) {


        this.contactsDao = contactsDao;
        this.contactListData = new ContactListData();
        this.api = new ChatsAPI();
    }

    class ContactListData extends MutableLiveData<List<Contact>> {

        public ContactListData() {
            super();
            try {

                 List<Contact> c =new ArrayList<>();
                c.add(new Contact("a",1,"a","a","a","a"));

                setValue(c);
            } catch (Exception e) {
                Log.e("msggg", e.toString());
            }

        }

        @Override
        protected void onActive() {
            super.onActive();
//            new Thread(() -> {
//                contactListData.postValue(contactsDao.index());
//            }).start();

        }
    }

    public LiveData<List<Contact>> getAll() {
        return contactListData;
    }
}

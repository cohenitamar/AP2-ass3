package com.example.ap2ass3androidchat.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.ap2ass3androidchat.ContactRepository;
import com.example.ap2ass3androidchat.entities.Contact;

import java.util.List;

public class ContactsViewModel extends ViewModel {

    private ContactRepository mRepository;
    private LiveData<List<Contact>> contacts;

    public ContactsViewModel(String token) {
        this.mRepository = new ContactRepository(token);
        this.contacts = mRepository.getAll();
    }

    public LiveData<List<Contact>> get() {
        return this.contacts;
    }

    public void add(Contact c) {
        // this.mRepository.add(c);
    }

    public void addContact(String username) {
        mRepository.addContact(username);
    }

    public void delete(Contact c) {
        // this.mRepository.delete(c);
    }

    public void reload() {
        this.mRepository.onReload();
    }

}

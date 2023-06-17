package com.example.myapplication.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.ContactRepository;
import com.example.myapplication.entities.Contact;

import java.util.List;

public class ContactsViewModel extends ViewModel {

    private ContactRepository mRepository;
    private LiveData<List<Contact>> contacts;

    public ContactsViewModel(String token) {
        this.mRepository = new ContactRepository(token);
        this.contacts = mRepository.getAll();
        int a = 4;
        int b = 6;
    }

    public LiveData<List<Contact>> get() {
        return this.contacts;
    }

    public void add(Contact c) {
       // this.mRepository.add(c);
    }

    public void addContact (String username){
        mRepository.addContact(username);
    }

    public void delete(Contact c) {
       // this.mRepository.delete(c);
    }

    public void reload() {
       // this.mRepository.reload();
    }

}

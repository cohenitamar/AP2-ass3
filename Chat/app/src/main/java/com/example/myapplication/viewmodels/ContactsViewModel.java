package com.example.myapplication.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.entities.Contact;

import java.util.List;

public class ContactsViewModel extends ViewModel {

   // private ContactsRepository mRepository;
    private LiveData<List<Contact>> contacts;

    public ContactsViewModel() {
        //this.mRepository = new ContactsRepository();
        //this.contacts = this.mRepository.getAll();
    }

    public LiveData<List<Contact>> get() {
        return this.contacts;
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

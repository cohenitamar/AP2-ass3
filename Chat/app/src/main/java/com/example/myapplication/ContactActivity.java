package com.example.myapplication;

import com.example.myapplication.adapters.ContactListAdapter;
import com.example.myapplication.entities.Contact;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;


import java.util.ArrayList;
import java.util.List;

public class ContactActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView contactsList = findViewById(R.id.contactsList);
        final ContactListAdapter adapter = new ContactListAdapter(this);
        contactsList.setAdapter(adapter);
        contactsList.setLayoutManager(new LinearLayoutManager(this));



        List<Contact> contacts = new ArrayList<>();
        contacts.add(new Contact("afhadfg", R.drawable.profilepicture,
                "a", "Ori Arad", "Hello", "12/02/2021 14:23"));
        contacts.add(new Contact("afhadfg", R.drawable.profilepicture,
                "b", "Tal Gelerman", "Sup", "12/02/2021 18:23"));
        contacts.add(new Contact("afhadfg", R.drawable.profilepicture,
                "c", "Itamar Cohen", "helllooo", "30/02/2021 14:23"));
        adapter.setContacts(contacts);




    }
}
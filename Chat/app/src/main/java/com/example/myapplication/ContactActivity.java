package com.example.myapplication;

import com.example.myapplication.adapters.ContactListAdapter;
import com.example.myapplication.entities.Contact;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;


import java.util.ArrayList;
import java.util.List;

public class ContactActivity extends AppCompatActivity {
    ListView listView;
    ContactListAdapter adapter;

    List<Contact> contacts;
    private ContactsDao contactsDao;
    private ContactDB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_activity);

        ///TODO I DONT THINK WE SHOULD ALLOW MAIN THREAD QUERIES IDK?
        this.db = Room.databaseBuilder(getApplicationContext(), ContactDB.class,
                "ContactDB").allowMainThreadQueries().build();

        this.contactsDao = db.contactDao();

        this.contacts = new ArrayList<>();
/*        contacts.add(new Contact("afhadfg", R.drawable.profilepicture,
                "a", "Ori Arad", "Hello", "12/02/2021 14:23"));
        contacts.add(new Contact("afhadfg", R.drawable.profilepicture,
                "b", "Tal Gelerman", "Sup", "12/02/2021 18:23"));
        contacts.add(new Contact("afhadfg", R.drawable.profilepicture,
                "c", "Itamar Cohen", "helllooo", "30/02/2021 14:23"));*/

        listView = findViewById(R.id.contactsList);
        adapter = new ContactListAdapter(getApplicationContext(), (ArrayList<Contact>) this.contacts);

        listView.setAdapter(adapter);
        listView.setClickable(true);

        Button addContact = findViewById(R.id.addcontact);
        addContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ContactActivity.this, AddContact.class);
                startActivity(intent);
            }
        });


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Contact clickedContact = contacts.get(position);
                Intent intent = new Intent(ContactActivity.this, MessageActivity.class);
                intent.putExtra("username", clickedContact.getName());
                intent.putExtra("pic", clickedContact.getPic());
                startActivity(intent);
            }

        });

    }


    @Override
    protected void onResume() {
        super.onResume();
        this.contacts.clear();
        this.contacts.addAll(this.contactsDao.index());
        this.adapter.notifyDataSetChanged();

    }

}
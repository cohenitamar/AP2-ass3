package com.example.myapplication.contacts;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.room.Room;

import com.example.myapplication.R;
import com.example.myapplication.adapters.ContactListAdapter;
import com.example.myapplication.entities.Contact;
import com.example.myapplication.messages.MessageActivity;
import com.example.myapplication.viewmodels.ContactsViewModel;

import java.util.ArrayList;
import java.util.List;

public class ContactActivity extends AppCompatActivity {
    ListView listView;
    ContactListAdapter adapter;

    List<Contact> contacts;
    private ContactsDao contactsDao;
    private ContactDB db;


    private ContactsViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_activity);

        ///TODO I DONT THINK WE SHOULD ALLOW MAIN THREAD QUERIES IDK?
        this.db = Room.databaseBuilder(getApplicationContext(), ContactDB.class,
                "ContactDB").allowMainThreadQueries().build();

        this.contactsDao = db.contactDao();

        this.viewModel = new ContactsViewModel(contactsDao);

        this.contacts = new ArrayList<>();


        listView = findViewById(R.id.contactsList);
        adapter = new ContactListAdapter(getApplicationContext(), new ArrayList<Contact>());

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

        viewModel.get().observe(this, new Observer<List<Contact>>() {
            @Override
            public void onChanged(List<Contact> contact) {
                Log.d("ori","gdgdd");
                List<Contact> contac =contact;
               adapter.setContacts(contact);

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
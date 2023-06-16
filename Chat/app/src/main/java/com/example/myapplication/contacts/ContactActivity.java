package com.example.myapplication.contacts;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;

import com.example.myapplication.ChatsAPI;
import com.example.myapplication.R;
import com.example.myapplication.SingletonDatabase;
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

    String token;
    private ContactsViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_activity);

        Intent activityIntent = getIntent();

        ChatsAPI chatsAPI = new ChatsAPI();
        if (activityIntent != null) {
            token = activityIntent.getStringExtra("token");
        }
        this.db = SingletonDatabase.getContactInstance(getApplicationContext());

        this.contactsDao = db.contactDao();

        this.viewModel = new ContactsViewModel(token);

        this.contacts = new ArrayList<>();


        listView = findViewById(R.id.contactsList);
        adapter = new ContactListAdapter(getApplicationContext(), (ArrayList<Contact>) this.contacts);

           viewModel.get().observe(this, contacts -> {
               adapter.setContacts(contacts);
        });


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
                intent.putExtra("chatID" , clickedContact.getId());
                intent.putExtra("username", clickedContact.getUser().getDisplayName());
                intent.putExtra("token", token);
                intent.putExtra("pic", R.drawable.profilepicture);
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
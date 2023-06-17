package com.example.myapplication.contacts;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.SingletonDatabase;
import com.example.myapplication.adapters.ContactListAdapter;
import com.example.myapplication.entities.Contact;
import com.example.myapplication.viewmodels.ContactsViewModel;

import java.util.List;

public class AddContact extends AppCompatActivity {

    ListView listView;
    ContactListAdapter adapter;

    List<Contact> contacts;
    private ContactsDao contactsDao;
    private ContactDB db;

    private String token;


    private ContactsViewModel viewModel;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_contact_activity);

        ///TODO I DONT THINK WE SHOULD ALLOW MAIN THREAD QUERIES IDK?
        this.db = SingletonDatabase.getContactInstance();

        this.contactsDao = db.contactDao();
        Intent activityIntent = getIntent();


        if (activityIntent != null) {
            token = activityIntent.getStringExtra("token");
        }
        this.viewModel = new ContactsViewModel(token);


        Button btnSave = findViewById(R.id.addcontactfinal);
        btnSave.setOnClickListener(view -> {
            EditText user = findViewById(R.id.contactinput);
            viewModel.addContact(user.getText().toString());


/*

            Contact contact = new Contact(new ChatUser(etItem.getText().toString(),
                    Integer.toString((int) (Math.random() * 9000 + 1000)),
                    "5"), new ChatMessage(Integer.toString((int) (Math.random() * 100 + 1)),
                    Integer.toString((int) (Math.random() * 9000000 + 1000000))));
            contactsDao.insert(contact);

            */

            finish();
        });


    }
}

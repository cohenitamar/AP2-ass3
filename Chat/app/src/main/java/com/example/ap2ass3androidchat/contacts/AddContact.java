package com.example.ap2ass3androidchat.contacts;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ap2ass3androidchat.R;
import com.example.ap2ass3androidchat.SingletonChatsAPI;
import com.example.ap2ass3androidchat.SingletonDatabase;
import com.example.ap2ass3androidchat.adapters.ContactListAdapter;
import com.example.ap2ass3androidchat.entities.Contact;
import com.example.ap2ass3androidchat.viewmodels.ContactsViewModel;

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

        SingletonChatsAPI.getSingletonChatsAPIInstance().observe(this, str -> {
            if (str.equals("ok"))
                finish();
            else {
                Toast.makeText(getApplicationContext(), "Invalid username",
                        Toast.LENGTH_SHORT).show();
            }
        });

        Button btnSave = findViewById(R.id.addcontactfinal);
        btnSave.setOnClickListener(view -> {
            EditText user = findViewById(R.id.contactinput);
            viewModel.addContact(user.getText().toString());
        });


    }
}

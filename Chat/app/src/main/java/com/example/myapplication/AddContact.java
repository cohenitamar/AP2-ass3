package com.example.myapplication;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.example.myapplication.adapters.ContactListAdapter;
import com.example.myapplication.entities.Contact;

import java.util.List;

public class AddContact extends AppCompatActivity {

    ListView listView;
    ContactListAdapter adapter;

    List<Contact> contacts;
    private ContactsDao contactsDao;
    private ContactDB db;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_contact_activity);

        ///TODO I DONT THINK WE SHOULD ALLOW MAIN THREAD QUERIES IDK?
        this.db = Room.databaseBuilder(getApplicationContext(), ContactDB.class,
                "ContactDB").allowMainThreadQueries().build();

        this.contactsDao = db.contactDao();

/*        contacts.add(new Contact("afhadfg", R.drawable.profilepicture,
                "a", "Ori Arad", "Hello", "12/02/2021 14:23"));*/


  /*      this.contactID = contactID;
        this.pic = pic;
        this.username = username;
        this.name = name;
        this.lastMessage = lastMessage;
        this.date = date;*/

        Button btnSave = findViewById(R.id.addcontactfinal);
        btnSave.setOnClickListener(view -> {
            EditText etItem = findViewById(R.id.contactinput);
            Contact contact = new Contact("asdfgasdfg", R.drawable.profilepicture,
                    etItem.getText().toString(), Integer.toString((int)(Math.random() * 9000 +
                    1000)), Integer.toString((int)(Math.random() * 9000000 +
                    1000000)),
                    Integer.toString((int)(Math.random() * 100 + 1)));
            contactsDao.insert(contact);

            finish();
        });


    }
}

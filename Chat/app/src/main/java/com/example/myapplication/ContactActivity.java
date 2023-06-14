package com.example.myapplication;

import com.example.myapplication.adapters.ContactListAdapter;
import com.example.myapplication.entities.Contact;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.List;

public class ContactActivity extends AppCompatActivity {
    ListView listView;
    ContactListAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<Contact> contacts = new ArrayList<>();
        contacts.add(new Contact("afhadfg", R.drawable.profilepicture,
                "a", "Ori Arad", "Hello", "12/02/2021 14:23"));
        contacts.add(new Contact("afhadfg", R.drawable.profilepicture,
                "b", "Tal Gelerman", "Sup", "12/02/2021 18:23"));
        contacts.add(new Contact("afhadfg", R.drawable.profilepicture,
                "c", "Itamar Cohen", "helllooo", "30/02/2021 14:23"));

        listView = findViewById(R.id.contactsList);
        adapter = new ContactListAdapter(getApplicationContext(), contacts);

        listView.setAdapter(adapter);
        listView.setClickable(true);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Contact clickedContact = contacts.get(position);
                Intent intent = new Intent(ContactActivity.this,MessageActivity.class);
                intent.putExtra("username",clickedContact.getName());
                intent.putExtra("pic",clickedContact.getPic());
                startActivity(intent);
            }

        });







    }
}
package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import com.example.myapplication.adapters.MessageListAdapter;
import com.example.myapplication.entities.Message;
import com.example.myapplication.adapters.ContactListAdapter;
import com.example.myapplication.entities.Contact;

import java.util.ArrayList;

public class MessageActivity extends AppCompatActivity {
    ListView listView;
    MessageListAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

  /*        public Message(int chatID, String sender,
                String receiver, String date, String content) {*/


        ArrayList<Message> messages = new ArrayList<>();
        messages.add(new Message("adfghasdfgh", "ME", "ADFGAD", "12/12/2023",  "hello1"));
        messages.add(new Message("adfghasdfgh", "SDFGH", "ADFGAD", "12/12/2023",  "hello2"));
        messages.add(new Message("adfghasdfgh", "ME", "ADFGAD", "12/12/2023",  "hello3"));
        messages.add(new Message("adfghasdfgh", "DFGH", "ADFGAD", "12/12/2023",  "hello4"));
        messages.add(new Message("adfghasdfgh", "ME", "ADFGAD", "12/12/2023",  "hello5"));



        listView = findViewById(R.id.messageList);
        adapter = new MessageListAdapter(getApplicationContext(), messages);
        listView.setClickable(false);
        listView.setAdapter(adapter);




    }
}
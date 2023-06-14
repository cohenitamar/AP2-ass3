package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.myapplication.adapters.MessageListAdapter;
import com.example.myapplication.entities.Message;
import com.example.myapplication.adapters.ContactListAdapter;
import com.example.myapplication.entities.Contact;
import com.google.android.material.imageview.ShapeableImageView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

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
        messages.add(new Message("adfghasdfgh", "ME", "ADFGAD", "12/12/2023",  "hello5"));
        messages.add(new Message("adfghasdfgh", "ME", "ADFGAD", "12/12/2023",  "hello5"));
        messages.add(new Message("adfghasdfgh", "ME", "ADFGAD", "12/12/2023",  "hello5"));
        messages.add(new Message("adfghasdfgh", "ME", "ADFGAD", "12/12/2023",  "hello5"));
        messages.add(new Message("adfghasdfgh", "ME", "ADFGAD", "12/12/2023",  "hello5"));



        listView = findViewById(R.id.messageList);
        adapter = new MessageListAdapter(getApplicationContext(), messages);
        listView.setClickable(false);
        listView.setAdapter(adapter);


        Intent activityIntent = getIntent();
        if (activityIntent != null) {
            String userName = activityIntent.getStringExtra("username");
            int profilePicture = activityIntent.getIntExtra("pic", 1);
            TextView user = findViewById(R.id.text_contactname);
            ShapeableImageView shape =findViewById(R.id.imageView);
            shape.setImageResource(profilePicture);
            user.setText(userName);

        }

        Button btn = findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText text = findViewById(R.id.text_messageinput);
                if(text.getText().toString().equals("")){
                    text.setText("");
                }
                else {
                    Date currentDateTime = new Date();
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                    String formattedDateTime = dateFormat.format(currentDateTime);
                    messages.add(new Message("adfghasdfgh", "ME", "ADFGAD", formattedDateTime.toString(), text.getText().toString()));
                    adapter.notifyDataSetChanged();
                }

            }
        });

    }

    @Override
    protected void onResume(){
        super.onResume();

    }

}
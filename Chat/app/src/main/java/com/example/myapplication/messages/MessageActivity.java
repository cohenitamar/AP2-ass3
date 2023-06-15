package com.example.myapplication.messages;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.adapters.MessageListAdapter;
import com.example.myapplication.entities.Message;
import com.google.android.material.imageview.ShapeableImageView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MessageActivity extends AppCompatActivity {
    ListView listView;

    List<Message> messages;
    MessageListAdapter adapter;

    private MessagesDao messagesDao;
    private MessageDB db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_activity);

        ///TODO I DONT THINK WE SHOULD ALLOW MAIN THREAD QUERIES IDK?
        this.db = Room.databaseBuilder(getApplicationContext(), MessageDB.class,
                "MessageDB").allowMainThreadQueries().build();

        this.messagesDao = db.messageDao();


   /*     ArrayList<Message> messages = new ArrayList<>();
        messages.add(new Message("adfghasdfgh", "ME", "ADFGAD", "12/12/2023",  "hello1"));
        messages.add(new Message("adfghasdfgh", "SDFGH", "ADFGAD", "12/12/2023",  "hello2"));
        messages.add(new Message("adfghasdfgh", "ME", "ADFGAD", "12/12/2023",  "hello3"));
        messages.add(new Message("adfghasdfgh", "DFGH", "ADFGAD", "12/12/2023",  "hello4"));
        messages.add(new Message("adfghasdfgh", "ME", "ADFGAD", "12/12/2023",  "hello5"));
        messages.add(new Message("adfghasdfgh", "ME", "ADFGAD", "12/12/2023",  "hello5"));
        messages.add(new Message("adfghasdfgh", "ME", "ADFGAD", "12/12/2023",  "hello5"));
        messages.add(new Message("adfghasdfgh", "ME", "ADFGAD", "12/12/2023",  "hello5"));
        messages.add(new Message("adfghasdfgh", "ME", "ADFGAD", "12/12/2023",  "hello5"));
        messages.add(new Message("adfghasdfgh", "ME", "ADFGAD", "12/12/2023",  "hello5"));*/


        this.messages = messagesDao.index();
        listView = findViewById(R.id.messageList);
        adapter = new MessageListAdapter(getApplicationContext(), (ArrayList<Message>) this.messages);
        listView.setClickable(false);
        listView.setAdapter(adapter);
        listView.setSelection(adapter.getCount() - 1);


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
                    Message msg = new Message("adfghasdfgh", "ME", "ADFGAD", formattedDateTime.toString(), text.getText().toString());
                    /*
                    messages.add(new Message("adfghasdfgh", "ME", "ADFGAD", formattedDateTime.toString(), text.getText().toString()));
*/
                    adapter.notifyDataSetChanged();
                    messagesDao.insert(msg);
                    text.setText("");
                    messages.clear();
                    messages.addAll(messagesDao.index());
                    listView.smoothScrollToPosition(adapter.getCount() - 1);
                }

            }
        });

    }

    @Override
    protected void onResume(){
        super.onResume();
/*        this.messages.clear();
        this.messages.addAll(this.messagesDao.index());
        this.adapter.notifyDataSetChanged();*/

    }

}
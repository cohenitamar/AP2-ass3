package com.example.ap2ass3androidchat.messages;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;

import com.example.ap2ass3androidchat.R;
import com.example.ap2ass3androidchat.SingletonDatabase;
import com.example.ap2ass3androidchat.SingletonFirebase;
import com.example.ap2ass3androidchat.adapters.MessageListAdapter;
import com.example.ap2ass3androidchat.entities.MessagesByID;
import com.example.ap2ass3androidchat.viewmodels.MessagesViewModel;
import com.google.android.material.imageview.ShapeableImageView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MessageActivity extends AppCompatActivity {
    ListView listView;

    List<MessagesByID> messages;
    MessageListAdapter adapter;

    private MessagesViewModel viewModel;


    private String chatID;
    private MessagesDao messagesDao;
    private MessageDB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_activity);

        this.db = SingletonDatabase.getMessageInstance(getApplicationContext());

        this.messagesDao = db.messageDao();

        Intent activityIntent = getIntent();
        String token = null;

        String username = null;

        if (activityIntent != null) {
            token = activityIntent.getStringExtra("token");
            chatID = activityIntent.getStringExtra("chatID");
            username = activityIntent.getStringExtra("myUsername");
        }

        this.viewModel = new MessagesViewModel(token, chatID);
        this.viewModel.getmRepository().setId(chatID);


        viewModel.get().observe(this, messages -> {
            adapter.setMessages(messages);
            listView.smoothScrollToPosition(adapter.getCount() - 1);
        });

        MutableLiveData<MessagesByID> messagesFirebase = SingletonFirebase.getFirebaseMessageInstance();

        messagesFirebase.observe(this, messages -> {
                viewModel.add(messages);
        });

        this.messages = new ArrayList<>();
        listView = findViewById(R.id.messageList);
        adapter = new MessageListAdapter(getApplicationContext(), (ArrayList<MessagesByID>) this.messages, username);
        listView.setClickable(false);
        listView.setAdapter(adapter);
        listView.setSelection(adapter.getCount() - 1);


        if (activityIntent != null) {
            String userName = activityIntent.getStringExtra("username");
            int profilePicture = activityIntent.getIntExtra("pic", 1);
            TextView user = findViewById(R.id.text_contactname);
            ShapeableImageView shape = findViewById(R.id.myprofileimage);
            shape.setImageResource(profilePicture);
            user.setText(userName);

        }
        Button btn = findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText text = findViewById(R.id.text_messageinput);
                if (text.getText().toString().equals("")) {
                    text.setText("");
                } else {
                    Date currentDateTime = new Date();
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                    String formattedDateTime = dateFormat.format(currentDateTime);
                    viewModel.addMsg(chatID, text.getText().toString());
                    text.setText("");
                                 /*  messagesDao.insert(msg);
                    text.setText("");
                    messages.clear();
                    messages.addAll(messagesDao.index());*/

                }

            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        this.messages.clear();
        this.messages.addAll(this.messagesDao.getMsgByChat(chatID));
        this.adapter.notifyDataSetChanged();

    }

}
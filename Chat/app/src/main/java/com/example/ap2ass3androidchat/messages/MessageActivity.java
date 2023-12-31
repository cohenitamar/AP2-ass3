package com.example.ap2ass3androidchat.messages;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;

import com.example.ap2ass3androidchat.R;
import com.example.ap2ass3androidchat.singleton.SingletonDatabase;
import com.example.ap2ass3androidchat.singleton.SingletonFirebase;
import com.example.ap2ass3androidchat.adapters.MessageListAdapter;
import com.example.ap2ass3androidchat.entities.MessagesByID;
import com.example.ap2ass3androidchat.viewmodels.MessagesViewModel;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.ArrayList;
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
            listView.setSelection(adapter.getCount() - 1);
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
            String encodedProfilePic = activityIntent.getStringExtra("pic");
            TextView user = findViewById(R.id.text_contactname);
            ShapeableImageView shape = findViewById(R.id.myprofileimage);
            if (encodedProfilePic.equals("/static/media/easter_egg.d0d1d09d533aee0fddf4.png")) {
                shape.setImageResource(R.drawable.easter_egg);
            } else {
                encodedProfilePic = encodedProfilePic
                        .replaceFirst("^data:image\\/.+;base64,", "");
                byte[] decodedBytes = Base64.decode(encodedProfilePic, Base64.DEFAULT);
                Bitmap decodedBitmap = BitmapFactory
                        .decodeByteArray(decodedBytes, 0, decodedBytes.length);
                shape.setImageBitmap(decodedBitmap);
            }
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
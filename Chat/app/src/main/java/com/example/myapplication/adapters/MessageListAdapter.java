package com.example.myapplication.adapters;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.entities.Contact;
import com.example.myapplication.entities.Message;
import com.google.android.material.imageview.ShapeableImageView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;

public class MessageListAdapter extends ArrayAdapter<Message> {
    LayoutInflater inflater;
    String me;

    public MessageListAdapter(Context context, ArrayList<Message> messageList) {
        super(context, 0, messageList);
        this.inflater = LayoutInflater.from(context);
        this.me = "ME";
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @Nullable ViewGroup parent) {
        Message message = getItem(position);
        TextView content = null, date = null;
        if (convertView == null) {
            if (this.me.equals(message.getSender())) {
                convertView = inflater.inflate(R.layout.message_me, parent, false);
                content = convertView.findViewById(R.id.messageMeText);
                date = convertView.findViewById(R.id.messageMeDate);
            } else {
                convertView = inflater.inflate(R.layout.message_him, parent, false);
                content = convertView.findViewById(R.id.messageHimText);
                date = convertView.findViewById(R.id.messageHimDate);
            }
        }
        content.setText(message.getContent());
        date.setText(message.getDate());

        return convertView;




    }


}

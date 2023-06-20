package com.example.myapplication.adapters;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;

import com.example.myapplication.R;
import com.example.myapplication.entities.MessagesByID;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class MessageListAdapter extends ArrayAdapter<MessagesByID> {
    LayoutInflater inflater;
    String me;

    public MessageListAdapter(Context context, ArrayList<MessagesByID> messageList, String username) {
        super(context, 0, messageList);
        this.inflater = LayoutInflater.from(context);
        this.me = username;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @Nullable ViewGroup parent) {
        MessagesByID message = getItem(position);
        TextView content = null, date = null;

        if (this.me.equals(message.getSender().getUsername())) {
            convertView = inflater.inflate(R.layout.message_me, parent, false);
            content = convertView.findViewById(R.id.messageMeText);
            date = convertView.findViewById(R.id.messageMeDate);
        } else {
            convertView = inflater.inflate(R.layout.message_him, parent, false);
            content = convertView.findViewById(R.id.messageHimText);
            date = convertView.findViewById(R.id.messageHimDate);
        }

        content.setText(message.getContent());
        date.setText(message.getCreated());

        int currentNightMode = AppCompatDelegate.getDefaultNightMode();

        if (currentNightMode == AppCompatDelegate.MODE_NIGHT_YES) {
            date.setTextColor(Color.parseColor("#ffffff"));
        } else {
            date.setTextColor(Color.parseColor("#000000"));
        }



        return convertView;

    }


    public void setMessages(List<MessagesByID> messages) {
        this.clear();
        if (messages != null) {
            addAll(messages);
        }
        notifyDataSetChanged();
    }

    public static String formatDate(String dateString) {
        OffsetDateTime offsetDateTime = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            offsetDateTime = OffsetDateTime.parse(dateString);
        }
        DateTimeFormatter formatter = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
            return offsetDateTime.format(formatter);
        }
        return dateString;
    }
}



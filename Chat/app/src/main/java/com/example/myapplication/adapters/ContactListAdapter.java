package com.example.myapplication.adapters;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.myapplication.R;
import com.example.myapplication.entities.Contact;

import java.util.ArrayList;
import java.util.List;

public class ContactListAdapter extends ArrayAdapter<Contact> {

    LayoutInflater inflater;


    public ContactListAdapter(Context context, ArrayList<Contact> contactList) {
        super(context, 0, contactList);
        this.inflater = LayoutInflater.from(context);

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Contact contact = getItem(position);

        convertView = inflater.inflate(R.layout.contact, parent, false);

        ImageView imageView = convertView.findViewById(R.id.contactProfilePic);
        TextView userName = convertView.findViewById(R.id.contactName);
        TextView lastMsg = convertView.findViewById(R.id.contactLastMessage);
        TextView time = convertView.findViewById(R.id.contactLMDate);


        String encodedProfilePic = contact.getUser().getProfilePic();
        encodedProfilePic = encodedProfilePic
                .replaceFirst("^data:image\\/.+;base64,", "");
        byte[] decodedBytes = Base64.decode(encodedProfilePic, Base64.DEFAULT);
        Bitmap decodedBitmap = BitmapFactory
                .decodeByteArray(decodedBytes, 0, decodedBytes.length);
        imageView.setImageBitmap(decodedBitmap);


        userName.setText(contact.getUser().getDisplayName());
        String lm = "";
        String created = "";
        if (contact.getLastMessage() != null) {
            if (contact.getLastMessage().getContent() != null)
                lm = contact.getLastMessage().getContent();
            if (contact.getLastMessage().getCreated() != null)
                created = contact.getLastMessage().getCreated();
        }
        lastMsg.setText(lm);
        time.setText(created);

        return convertView;
    }

    public void setContacts(List<Contact> contacts) {
        this.clear();
        if (contacts != null) {
            addAll(contacts);
        }
        notifyDataSetChanged();
    }
}




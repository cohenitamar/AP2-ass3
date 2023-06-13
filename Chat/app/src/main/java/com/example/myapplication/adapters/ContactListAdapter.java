package com.example.myapplication.adapters;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.entities.Contact;
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

public class ContactListAdapter extends ArrayAdapter<Contact> {

    LayoutInflater inflater;






    public ContactListAdapter(Context context, ArrayList<Contact> contactList) {
        super(context, R.layout.custom_list_item, contactList);
        this.inflater = LayoutInflater.from(context);

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Contact contact = getItem(position);

            convertView = inflater.inflate(R.layout.custom_list_item, parent, false);

        ImageView imageView = convertView.findViewById(R.id.contactProfilePic);
        TextView userName = convertView.findViewById(R.id.contactName);
        TextView lastMsg = convertView.findViewById(R.id.contactLastMessage);
        TextView time = convertView.findViewById(R.id.contactLMDate);

        imageView.setImageResource(contact.getPic());
        userName.setText(contact.getName());
        lastMsg.setText(contact.getLastMessage());
        time.setText(contact.getDate());

        return convertView;
    }
}




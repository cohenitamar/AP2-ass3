package com.example.myapplication.adapters;


import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.entities.Contact;
import com.google.android.material.imageview.ShapeableImageView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class ContactListAdapter extends RecyclerView.Adapter<ContactListAdapter.ContactsViewHolder> {



    // Member variable for the listener


    class ContactsViewHolder extends RecyclerView.ViewHolder {
        private final ShapeableImageView profilePic;
        private final TextView name;
        private final TextView date;
        private final TextView lastMessage;


        private ContactsViewHolder(View itemView) {
            super(itemView);
            this.profilePic = itemView.findViewById(R.id.contactProfilePic);
            this.name = itemView.findViewById(R.id.contactName);
            this.date = itemView.findViewById(R.id.contactLMDate);
            this.lastMessage = itemView.findViewById(R.id.contactLastMessage);
        }
    }



    private final LayoutInflater mInflater;
    private List<Contact> contacts;

    public ContactListAdapter(Context context) {
        this.mInflater = LayoutInflater.from(context);
    }

    @Override
    public ContactsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.custom_list_item, parent, false);
        return new ContactsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ContactsViewHolder holder, int pos) {
        if (this.contacts != null) {
            final Contact current = this.contacts.get(pos);
            holder.lastMessage.setText(current.getLastMessage());
            holder.lastMessage.setText(current.getDate());
            holder.name.setText(current.getName());
            holder.profilePic.setImageResource(current.getPic());

        }
    }

    public void setContacts(List<Contact> s) {
        this.contacts = s;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (this.contacts != null) {
            return this.contacts.size();
        } else {
            return 0;
        }
    }

    public List<Contact> getContacts() {
        return this.contacts;
    }

}

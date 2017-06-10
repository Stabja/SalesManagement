package com.stabstudio.salesmgmt.adapters;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.stabstudio.salesmgmt.MainActivity;
import com.stabstudio.salesmgmt.Modules.Contacts.ReadContactActivity;
import com.stabstudio.salesmgmt.R;
import com.stabstudio.salesmgmt.models.Contact;

import java.util.ArrayList;

public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ViewHolder>{

    private Activity activity;
    private ArrayList<Contact> contactsList;

    public ContactsAdapter(Activity activity){
        this.activity = activity;
        this.contactsList = MainActivity.contactsList;
    }

    @Override
    public ContactsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View vi = LayoutInflater.from(parent.getContext()).inflate(R.layout.contacts_list_item, parent, false);
        return new ViewHolder(vi);
    }

    @Override
    public void onBindViewHolder(ContactsAdapter.ViewHolder holder, final int position) {
        String name = contactsList.get(position).getFirstName() + " " + contactsList.get(position).getLastName();
        holder.contactName.setText(name);
        holder.contactNumber.setText(contactsList.get(position).getMobile());
        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, ReadContactActivity.class);
                intent.putExtra("contactId", contactsList.get(position).getId());
                activity.startActivity(intent);
                //activity.overridePendingTransition(R.anim.slide_in_up, R.anim.still);
            }
        });
    }

    @Override
    public int getItemCount() {
        return contactsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ViewGroup parent;
        private TextView contactName;
        private TextView contactNumber;
        public ViewHolder(View itemView) {
            super(itemView);
            parent = (LinearLayout) itemView.findViewById(R.id.contact_parent);
            contactName = (TextView) itemView.findViewById(R.id.contact_name);
            contactNumber = (TextView) itemView.findViewById(R.id.contact_number);
        }
    }
}

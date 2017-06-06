package com.stabstudio.drawernavig.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.stabstudio.drawernavig.MainActivity;
import com.stabstudio.drawernavig.Modules.Contacts.ReadContactActivity;
import com.stabstudio.drawernavig.Modules.Leads.ReadLeadActivity;
import com.stabstudio.drawernavig.R;
import com.stabstudio.drawernavig.models.Lead;

import java.util.ArrayList;

import static com.stabstudio.drawernavig.MainActivity.contactsList;

public class LeadsAdapter extends RecyclerView.Adapter<LeadsAdapter.ViewHolder>{

    private Activity activity;
    private ArrayList<Lead> leadsList;

    public LeadsAdapter(Activity activity){
        this.activity = activity;
        this.leadsList = MainActivity.leadsList;
    }

    @Override
    public LeadsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View vi = LayoutInflater.from(parent.getContext()).inflate(R.layout.leads_list_item, parent, false);
        return new ViewHolder(vi);
    }

    @Override
    public void onBindViewHolder(LeadsAdapter.ViewHolder holder, final int position) {
        String name = leadsList.get(position).getFirstName() + " " + leadsList.get(position).getLastName();
        holder.leadName.setText(name);
        holder.leadSource.setText(leadsList.get(position).getLeadSource());
        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, ReadLeadActivity.class);
                intent.putExtra("leadsId", leadsList.get(position).getId());
                activity.startActivity(intent);
                activity.overridePendingTransition(R.anim.slide_in_up, R.anim.still);
            }
        });
    }

    @Override
    public int getItemCount() {
        return leadsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ViewGroup parent;
        private TextView leadName;
        private TextView leadSource;
        public ViewHolder(View itemView) {
            super(itemView);
            parent = (LinearLayout) itemView.findViewById(R.id.lead_parent);
            leadName = (TextView) itemView.findViewById(R.id.lead_name);
            leadSource = (TextView) itemView.findViewById(R.id.lead_source);
        }
    }
}

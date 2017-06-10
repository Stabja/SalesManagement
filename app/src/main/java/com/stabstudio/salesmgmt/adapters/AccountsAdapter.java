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
import com.stabstudio.salesmgmt.models.Account;

import java.util.ArrayList;

import com.stabstudio.salesmgmt.R;
import com.stabstudio.salesmgmt.Modules.Accounts.ReadAccountActivity;

public class AccountsAdapter extends RecyclerView.Adapter<AccountsAdapter.ViewHolder>{

    private Activity activity;
    private ArrayList<Account> accountsList;

    public AccountsAdapter(Activity activity){
        this.activity = activity;
        this.accountsList = MainActivity.accountsList;
    }

    @Override
    public AccountsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View vi = LayoutInflater.from(parent.getContext()).inflate(R.layout.accounts_list_item, parent, false);
        return new ViewHolder(vi);
    }

    @Override
    public void onBindViewHolder(AccountsAdapter.ViewHolder holder, final int position) {
        String name = accountsList.get(position).getFirstName() + " " + accountsList.get(position).getLastName();
        holder.accountName.setText(name);
        holder.accountBody.setText(accountsList.get(position).getAccountName());
        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, ReadAccountActivity.class);
                intent.putExtra("accountId", accountsList.get(position).getId());
                activity.startActivity(intent);
                //activity.overridePendingTransition(R.anim.slide_in_up, R.anim.still);
            }
        });
    }

    @Override
    public int getItemCount() {
        return accountsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ViewGroup parent;
        private TextView accountName;
        private TextView accountBody;
        public ViewHolder(View itemView) {
            super(itemView);
            parent = (LinearLayout) itemView.findViewById(R.id.account_parent);
            accountName = (TextView) itemView.findViewById(R.id.account_name);
            accountBody = (TextView) itemView.findViewById(R.id.comment_body);
        }
    }
}

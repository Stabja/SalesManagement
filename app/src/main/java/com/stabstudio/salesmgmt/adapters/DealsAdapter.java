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
import com.stabstudio.salesmgmt.Modules.Deals.ReadDealActivity;
import com.stabstudio.salesmgmt.R;
import com.stabstudio.salesmgmt.models.Deal;

import java.util.ArrayList;

public class DealsAdapter extends RecyclerView.Adapter<DealsAdapter.ViewHolder>{

    private Activity activity;
    private ArrayList<Deal> dealsList;

    public DealsAdapter(Activity activity){
        this.activity = activity;
        this.dealsList = MainActivity.dealsList;
    }

    @Override
    public DealsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View vi = LayoutInflater.from(parent.getContext()).inflate(R.layout.deals_list_item, parent, false);
        return new ViewHolder(vi);
    }

    @Override
    public void onBindViewHolder(DealsAdapter.ViewHolder holder, final int position) {
        holder.dealName.setText(dealsList.get(position).getDealName());
        holder.dealStage.setText(dealsList.get(position).getStage());
        holder.dealAmount.setText(dealsList.get(position).getAmount());
        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, ReadDealActivity.class);
                intent.putExtra("dealId", dealsList.get(position).getId());
                activity.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dealsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ViewGroup parent;
        private TextView dealName;
        private TextView dealStage;
        private TextView dealAmount;

        public ViewHolder(View itemView) {
            super(itemView);
            parent = (LinearLayout) itemView.findViewById(R.id.deal_parent);
            dealName = (TextView) itemView.findViewById(R.id.deal_name);
            dealStage = (TextView) itemView.findViewById(R.id.deal_stage);
            dealAmount = (TextView) itemView.findViewById(R.id.deal_amount);
        }
    }
}

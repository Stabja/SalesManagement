package com.stabstudio.drawernavig.adapters;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.stabstudio.drawernavig.MainActivity;
import com.stabstudio.drawernavig.R;
import com.stabstudio.drawernavig.models.Deal;

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
    public void onBindViewHolder(DealsAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return dealsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}

package com.stabstudio.drawernavig.adapters;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.stabstudio.drawernavig.MainActivity;
import com.stabstudio.drawernavig.R;
import com.stabstudio.drawernavig.models.Feed;

import java.util.ArrayList;

public class FeedsAdapter extends RecyclerView.Adapter<FeedsAdapter.ViewHolder>{

    private Activity activity;
    private ArrayList<Feed> feedsList;

    public FeedsAdapter(Activity activity){
        this.activity = activity;
        this.feedsList = MainActivity.feedsList;
    }

    @Override
    public FeedsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View vi = LayoutInflater.from(parent.getContext()).inflate(R.layout.feeds_list_item, parent, false);
        return new ViewHolder(vi);
    }

    @Override
    public void onBindViewHolder(FeedsAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return feedsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}

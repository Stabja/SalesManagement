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
import com.stabstudio.salesmgmt.Modules.Events.ReadEventActivity;
import com.stabstudio.salesmgmt.R;
import com.stabstudio.salesmgmt.models.Event;

import java.util.ArrayList;


public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.ViewHolder>{

    private Activity activity;
    private ArrayList<Event> eventsList;

    public EventsAdapter(Activity activity){
        this.activity = activity;
        this.eventsList = MainActivity.eventsList;
    }

    @Override
    public EventsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View vi = LayoutInflater.from(parent.getContext()).inflate(R.layout.events_list_item, parent, false);
        return new ViewHolder(vi);
    }

    @Override
    public void onBindViewHolder(EventsAdapter.ViewHolder holder, final int position) {
        holder.eventTitle.setText(eventsList.get(position).getTitle());
        holder.eventAuthor.setText(eventsList.get(position).getOrganiser());
        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, ReadEventActivity.class);
                intent.putExtra("eventId", eventsList.get(position).getId());
                activity.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return eventsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ViewGroup parent;
        private TextView eventTitle;
        private TextView eventAuthor;

        public ViewHolder(View itemView) {
            super(itemView);
            parent = (LinearLayout) itemView.findViewById(R.id.event_parent);
            eventTitle = (TextView) itemView.findViewById(R.id.event_title);
            eventAuthor = (TextView) itemView.findViewById(R.id.event_organiser);
        }
    }
}

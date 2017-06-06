package com.stabstudio.drawernavig.adapters;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.stabstudio.drawernavig.MainActivity;
import com.stabstudio.drawernavig.R;
import com.stabstudio.drawernavig.models.Task;

import java.util.ArrayList;

public class TasksAdapter extends RecyclerView.Adapter<TasksAdapter.ViewHolder>{

    private Activity activity;
    private ArrayList<Task> tasksList;

    public TasksAdapter(Activity activity){
        this.activity = activity;
        this.tasksList = MainActivity.tasksList;
    }

    @Override
    public TasksAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View vi = LayoutInflater.from(parent.getContext()).inflate(R.layout.tasks_list_item, parent, false);
        return new ViewHolder(vi);
    }

    @Override
    public void onBindViewHolder(TasksAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return tasksList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}

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
import com.stabstudio.salesmgmt.Modules.Tasks.ReadTaskActivity;
import com.stabstudio.salesmgmt.R;
import com.stabstudio.salesmgmt.models.Task;

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
    public void onBindViewHolder(TasksAdapter.ViewHolder holder, final int position) {
        holder.taskSubject.setText(tasksList.get(position).getSubject());
        holder.taskAssigner.setText(tasksList.get(position).getAssigner());
        holder.taskPriority.setText(tasksList.get(position).getPriority());
        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, ReadTaskActivity.class);
                intent.putExtra("taskId", tasksList.get(position).getId());
                activity.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return tasksList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ViewGroup parent;
        private TextView taskSubject;
        private TextView taskAssigner;
        private TextView taskPriority;

        public ViewHolder(View itemView) {
            super(itemView);
            parent = (LinearLayout) itemView.findViewById(R.id.task_parent);
            taskSubject = (TextView) itemView.findViewById(R.id.task_subject);
            taskAssigner = (TextView) itemView.findViewById(R.id.task_assigner);
            taskPriority = (TextView) itemView.findViewById(R.id.task_priority);
        }
    }
}

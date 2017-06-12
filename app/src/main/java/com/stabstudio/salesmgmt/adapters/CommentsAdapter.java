package com.stabstudio.salesmgmt.adapters;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.stabstudio.salesmgmt.R;
import com.stabstudio.salesmgmt.models.Comment;

import org.joda.time.DateTime;

import java.util.ArrayList;

public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.ViewHolder>{

    private Context context;
    private ArrayList<Comment> commentsList = new ArrayList<>();

    public CommentsAdapter(Context context, ArrayList<Comment> commentsList){
        this.context = context;
        this.commentsList = commentsList;
    }

    @Override
    public CommentsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View vi = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comment, parent, false);
        return new ViewHolder(vi);
    }

    @Override
    public void onBindViewHolder(CommentsAdapter.ViewHolder holder, int position) {
        holder.commentAuthor.setText(commentsList.get(position).getAuthor());
        holder.commentText.setText(commentsList.get(position).getText());
        String postTime = commentsList.get(position).getTimestamp();
        calculateTimeAGO(holder, postTime);
        //Glide.with(context).load(commentsList.get(position).getPhotoUrl()).into(holder.commentPhoto);
    }

    private void calculateTimeAGO(final ViewHolder holder, String timeStamp){
        String[] chars = timeStamp.split("/");
        int second = Integer.parseInt(chars[0]);
        int minute = Integer.parseInt(chars[1]);
        int hour = Integer.parseInt(chars[2]);
        int day = Integer.parseInt(chars[3]);
        int month = Integer.parseInt(chars[4]);
        int year = Integer.parseInt(chars[5]);
        int secondNow = DateTime.now().getSecondOfMinute();
        int minuteNow = DateTime.now().getMinuteOfHour();
        int hourNow = DateTime.now().getHourOfDay();
        int dayNow = DateTime.now().getDayOfMonth();
        int monthNow = DateTime.now().getMonthOfYear();
        int yearNow = DateTime.now().getYear();
        int displayTime;
        if(yearNow - year != 0){
            displayTime = yearNow - year;
            holder.timeText.setText(displayTime + " years ago");
        }else if(monthNow - month != 0){
            displayTime = monthNow - month;
            holder.timeText.setText(displayTime + " months ago");
        }else if(dayNow - day != 0){
            displayTime = dayNow - day;
            holder.timeText.setText(displayTime + " days ago");
        }else if(hourNow - hour != 0){
            displayTime = hourNow - hour;
            holder.timeText.setText(displayTime + " hours ago");
        }else if(minuteNow - minute != 0){
            displayTime = minuteNow - minute;
            holder.timeText.setText(displayTime + " minutes ago");
        }else{
            displayTime = secondNow - second;
            holder.timeText.setText(displayTime + " seconds ago");
        }
    }

    @Override
    public int getItemCount() {
        return commentsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView commentPhoto;
        private TextView commentAuthor;
        private TextView commentText;
        private TextView timeText;

        public ViewHolder(View itemView) {
            super(itemView);
            commentPhoto = (ImageView) itemView.findViewById(R.id.comment_photo);
            commentAuthor = (TextView) itemView.findViewById(R.id.comment_author);
            commentText = (TextView) itemView.findViewById(R.id.comment_text);
            timeText = (TextView) itemView.findViewById(R.id.timestamp);
        }
    }
}

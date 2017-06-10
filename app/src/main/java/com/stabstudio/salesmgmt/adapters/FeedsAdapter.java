package com.stabstudio.salesmgmt.adapters;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.stabstudio.salesmgmt.MainActivity;
import com.stabstudio.salesmgmt.Modules.Feeds.ReadFeedActivity;
import com.stabstudio.salesmgmt.R;
import com.stabstudio.salesmgmt.models.Account;
import com.stabstudio.salesmgmt.models.Feed;

import org.joda.time.DateTime;

import java.util.ArrayList;

public class FeedsAdapter extends RecyclerView.Adapter<FeedsAdapter.ViewHolder>{

    private Activity activity;
    private ArrayList<Feed> feedsList;

    private DatabaseReference dRef;
    private DatabaseReference usersRef;

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
    public void onBindViewHolder(final FeedsAdapter.ViewHolder holder, int position) {
        final Feed tempFeed = feedsList.get(position);
        String userId = tempFeed.getUserId();
        getUserData(holder, userId);
        holder.postTitle.setText(tempFeed.getTitle());
        holder.postStatus.setText(tempFeed.getStatus());
        calculateTimeAGO(holder, tempFeed.getTimeStamp());
        holder.likesCount.setText(String.valueOf(tempFeed.getLikes()));
        holder.commentCount.setText(String.valueOf(tempFeed.getComments()));
        holder.addComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, ReadFeedActivity.class);
                intent.putExtra("feedId", tempFeed.getId());
                activity.startActivity(intent);
            }
        });
        //Glide.with(activity).load(tempFeed.getPhotoUrl()).into(holder.postAuthorImage);
    }

    private void getUserData(final FeedsAdapter.ViewHolder holder, final String userId){
        dRef = FirebaseDatabase.getInstance().getReference("Sales");
        usersRef = dRef.child("accounts");
        usersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Account account = dataSnapshot.child(userId).getValue(Account.class);
                holder.postAuthor.setText(account.getAccountName());
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
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
            holder.timeStamp.setText(displayTime + " years ago");
        }else if(monthNow - month != 0){
            displayTime = monthNow - month;
            holder.timeStamp.setText(displayTime + " months ago");
        }else if(dayNow - day != 0){
            displayTime = dayNow - day;
            holder.timeStamp.setText(displayTime + " days ago");
        }else if(hourNow - hour != 0){
            displayTime = hourNow - hour;
            holder.timeStamp.setText(displayTime + " hours ago");
        }else if(minuteNow - minute != 0){
            displayTime = minuteNow - minute;
            holder.timeStamp.setText(displayTime + " minutes ago");
        }else{
            displayTime = secondNow - second;
            holder.timeStamp.setText(displayTime + " seconds ago");
        }
    }

    @Override
    public int getItemCount() {
        return feedsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView postAuthor;
        private TextView postTitle;
        private TextView postStatus;
        private ImageView postAuthorImage;
        private TextView timeStamp;
        private TextView likesCount;
        private TextView commentCount;
        private TextView addComment;

        public ViewHolder(View itemView) {
            super(itemView);
            postAuthor = (TextView) itemView.findViewById(R.id.post_author);
            postTitle = (TextView) itemView.findViewById(R.id.post_title);
            postStatus = (TextView) itemView.findViewById(R.id.post_body);
            postAuthorImage = (ImageView) itemView.findViewById(R.id.post_author_image);
            timeStamp = (TextView) itemView.findViewById(R.id.post_timestamp);
            likesCount = (TextView) itemView.findViewById(R.id.like_count);
            commentCount = (TextView) itemView.findViewById(R.id.comment_count);
            addComment = (TextView) itemView.findViewById(R.id.add_comment);
        }
    }
}

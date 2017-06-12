package com.stabstudio.salesmgmt.Modules.Feeds;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.stabstudio.salesmgmt.R;
import com.stabstudio.salesmgmt.adapters.CommentsAdapter;
import com.stabstudio.salesmgmt.models.Account;
import com.stabstudio.salesmgmt.models.Comment;
import com.stabstudio.salesmgmt.models.Feed;

import org.joda.time.DateTime;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ReadFeedActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private FirebaseUser user;
    private DatabaseReference dRef;
    private StorageReference sRef;

    @BindView(R.id.authorimage) ImageView authorIcon;
    @BindView(R.id.author) TextView authorName;
    @BindView(R.id.title) TextView postTitle;
    @BindView(R.id.body) TextView postBody;
    @BindView(R.id.timestamp) TextView timeStamp;
    @BindView(R.id.comments_recyclerview) RecyclerView recyclerView;
    @BindView(R.id.comment_text) EditText commentText;
    @BindView(R.id.button_publish_comment) Button publishComment;
    @BindView(R.id.loading_layout) LinearLayout progressLayout;

    private String feedId;
    private LinearLayoutManager layoutManager;
    private CommentsAdapter adapter;
    private ArrayList<Comment> commentsList = new ArrayList<Comment>();

    private String fetchedFeedId;
    private String author;
    private int commentNo;
    private int likesNo;
    private Boolean liked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.activity_open_translate, R.anim.activity_close_scale);
        setContentView(R.layout.activity_read_feed);
        ButterKnife.bind(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        dRef = FirebaseDatabase.getInstance().getReference("Sales");

        Intent intent = getIntent();
        feedId = intent.getStringExtra("feedId");

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        progressLayout.setVisibility(View.VISIBLE);

        publishComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                publishNewComment();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        fetchFeed();
        fetchComments();
    }

    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(R.anim.activity_open_scale, R.anim.activity_close_translate);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.feed_comments_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.refresh_comments){
            fetchComments();
        }
        if(id == android.R.id.home){
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.activity_open_scale, R.anim.activity_close_translate);
    }

    private void fetchFeed(){
        DatabaseReference feedsRef = dRef.child("feeds");
        feedsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Feed temp = dataSnapshot.child(feedId).getValue(Feed.class);
                fetchedFeedId = temp.getId();
                obtainUsernameFromId(temp.getUserId());
                postTitle.setText(temp.getTitle());
                postBody.setText(temp.getStatus());
                calculateTimeAGO(timeStamp, temp.getTimeStamp());
                commentNo = temp.getComments();
                likesNo = temp.getLikes();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), "Cannot Load Feed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void obtainUsernameFromId(final String id){
        DatabaseReference accountRef = dRef.child("accounts");
        accountRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Account temp = dataSnapshot.child(id).getValue(Account.class);
                String name = temp.getFirstName() + " " + temp.getLastName();
                authorName.setText(name);
                author = name;
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void fetchComments(){
        DatabaseReference commentsRef = dRef.child("feed-comments").child(feedId);
        commentsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                commentsList.clear();
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Comment temp = snapshot.getValue(Comment.class);
                    commentsList.add(temp);
                }
                adapter = new CommentsAdapter(getApplicationContext(), commentsList);
                recyclerView.setAdapter(adapter);
                progressLayout.setVisibility(View.GONE);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), "Failed to load comments, Please Refresh", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void publishNewComment(){
        if(!TextUtils.isEmpty(commentText.getText().toString())){
            DatabaseReference commentsRef = dRef.child("feed-comments").child(feedId);
            String key = commentsRef.push().getKey();
            String text = commentText.getText().toString();
            String timeStamp = DateTime.now().getSecondOfMinute() + "/" +
                    DateTime.now().getMinuteOfHour() + "/" +
                    DateTime.now().getHourOfDay() + "/" +
                    DateTime.now().getDayOfMonth() + "/" +
                    DateTime.now().getMonthOfYear() + "/" +
                    DateTime.now().getYear();

            Comment comment = new Comment(key, feedId, "", "John Doe", text, timeStamp);
            commentsRef.child(key).setValue(comment);      //Adding in Firebase
            commentsList.add(comment);                     //Adding in Local List
            adapter.notifyDataSetChanged();
            commentText.setText("");

            DatabaseReference feedsRef = dRef.child("feeds").child(feedId).child("comments");
            feedsRef.setValue(commentNo+1);                //Increase the no of comments in feeds
            commentNo++;
        }
    }

    private void calculateTimeAGO(TextView timeText, String timeStamp){
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
            timeText.setText(displayTime + " years ago");
        }else if(monthNow - month != 0){
            displayTime = monthNow - month;
            timeText.setText(displayTime + " months ago");
        }else if(dayNow - day != 0){
            displayTime = dayNow - day;
            timeText.setText(displayTime + " days ago");
        }else if(hourNow - hour != 0){
            displayTime = hourNow - hour;
            timeText.setText(displayTime + " hours ago");
        }else if(minuteNow - minute != 0){
            displayTime = minuteNow - minute;
            timeText.setText(displayTime + " minutes ago");
        }else{
            displayTime = secondNow - second;
            timeText.setText(displayTime + " seconds ago");
        }
    }

}

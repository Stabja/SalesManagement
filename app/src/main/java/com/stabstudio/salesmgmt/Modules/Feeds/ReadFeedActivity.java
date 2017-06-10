package com.stabstudio.salesmgmt.Modules.Feeds;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.stabstudio.salesmgmt.Modules.Contacts.CreateContactActivity;
import com.stabstudio.salesmgmt.Modules.Contacts.UpdateContactActivity;
import com.stabstudio.salesmgmt.R;
import com.stabstudio.salesmgmt.models.Feed;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ReadFeedActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private FirebaseUser user;
    private DatabaseReference dRef;
    private StorageReference sRef;

    @BindView(R.id.authorimage) ImageView authorIcon;
    @BindView(R.id.author) TextView authorName;
    @BindView(R.id.timestamp) TextView timeStamp;
    @BindView(R.id.title) TextView postTitle;
    @BindView(R.id.body) TextView postBody;
    @BindView(R.id.comments_recyclerview) RecyclerView recyclerView;
    @BindView(R.id.comment_text) EditText commentText;
    @BindView(R.id.button_publish_comment) Button publishComment;

    private String feedId;

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

        publishComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                publishCommentText();
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        loadValues();
    }

    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(R.anim.activity_open_scale, R.anim.activity_close_translate);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.read_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        /*if(id == R.id.menu_delete){
            //showdeleteDialog();
        }
        if(id == R.id.menu_refresh){
            loadValues();
        }*/
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

    private void loadValues(){
        DatabaseReference feedsRef = dRef.child("feeds");
        feedsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Feed temp = dataSnapshot.child(feedId).getValue(Feed.class);
                authorName.setText(temp.getUserId());
                timeStamp.setText(temp.getTimeStamp());
                postTitle.setText(temp.getTitle());
                postBody.setText(temp.getStatus());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), "Cannot Load Values", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void publishCommentText(){
        if(!TextUtils.isEmpty(commentText.getText().toString())){
            Toast.makeText(this, "Commenting System not Implemented", Toast.LENGTH_SHORT).show();
        }
    }

}

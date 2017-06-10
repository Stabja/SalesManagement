package com.stabstudio.salesmgmt.Modules.Feeds;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.stabstudio.salesmgmt.R;

public class UpdateFeedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.activity_open_translate, R.anim.activity_close_scale);
        setContentView(R.layout.activity_update_feed);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    protected void onPause() {
        super.onPause();
        overridePendingTransition(R.anim.activity_open_scale, R.anim.activity_close_translate);
    }

    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.activity_open_scale, R.anim.activity_close_translate);
    }

}

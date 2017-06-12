package com.stabstudio.salesmgmt.Modules.Leads;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.stabstudio.salesmgmt.MainActivity;
import com.stabstudio.salesmgmt.R;
import com.stabstudio.salesmgmt.fragments.LeadsFragment;
import com.stabstudio.salesmgmt.models.Lead;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UpdateLeadActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private FirebaseUser user;
    private DatabaseReference dRef;
    private StorageReference sRef;

    @BindView(R.id.dis_image) ImageView photo;
    @BindView(R.id.editText) EditText et1;
    @BindView(R.id.editText2) EditText et2;
    @BindView(R.id.editText3) EditText et3;
    @BindView(R.id.editText4) EditText et4;
    @BindView(R.id.editText5) EditText et5;
    @BindView(R.id.editText6) EditText et6;
    @BindView(R.id.editText7) TextView tv7;
    @BindView(R.id.editText8) TextView tv8;
    @BindView(R.id.editText9) TextView tv9;
    @BindView(R.id.editText10) EditText et10;
    @BindView(R.id.editText11) EditText et11;
    @BindView(R.id.editText12) TextView tv12;
    @BindView(R.id.update_lead) Button update;

    private Uri imageFile;
    private String leadId;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.activity_open_translate, R.anim.activity_close_scale);
        setContentView(R.layout.activity_update_lead);
        ButterKnife.bind(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        dRef = FirebaseDatabase.getInstance().getReference("Sales");
        sRef = FirebaseStorage.getInstance().getReference();

        Intent intent = getIntent();
        leadId = intent.getStringExtra("leadId");
        position = intent.getIntExtra("position", 0);

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceDataToServer();
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
        getMenuInflater().inflate(R.menu.update_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.menu_done){
            replaceDataToServer();
        }
        if(id == android.R.id.home){
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        LeadsFragment.recyclerUtil.refreshData();
        overridePendingTransition(R.anim.activity_open_scale, R.anim.activity_close_translate);
        MainActivity.leadsAdapter.notifyDataSetChanged();
        finish();
    }

    private void loadValues(){
        DatabaseReference leadsRef = dRef.child("leads");
        leadsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Lead temp = dataSnapshot.child(leadId).getValue(Lead.class);
                et1.setText(temp.getFirstName());
                et2.setText(temp.getLastName());
                et3.setText(temp.getCompany());
                et4.setText(temp.getEmail());
                et5.setText(temp.getPhone());
                et6.setText(temp.getWebsite());
                tv7.setText(temp.getLeadSource());
                tv8.setText(temp.getLeadStatus());
                tv9.setText(temp.getIndustry());
                et10.setText(temp.getEmployees());
                et11.setText(temp.getRevenue());
                tv12.setText(temp.getRating());
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), "Cannot Load Values", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean chkEmt(EditText editText){
        return TextUtils.isEmpty(editText.getText().toString());
    }

    private boolean chkEmtTV(TextView textView){
        return TextUtils.isEmpty(textView.getText().toString());
    }

    private void replaceDataToServer(){
        if(!chkEmt(et1) && !chkEmt(et2) && !chkEmt(et3) && !chkEmt(et4) && !chkEmt(et5) && !chkEmt(et6)
                && !chkEmtTV(tv7) && !chkEmtTV(tv8) && !chkEmtTV(tv9) && !chkEmt(et10) && !chkEmt(et11) &&!chkEmtTV(tv12)){
            DatabaseReference leadsRef = dRef.child("leads");
            String key = leadId;
            String photoUrl;
            if(imageFile != null){
                photoUrl = imageFile.toString();
            } else {
                photoUrl = "";
            }
            String a = et1.getText().toString();
            String b = et2.getText().toString();
            String c = et3.getText().toString();
            String d = et4.getText().toString();
            String e = et5.getText().toString();
            String f = et6.getText().toString();
            String g = tv7.getText().toString();
            String h = tv8.getText().toString();
            String i = tv9.getText().toString();
            String j = et10.getText().toString();
            String k = et11.getText().toString();
            String l = tv12.getText().toString();

            Lead temp = new Lead(key, photoUrl, a, b, c, d, e, f, g, h, i, j, k, l);
            leadsRef.child(key).setValue(temp);         //Editing in Firebase
            changeDataInLocalList(temp);                   //Editing in Local List
            LeadsFragment.recyclerUtil.refreshData();
            Toast.makeText(this, "Lead Updated", Toast.LENGTH_SHORT).show();
            finish();
            overridePendingTransition(R.anim.still, R.anim.slide_out_down);
        } else {
            Toast.makeText(this, "Please fill all values.", Toast.LENGTH_SHORT).show();
        }
    }

    private void changeDataInLocalList(Lead lead){
        MainActivity.leadsList.set(position, lead);
        MainActivity.leadsAdapter.notifyDataSetChanged();
    }

}

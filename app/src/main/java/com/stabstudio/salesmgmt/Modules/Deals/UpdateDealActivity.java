package com.stabstudio.salesmgmt.Modules.Deals;

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
import com.stabstudio.salesmgmt.fragments.DealsFragment;
import com.stabstudio.salesmgmt.models.Contact;
import com.stabstudio.salesmgmt.models.Deal;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UpdateDealActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private FirebaseUser user;
    private DatabaseReference dRef;
    private StorageReference sRef;

    @BindView(R.id.editText) EditText et1;
    @BindView(R.id.editText2) EditText et2;
    @BindView(R.id.editText3) EditText et3;
    @BindView(R.id.editText4) TextView tv4;
    @BindView(R.id.editText5) TextView tv5;
    @BindView(R.id.editText6) TextView tv6;
    @BindView(R.id.editText7) TextView tv7;
    @BindView(R.id.editText8) EditText et8;
    @BindView(R.id.editText9) EditText et9;
    @BindView(R.id.editText10) TextView tv10;
    @BindView(R.id.editText11) TextView tv11;
    @BindView(R.id.update_deal) Button update;

    private Uri imageFile;
    private String dealId;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.activity_open_translate, R.anim.activity_close_scale);
        setContentView(R.layout.activity_update_deal);
        ButterKnife.bind(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        dRef = FirebaseDatabase.getInstance().getReference("Sales");
        sRef = FirebaseStorage.getInstance().getReference();

        Intent intent = getIntent();
        dealId = intent.getStringExtra("dealId");
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
        DealsFragment.recyclerUtil.refreshData();
        MainActivity.dealsAdapter.notifyDataSetChanged();
        overridePendingTransition(R.anim.activity_open_scale, R.anim.activity_close_translate);
        finish();
    }

    private void loadValues(){
        DatabaseReference dealsRef = dRef.child("deals");
        dealsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Deal temp = dataSnapshot.child(dealId).getValue(Deal.class);
                et1.setText(temp.getDealOwner());
                et2.setText(temp.getAmount());
                et3.setText(temp.getDealName());
                tv4.setText(temp.getDeadLine());
                tv5.setText(temp.getAccountName());
                tv6.setText(temp.getStage());
                tv7.setText(temp.getType());
                et8.setText(temp.getProbability());
                et9.setText(temp.getNextStep());
                tv10.setText(temp.getLeadSource());
                tv11.setText(temp.getContactName());
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), "Cannot load values", Toast.LENGTH_SHORT).show();
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
        if(!chkEmt(et1) && !chkEmt(et2) && !chkEmt(et3) && !chkEmtTV(tv4) && !chkEmtTV(tv5) && !chkEmtTV(tv6)
                && !chkEmtTV(tv7) && !chkEmt(et8) && !chkEmt(et9) && !chkEmtTV(tv10) &&!chkEmtTV(tv11)){

            DatabaseReference dealsRef = dRef.child("deals");
            String a = et1.getText().toString();
            String b = et2.getText().toString();
            String c = et3.getText().toString();
            String d = tv4.getText().toString();
            String e = tv5.getText().toString();
            String f = tv6.getText().toString();
            String g = tv7.getText().toString();
            String h = et8.getText().toString();
            String i = et9.getText().toString();
            String j = tv10.getText().toString();
            String k = tv11.getText().toString();

            Deal temp = new Deal(dealId, a, b, c, d, e, f, g, h, i, j, k);
            dealsRef.child(dealId).setValue(temp);         //Editing in Firebase
            changeDataInLocalList(temp);                   //Editing in Local List
            DealsFragment.recyclerUtil.refreshData();
            Toast.makeText(this, "Contact Updated", Toast.LENGTH_SHORT).show();
            overridePendingTransition(R.anim.activity_open_scale, R.anim.activity_close_translate);
            finish();
        } else {
            Toast.makeText(this, "Please fill all values", Toast.LENGTH_SHORT).show();
        }
    }

    private void changeDataInLocalList(Deal deal){
        MainActivity.dealsList.set(position, deal);
        MainActivity.dealsAdapter.notifyDataSetChanged();
    }

}

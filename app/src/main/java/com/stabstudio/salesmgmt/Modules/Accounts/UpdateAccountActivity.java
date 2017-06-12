package com.stabstudio.salesmgmt.Modules.Accounts;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import com.stabstudio.salesmgmt.fragments.AccountsFragment;
import com.stabstudio.salesmgmt.models.Account;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UpdateAccountActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private FirebaseUser firebaseUser;
    private DatabaseReference dRef;
    private StorageReference sRef;

    @BindView(R.id.editText) EditText et1;
    @BindView(R.id.editText2) EditText et2;
    @BindView(R.id.editText3) EditText et3;
    @BindView(R.id.editText4) EditText et4;
    @BindView(R.id.editText5) EditText et5;
    @BindView(R.id.editText6) EditText et6;
    @BindView(R.id.editText7) EditText et7;
    @BindView(R.id.editText8) EditText et8;
    @BindView(R.id.editText9) EditText et9;
    @BindView(R.id.editText10) EditText et11;
    @BindView(R.id.editText11) EditText et12;
    @BindView(R.id.update_account) Button update;

    private String accountId;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.activity_open_translate, R.anim.activity_close_scale);
        setContentView(R.layout.activity_update_account);

        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        dRef = FirebaseDatabase.getInstance().getReference("Sales");
        sRef = FirebaseStorage.getInstance().getReference();

        Intent intent = getIntent();
        accountId = intent.getStringExtra("accountId");
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
        AccountsFragment.recyclerUtil.refreshData();
        overridePendingTransition(R.anim.activity_open_scale, R.anim.activity_close_translate);
        MainActivity.accountsAdapter.notifyDataSetChanged();
        finish();
    }

    private void loadValues(){
        DatabaseReference accountsRef = dRef.child("accounts");
        accountsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Account temp = dataSnapshot.child(accountId).getValue(Account.class);
                et1.setText(temp.getFirstName());
                et2.setText(temp.getLastName());
                et3.setText(temp.getEmail());
                et4.setText(temp.getPhone());
                et5.setText(temp.getAccountName());
                et6.setText(temp.getAccountSite());
                et7.setText(temp.getAccountNumber());
                et8.setText(temp.getAccountType());
                et9.setText(temp.getOwnership());
                et11.setText(temp.getEmployees());
                et12.setText(temp.getRevenue());
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

    private void replaceDataToServer(){
        if(!chkEmt(et1) && !chkEmt(et2) && !chkEmt(et3) && !chkEmt(et4) && !chkEmt(et5) && !chkEmt(et6)
                        && !chkEmt(et7) && !chkEmt(et8) && !chkEmt(et9) && !chkEmt(et11) &&!chkEmt(et12)){

            DatabaseReference accountsRef = dRef.child("accounts");
            String key = accountId;
            String firstName = et1.getText().toString();
            String lastName = et2.getText().toString();
            String email = et3.getText().toString();
            String phone = et4.getText().toString();
            String accountName = et5.getText().toString();
            String accountSite = et6.getText().toString();
            String accountNumber = et7.getText().toString();
            String accountType = et8.getText().toString();
            String ownership = et9.getText().toString();
            String employees = et11.getText().toString();
            String revenue = et12.getText().toString();

            Account temp = new Account(key, firstName, lastName, email, phone, accountName, accountSite, accountNumber, accountType, ownership, employees, revenue);
            accountsRef.child(key).setValue(temp);         //Editing in Firebase
            changeDataInLocalList(temp);                   //Editing in Local List
            AccountsFragment.recyclerUtil.refreshData();
            Toast.makeText(this, "Account Updated", Toast.LENGTH_SHORT).show();
            overridePendingTransition(R.anim.activity_open_scale, R.anim.activity_close_translate);
            finish();
        } else {
            Toast.makeText(this, "Please fill all values.", Toast.LENGTH_SHORT).show();
        }
    }

    private void changeDataInLocalList(Account account){
        MainActivity.accountsList.set(position, account);
        MainActivity.accountsAdapter.notifyDataSetChanged();
    }

}

package com.stabstudio.salesmgmt.Modules.Contacts;

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
import com.stabstudio.salesmgmt.fragments.ContactsFragment;
import com.stabstudio.salesmgmt.models.Contact;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UpdateContactActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private FirebaseUser user;
    private DatabaseReference dRef;
    private StorageReference sRef;

    @BindView(R.id.dis_image) ImageView photo;
    @BindView(R.id.editText) EditText et1;
    @BindView(R.id.editText2) EditText et2;
    @BindView(R.id.editText3) EditText et3;
    @BindView(R.id.editText4) TextView et4;
    @BindView(R.id.editText5) EditText et5;
    @BindView(R.id.editText6) TextView et6;
    @BindView(R.id.editText7) EditText et7;
    @BindView(R.id.editText8) EditText et8;
    @BindView(R.id.editText9) EditText et9;
    @BindView(R.id.editText10) EditText et10;
    @BindView(R.id.editText11) EditText et11;
    @BindView(R.id.editText12) EditText et12;
    @BindView(R.id.editText13) EditText et13;
    @BindView(R.id.editText14) EditText et14;
    @BindView(R.id.update_contact) Button update;

    private Uri imageFile;
    private String contactId;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.activity_open_translate, R.anim.activity_close_scale);
        setContentView(R.layout.activity_update_contact);
        ButterKnife.bind(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        dRef = FirebaseDatabase.getInstance().getReference("Sales");
        sRef = FirebaseStorage.getInstance().getReference();

        Intent intent = getIntent();
        contactId = intent.getStringExtra("contactId");
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
        ContactsFragment.recyclerUtil.refreshData();
        MainActivity.contactsAdapter.notifyDataSetChanged();
        overridePendingTransition(R.anim.activity_open_scale, R.anim.activity_close_translate);
        finish();
    }

    private void loadValues(){
        DatabaseReference contactsRef = dRef.child("contacts");
        contactsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Contact temp = dataSnapshot.child(contactId).getValue(Contact.class);
                et1.setText(temp.getFirstName());
                et2.setText(temp.getLastName());
                et3.setText(temp.getAccountName());
                et4.setText(temp.getLeadSource());
                et5.setText(temp.getDepartment());
                et6.setText(temp.getDateofBirth());
                et7.setText(temp.getEmail());
                et8.setText(temp.getMobile());
                et9.setText(temp.getHomePhone());
                et10.setText(temp.getOfficePhone());
                et11.setText(temp.getSkypeId());
                et12.setText(temp.getLinkedin());
                et13.setText(temp.getTwitter());
                et14.setText(temp.getFacebook());
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
        if(!chkEmt(et1) && !chkEmt(et2) && !chkEmt(et3) && !chkEmtTV(et4) && !chkEmt(et5) && !chkEmtTV(et6)
                && !chkEmt(et7) && !chkEmt(et8) && !chkEmt(et9) && !chkEmt(et11) &&!chkEmt(et12)){

            DatabaseReference contactsRef = dRef.child("contacts");
            String key = contactId;
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
            String g = et7.getText().toString();
            String h = et8.getText().toString();
            String i = et9.getText().toString();
            String j = et10.getText().toString();
            String k = et11.getText().toString();
            String l = et12.getText().toString();
            String m = et13.getText().toString();
            String n = et14.getText().toString();

            Contact temp = new Contact(key, photoUrl, a, b, c, d, e, f, g, h, i, j, k, l, m, n);
            contactsRef.child(key).setValue(temp);         //Editing in Firebase
            changeDataInLocalList(temp);                   //Editing in Local List
            ContactsFragment.recyclerUtil.refreshData();
            Toast.makeText(this, "Contact Updated", Toast.LENGTH_SHORT).show();
            overridePendingTransition(R.anim.activity_open_scale, R.anim.activity_close_translate);
            finish();
        } else {
            Toast.makeText(this, "Please fill all values.", Toast.LENGTH_SHORT).show();
        }
    }

    private void changeDataInLocalList(Contact contact){
        MainActivity.contactsList.set(position, contact);
        MainActivity.contactsAdapter.notifyDataSetChanged();
    }

}

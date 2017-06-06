package com.stabstudio.drawernavig.Modules.Contacts;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
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
import com.stabstudio.drawernavig.Modules.Contacts.CreateContactActivity;
import com.stabstudio.drawernavig.Modules.Contacts.UpdateContactActivity;
import com.stabstudio.drawernavig.R;
import com.stabstudio.drawernavig.models.Contact;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ReadContactActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private FirebaseUser firebaseUser;
    private DatabaseReference dRef;
    private StorageReference sRef;

    @BindView(R.id.editText) TextView tv1;
    @BindView(R.id.editText2) TextView tv2;
    @BindView(R.id.editText3) TextView tv3;
    @BindView(R.id.editText4) TextView tv4;
    @BindView(R.id.editText5) TextView tv5;
    @BindView(R.id.editText6) TextView tv6;
    @BindView(R.id.editText7) TextView tv7;
    @BindView(R.id.editText8) TextView tv8;
    @BindView(R.id.editText9) TextView tv9;
    @BindView(R.id.editText10) TextView tv11;
    @BindView(R.id.editText11) TextView tv12;
    @BindView(R.id.editText12) TextView tv13;

    private String contactId;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //opening transition animations
        overridePendingTransition(R.anim.activity_open_translate, R.anim.activity_close_scale);

        setContentView(R.layout.activity_read_contact);
        ButterKnife.bind(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        dRef = FirebaseDatabase.getInstance().getReference("Sales");

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        Intent intent = getIntent();
        contactId = intent.getStringExtra("contactId");
    }

    @Override
    protected void onStart() {
        super.onStart();
        loadValues();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //closing transition animations
        overridePendingTransition(R.anim.activity_open_scale, R.anim.activity_close_scale);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.read_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.menu_edit){
            Intent intent = new Intent(this, UpdateContactActivity.class);
            intent.putExtra("contactId", contactId);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_up, R.anim.still);
        }
        if(id == R.id.menu_clone){
            Intent intent = new Intent(this, CreateContactActivity.class);
            intent.putExtra("contactId", contactId);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_up, R.anim.still);
        }
        if(id == R.id.menu_delete){
            showdeleteDialog();
        }
        if(id == R.id.menu_refresh){
            loadValues();
        }
        if(id == android.R.id.home){
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.still, R.anim.slide_out_down);
    }

    private void showdeleteDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to delete");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.setCancelable(false);
        builder.show();
    }

    private void loadValues(){
        DatabaseReference contactsRef = dRef.child("contacts");
        contactsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Contact temp = dataSnapshot.child(contactId).getValue(Contact.class);
                tv1.setText(temp.getFirstName());
                tv2.setText(temp.getLastName());
                tv3.setText(temp.getAccountName());
                tv4.setText(temp.getLeadSource());
                tv5.setText(temp.getDepartment());
                tv6.setText(temp.getDateofBirth());
                tv7.setText(temp.getEmail());
                tv8.setText(temp.getMobile());
                tv9.setText(temp.getSkypeId());
                tv11.setText(temp.getLinkedin());
                tv12.setText(temp.getTwitter());
                tv13.setText(temp.getFacebook());
                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), "Cannot Load Values", Toast.LENGTH_SHORT).show();
            }
        });
    }

}

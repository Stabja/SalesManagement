package com.stabstudio.salesmgmt.Modules.Contacts;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.stabstudio.salesmgmt.MainActivity;
import com.stabstudio.salesmgmt.R;
import com.stabstudio.salesmgmt.models.Account;
import com.stabstudio.salesmgmt.models.Contact;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CreateContactActivity extends AppCompatActivity {

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
    @BindView(R.id.create_contact) Button create;

    private Uri imageFile;
    private int mYear;
    private int mMonth;
    private int mDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_contact);

        ButterKnife.bind(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        dRef = FirebaseDatabase.getInstance().getReference("Sales");
        sRef = FirebaseStorage.getInstance().getReference();

        photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showImageChooser();
            }
        });

        et4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLeadSourceDialog();
            }
        });

        et6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postDataToServer();
            }
        });

    }

    private void showImageChooser(){
        Intent pickIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickIntent.setType("image/*");
        Intent chooserIntent = Intent.createChooser(pickIntent, "select Image");
        startActivityForResult(chooserIntent, 900);
    }

    private void showLeadSourceDialog(){
        final CharSequence[] leadArray = getResources().getStringArray(R.array.lead_source);
        final AlertDialog.Builder leadDialog = new AlertDialog.Builder(this);
        leadDialog.setTitle("Lead Sources");
        leadDialog.setSingleChoiceItems(leadArray, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                et4.setText(leadArray[which].toString());
                dialog.dismiss();
            }
        });
        leadDialog.create();
        leadDialog.show();
    }

    private void showDatePickerDialog(){
        DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                mDay = dayOfMonth;
                mMonth = month + 1;
                mYear = year;
                String dateStr = mDay + "/" + mMonth + "/" + mYear;
                et6.setText(dateStr);
            }
        };
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, mDateSetListener, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 900 && resultCode == RESULT_OK && data != null){
            imageFile = data.getData();
            photo.setImageURI(imageFile);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.create_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.menu_done){
            postDataToServer();
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

    private boolean chkEmt(EditText editText){
        return TextUtils.isEmpty(editText.getText().toString());
    }

    private boolean chkEmtTV(TextView textView){
        return TextUtils.isEmpty(textView.getText().toString());
    }

    private void postDataToServer(){
        if(!chkEmt(et1) && !chkEmt(et2) && !chkEmt(et3) && !chkEmtTV(et4) && !chkEmt(et5) && !chkEmtTV(et6) && !chkEmt(et7) && !chkEmt(et8)
                        && !chkEmt(et9) && !chkEmt(et10) && !chkEmt(et11) && !chkEmt(et12) && !chkEmt(et13) && !chkEmt(et14)){

            DatabaseReference contactsRef = dRef.child("contacts");
            String key = contactsRef.push().getKey();          //Creating a new node
            String photoUrl = imageFile.toString();
            String firstName = et1.getText().toString();
            String lastName = et2.getText().toString();
            String accountName = et3.getText().toString();
            String leadSource = et4.getText().toString();
            String department = et5.getText().toString();
            String dateOfBirth = et6.getText().toString();
            String email = et7.getText().toString();
            String mobile = et8.getText().toString();
            String homePhone = et9.getText().toString();
            String officePhone = et10.getText().toString();
            String skypeId = et11.getText().toString();
            String linkedin = et12.getText().toString();
            String twitter = et13.getText().toString();
            String facebook = et14.getText().toString();

            Contact temp = new Contact(key, photoUrl, firstName, lastName, accountName, leadSource, department, dateOfBirth, email, mobile, homePhone, officePhone, skypeId, linkedin, twitter, facebook);
            contactsRef.child(key).setValue(temp);             //Adding in Firebase
            addDataInLocalList(temp);                          //Adding in Local List
            Toast.makeText(this, "Contact Created", Toast.LENGTH_SHORT).show();
            finish();
            overridePendingTransition(R.anim.still, R.anim.slide_out_down);
        } else {
            Toast.makeText(this, "Please fill all values.", Toast.LENGTH_SHORT).show();
        }
    }

    private void addDataInLocalList(Contact contact){
        MainActivity.contactsList.add(contact);
        MainActivity.contactsAdapter.notifyItemInserted(MainActivity.contactsList.size());
        MainActivity.contactsAdapter.notifyDataSetChanged();
    }

}

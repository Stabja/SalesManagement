package com.stabstudio.salesmgmt.Modules.Events;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
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
import com.stabstudio.salesmgmt.R;
import com.stabstudio.salesmgmt.models.Account;
import com.stabstudio.salesmgmt.models.Contact;
import com.stabstudio.salesmgmt.models.Event;
import com.stabstudio.salesmgmt.models.Task;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CreateEventActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private FirebaseUser user;
    private DatabaseReference dRef;
    private StorageReference sRef;

    @BindView(R.id.editText) EditText et1;
    @BindView(R.id.editText2) EditText et2;
    @BindView(R.id.editText3) TextView tv3;
    @BindView(R.id.editText4) TextView tv4;
    @BindView(R.id.editText5) TextView tv5;
    @BindView(R.id.editText6) TextView tv6;
    @BindView(R.id.editText7) TextView tv7;
    @BindView(R.id.allday) Switch swt;
    @BindView(R.id.create_event) Button create;

    private ArrayList<String> accountsList = new ArrayList<>();
    private ArrayList<String> contactsList = new ArrayList<>();

    private String dateTimeStr = "";
    private int mYear;
    private int mMonth;
    private int mDay;
    private int mHour;
    private int mMinute;
    private ArrayList<String> contactsArr = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);

        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        dRef = FirebaseDatabase.getInstance().getReference("Sales");
        sRef = FirebaseStorage.getInstance().getReference();

        fetchAccounts();
        fetchContacts();

        tv3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(swt.isChecked()){
                    swt.setChecked(false);
                } else {
                    swt.setChecked(true);
                }
            }
        });

        tv4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog(tv4);
            }
        });

        tv5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog(tv5);
            }
        });

        tv6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAccountPickerDialog();
            }
        });

        tv7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showContactPickerDialog();
            }
        });

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postDataToServer();
            }
        });

    }

    private void fetchAccounts(){
        DatabaseReference accRef = dRef.child("accounts");
        accRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                accountsList.clear();
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Account temp = snapshot.getValue(Account.class);
                    String name = temp.getFirstName() + " " + temp.getLastName();
                    accountsList.add(name);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    private void fetchContacts(){
        DatabaseReference conRef = dRef.child("contacts");
        conRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                contactsList.clear();
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Contact temp = snapshot.getValue(Contact.class);
                    String name = temp.getFirstName() + " " + temp.getLastName();
                    contactsList.add(name);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    private void showAccountPickerDialog(){
        final CharSequence[] accountArray = accountsList.toArray(new CharSequence[accountsList.size()]);
        AlertDialog.Builder accountDialog = new AlertDialog.Builder(this);
        accountDialog.setTitle("Accounts");
        accountDialog.setSingleChoiceItems(accountArray, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                tv6.setText(accountArray[which].toString());
                dialog.dismiss();
            }
        });
        accountDialog.create();
        accountDialog.show();
    }

    private void showContactPickerDialog(){
        final CharSequence[] contactsArray = contactsList.toArray(new CharSequence[contactsList.size()]);
        final ArrayList<Integer> selectedItems = new ArrayList<Integer>();

        AlertDialog.Builder contactDialog = new AlertDialog.Builder(this);
        contactDialog.setTitle("Contacts");
        /*contactDialog.setSingleChoiceItems(contactsArray, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                tv7.setText(contactsArray[which]);
                dialog.dismiss();
            }
        });*/
        contactDialog.setMultiChoiceItems(contactsArray, null, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                if (isChecked) {
                    selectedItems.add(which);
                } else if (selectedItems.contains(which)) {
                    selectedItems.remove(Integer.valueOf(which));
                }
            }
        });
        contactDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                tv7.setText("");
                int j = 0;
                for(Integer i : selectedItems){
                    tv7.append(contactsArray[i] + "\n");
                    contactsArr.add(contactsArray[i].toString());
                }
                dialog.dismiss();
            }
        });
        contactDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        contactDialog.create();
        contactDialog.show();
    }

    private void showTimePickerDialog(final TextView textView){
        TimePickerDialog.OnTimeSetListener mTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                mMinute = minute;
                mHour = hourOfDay;
                dateTimeStr = minute + "/" + hourOfDay + "/";
                showDatePickerDialog(textView);
            }
        };
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, mTimeSetListener, mHour, mMinute, true);
        timePickerDialog.show();
    }

    private void showDatePickerDialog(final TextView textView){
        DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                mDay = dayOfMonth;
                mMonth = month + 1;
                mYear = year;
                dateTimeStr += mDay + "/" + mMonth + "/" + mYear;
                textView.setText(dateTimeStr);
            }
        };
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, mDateSetListener, mYear, mMonth, mDay);
        datePickerDialog.show();
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

    public void postDataToServer(){
        if(!chkEmt(et1) && !chkEmt(et2) && !chkEmtTV(tv4) && !chkEmtTV(tv5) && !chkEmtTV(tv6) && !chkEmtTV(tv7)){

            DatabaseReference eventRef = dRef.child("events");
            String key = eventRef.push().getKey();
            String a = et1.getText().toString();
            String b = et2.getText().toString();
            Boolean c = swt.isChecked();
            String d = tv4.getText().toString();
            String e = tv5.getText().toString();
            String f = tv6.getText().toString();
            String g = tv7.getText().toString();

            Event event = new Event(key, a, b, c, d, e, f, contactsArr);
            eventRef.child(key).setValue(event);
            Toast.makeText(this, "Event Created", Toast.LENGTH_SHORT).show();
            finish();
            overridePendingTransition(R.anim.still, R.anim.slide_out_down);
        }
        else{
            Toast.makeText(this, "Please fill all values.", Toast.LENGTH_SHORT).show();
        }
    }

}

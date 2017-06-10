package com.stabstudio.salesmgmt.Modules.Deals;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.DatePicker;
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
import com.stabstudio.salesmgmt.R;
import com.stabstudio.salesmgmt.models.Account;
import com.stabstudio.salesmgmt.models.Contact;
import com.stabstudio.salesmgmt.models.Deal;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CreateDealActivity extends AppCompatActivity {

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
    @BindView(R.id.create_deal) Button create;

    private ArrayList<String> accountsList = new ArrayList<>();
    private ArrayList<String> contactsList = new ArrayList<>();

    private int mDay;
    private int mMonth;
    private int mYear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_deal);

        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        dRef = FirebaseDatabase.getInstance().getReference("Sales");
        sRef = FirebaseStorage.getInstance().getReference();

        fetchAccounts();
        fetchContacts();

        et2.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    Locale locale = new Locale("en", "US");
                    NumberFormat fmt = NumberFormat.getCurrencyInstance(locale);
                    et2.setText(fmt.format(Long.parseLong(et2.getText().toString())));
                }
            }
        });

        tv4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        tv5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Fetch accounts from Firebase and store the names in ArrayList
                showAccountPickerDialog();
            }
        });

        tv6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDealStageDialog();
            }
        });

        tv7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDealTypeDialog();
            }
        });

        tv10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLeadSourceDialog();
            }
        });

        tv11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Fetch contacts from Firebase and store the names in ArrayList
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
                tv5.setText(accountArray[which].toString());
                dialog.dismiss();
            }
        });
        accountDialog.create();
        accountDialog.show();
    }

    private void showContactPickerDialog(){
        final CharSequence[] contactsArray = contactsList.toArray(new CharSequence[contactsList.size()]);
        AlertDialog.Builder contactDialog = new AlertDialog.Builder(this);
        contactDialog.setTitle("Contacts");
        contactDialog.setSingleChoiceItems(contactsArray, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                tv11.setText(contactsArray[which].toString());
                dialog.dismiss();
            }
        });
        contactDialog.create();
        contactDialog.show();
    }

    private void showDatePickerDialog(){
        DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                mDay = dayOfMonth;
                mMonth = month + 1;
                mYear = year;
                String dateStr = mDay + "/" + mMonth + "/" + mYear;
                tv4.setText(dateStr);
            }
        };
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, mDateSetListener, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    private void showDealStageDialog(){
        final CharSequence[] dealStageArray = getResources().getStringArray(R.array.deal_stage);
        final AlertDialog.Builder leadDialog = new AlertDialog.Builder(this);
        leadDialog.setTitle("Deal Stages");
        leadDialog.setSingleChoiceItems(dealStageArray, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                tv6.setText(dealStageArray[which].toString());
                dialog.dismiss();
            }
        });
        leadDialog.create();
        leadDialog.show();
    }

    private void showDealTypeDialog(){
        final CharSequence[] dealTypeArray = getResources().getStringArray(R.array.deal_type);
        final AlertDialog.Builder leadDialog = new AlertDialog.Builder(this);
        leadDialog.setTitle("Deal Type");
        leadDialog.setSingleChoiceItems(dealTypeArray, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                tv7.setText(dealTypeArray[which].toString());
                dialog.dismiss();
            }
        });
        leadDialog.create();
        leadDialog.show();
    }

    private void showLeadSourceDialog(){
        final CharSequence[] leadArray = getResources().getStringArray(R.array.lead_source);
        final AlertDialog.Builder leadDialog = new AlertDialog.Builder(this);
        leadDialog.setTitle("Lead Sources");
        leadDialog.setSingleChoiceItems(leadArray, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                tv10.setText(leadArray[which].toString());
                dialog.dismiss();
            }
        });
        leadDialog.create();
        leadDialog.show();
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
        if(!chkEmt(et1) && !chkEmt(et2) && !chkEmt(et3) && !chkEmtTV(tv4) && !chkEmtTV(tv5) && !chkEmtTV(tv6)
                        && !chkEmtTV(tv7) && !chkEmt(et8) && !chkEmt(et9) && !chkEmtTV(tv10) && !chkEmtTV(tv11)){

            DatabaseReference dealsRef = dRef.child("deals");
            String key = dealsRef.push().getKey();
            String dealer = et1.getText().toString();
            String amount = et2.getText().toString();
            String dealname = et3.getText().toString();
            String deadline = tv4.getText().toString();
            String accountname = tv5.getText().toString();
            String stage = tv6.getText().toString();
            String type = tv7.getText().toString();
            String probability = et8.getText().toString();
            String nextstep = et9.getText().toString();
            String leadsource = tv10.getText().toString();
            String contactname = tv11.getText().toString();

            Deal deal = new Deal(key, dealer, amount, dealname, deadline, accountname, stage, type, probability, nextstep, leadsource, contactname);
            dealsRef.child(key).setValue(deal);
            Toast.makeText(this, "Deal Created", Toast.LENGTH_SHORT).show();
            finish();
            overridePendingTransition(R.anim.still, R.anim.slide_out_down);
        }
        else{
            Toast.makeText(this, "Please fill all values.", Toast.LENGTH_SHORT).show();
        }
    }


}

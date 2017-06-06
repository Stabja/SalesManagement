package com.stabstudio.drawernavig.Modules.Accounts;

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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.stabstudio.drawernavig.MainActivity;
import com.stabstudio.drawernavig.R;
import com.stabstudio.drawernavig.models.Account;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CreateAccountActivity extends AppCompatActivity {

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
    @BindView(R.id.create_account) Button create;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        ButterKnife.bind(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        dRef = FirebaseDatabase.getInstance().getReference("Sales");
        sRef = FirebaseStorage.getInstance().getReference();

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                postDataToServer();
            }
        });
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

    private void postDataToServer(){
        if(!chkEmt(et1) && !chkEmt(et2) && !chkEmt(et3) && !chkEmt(et4) && !chkEmt(et5) && !chkEmt(et6)
                        && !chkEmt(et7) && !chkEmt(et8) && !chkEmt(et9) && !chkEmt(et11) &&!chkEmt(et12)){

            DatabaseReference accountsRef = dRef.child("accounts");

            String key = accountsRef.push().getKey();
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

            accountsRef.child(key).setValue(temp);        //Adding in Firebase
            addDataInLocalList(temp);                     //Adding in Local List

            Toast.makeText(this, "Account Created", Toast.LENGTH_SHORT).show();
            finish();
            overridePendingTransition(R.anim.still, R.anim.slide_out_down);
        } else {
            Toast.makeText(this, "Please fill all values.", Toast.LENGTH_SHORT).show();
        }

    }

    private void addDataInLocalList(Account account){
        MainActivity.accountsList.add(account);
        MainActivity.accountsAdapter.notifyItemInserted(MainActivity.accountsList.size());
        MainActivity.accountsAdapter.notifyDataSetChanged();
    }

}

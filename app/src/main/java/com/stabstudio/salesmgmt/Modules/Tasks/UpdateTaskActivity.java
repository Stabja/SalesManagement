package com.stabstudio.salesmgmt.Modules.Tasks;

import android.app.DatePickerDialog;
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
import android.widget.Switch;
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
import com.stabstudio.salesmgmt.fragments.TasksFragment;
import com.stabstudio.salesmgmt.models.Account;
import com.stabstudio.salesmgmt.models.Contact;
import com.stabstudio.salesmgmt.models.Task;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UpdateTaskActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private FirebaseUser user;
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
    @BindView(R.id.update_task) Button update;
    @BindView(R.id.notification_trigger) Switch swt;

    private ArrayList<String> accountsList = new ArrayList<>();
    private ArrayList<String> contactsList = new ArrayList<>();

    private int mDay;
    private int mMonth;
    private int mYear;
    private String taskId;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.activity_open_translate, R.anim.activity_close_scale);
        setContentView(R.layout.activity_update_task);

        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        dRef = FirebaseDatabase.getInstance().getReference("Sales");
        sRef = FirebaseStorage.getInstance().getReference();

        Intent intent = getIntent();
        taskId = intent.getStringExtra("taskId");
        position = intent.getIntExtra("position", 0);

        fetchAccounts();
        fetchContacts();

        tv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAccountPickerDialog();
            }
        });

        tv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTaskSubjectDialog();
            }
        });

        tv3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        tv4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showContactPickerDialog();
            }
        });

        tv5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTaskStatusDialog();
            }
        });

        tv6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTaskPriorityDialog();
            }
        });

        tv7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(swt.isChecked() == false){
                    swt.setChecked(true);
                }
                else{
                    swt.setChecked(false);
                }
            }
        });

        tv8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showRepeatDialog();
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceDataToServer();
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
                tv1.setText(accountArray[which].toString());
                dialog.dismiss();
            }
        });
        accountDialog.create();
        accountDialog.show();
    }

    private void showTaskSubjectDialog(){
        final CharSequence[] taskStageArray = getResources().getStringArray(R.array.task_subject);
        final AlertDialog.Builder leadDialog = new AlertDialog.Builder(this);
        leadDialog.setTitle("Task Subject");
        leadDialog.setSingleChoiceItems(taskStageArray, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                tv2.setText(taskStageArray[which].toString());
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
                tv3.setText(dateStr);
            }
        };
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, mDateSetListener, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    private void showContactPickerDialog(){
        final CharSequence[] contactsArray = contactsList.toArray(new CharSequence[contactsList.size()]);
        AlertDialog.Builder contactDialog = new AlertDialog.Builder(this);
        contactDialog.setTitle("Contacts");
        contactDialog.setSingleChoiceItems(contactsArray, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                tv4.setText(contactsArray[which].toString());
                dialog.dismiss();
            }
        });
        contactDialog.create();
        contactDialog.show();
    }

    private void showTaskStatusDialog(){
        final CharSequence[] taskStageArray = getResources().getStringArray(R.array.task_status);
        final AlertDialog.Builder leadDialog = new AlertDialog.Builder(this);
        leadDialog.setTitle("Task Status");
        leadDialog.setSingleChoiceItems(taskStageArray, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                tv5.setText(taskStageArray[which].toString());
                dialog.dismiss();
            }
        });
        leadDialog.create();
        leadDialog.show();
    }

    private void showTaskPriorityDialog(){
        final CharSequence[] taskStageArray = getResources().getStringArray(R.array.task_priority);
        final AlertDialog.Builder leadDialog = new AlertDialog.Builder(this);
        leadDialog.setTitle("Task Priority");
        leadDialog.setSingleChoiceItems(taskStageArray, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                tv6.setText(taskStageArray[which].toString());
                dialog.dismiss();
            }
        });
        leadDialog.create();
        leadDialog.show();
    }

    private void showRepeatDialog(){
        final CharSequence[] taskStageArray = getResources().getStringArray(R.array.task_repeat);
        final AlertDialog.Builder leadDialog = new AlertDialog.Builder(this);
        leadDialog.setTitle("Repeat");
        leadDialog.setSingleChoiceItems(taskStageArray, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                tv8.setText(taskStageArray[which].toString());
                dialog.dismiss();
            }
        });
        leadDialog.create();
        leadDialog.show();
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
        TasksFragment.recyclerUtil.refreshData();
        overridePendingTransition(R.anim.activity_open_scale, R.anim.activity_close_translate);
        MainActivity.feedsAdapter.notifyDataSetChanged();
        finish();
    }

    private void loadValues(){
        DatabaseReference tasksRef = dRef.child("tasks");
        tasksRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Task temp = dataSnapshot.child(taskId).getValue(Task.class);
                tv1.setText(temp.getAssigner());
                tv2.setText(temp.getSubject());
                tv3.setText(temp.getDueDate());
                tv4.setText(temp.getAssignee());
                tv5.setText(temp.getStatus());
                tv6.setText(temp.getPriority());
                swt.setChecked(temp.isNotification());
                tv8.setText(temp.getRepeat());
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), "Cannot Load values", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean chkEmtTV(TextView textView){
        return TextUtils.isEmpty(textView.getText().toString());
    }

    private void replaceDataToServer(){
        if(!chkEmtTV(tv1) && !chkEmtTV(tv2) && !chkEmtTV(tv3) && !chkEmtTV(tv4) && !chkEmtTV(tv5) && !chkEmtTV(tv6) && !chkEmtTV(tv8)){
            DatabaseReference tasksRef = dRef.child("tasks");
            String key = taskId;
            String a = tv1.getText().toString();
            String b = tv2.getText().toString();
            String c = tv3.getText().toString();
            String d = tv4.getText().toString();
            String e = tv5.getText().toString();
            String f = tv6.getText().toString();
            Boolean g = swt.isChecked();
            String h = tv8.getText().toString();

            Task temp = new Task(key, a, b, c, d, e, f, g, h);
            tasksRef.child(taskId).setValue(temp);
            changeDataInLocalList(temp);
            TasksFragment.recyclerUtil.refreshData();
            Toast.makeText(this, "Task Updated", Toast.LENGTH_SHORT).show();
            overridePendingTransition(R.anim.activity_open_scale, R.anim.activity_close_translate);
            finish();
        }
        else{
            Toast.makeText(this, "Please fill all the values", Toast.LENGTH_SHORT).show();
        }
    }

    private void changeDataInLocalList(Task task){
        MainActivity.tasksList.set(position, task);
        MainActivity.tasksAdapter.notifyDataSetChanged();
    }

}

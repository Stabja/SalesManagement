package com.stabstudio.salesmgmt;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.stabstudio.salesmgmt.adapters.AccountsAdapter;
import com.stabstudio.salesmgmt.adapters.ContactsAdapter;
import com.stabstudio.salesmgmt.adapters.DealsAdapter;
import com.stabstudio.salesmgmt.adapters.EventsAdapter;
import com.stabstudio.salesmgmt.adapters.FeedsAdapter;
import com.stabstudio.salesmgmt.adapters.LeadsAdapter;
import com.stabstudio.salesmgmt.adapters.TasksAdapter;
import com.stabstudio.salesmgmt.fragments.AccountsFragment;
import com.stabstudio.salesmgmt.fragments.ContactsFragment;
import com.stabstudio.salesmgmt.fragments.DealsFragment;
import com.stabstudio.salesmgmt.fragments.EventsFragment;
import com.stabstudio.salesmgmt.fragments.FeedsFragment;
import com.stabstudio.salesmgmt.fragments.HomeFragment;
import com.stabstudio.salesmgmt.fragments.LeadsFragment;
import com.stabstudio.salesmgmt.fragments.TasksFragment;
import com.stabstudio.salesmgmt.models.Account;
import com.stabstudio.salesmgmt.models.Contact;
import com.stabstudio.salesmgmt.models.Deal;
import com.stabstudio.salesmgmt.models.Event;
import com.stabstudio.salesmgmt.models.Feed;
import com.stabstudio.salesmgmt.models.Lead;
import com.stabstudio.salesmgmt.models.Task;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.drawer_layout) DrawerLayout drawer;
    @BindView(R.id.nav_view) NavigationView navigationView;

    private FragmentManager manager;

    private AccountsFragment accountsFragment;
    private ContactsFragment contactsFragment;
    private LeadsFragment leadsFragment;
    private DealsFragment dealsFragment;
    private EventsFragment eventsFragment;
    private FeedsFragment feedsFragment;
    private TasksFragment tasksFragment;
    private HomeFragment homeFragment;

    public static ArrayList<Account> accountsList = new ArrayList<Account>();
    public static ArrayList<Contact> contactsList = new ArrayList<Contact>();
    public static ArrayList<Lead> leadsList = new ArrayList<Lead>();
    public static ArrayList<Deal> dealsList = new ArrayList<Deal>();
    public static ArrayList<Event> eventsList = new ArrayList<Event>();
    public static ArrayList<Feed> feedsList = new ArrayList<Feed>();
    public static ArrayList<Task> tasksList = new ArrayList<Task>();

    public static AccountsAdapter accountsAdapter;
    public static ContactsAdapter contactsAdapter;
    public static LeadsAdapter leadsAdapter;
    public static DealsAdapter dealsAdapter;
    public static EventsAdapter eventsAdapter;
    public static FeedsAdapter feedsAdapter;
    public static TasksAdapter tasksAdapter;

    public static ProgressDialog progressDialog;

    public static FirebaseAuth auth;
    public static FirebaseUser user;
    public static DatabaseReference salesRef;
    public static DatabaseReference accountsRef;
    public static DatabaseReference contactsRef;
    public static DatabaseReference leadsRef;
    public static DatabaseReference dealsRef;
    public static DatabaseReference tasksRef;
    public static DatabaseReference eventsRef;
    public static DatabaseReference feedsRef;
    public static DatabaseReference commentsRef;
    public static StorageReference storageReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.stabstudio.salesmgmt.R.layout.activity_main);
        ButterKnife.bind(this);

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Loading Data");
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
        manager = getSupportFragmentManager();

        accountsFragment = new AccountsFragment();
        contactsFragment = new ContactsFragment();
        dealsFragment = new DealsFragment();
        eventsFragment = new EventsFragment();
        feedsFragment = new FeedsFragment();
        homeFragment = new HomeFragment();
        leadsFragment = new LeadsFragment();
        tasksFragment = new TasksFragment();

        manager.beginTransaction().add(R.id.parent, homeFragment).commit();
    }


    @Override
    protected void onStart() {
        super.onStart();

        /*******************************************

        METHOD TO GENERATE TEST DATA

        1. Comment out updateData()
        2. Remove the comments from createTestAccountsData(), createTestContactsData(), createTestLeadsData()
        3. Run the apk once and then revert this back i.e
           3.1. Remove comments from updateData()
           3.2. Comment out createTestAccountsData(), createTestContactsData(), createTestLeadsData()

        *******************************************/

        updateData();
        //createTestAccountsData();
        //createTestContactsData();
        //createTestLeadsData();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch(id){
            case R.id.nav_home:
                FragmentTransaction transaction1 = manager.beginTransaction();
                transaction1.setCustomAnimations(R.anim.enter, R.anim.exit);
                transaction1.replace(R.id.parent, homeFragment).commit();
                break;
            case R.id.nav_feeds:
                FragmentTransaction transaction2 = manager.beginTransaction();
                transaction2.setCustomAnimations(R.anim.enter, R.anim.exit);
                transaction2.replace(R.id.parent, feedsFragment).commit();
                break;
            case R.id.nav_leads:
                FragmentTransaction transaction3 = manager.beginTransaction();
                transaction3.setCustomAnimations(R.anim.enter, R.anim.exit);
                transaction3.replace(R.id.parent, leadsFragment).commit();
                break;
            case R.id.nav_accounts:
                FragmentTransaction transaction4 = manager.beginTransaction();
                transaction4.setCustomAnimations(R.anim.enter, R.anim.exit);
                transaction4.replace(R.id.parent, accountsFragment).commit();
                break;
            case R.id.nav_contacts:
                FragmentTransaction transaction5 = manager.beginTransaction();
                transaction5.setCustomAnimations(R.anim.enter, R.anim.exit);
                transaction5.replace(R.id.parent, contactsFragment).commit();
                break;
            case R.id.nav_deals:
                FragmentTransaction transaction6 = manager.beginTransaction();
                transaction6.setCustomAnimations(R.anim.enter, R.anim.exit);
                transaction6.replace(R.id.parent, dealsFragment).commit();
                break;
            case R.id.nav_tasks:
                FragmentTransaction transaction7 = manager.beginTransaction();
                transaction7.setCustomAnimations(R.anim.enter, R.anim.exit);
                transaction7.replace(R.id.parent, tasksFragment).commit();
                break;
            case R.id.nav_events:
                FragmentTransaction transaction8 = manager.beginTransaction();
                transaction8.setCustomAnimations(R.anim.enter, R.anim.exit);
                transaction8.replace(R.id.parent, eventsFragment).commit();
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void updateData(){
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        salesRef = FirebaseDatabase.getInstance().getReference("Sales");
        accountsRef = salesRef.child("accounts");
        contactsRef = salesRef.child("contacts");
        leadsRef = salesRef.child("leads");
        dealsRef = salesRef.child("deals");
        tasksRef = salesRef.child("tasks");
        eventsRef = salesRef.child("events");
        feedsRef = salesRef.child("feeds");
        commentsRef = salesRef.child("comments");

        salesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                accountsList.clear();
                contactsList.clear();
                leadsList.clear();
                dealsList.clear();
                tasksList.clear();
                eventsList.clear();
                feedsList.clear();

                for(DataSnapshot snapshot : dataSnapshot.child("accounts").getChildren()){
                    Account temp = snapshot.getValue(Account.class);
                    accountsList.add(temp);
                }

                for(DataSnapshot snapshot : dataSnapshot.child("contacts").getChildren()){
                    Contact temp = snapshot.getValue(Contact.class);
                    contactsList.add(temp);
                }

                for(DataSnapshot snapshot : dataSnapshot.child("leads").getChildren()){
                    Lead temp = snapshot.getValue(Lead.class);
                    leadsList.add(temp);
                }

                for(DataSnapshot snapshot : dataSnapshot.child("deals").getChildren()){
                    Deal temp = snapshot.getValue(Deal.class);
                    dealsList.add(temp);
                }

                for(DataSnapshot snapshot : dataSnapshot.child("tasks").getChildren()){
                    Task temp = snapshot.getValue(Task.class);
                    tasksList.add(temp);
                }

                for(DataSnapshot snapshot : dataSnapshot.child("events").getChildren()){
                    Event temp = snapshot.getValue(Event.class);
                    eventsList.add(temp);
                }

                for(DataSnapshot snapshot : dataSnapshot.child("feeds").getChildren()){
                    Feed temp = snapshot.getValue(Feed.class);
                    feedsList.add(temp);
                }

                accountsAdapter = new AccountsAdapter(MainActivity.this);
                contactsAdapter = new ContactsAdapter(MainActivity.this);
                leadsAdapter = new LeadsAdapter(MainActivity.this);
                dealsAdapter = new DealsAdapter(MainActivity.this);
                eventsAdapter = new EventsAdapter(MainActivity.this);
                tasksAdapter = new TasksAdapter(MainActivity.this);
                feedsAdapter = new FeedsAdapter(MainActivity.this);

                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void createTestAccountsData(){
        salesRef = FirebaseDatabase.getInstance().getReference("Sales");
        accountsRef = salesRef.child("accounts");

        String key = accountsRef.push().getKey();
        Account account = new Account(key, "Anmol", "Saxena", "anmolsaxena@gmail.com", "8120018682", "AnmolAc", "accounts.com", "9568970898", "Distributor", "Private", "50", "55000");
        accountsRef.child(key).setValue(account);

        key = accountsRef.push().getKey();
        account = new Account(key, "Sanidhya", "Pal", "sanidhyapal@gmail.com", "9711082122", "SanidhyaAc", "accounts.com", "9568910898", "Retailer", "Private", "50", "55000");
        accountsRef.child(key).setValue(account);

        key = accountsRef.push().getKey();
        account = new Account(key, "Siddhart", "Somani", "siddhartsomani@gmail.com", "9731467295", "SiddharthAc", "accounts.com", "9562970898", "Supplier", "Private", "50", "55000");
        accountsRef.child(key).setValue(account);

        key = accountsRef.push().getKey();
        account = new Account(key, "Tofiq", "Quadri", "tofiqquadri@gmail.com", "9462192122", "TofiqAc", "accounts.com", "4568970898", "Reseller", "Private", "50", "55000");
        accountsRef.child(key).setValue(account);

        key = accountsRef.push().getKey();
        account = new Account(key, "Yashaswi", "Priyadarshi", "yashaswip@gmail.com", "9810299632", "YashaswiAc", "accounts.com", "9568970598", "Integrator", "Private", "50", "55000");
        accountsRef.child(key).setValue(account);

        key = accountsRef.push().getKey();
        account = new Account(key, "Kenneth", "Tenny", "kennethtenny@gmail.com", "9818934935", "KennyAc", "accounts.com", "9565970898", "Partner", "Private", "50", "55000");
        accountsRef.child(key).setValue(account);

        key = accountsRef.push().getKey();
        account = new Account(key, "Stabja", "Hazra", "stabjahazra@gmail.com", "7540881104", "StabjaAc", "accounts.com", "9518970898", "Integrator", "Private", "50", "55000");
        accountsRef.child(key).setValue(account);

        Toast.makeText(MainActivity.this, "Test Accounts Created", Toast.LENGTH_SHORT).show();
    }

    private void createTestContactsData(){
        salesRef = FirebaseDatabase.getInstance().getReference("Sales");
        contactsRef = salesRef.child("contacts");

        String key = contactsRef.push().getKey();
        Contact contact = new Contact(key, "url", "Anmol", "Saxena", "AnmolAc", "Employee Referral", "Marketing", "24/2/1995", "anmolsaxena@gmail.com", "8120018682", "661227", "661334", "anmolS", "linkedinurl", "twitterurl", "facebookurl");
        contactsRef.child(key).setValue(contact);

        key = contactsRef.push().getKey();
        contact = new Contact(key, "url", "Sanidhya", "Pal", "AnmolAc", "Employee Referral", "Marketing", "24/2/1996", "anmolsaxena@gmail.com", "8120018682", "661227", "661334", "anmolS", "linkedinurl", "twitterurl", "facebookurl");
        contactsRef.child(key).setValue(contact);

        key = contactsRef.push().getKey();
        contact = new Contact(key, "url", "Siddhart", "Somani", "AnmolAc", "Employee Referral", "Marketing", "24/2/1996", "anmolsaxena@gmail.com", "8120018682", "661227", "661334", "anmolS", "linkedinurl", "twitterurl", "facebookurl");
        contactsRef.child(key).setValue(contact);

        key = contactsRef.push().getKey();
        contact = new Contact(key, "url", "Kenneth", "Tenny", "AnmolAc", "Employee Referral", "Marketing", "24/2/1996", "anmolsaxena@gmail.com", "8120018682", "661227", "661334", "anmolS", "linkedinurl", "twitterurl", "facebookurl");
        contactsRef.child(key).setValue(contact);

        key = contactsRef.push().getKey();
        contact = new Contact(key, "url", "Tofiq", "Quadri", "AnmolAc", "Employee Referral", "Marketing", "24/2/1996", "anmolsaxena@gmail.com", "8120018682", "661227", "661334", "anmolS", "linkedinurl", "twitterurl", "facebookurl");
        contactsRef.child(key).setValue(contact);

        key = contactsRef.push().getKey();
        contact = new Contact(key, "url", "Yashaswi", "Priyadarshi", "AnmolAc", "Employee Referral", "Marketing", "24/2/1996", "anmolsaxena@gmail.com", "8120018682", "661227", "661334", "anmolS", "linkedinurl", "twitterurl", "facebookurl");
        contactsRef.child(key).setValue(contact);

        key = contactsRef.push().getKey();
        contact = new Contact(key, "url", "Stabja", "Hazra", "AnmolAc", "Employee Referral", "Marketing", "24/2/1996", "anmolsaxena@gmail.com", "8120018682", "661227", "661334", "anmolS", "linkedinurl", "twitterurl", "facebookurl");
        contactsRef.child(key).setValue(contact);

        Toast.makeText(MainActivity.this, "Test Contacts Created", Toast.LENGTH_SHORT).show();
    }

    private void createTestLeadsData(){
        salesRef = FirebaseDatabase.getInstance().getReference("Sales");
        leadsRef = salesRef.child("leads");

        String key = leadsRef.push().getKey();
        Lead lead = new Lead(key, "url", "Anmol", "Saxena", "Tophawks", "anmolsaxena@gmail.com", "8120018682", "anmolsaxena.com", "Cold Call", "Contacted", "ManagementISV", "500", "55,000.00", "5");
        leadsRef.child(key).setValue(lead);

        key = leadsRef.push().getKey();
        lead = new Lead(key, "url", "Sanidhya", "Pal", "Tophawks", "anmolsaxena@gmail.com", "8120018682", "anmolsaxena.com", "Cold Call", "Contacted", "ManagementISV", "500", "55,000.00", "5");
        leadsRef.child(key).setValue(lead);

        key = leadsRef.push().getKey();
        lead = new Lead(key, "url", "Siddhart", "Somani", "Tophawks", "anmolsaxena@gmail.com", "8120018682", "anmolsaxena.com", "Cold Call", "Contacted", "ManagementISV", "500", "55,000.00", "5");
        leadsRef.child(key).setValue(lead);

        key = leadsRef.push().getKey();
        lead = new Lead(key, "url", "Kenneth", "Tenny", "Tophawks", "anmolsaxena@gmail.com", "8120018682", "anmolsaxena.com", "Cold Call", "Contacted", "ManagementISV", "500", "55,000.00", "5");
        leadsRef.child(key).setValue(lead);

        key = leadsRef.push().getKey();
        lead = new Lead(key, "url", "Tofiq", "Quadri", "Tophawks", "anmolsaxena@gmail.com", "8120018682", "anmolsaxena.com", "Cold Call", "Contacted", "ManagementISV", "500", "55,000.00", "5");
        leadsRef.child(key).setValue(lead);

        key = leadsRef.push().getKey();
        lead = new Lead(key, "url", "Yashaswi", "Priyadarshi", "Tophawks", "anmolsaxena@gmail.com", "8120018682", "anmolsaxena.com", "Cold Call", "Contacted", "ManagementISV", "500", "55,000.00", "5");
        leadsRef.child(key).setValue(lead);

        key = leadsRef.push().getKey();
        lead = new Lead(key, "url", "Stabja", "Hazra", "Tophawks", "anmolsaxena@gmail.com", "8120018682", "anmolsaxena.com", "Cold Call", "Contacted", "ManagementISV", "500", "55,000.00", "5");
        leadsRef.child(key).setValue(lead);

        Toast.makeText(MainActivity.this, "Test Leads Created", Toast.LENGTH_SHORT).show();
    }

    private void createTestDealsData(){
        salesRef = FirebaseDatabase.getInstance().getReference("Sales");
        dealsRef = salesRef.child("deals");

        //Create the test data here

        Toast.makeText(MainActivity.this, "Test Deals Created", Toast.LENGTH_SHORT).show();
    }

    private void createTestTasksData(){
        salesRef = FirebaseDatabase.getInstance().getReference("Sales");
        tasksRef = salesRef.child("tasks");

        //Create the test data here.

        Toast.makeText(MainActivity.this, "Test Tasks Created", Toast.LENGTH_SHORT).show();
    }

    private void createTestEventData(){
        salesRef = FirebaseDatabase.getInstance().getReference("Sales");
        eventsRef = salesRef.child("events");

        //Create the test data here

        Toast.makeText(MainActivity.this, "Test Events Created", Toast.LENGTH_SHORT).show();
    }

    private void createTestFeedsData(){
        salesRef = FirebaseDatabase.getInstance().getReference();
        feedsRef = salesRef.child("feeds");

        //Create the test data here

        Toast.makeText(MainActivity.this, "Test Feeds Created", Toast.LENGTH_SHORT).show();
    }

}

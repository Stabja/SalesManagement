package com.stabstudio.salesmgmt.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.github.fabtransitionactivity.SheetLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.stabstudio.salesmgmt.MainActivity;
import com.stabstudio.salesmgmt.Modules.Leads.CreateLeadActivity;
import com.stabstudio.salesmgmt.R;
import com.stabstudio.salesmgmt.adapters.LeadsAdapter;
import com.stabstudio.salesmgmt.models.Lead;
import com.stabstudio.salesmgmt.utils.RecyclerUtil;


public class LeadsFragment extends Fragment {

    private SwipeRefreshLayout refreshLayout;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private FloatingActionButton mFab;
    private SheetLayout mSheetLayout;

    private RecyclerUtil recyclerUtil;
    public static LeadsFragment leadsFragment;

    private DatabaseReference dRef;
    private StorageReference sRef;

    public LeadsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View vi = inflater.inflate(R.layout.fragment_leads, container, false);

        dRef = FirebaseDatabase.getInstance().getReference("Sales");
        sRef = FirebaseStorage.getInstance().getReference();

        leadsFragment = this;

        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView = (RecyclerView) vi.findViewById(R.id.lead_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(MainActivity.leadsAdapter);

        recyclerUtil = new RecyclerUtil(getActivity(), recyclerView, "leads");
        recyclerUtil.initSwipe();

        refreshLayout = (SwipeRefreshLayout) vi.findViewById(R.id.lead_refresh_layout);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //refreshData();
                recyclerUtil.refreshData("leads", recyclerView, refreshLayout);
            }
        });

        mFab = (FloatingActionButton) vi.findViewById(R.id.lead_fab);
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSheetLayout.expandFab();
            }
        });

        mSheetLayout = (SheetLayout) vi.findViewById(R.id.lead_bottom_sheet);
        mSheetLayout.setFab(mFab);
        mSheetLayout.setFabAnimationEndListener(new SheetLayout.OnFabAnimationEndListener() {
            @Override
            public void onFabAnimationEnd() {
                Intent intent = new Intent(getActivity(), CreateLeadActivity.class);
                startActivityForResult(intent, 100);
                getActivity().overridePendingTransition(R.anim.slide_in_up, R.anim.still);
            }
        });

        return vi;
    }

    public void refreshData(){
        DatabaseReference leadsRef = dRef.child("leads");
        leadsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                MainActivity.leadsList.clear();
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Lead lead = snapshot.getValue(Lead.class);
                    MainActivity.leadsList.add(lead);
                }
                MainActivity.leadsAdapter = new LeadsAdapter(getActivity());
                recyclerView.setAdapter(MainActivity.leadsAdapter);
                refreshLayout.setRefreshing(false);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getActivity(), "Failed to Refresh Data", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 100){
            mSheetLayout.contractFab();
        }
    }
}

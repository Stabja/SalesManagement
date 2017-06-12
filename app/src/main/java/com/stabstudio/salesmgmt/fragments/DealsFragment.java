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
import android.widget.LinearLayout;
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
import com.stabstudio.salesmgmt.Modules.Deals.CreateDealActivity;
import com.stabstudio.salesmgmt.R;
import com.stabstudio.salesmgmt.adapters.DealsAdapter;
import com.stabstudio.salesmgmt.models.Deal;
import com.stabstudio.salesmgmt.utils.RecyclerUtil;

import butterknife.BindView;
import butterknife.ButterKnife;


public class DealsFragment extends Fragment {

    private SwipeRefreshLayout refreshLayout;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private FloatingActionButton mFab;
    private SheetLayout mSheetLayout;

    LinearLayout progressLayout;
    public static RecyclerUtil recyclerUtil;
    public static DealsFragment dealsFragment;

    private DatabaseReference dRef;
    private StorageReference sRef;

    public DealsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View vi = inflater.inflate(R.layout.fragment_deals, container, false);

        progressLayout = (LinearLayout) vi.findViewById(R.id.progress_layout);
        progressLayout.setVisibility(View.VISIBLE);
        dRef = FirebaseDatabase.getInstance().getReference("Sales");
        sRef = FirebaseStorage.getInstance().getReference();

        dealsFragment = this;

        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView = (RecyclerView) vi.findViewById(R.id.deal_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        //recyclerView.setAdapter(MainActivity.dealsAdapter);

        refreshLayout = (SwipeRefreshLayout) vi.findViewById(R.id.deal_refresh_layout);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                recyclerUtil.refreshData();
            }
        });

        recyclerUtil = new RecyclerUtil("deals", getActivity(), recyclerView, refreshLayout);
        recyclerUtil.initSwipe();

        mFab = (FloatingActionButton) vi.findViewById(R.id.deal_fab);
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSheetLayout.expandFab();
            }
        });

        mSheetLayout = (SheetLayout) vi.findViewById(R.id.deal_bottom_sheet);
        mSheetLayout.setFab(mFab);
        mSheetLayout.setFabAnimationEndListener(new SheetLayout.OnFabAnimationEndListener() {
            @Override
            public void onFabAnimationEnd() {
                Intent intent = new Intent(getActivity(), CreateDealActivity.class);
                startActivityForResult(intent, 100);
                getActivity().overridePendingTransition(R.anim.slide_in_up, R.anim.still);
            }
        });

        loadValues();

        return vi;
    }

    private void loadValues(){
        DatabaseReference dealsRef = dRef.child("deals");
        dealsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                MainActivity.dealsList.clear();
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Deal temp = snapshot.getValue(Deal.class);
                    MainActivity.dealsList.add(temp);
                }
                MainActivity.dealsAdapter = new DealsAdapter(getActivity());
                recyclerView.setAdapter(MainActivity.dealsAdapter);
                progressLayout.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

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
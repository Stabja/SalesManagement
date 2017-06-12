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
import com.stabstudio.salesmgmt.Modules.Tasks.CreateTaskActivity;
import com.stabstudio.salesmgmt.R;
import com.stabstudio.salesmgmt.adapters.TasksAdapter;
import com.stabstudio.salesmgmt.models.Task;
import com.stabstudio.salesmgmt.utils.RecyclerUtil;

import butterknife.BindView;
import butterknife.ButterKnife;


public class TasksFragment extends Fragment {

    private SwipeRefreshLayout refreshLayout;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private FloatingActionButton mFab;
    private SheetLayout mSheetLayout;

    LinearLayout progressLayout;
    public static RecyclerUtil recyclerUtil;
    public static TasksFragment tasksFragment;

    private DatabaseReference dRef;
    private StorageReference sRef;

    public TasksFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View vi = inflater.inflate(R.layout.fragment_tasks, container, false);

        progressLayout = (LinearLayout) vi.findViewById(R.id.progress_layout);
        progressLayout.setVisibility(View.VISIBLE);
        dRef = FirebaseDatabase.getInstance().getReference("Sales");
        sRef = FirebaseStorage.getInstance().getReference();

        tasksFragment = this;

        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView = (RecyclerView) vi.findViewById(R.id.task_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        //recyclerView.setAdapter(MainActivity.tasksAdapter);

        refreshLayout = (SwipeRefreshLayout) vi.findViewById(R.id.task_refresh_layout);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                recyclerUtil.refreshData();
            }
        });

        recyclerUtil = new RecyclerUtil("tasks", getActivity(), recyclerView, refreshLayout);
        recyclerUtil.initSwipe();

        mFab = (FloatingActionButton) vi.findViewById(R.id.task_fab);
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSheetLayout.expandFab();
            }
        });

        mSheetLayout = (SheetLayout) vi.findViewById(R.id.task_bottom_sheet);
        mSheetLayout.setFab(mFab);
        mSheetLayout.setFabAnimationEndListener(new SheetLayout.OnFabAnimationEndListener() {
            @Override
            public void onFabAnimationEnd() {
                Intent intent = new Intent(getActivity(), CreateTaskActivity.class);
                startActivityForResult(intent, 100);
                getActivity().overridePendingTransition(R.anim.slide_in_up, R.anim.still);
            }
        });

        loadValues();

        return vi;
    }

    private void loadValues(){
        DatabaseReference tasksRef = dRef.child("tasks");
        tasksRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                MainActivity.tasksList.clear();
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Task temp = snapshot.getValue(Task.class);
                    MainActivity.tasksList.add(temp);
                }
                MainActivity.tasksAdapter = new TasksAdapter(getActivity());
                recyclerView.setAdapter(MainActivity.tasksAdapter);
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

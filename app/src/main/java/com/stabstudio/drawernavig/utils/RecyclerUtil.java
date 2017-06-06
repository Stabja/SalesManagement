package com.stabstudio.drawernavig.utils;


import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.stabstudio.drawernavig.MainActivity;
import com.stabstudio.drawernavig.Modules.Accounts.UpdateAccountActivity;
import com.stabstudio.drawernavig.R;
import com.stabstudio.drawernavig.fragments.AccountsFragment;
import com.stabstudio.drawernavig.models.Account;
import com.stabstudio.drawernavig.models.Contact;

import java.util.ArrayList;

public class RecyclerUtil{

    private Activity activity;
    private AccountsFragment accountsFragment;
    private Paint paint;
    private String fromFragment;
    private RecyclerView recyclerView;

    private ArrayList<String> countries = new ArrayList<String>();

    private FirebaseAuth auth;
    private DatabaseReference dRef;
    private StorageReference sRef;

    public RecyclerUtil(AccountsFragment accountsFragment, Activity activity, RecyclerView recyclerView, String fromFragment){
        this.activity = activity;
        this.accountsFragment = accountsFragment;
        this.recyclerView = recyclerView;
        this.fromFragment = fromFragment;
        paint = new Paint();

        dRef = FirebaseDatabase.getInstance().getReference("Sales");
        sRef = FirebaseStorage.getInstance().getReference();

        countries.add("Singapore");
        countries.add("Prague");
        countries.add("Taiwan");
        countries.add("San Fransisco");
        countries.add("New York");
    }

    public void initSwipe(){
        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                if(direction == ItemTouchHelper.LEFT){
                    startDeleteDialog(position);
                    accountsFragment.refreshData();
                }
                else if(direction == ItemTouchHelper.RIGHT){
                    startUpdateActivity(position);
                }
            }

            @Override
            public void onChildDraw(Canvas canvas, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                changeBackgroundColorOfItem(canvas, actionState, viewHolder, dX, dY);
                super.onChildDraw(canvas, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    private void changeBackgroundColorOfItem(Canvas canvas, int actionState, RecyclerView.ViewHolder viewHolder, float dX, float dY){
        Bitmap icon;
        if(actionState == ItemTouchHelper.ACTION_STATE_SWIPE){
            View itemView = viewHolder.itemView;
            float height = (float) itemView.getBottom() - (float) itemView.getTop();
            float width = height / 3;
            if(dX > 0){
                paint.setColor(Color.parseColor("#388E3C"));
                RectF background = new RectF((float) itemView.getLeft(), (float) itemView.getTop(), dX,(float) itemView.getBottom());
                canvas.drawRect(background, paint);
                icon = BitmapFactory.decodeResource(activity.getResources(), R.drawable.ic_edit_white);
                RectF icon_dest = new RectF((float) itemView.getLeft() + width ,(float) itemView.getTop() + width,(float) itemView.getLeft()+ 2*width,(float)itemView.getBottom() - width);
                canvas.drawBitmap(icon,null,icon_dest, paint);
            } else {
                paint.setColor(Color.parseColor("#D32F2F"));
                RectF background = new RectF((float) itemView.getRight() + dX, (float) itemView.getTop(),(float) itemView.getRight(), (float) itemView.getBottom());
                canvas.drawRect(background, paint);
                icon = BitmapFactory.decodeResource(activity.getResources(), R.drawable.ic_delete_white);
                RectF icon_dest = new RectF((float) itemView.getRight() - 2*width ,(float) itemView.getTop() + width,(float) itemView.getRight() - width,(float)itemView.getBottom() - width);
                canvas.drawBitmap(icon,null,icon_dest, paint);
            }
        }
    }

    private void startUpdateActivity(int position){
        Intent intent = new Intent(activity, UpdateAccountActivity.class);
        String accountId = MainActivity.accountsList.get(position).getId();
        intent.putExtra("accountId", accountId);
        intent.putExtra("position", position);
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.pop_enter, R.anim.still);
    }

    private void startDeleteDialog(final int position){
        AlertDialog.Builder deleteDialog = new AlertDialog.Builder(activity);
        deleteDialog.setMessage("Are you sure you want to remove this item?");
        deleteDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                removeItem(position);
                dialog.dismiss();

            }
        });
        deleteDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        deleteDialog.setCancelable(false);
        deleteDialog.show();
    }

    public void removeItem(final int position){
        String id = MainActivity.accountsList.get(position).getId();
        DatabaseReference accountsRef = dRef.child("accounts");
        accountsRef.child(id).removeValue();                                  //Remove from Firebase
        MainActivity.accountsList.remove(position);                           //Remove from Local List
        MainActivity.accountsAdapter.notifyItemRemoved(position);
        MainActivity.accountsAdapter.notifyItemRangeChanged(position, countries.size());
        MainActivity.accountsAdapter.notifyDataSetChanged();
    }

    public void addItem(Object item){
        if(fromFragment.equals("AccountsFragment")) {
            MainActivity.accountsList.add((Account) item);
            MainActivity.accountsAdapter.notifyItemInserted(MainActivity.accountsList.size());
        }
    }

}

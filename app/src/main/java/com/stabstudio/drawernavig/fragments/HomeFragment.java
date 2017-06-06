package com.stabstudio.drawernavig.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.fabtransitionactivity.SheetLayout;
import com.github.jhonnyx2012.horizontalpicker.DatePickerListener;
import com.github.jhonnyx2012.horizontalpicker.HorizontalPicker;
import com.stabstudio.drawernavig.AddItemActivity;
import com.stabstudio.drawernavig.MainActivity;
import com.stabstudio.drawernavig.R;

import org.joda.time.DateTime;

import butterknife.BindView;


public class HomeFragment extends Fragment {

    private HorizontalPicker picker;

    public HomeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View vi = inflater.inflate(R.layout.fragment_home, container, false);

        picker = (HorizontalPicker) vi.findViewById(R.id.datePicker);
        picker.setListener(new DatePickerListener() {
            @Override
            public void onDateSelected(DateTime dateSelected) {

            }
        });
        picker.init();

        return vi;
    }

}

package com.example.triporganizer;


import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class DetailFragment extends Fragment {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup
            container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_trip_details, container, false);
        return view;
    }

    public void setTrip(Cursor c ) {
        if (c.moveToFirst()) {
            TextView etTitle = (TextView) getView().findViewById(R.id.etTitle);
            TextView etDescription = (TextView) getView().findViewById(R.id.etDescription);
            TextView etDate = (TextView) getView().findViewById(R.id.etDate);
            TextView etTime = (TextView) getView().findViewById(R.id.etTime);
            TextView etAddress = (TextView) getView().findViewById(R.id.etAddress);


            etTitle.setText(c.getString(c.getColumnIndex("title")));
            etDescription.setText(c.getString(c.getColumnIndex("description")));
            etDate.setText(c.getString(c.getColumnIndex("date")));
            etTime.setText(c.getString(c.getColumnIndex("time")));
            etAddress.setText(c.getString(c.getColumnIndex("address")));
        }
    }




}
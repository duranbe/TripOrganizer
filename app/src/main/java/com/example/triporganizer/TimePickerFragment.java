package com.example.triporganizer;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.text.format.DateFormat;

import androidx.fragment.app.DialogFragment;

public class TimePickerFragment extends DialogFragment {

    private int hour, minute ;
    TimePickerDialog.OnTimeSetListener onTimeSet;

    public TimePickerFragment(){

    }

    public void setCallBack(TimePickerDialog.OnTimeSetListener onTime){ onTimeSet = onTime;}

    @Override
    public void setArguments(Bundle args){
        super.setArguments(args);
        hour = args.getInt("year");
        minute = args.getInt("minute");

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        return new TimePickerDialog(getActivity(), onTimeSet, hour, minute,
                DateFormat.is24HourFormat(getActivity()));


    }}

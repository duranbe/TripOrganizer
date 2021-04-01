package com.example.triporganizer;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;


import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class TripDetails extends AppCompatActivity {

    private DatabaseHelper myHelper;
    private EditText etTitle;
    private EditText etDescription;
    private EditText etDate;
    private EditText etTime;
    private EditText etAddress;
    private CheckBox cbVisited;
    private TextView tvId;
    private boolean fromAdd;

    int year,month,day;
    DatePickerDialog.OnDateSetListener onDate;

    int hour,minute;
    TimePickerDialog.OnTimeSetListener onTime;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_details);
        etTitle = (EditText) findViewById(R.id.etTitle);
        etDescription = (EditText) findViewById(R.id.etDescription);
        etDate = (EditText) findViewById(R.id.etDate);
        etTime = (EditText) findViewById(R.id.etTime);
        etAddress = (EditText) findViewById(R.id.etAddress);
        cbVisited = (CheckBox) findViewById(R.id.cbVisited);

        onDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay)
            {
                year = selectedYear;
                month = selectedMonth;
                day = selectedDay;
                etDate.setText(new StringBuilder().append(month +1).
                        append("-").append(day).append("-").append(year).append(" "));
            }
        };

        onTime = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int selectedHour, int selectedMinute)
            {
                hour = selectedHour;
                minute = selectedMinute;
                etTime.setText(new StringBuilder().append(hour).
                        append(":").append(minute).append(" "));
            }
        };

        tvId = (TextView) findViewById(R.id.tvId);

        myHelper = new DatabaseHelper(this);
        myHelper.open();

        Intent intent = getIntent();
        fromAdd = intent.getBooleanExtra("fromAdd", false);

        if (!fromAdd) {
            Bundle b = intent.getExtras();
            Trip selectedTrip = b.getParcelable("SelectedTrip");
            tvId.setText(String.valueOf(selectedTrip.getId()));
            etTitle.setText(selectedTrip.getTitle());
            etDescription.setText(selectedTrip.getDescription());
            etDate.setText(selectedTrip.getDate());
            etTime.setText(selectedTrip.getTime());
            etAddress.setText(selectedTrip.getAddress());
            cbVisited.setChecked(selectedTrip.getVisited());
        }
    }

    public void onCancelClick(View v){
        finish();
    }



    private void showDatePicker() {
        DatePickerFragment date= new DatePickerFragment();
        final Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);
        Bundle args = new Bundle();
        args.putInt("year",year);
        args.putInt("month",month);
        args.putInt("day",day);
        date.setArguments(args);
        date.setCallBack(onDate);
        date.show(getSupportFragmentManager(),"Date Picker");
    }

    private void showTimePicker() {
        TimePickerFragment time = new TimePickerFragment();
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        Bundle args = new Bundle();
        args.putInt("hour",hour);
        args.putInt("minute",minute);
        time.setArguments(args);
        time.setCallBack(onTime);
        time.show(getSupportFragmentManager(),"Time Picker");
    }

    public void pickDate(View view){
        showDatePicker();
    }
    public void pickTime(View view){
        showTimePicker();
    }

    public void saveTrip(View view) {
        //Log.d("ADD", String.valueOf(fromAdd));
        String title = etTitle.getText().toString();
        String description = etDescription.getText().toString();
        String date = etDate.getText().toString();
        String time = etTime.getText().toString();
        String address = etAddress.getText().toString();
        boolean visited = cbVisited.isChecked();

        if(fromAdd) {
            Trip trip = new Trip(title,description,date,time,address,visited);
            myHelper.add(trip);

            Intent main = new Intent(this,MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(main);
        }
        else {
            Long id = Long.parseLong(tvId.getText().toString());
            Trip trip = new Trip(id,title,description,date,time,address,visited);
            int n = myHelper.update(trip);

            Intent main = new Intent(this,MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(main);
        }


    }
}

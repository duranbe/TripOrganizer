package com.example.triporganizer;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;


import androidx.appcompat.app.AppCompatActivity;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

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


        String title = etTitle.getText().toString();
        String description = etDescription.getText().toString();
        String date = etDate.getText().toString();
        String time = etTime.getText().toString();
        String address = etAddress.getText().toString();
        boolean visited = cbVisited.isChecked();

        Intent main ;

        if(fromAdd) {
            Trip trip = new Trip(title,description,date,time,address,visited);
            myHelper.add(trip);

            main = new Intent(this,MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        }
        else {
            Long id = Long.parseLong(tvId.getText().toString());
            Trip trip = new Trip(id,title,description,date,time,address,visited);
            int n = myHelper.update(trip);

            main = new Intent(this,MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        }


        SharedPreferences sharedPreferences = getSharedPreferences("PREF_NAME", Context.MODE_PRIVATE);
        String reminder = sharedPreferences.getString("reminder","1w");

        int nbWeeks = 1;
         switch (reminder){
             case "1w":
                 nbWeeks =1 ;
                 break;
             case "2w":
                 nbWeeks =2 ;
                 break;
             case "3w":
                 nbWeeks =3 ;
                 break;
         }

        try {

            final String OLD_FORMAT = "MM-dd-yyyy";
            final String NEW_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS";
            String newDateString;
            SimpleDateFormat formatter = new SimpleDateFormat(OLD_FORMAT);
            Date d = formatter.parse(date);
            formatter.applyPattern(NEW_FORMAT);
            newDateString = formatter.format(d);

            Timestamp ts = Timestamp.valueOf(newDateString);
            long tsTime= ts.getTime();

            Intent notifyIntent = new Intent(this,MyReceiver.class);
            notifyIntent.putExtra("TripTitle",title);

            PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 2, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);

            AlarmManager alarmManager = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);

            alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(1), pendingIntent);
            //alarmManager.set(AlarmManager.RTC_WAKEUP,  tsTime - TimeUnit.MINUTES.toMillis(nbWeeks*7*24*60), pendingIntent);

        } catch(Exception e) {

        }

        startActivity(main);

    }
}

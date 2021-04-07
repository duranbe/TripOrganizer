package com.example.triporganizer;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class MyReceiver extends BroadcastReceiver {
    public MyReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        Intent myIntent = new Intent(context, MyNewIntentService.class);
        myIntent.putExtra("TripTitle",intent.getStringExtra("TripTitle"));
        context.startService(myIntent);
    }
}
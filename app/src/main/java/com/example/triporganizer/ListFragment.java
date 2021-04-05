package com.example.triporganizer;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class ListFragment extends androidx.fragment.app.ListFragment {

    private DatabaseHelper myHelper;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        myHelper = new DatabaseHelper(getActivity());
        myHelper.open();
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        final String[] from = new String[]{DatabaseHelper._ID, DatabaseHelper.TITLE,
                DatabaseHelper.DESCRIPTION, DatabaseHelper.DATE, DatabaseHelper.TIME,DatabaseHelper.ADDRESS,
                DatabaseHelper.VISITED};

        final int[]to = new int[]{R.id.idTrip,R.id.tvTitle,R.id.tvDescription,R.id.tvDate,R.id.tvTime,R.id.tvAddress,R.id.tvVisited};


        Cursor c = myHelper.getAllTrips();
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(getActivity(),R.layout.trip_item_card_view,c,from,to,0);
        adapter.notifyDataSetChanged();
        setListAdapter(adapter);
    }


    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        Cursor c = (Cursor) myHelper.getOne(id);

        DetailFragment fragment = (DetailFragment) getFragmentManager().findFragmentById(R.id.detailFragment);

        if (fragment != null && fragment.isInLayout()) {

                fragment.setTrip(c);

        } else {
            Intent intent = new Intent(getActivity(),
                    TripDetails.class);
            startActivity(intent);

        }
    }
}

package com.example.triporganizer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private DatabaseHelper myHelper;

    ListView lvTrips;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myHelper = new DatabaseHelper(this);
        myHelper.open();

        lvTrips = (ListView) findViewById(R.id.lvTrip);
        lvTrips.setEmptyView(findViewById(R.id.tvEmpty));
        chargeData();

        lvTrips.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                String idItem= ((TextView)view.findViewById(R.id.idTrip)).getText().toString();
                String titleItem = ((TextView)view.findViewById(R.id.tvTitle)).getText().toString();
                String descriptionItem = ((TextView)view.findViewById(R.id.tvDescription)).getText().toString();
                String dateItem = ((TextView)view.findViewById(R.id.tvDate)).getText().toString();
                String timeItem = ((TextView)view.findViewById(R.id.tvTime)).getText().toString();
                String addressItem = ((TextView)view.findViewById(R.id.tvAddress)).getText().toString();
                Boolean visitedItem = ((TextView)view.findViewById(R.id.tvVisited)).getText().toString().equals("1");
                Trip pTrip = new Trip(Long.parseLong(idItem),titleItem,descriptionItem,dateItem,timeItem,addressItem,visitedItem);
                Intent intent = new Intent(getApplicationContext(), TripDetails.class);
                intent.putExtra("SelectedTrip",pTrip);
                intent.putExtra("fromAdd",false);
                startActivity(intent);
            }
        });

        registerForContextMenu(lvTrips);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.trip_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.new_trip:{
                Intent intent = new Intent(this,TripDetails.class);
                intent.putExtra("fromAdd",true);
                startActivity(intent);
                return true;
            }
            case R.id.search: {
                Toast.makeText(this,"Search",Toast.LENGTH_SHORT).show();
                return true;
            }
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void chargeData(){
        final String[] from = new String[]{DatabaseHelper._ID, DatabaseHelper.TITLE,
                DatabaseHelper.DESCRIPTION, DatabaseHelper.DATE, DatabaseHelper.TIME,DatabaseHelper.ADDRESS,
                DatabaseHelper.VISITED};

        final int[]to= new int[]{R.id.idTrip,R.id.tvTitle,R.id.tvDescription,R.id.tvDate,R.id.tvTime,R.id.tvAddress,R.id.tvVisited};

        Cursor c = myHelper.getAllTrips();
        SimpleCursorAdapter adapter= new SimpleCursorAdapter(this,R.layout.trip_item_card_view,c,from,to,0);
        adapter.notifyDataSetChanged();
        lvTrips.setAdapter(adapter);

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.context_menu,menu);
    }
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();

        if (item.getItemId()==R.id.delete){
            myHelper.delete(info.id);
            chargeData();
            return true;
        }

        if (item.getItemId()==R.id.open_google_maps){

            Cursor c = myHelper.getAddress(info.id);
            String trip_address = null;

            if (c.moveToFirst()){
                trip_address = c.getString(c.getColumnIndex("address"));
            }

            String map = "http://maps.google.co.in/maps?q=" + trip_address ;
            Uri gmmIntentUri = Uri.parse(map);
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
            mapIntent.setPackage("com.google.android.apps.maps");
            if (mapIntent.resolveActivity(getPackageManager()) != null) {
                startActivity(mapIntent);
            }

        }

        if (item.getItemId()==R.id.share_data){
            shareInfo(info.id);
        }

        return super.onContextItemSelected(item);
    }

    public void shareInfo(long id){
        Cursor c = myHelper.getOne(id);
        String info = null;

        if (c.moveToFirst()) {
            info = c.getString(c.getColumnIndex("title")) + " " +
                    c.getString(c.getColumnIndex("date")) + " " +
                    c.getString(c.getColumnIndex("time")) + " " +
                    c.getString(c.getColumnIndex("address"));
        }

        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, info);
        sendIntent.setType("text/plain");
        startActivity(sendIntent) ;
    }
}


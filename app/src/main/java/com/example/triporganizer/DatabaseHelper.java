package com.example.triporganizer;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private SQLiteDatabase database;

    public static final String TABLE_NAME = "TRIP";

    public static final String _ID = "_id";
    public static final String TITLE = "title";
    public static final String DESCRIPTION = "description";
    public static final String DATE = "date";
    public static final String TIME = "time";
    public static final String ADDRESS = "address";
    public static final String VISITED  = "visited";

    static final String DB_NAME = "PreciousMoments.DB";

    static final int DB_VERSION = 1;

    private static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "(" +
            _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            TITLE + " TEXT NOT NULL, " +
            DESCRIPTION + " CHAR(250)," +
            DATE + " TEXT, "+
            TIME + " TEXT," +
            ADDRESS + " TEXT," +
            VISITED + " INTEGER);" ;


    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION) ;
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion,int newVersion){

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void open() throws SQLException {
        database = this.getWritableDatabase();
    }

    public void close(){
        database.close();
    }

    public void add(Trip trip){
        ContentValues contentValues= new ContentValues();
        contentValues.put(TITLE,trip.getTitle());
        contentValues.put(DESCRIPTION,trip.getDescription());
        contentValues.put(DATE,trip.getDate());
        contentValues.put(TIME,trip.getTime());
        contentValues.put(ADDRESS,trip.getAddress());
        contentValues.put(VISITED,trip.getVisited());
        database.insert(TABLE_NAME,null,contentValues);
    }
    public int update(Trip trip) {
        Long _id= trip.getId();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DESCRIPTION, trip.getDescription());
        contentValues.put(TITLE, trip.getTitle());
        contentValues.put(DATE, trip.getDate());
        contentValues.put(TIME, trip.getTime());
        contentValues.put(ADDRESS,trip.getAddress());
        contentValues.put(VISITED,trip.getVisited());
        int count = database.update(TABLE_NAME, contentValues, this._ID + " = " + _id, null);
        return count;
    }

    public Cursor getAllTrips(){
        String[] projection = {_ID,TITLE, DESCRIPTION, DATE, TIME , ADDRESS , VISITED};
        Cursor cursor = database.query(TABLE_NAME,projection,null,null,null,null,null,null);
        return cursor;
    }

    public void delete(long _id)
    {
        database.delete(TABLE_NAME, _ID + "=" + _id, null);
    }

    public Cursor getAddress(long _id){
        String[] columns = {_ID,ADDRESS};
        String[] args = {String.valueOf(_id)};
        Cursor cursor = database.query(TABLE_NAME,columns,_ID + "=?", args,null,null,null,"1");
        return cursor;
    }


}

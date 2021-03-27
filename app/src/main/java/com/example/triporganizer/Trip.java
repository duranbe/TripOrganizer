package com.example.triporganizer;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.provider.ContactsContract;
import android.util.EventLogTags;

import androidx.annotation.RequiresApi;

public class Trip implements Parcelable {

    long id;
    String title;
    String description;
    String date;
    String time;
    String address;
    boolean visited ;

    public Trip() {

    }

    public Trip(String title, String description, String date, String time, String address, boolean visited){
        this.title = title;
        this.description =  description;
        this.date = date;
        this.time = time;
        this.address = address;
        this.visited = visited;
    }

    public Trip(long id, String title, String description, String date, String time, String address, boolean visited){
        this.id = id;
        this.title = title;
        this.description =  description;
        this.date = date;
        this.time = time;
        this.address = address;
        this.visited = visited;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime(){
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getAddress(){ return address ;}

    public void setAddress(String address){ this.address = address;}

    public boolean getVisited(){ return visited;}

    public void setVisited(boolean visited){ this.visited = visited;}


    @Override
    public int describeContents(){
        return hashCode();
    }


    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    public void writeToParcel(Parcel dest, int flags){
        dest.writeLong(id);
        dest.writeString(title);
        dest.writeString(description);
        dest.writeString(date);
        dest.writeString(time);
        dest.writeString(address);
        dest.writeBoolean(visited);

    }

    public static final Creator<Trip> CREATOR = new Creator<Trip>(){

        @Override
        public Trip createFromParcel(Parcel parcel){
            return new Trip(parcel);
        }

        @Override
        public Trip[] newArray(int size){
            return  new Trip[size];
        }
    };

    @RequiresApi(api = Build.VERSION_CODES.Q)
    public Trip(Parcel parcel){
        id = parcel.readLong();
        title = parcel.readString();
        description = parcel.readString();
        date = parcel.readString();
        time = parcel.readString();
        address = parcel.readString();
        visited = parcel.readBoolean();
    }













}

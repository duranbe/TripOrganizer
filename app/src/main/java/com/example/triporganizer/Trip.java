package com.example.triporganizer;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.provider.ContactsContract;
import android.util.EventLogTags;

import androidx.annotation.RequiresApi;

import static java.lang.Boolean.parseBoolean;

public class Trip implements Parcelable {

    long id;
    String title;
    String description;
    String date;
    String time;
    String address;
    String phone;
    boolean visited ;

    public Trip() {

    }

    public Trip(String title, String description, String date, String time, String address, String phone, boolean visited){
        this.title = title;
        this.description =  description;
        this.date = date;
        this.time = time;
        this.address = address;
        this.phone = phone;
        this.visited = visited;
    }

    public Trip(long id, String title, String description, String date, String time, String address,String phone, boolean visited){
        this.id = id;
        this.title = title;
        this.description =  description;
        this.date = date;
        this.time = time;
        this.address = address;
        this.phone = phone;
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

    public String getPhone(){return  phone;}

    public void setPhone(String phone){this.phone = phone;}

    public boolean getVisited(){ return visited;}

    public void setVisited(boolean visited){ this.visited = visited;}


    @Override
    public int describeContents(){
        return hashCode();
    }



    @Override
    public void writeToParcel(Parcel dest, int flags){
        dest.writeLong(id);
        dest.writeString(title);
        dest.writeString(description);
        dest.writeString(date);
        dest.writeString(time);
        dest.writeString(address);
        dest.writeString(phone);
        dest.writeString(String.valueOf(visited));

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


    public Trip(Parcel parcel){
        id = parcel.readLong();
        title = parcel.readString();
        description = parcel.readString();
        date = parcel.readString();
        time = parcel.readString();
        address = parcel.readString();
        phone = parcel.readString();
        visited = parseBoolean(parcel.readString());
    }













}

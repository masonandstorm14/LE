package com.example.mason.le;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by mason on 11/14/2016.
 */
public class Event {

    public double lat;
    public double lng;
    public int hour;
    public int minute;
    public String description;
    public int day;
    public int month;
    public int year;

    public Event(Object testValues) {
    }


    public void Event(){

    }

    public Event(double lat, double lng, String description, int hour, int minute, int day, int month, int year){
        this.lat = lat;
        this.lng = lng;
        this.description = description;
        this.hour = hour;
        this.minute = minute;
        this.day = day;
        this.month = month;
        this.year = year;
    }
}

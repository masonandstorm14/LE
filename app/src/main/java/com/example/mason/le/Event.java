package com.example.mason.le;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by mason on 11/14/2016.
 */
public class Event {

    protected LatLng latLng;
    protected String description;
    protected String date;
    protected String time;
    protected Boolean privacy;


    public void Event(){

    }

    public Event(LatLng latLng, String description,String date, String time, Boolean privacy){
        this.latLng = latLng;
        this.description = description;
        this.date = date;
        this.time = time;
        this.privacy = privacy;
    }
}

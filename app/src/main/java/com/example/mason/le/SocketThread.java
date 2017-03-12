package com.example.mason.le;


import android.os.Handler;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by mason on 3/9/2017.
 */

public class SocketThread extends Thread {
    private final Handler mHandler;
    private final DatabaseReference mDatabaseReference;
    private final FirebaseAuth mAuth;
    SocketThread(Handler handler, DatabaseReference databaseReference, FirebaseAuth auth){
        mHandler = handler;
        mDatabaseReference = databaseReference;
        mAuth = auth;
    }
    @Override public void run(){
        mHandler.post(new Runnable() {
            @Override
            public void run() {
               mDatabaseReference.addValueEventListener(new ValueEventListener() {
                   @Override
                   public void onDataChange(DataSnapshot dataSnapshot) {

                       //// TODO: 3/9/2017 Fix data not loading properly
                       for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                           Event event = new Event(snapshot.child("testValues").getValue());
                           map map = new map();
                           map.update(event);
                       }
                   }

                   @Override
                   public void onCancelled(DatabaseError databaseError) {

                   }
               });
            }
        });
    }

}

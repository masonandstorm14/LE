package com.example.mason.le;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;

public class createEvent extends AppCompatActivity {
    @BindView(R.id.editText_datePIcker)EditText editText_datePicker;
    @BindView(R.id.editText_timePicker)EditText editText_timePicker;
    @BindView(R.id.editText_address)EditText editText_addressPicker;
    @BindView(R.id.button_submit)Button button_submit;
    @BindView(R.id.editText_description)EditText editText_descrition;

    public int PLACE_PICKER_REQUEST = 1;
    private DatabaseReference databaseReference;
    private FirebaseAuth auth;

    //stuff for the database event
    private LatLng latLng;
    private int hour;
    private int minute;
    private String description;
    private int day;
    private int month;
    private int year;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);
        ButterKnife.bind(this);

        databaseReference = FirebaseDatabase.getInstance().getReference();
        auth = FirebaseAuth.getInstance();

        button_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(valueChecker()){
                    description = editText_descrition.getText().toString();
                    Event event = new Event(latLng.latitude, latLng.longitude, description, hour, minute, day, month, year);
                    databaseReference.child("testValues").child(auth.getCurrentUser().getUid()). child("Events").setValue(event)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            mover(map.class);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(createEvent.this, "Faild To Make Event Try Again Later", Toast.LENGTH_LONG);
                        }
                    });
                }else if(editText_addressPicker.getText().toString().equals("")){
                    Toast.makeText(createEvent.this, "Enter An Address", Toast.LENGTH_LONG).show();
                }else if(editText_datePicker.getText().toString().equals("")){
                    Toast.makeText(createEvent.this, "Enter A Date", Toast.LENGTH_LONG).show();
                }else if(editText_timePicker.getText().toString().equals("")){
                    Toast.makeText(createEvent.this, "Enter A time", Toast.LENGTH_LONG).show();
                }
            }
        });

        editText_addressPicker.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN){
                    PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                    try {
                        startActivityForResult(builder.build(createEvent.this), PLACE_PICKER_REQUEST);
                    } catch (GooglePlayServicesRepairableException e) {
                        e.printStackTrace();
                    } catch (GooglePlayServicesNotAvailableException e) {
                        e.printStackTrace();
                    }

                }
                return true;
            }
        });

        editText_datePicker.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    new DatePickerDialog(createEvent.this, dateSetListener, calendar
                            .get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                            calendar.get(Calendar.DAY_OF_MONTH)).show();

                    day = calendar.get(Calendar.DAY_OF_MONTH);
                    month = calendar.get(Calendar.MONTH);
                    year = calendar.get(Calendar.YEAR);
                }
                return true;
            }
        });

        editText_timePicker.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    Calendar currentTime = Calendar.getInstance();
                    int hours = currentTime.get(Calendar.HOUR_OF_DAY);
                    int minutes = currentTime.get(Calendar.MINUTE);
                    hour = currentTime.get(Calendar.HOUR_OF_DAY);
                    minute = currentTime.get(Calendar.MINUTE);
                    TimePickerDialog mTimePicker;
                    mTimePicker = new TimePickerDialog(createEvent.this, new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                                //fixes time from am to pm
                                if (selectedHour > 12) {
                                    selectedHour = selectedHour - 12;
                                    editText_timePicker.setText(selectedHour + ":" + selectedMinute + "pm");
                                } else if (selectedHour <= 12) {
                                    editText_timePicker.setText(selectedHour + ":" + selectedMinute + "am");
                                }
                            }
                        }, hours, minutes, false);//Yes 24 hour time
                    mTimePicker.setTitle("Select Time");
                    mTimePicker.show();
                    }
                return true;
            }
        });
    }



    Calendar calendar = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, monthOfYear);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();
        }
    };

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(data, this);
                latLng = place.getLatLng();
                editText_addressPicker.setText(place.getName());

            }
        }
    }

    private boolean valueChecker(){
        if(!editText_addressPicker.getText().toString().equals("") && !editText_timePicker.getText().toString().equals("") && !editText_datePicker.getText().toString().equals("")){
            return true;
        }else {
            return false;
        }
    }

    private void updateLabel() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        editText_datePicker.setText(sdf.format(calendar.getTime()));
    }

    protected void mover(Class move) {
        Intent i = new Intent(this, move);
        startActivity(i);
    }
}

package com.example.mason.le;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;

import com.google.firebase.database.DatabaseReference;

public class datePicker extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_picker);

        final DatePicker datePicker = (DatePicker) findViewById(R.id.datePicker);
        Button setDate = (Button) findViewById(R.id.btn_setDate);
        setDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getBaseContext(), createEvent.class);
                i.putExtra(Integer.toString(datePicker.getDayOfMonth()), "day");
                i.putExtra(Integer.toString(datePicker.getMonth()), "month");
                i.putExtra(Integer.toString(datePicker.getYear()), "year");
                startActivity(i);
            }
        });
    }
}

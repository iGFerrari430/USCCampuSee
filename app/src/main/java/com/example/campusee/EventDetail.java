package com.example.campusee;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class EventDetail extends AppCompatActivity {
    public DB_Post mEvent = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail);
        Bundle intent = getIntent().getExtras();
        //Log.d("check content: ",mEvent.toString());
        ((TextView)findViewById(R.id.event_title)).setText(intent.getString("Title"));
        ((TextView)findViewById(R.id.event_description)).setText(intent.getString("Description"));
        String date = String.format("%02d.%02d.%4d", intent.getInt("month"), intent.getInt("day"), intent.getInt("year"));
        ((TextView)findViewById(R.id.event_date)).setText(date);
        String time = String.format("%02d : %02d", intent.getInt("hour"), intent.getInt("minute"));
        ((TextView)findViewById(R.id.event_time)).setText(time);
        String email = "Contact " + intent.getString("AuthorEmail") + " if you have any questions";
        ((TextView)findViewById(R.id.event_email)).setText(email);
//        ArrayList<String> photoURL = intent.getStringArrayList("ImageUrls");
//        Log.d("check content: ", photoURL.get(0));
    }
}

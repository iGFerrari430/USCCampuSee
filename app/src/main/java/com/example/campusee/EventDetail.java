package com.example.campusee;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class EventDetail extends AppCompatActivity {
    public DB_Post mEvent = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail);
        // TODO: create the intent from user_dashboard and pass event name
//        Intent intent = getIntent();
//        mEvent = intent.getStringExtra("event");
//        ((TextView)findViewById(R.id.event_title)).setText(mName);
    }
}

package com.example.campusee;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class EventDetail extends AppCompatActivity {
    public DB_Post mEvent = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail);
//        // TODO: create the intent from user_dashboard and pass event name
//        Intent intent = getIntent();
//        mEvent = intent.getSerializableExtra("event");
//        ((TextView)findViewById(R.id.event_title)).setText(mEvent.Title);
//        ((TextView)findViewById(R.id.event_description)).setText(mEvent.Description);
//        String date = String.format("%02d.%02d.%4d", mEvent.month, mEvent.date, mEvent.year);
//        ((TextView)findViewById(R.id.event_date)).setText(date);
//        String time = String.format("%02d : %02d", mEvent.hour, mEvent.minute);
//        ((TextView)findViewById(R.id.event_time)).setText(time);
//        String email = "Contact " + mEvent.AuthorEmail + " if you have any questions";
//        ((TextView)findViewById(R.id.event_email)).setText(email);
//        ((ImageView)findViewById(R.id.event_image)).setImageURI(mEvent.DownloadUrls[]);
    }
}

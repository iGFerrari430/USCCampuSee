package com.example.campusee;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class UserEventDetail extends AppCompatActivity {
    public DB_Post mEvent = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_event_detail);
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
    }

    public void switchToSubscribee(View view) {
        TextView textView=(TextView)findViewById(R.id.subscribe);
        textView.setText("unsubscribe");
    }
}

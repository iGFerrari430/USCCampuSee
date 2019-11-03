package com.example.campusee;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class UserEventList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_event_list);
        Intent intent = getIntent();
        String email = intent.getStringExtra("Email");
        TextView textView=(TextView)findViewById(R.id.eventlist);
        textView.setText("Welcome to NormalUserDashboard, "+email+" !");
    }

}

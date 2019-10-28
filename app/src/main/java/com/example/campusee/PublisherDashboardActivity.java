package com.example.campusee;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class PublisherDashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_publisher_dashboard);

        Intent intent = getIntent();
        String email = intent.getStringExtra("Email");
        TextView textView=(TextView)findViewById(R.id.textView3);
        textView.setText("Welcome to PublisherEashboard, "+email+" !");


    }


}

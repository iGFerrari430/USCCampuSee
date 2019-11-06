package com.example.campusee;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class UserPublisherDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_publisher_detail);
        Bundle intent = getIntent().getExtras();
        ((TextView)findViewById(R.id.event_title)).setText(intent.getString("PublisherEmail"));


    }

    public void switchToSubscribee(View view) {
        TextView textView=(TextView)findViewById(R.id.subscribe);
        String curr = textView.getText().toString();

    }
}

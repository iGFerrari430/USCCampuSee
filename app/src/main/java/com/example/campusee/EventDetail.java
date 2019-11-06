package com.example.campusee;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class EventDetail extends AppCompatActivity {
    public DB_Post mEvent = null;
    String title = null;
    String authorEmail = null;
    int month = -1;
    int day = -1;
    int year = -1;
    int hour = -1;
    int minute = -1;
    String description = null;
    String uniqueID = null;
    ArrayList<String> photoURL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail);
        Bundle intent = getIntent().getExtras();
        title = intent.getString("Title");
        authorEmail = intent.getString("AuthorEmail");
        month = intent.getInt("month");
        day = intent.getInt("day");
        year = intent.getInt("year");
        hour = intent.getInt("hour");
        minute = intent.getInt("minute");
        description = intent.getString("Description");
        uniqueID = intent.getString("uniqueID");

        ((TextView)findViewById(R.id.event_title)).setText(title);
        ((TextView)findViewById(R.id.event_description)).setText("Description: " + description);
        String date = String.format("Date: %02d.%02d.%4d", month, day, year);
        ((TextView)findViewById(R.id.event_date)).setText(date);
        String time = String.format("Time: %02d : %02d", hour, minute);
        ((TextView)findViewById(R.id.event_time)).setText(time);
        String email = "Contact " + authorEmail + " if you have any questions";
        ((TextView)findViewById(R.id.event_email)).setText(email);
        photoURL = intent.getStringArrayList("ImageUrls");
        if (photoURL.size() != 0) {
            Log.d("check content: ", photoURL.get(0));
            Glide.with(getApplicationContext()).load(Uri.parse(photoURL.get(0))).into((ImageView)findViewById(R.id.event_image));
        } else {
            Log.d("check content", "No image");
        }
    }

    public void switchToPublisherDashboard(View view) {
        Intent intent = new Intent(this,PublisherDashboardActivity.class);
        intent.putExtra("Email",authorEmail);
        startActivity(intent);
    }

    public void switchToEditPost(View view) {
        Intent intent = new Intent(this,EditPostActivity.class);
        intent.putExtra("Email",authorEmail);
        intent.putExtra("title",title);
        intent.putExtra("description",description);
        intent.putExtra("year",year);
        intent.putExtra("month",month);
        intent.putExtra("day",day);
        intent.putExtra("hour",hour);
        intent.putExtra("minute",minute);
        intent.putExtra("activity", 2);
        intent.putExtra("uniqueID", uniqueID);
        intent.putStringArrayListExtra("imageURL", photoURL);
        startActivity(intent);
    }
}

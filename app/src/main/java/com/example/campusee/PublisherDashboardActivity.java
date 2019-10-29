package com.example.campusee;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class PublisherDashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_publisher_dashboard);
    }

    public void onClickCreatePost(View v){
        Intent intent = new Intent(this,EditPostActivity.class);
        startActivity(intent);
    }

}

package com.example.campusee;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.StorageReference;

import java.io.File;

public class PublisherDashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_publisher_dashboard);

        ImageView imageview = (ImageView)findViewById(R.id.imageView);
        DB_util db = new DB_util();


    }

    public void onClickCreatePost(View v){
        Intent intent = new Intent(this,EditPostActivity.class);
        Intent oldIntent = getIntent();
        String email = intent.getStringExtra("Email");
        intent.putExtra("Email",oldIntent.getStringExtra("Email"));
        startActivity(intent);
    }

}

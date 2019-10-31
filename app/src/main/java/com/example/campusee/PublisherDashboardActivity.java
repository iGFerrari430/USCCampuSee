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
        String url = "https://firebasestorage.googleapis.com/v0/b/usccampusee.appspot.com/o/1572421100618.png?alt=media&token=4d658119-e4aa-478d-b6ec-3702aa246aa4";
        DB_util db = new DB_util();
        String anotherOne = "https://firebasestorage.googleapis.com/v0/b/usccampusee.appspot.com/o/1572482013887.jpg?alt=media&token=cf5304f7-2794-4bc8-b247-177b899ce365";
        StorageReference pic = db.mStorageRef.child("1572421100618.png\n");
        Glide.with(getApplicationContext()).load(url).into(imageview);
        Glide.with(getApplicationContext()).load(anotherOne).into(imageview);


    }

    public void onClickCreatePost(View v){
        Intent intent = new Intent(this,EditPostActivity.class);
        Intent oldIntent = getIntent();
        String email = intent.getStringExtra("Email");
        intent.putExtra("Email",oldIntent.getStringExtra("Email"));
        startActivity(intent);
    }

}

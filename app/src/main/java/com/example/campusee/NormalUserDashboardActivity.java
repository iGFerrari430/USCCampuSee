package com.example.campusee;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;

public class NormalUserDashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_normal_user_dashboard);

        Intent intent = getIntent();
        String email = intent.getStringExtra("Email");
        TextView textView=(TextView)findViewById(R.id.follow);
        textView.setText("Welcome to NormalUserDashboard, "+email+" !");
    }
    public void switchtoUserEventList(View view) {
        Intent intent = new Intent(this,UserEventList.class);
        startActivity(intent);
    }


}

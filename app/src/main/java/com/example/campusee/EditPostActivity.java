package com.example.campusee;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class EditPostActivity extends AppCompatActivity {

    public TextView view = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_post);

        view = (TextView)findViewById(R.id.PictureDescription);
        view.setText("erziwawa\nceidongxi");
        Log.d("check set","check set");

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

}

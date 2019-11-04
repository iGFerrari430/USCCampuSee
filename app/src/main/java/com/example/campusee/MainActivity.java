package com.example.campusee;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_LOGIN_MESSAGE = "com.example.campusee.MESSAGE";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /** Called when the user taps the Send button */
    public void switchToLogIn(View view) {
        // Do something in response to button
        Intent intent = new Intent(this,LogInActivity.class);
        startActivity(intent);
    }

    public void switchToSignUp(View view) {
        // Do something in response to button
        Intent intent = new Intent(this,SignUpActivity.class);
        startActivity(intent);
    }
}

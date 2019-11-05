package com.example.campusee;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class UserEventDetail extends AppCompatActivity {
    public DB_Post mEvent = null;
    public DB_util db = null;
    public String userEmail = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_event_detail);
        Bundle intent = getIntent().getExtras();
        //Log.d("check content: ",mEvent.toString());
        ((TextView)findViewById(R.id.event_title)).setText(intent.getString("Title"));
        ((TextView)findViewById(R.id.event_description)).setText(intent.getString("Description"));
        String date = String.format("%02d.%02d.%4d", intent.getInt("month"), intent.getInt("day"), intent.getInt("year"));
        ((TextView)findViewById(R.id.event_date)).setText(date);
        String time = String.format("%02d : %02d", intent.getInt("hour"), intent.getInt("minute"));
        ((TextView)findViewById(R.id.event_time)).setText(time);
        userEmail = intent.getString("CurrentUser");
        String email = "Contact " + intent.getString("AuthorEmail") + " if you have any questions";
        ((TextView)findViewById(R.id.event_email)).setText(email);
        db = new DB_util();
        queryFirebase();
    }

    public void queryFirebase() {
        this.db.userCollection
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        List<String> userSubscribedPost =  new ArrayList<>();
                        if (task.isSuccessful()) {
                            Log.d("precheck:", userEmail);
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                DB_User user = document.toObject(DB_User.class);
                                Log.d("precheck2:", (user.Email));
                                if (user.Email.equals(userEmail)) {
                                    userSubscribedPost = user.subPost;
                                    Log.d("zuibadudu:", user.Email);
                                }


//                                mPublishers.add(user);
//                                Log.d("User Email", user.Email);
//                                Log.d("Print document", document.getId() + " => " + document.getData());
                            }
//                            UpdateUI(mPublishers);

                            for (int i = 0; i < userSubscribedPost.size(); i++) {
                                Log.d("User zuiba:", userSubscribedPost.get(i));
                            }
                        } else {
                            Log.d("Error documents: ", "Something wrong in query firebase");
                        }
                    }
                });
    }

    public void switchToSubscribee(View view) {
        TextView textView=(TextView)findViewById(R.id.subscribe);
        textView.setText("unsubscribe");
    }
}

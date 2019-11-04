package com.example.campusee;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class UserPublisherList extends AppCompatActivity {
    public String mEmail = null;
    public DB_util db = null;
    public ArrayList<DB_User> mPublishers = new ArrayList<>();
    public LinearLayout linearLayout = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_publisher_list);
        Intent intent = getIntent();
        db = new DB_util();
        this.mEmail = intent.getStringExtra("Email");
        this.linearLayout = findViewById(R.id.publisherListLL);
        TextView textView = findViewById(R.id.publisherList);
        textView.setText("Welcome to Publishers List, "+ this.mEmail+" !");
        queryFirebase();
        for(DB_User user : mPublishers){
            Log.d("PUB in List", user.Email);
        }

    }

    public void queryFirebase() {
        this.db.publisherCollection
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        ArrayList<DB_User> publisherList = new ArrayList<>();
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                DB_User user = document.toObject(DB_User.class);
                                mPublishers.add(user);
//                                Log.d("User Email", user.Email);
//                                Log.d("Print document", document.getId() + " => " + document.getData());
                            }
                            UpdateUI(mPublishers);
                        } else {
                            Log.d("Error documents: ", "Something wrong in query firebase");
                        }
                    }
                });
    }

    public void UpdateUI(ArrayList<DB_User> mPublishers){
        Log.d("In UpdateUI", "length of parameter is " + mPublishers.size());
        for(DB_User publisher: mPublishers){
            TextView txt1 = new TextView(this);
            txt1.setText(publisher.Email);
            LinearLayout.LayoutParams linearParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT
            );
            linearParams.setMargins(20,20,20,20);
            txt1.setLayoutParams(linearParams);

            txt1.setTextSize(TypedValue.COMPLEX_UNIT_SP,24);
            txt1.setTypeface(null, Typeface.BOLD);
            txt1.setTextColor(Color.parseColor("#0000FF"));

            txt1.setGravity(Gravity.CENTER);
            txt1.setPaintFlags(txt1.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
            linearLayout.addView(txt1);
        }
    }
}

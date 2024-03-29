package com.example.campusee;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class UserEventDetail extends AppCompatActivity {
    public DB_Post mEvent = null;
    public DB_util db = null;
    public String userEmail = null;
    public String postTitle = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_event_detail);
        Intent intent = getIntent();
        //Log.d("check content: ",mEvent.toString());
        postTitle = intent.getStringExtra("Title");
        ((TextView)findViewById(R.id.event_title)).setText(intent.getStringExtra("Title"));
        ((TextView)findViewById(R.id.event_description)).setText(intent.getStringExtra("Description"));
        String date = String.format("%02d.%02d.%4d", intent.getIntExtra("month", 0), intent.getIntExtra("day", 0), intent.getIntExtra("year", 0));
        ((TextView)findViewById(R.id.event_date)).setText(date);
        String time = String.format("%02d : %02d", intent.getIntExtra("hour", 0), intent.getIntExtra("minute", 0));
        ((TextView)findViewById(R.id.event_time)).setText(time);
        userEmail = intent.getStringExtra("CurrentUser");
        String email = "Contact " + intent.getStringExtra("AuthorEmail") + " if you have any questions";
        ((TextView)findViewById(R.id.event_email)).setText(email);
        ((TextView)findViewById(R.id.event_email)).setText(email);
        ArrayList<String> photoURL = intent.getStringArrayListExtra("ImageUrls");
        if (photoURL != null && photoURL.size() != 0) {
            Log.d("check content: ", photoURL.get(0));
            Glide.with(getApplicationContext()).load(Uri.parse(photoURL.get(0))).into((ImageView)findViewById(R.id.user_event_image));
        } else {
            Log.d("check content", "No image");
        }
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
                            Log.d("precheck:", "precheck");
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                DB_User user = document.toObject(DB_User.class);
                                Log.d("precheck2:", (user.Email));
                                if (user.Email.equals(userEmail)) {
                                    userSubscribedPost = user.subPost;
                                    Log.d("zuibadudu:", user.Email);
                                }

                            }
                            boolean postSubscribe = false;
                            for (int i = 0; i < userSubscribedPost.size(); i++) {
                                Log.d("User zuiba:", userSubscribedPost.get(i));
                                if (postTitle.equals(userSubscribedPost.get(i))) {
                                    postSubscribe = true;
                                }
                            }
                            if (postSubscribe) {
                                TextView textView=(TextView)findViewById(R.id.subscribe);
                                textView.setText("unsubscribe");
                            } else {
                                TextView textView=(TextView)findViewById(R.id.subscribe);
                                textView.setText("subscribe");
                            }


                        } else {
                            Log.d("Error documents: ", "Something wrong in query firebase");
                        }
                    }
                });
    }

    public void addPostFirebase() {
        this.db.userCollection
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        List<String> userSubscribedPost =  new ArrayList<>();
                        if (task.isSuccessful()) {
                            Log.d("precheck:", userEmail);
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                DB_User user = document.toObject(DB_User.class);
                                Log.d("precheck2:", (user.Email));
                                if (user.Email.equals(userEmail)) {
//                                    userSubscribedPost = user.subPost;
//                                    Log.d("zuibadudu:", user.Email);
                                    String uniqueId = document.getId();
                                    user.subPost.add(postTitle);
                                    db.userCollection.document(uniqueId).update("subPost", user.subPost);

                                }

                            }
                        } else {
                            Log.d("Error documents: ", "Something wrong in query firebase");
                        }
                    }
                });
    }

    public void deletePostFirebase() {
        this.db.userCollection
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        List<String> userSubscribedPost =  new ArrayList<>();
                        if (task.isSuccessful()) {
                            Log.d("precheck:", userEmail);
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                DB_User user = document.toObject(DB_User.class);
                                Log.d("precheck2:", (user.Email));
                                if (user.Email.equals(userEmail)) {
//                                    userSubscribedPost = user.subPost;
//                                    Log.d("zuibadudu:", user.Email);
                                    String uniqueId = document.getId();
                                    user.subPost.remove(postTitle);
                                    db.userCollection.document(uniqueId).update("subPost", user.subPost);

                                }

                            }
                        } else {
                            Log.d("Error documents: ", "Something wrong in query firebase");
                        }
                    }
                });
    }

    public boolean switchToSubscribee(View view) {
        TextView textView=(TextView)findViewById(R.id.subscribe);
        String curr = textView.getText().toString();
//        Log.d("Current Stat:", curr);
        if (curr.equals("subscribe")) {
            Log.d("Current Stat:", curr);
            addPostFirebase();
            textView.setText("unsubscribe");
        } else {
            Log.d("Current Stat:", curr);
            deletePostFirebase();
            textView.setText("subscribe");
        }
        return true;

    }
//    public void setSubcribeButton(View view, String text) {
//        TextView textView=(TextView)findViewById(R.id.subscribe);
//        textView.setText(text);
//    }
}

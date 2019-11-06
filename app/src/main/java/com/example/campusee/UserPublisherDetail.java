package com.example.campusee;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

public class UserPublisherDetail extends AppCompatActivity {


    public DB_util db = null;
    public String userEmail = null;
    public String publisherEmail = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_publisher_detail);
        Bundle intent = getIntent().getExtras();
        db = new DB_util();
        publisherEmail = intent.getString("PublisherEmail");
        ((TextView)findViewById(R.id.event_title)).setText(intent.getString("PublisherEmail"));
        userEmail = intent.getString("CurrentUser");
        queryFirebase();



    }

    public void queryFirebase() {
        this.db.userCollection
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        List<String> userSubscribedPublisher =  new ArrayList<>();
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                DB_User user = document.toObject(DB_User.class);
                                if (user.Email.equals(userEmail)) {
                                    userSubscribedPublisher = user.subPublisher;
                                }

                            }
                            boolean publisherSubscribe = false;
                            for (int i = 0; i < userSubscribedPublisher.size(); i++) {
                                if (publisherEmail.equals(userSubscribedPublisher.get(i))) {
                                    publisherSubscribe = true;
                                }
                            }
                            if (publisherSubscribe) {
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

    public void addPublisherFirebase() {
        this.db.userCollection
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        List<String> userSubscribedPost =  new ArrayList<>();
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                DB_User user = document.toObject(DB_User.class);
                                if (user.Email.equals(userEmail)) {
                                    String uniqueId = document.getId();
                                    user.subPublisher.add(publisherEmail);
                                    db.userCollection.document(uniqueId).update("subPublisher", user.subPublisher);

                                }

                            }
                        } else {
                            Log.d("Error documents: ", "Something wrong in query firebase");
                        }
                    }
                });
    }

    public void deletePublisherFirebase() {
        this.db.userCollection
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        List<String> userSubscribedPost =  new ArrayList<>();
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                DB_User user = document.toObject(DB_User.class);
                                if (user.Email.equals(userEmail)) {
                                    String uniqueId = document.getId();
                                    user.subPublisher.remove(publisherEmail);
                                    db.userCollection.document(uniqueId).update("subPublisher", user.subPublisher);
                                }

                            }
                        } else {
                            Log.d("Error documents: ", "Something wrong in query firebase");
                        }
                    }
                });
    }

    public void switchToSubscribee(View view) {
        TextView textView=(TextView)findViewById(R.id.subscribe);
        String curr = textView.getText().toString();
        if (curr.equals("subscribe")) {
            Log.d("Current Stat:", curr);
            addPublisherFirebase();
            textView.setText("unsubscribe");
        } else {
            Log.d("Current Stat:", curr);
            deletePublisherFirebase();
            textView.setText("subscribe");
        }

    }
}

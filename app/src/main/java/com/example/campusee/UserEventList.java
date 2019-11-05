package com.example.campusee;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class UserEventList extends AppCompatActivity {
    public DB_util db = null;
    public String mEmail = null;
    public ArrayList<DB_Post> mPosts = new ArrayList<>();
    public LinearLayout linearLayout = null;
    public String currentUser = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_event_list);
        Intent intent = getIntent();
        String email = intent.getStringExtra("Email");
        TextView textView=(TextView)findViewById(R.id.eventlist);
        textView.setText("Welcome to User Event List, "+email+" !");
        currentUser = email;
        db = new DB_util();
        mEmail = intent.getStringExtra("Email");
        Log.d("see see content? ","see see content");
        linearLayout = (LinearLayout)findViewById(R.id.userLinearLayout);
        UserEventList.GetPostsTask task = new UserEventList.GetPostsTask();
        task.execute((Void)null);
    }


    public void displayPosts(){
        Log.d("POST SIZE:::::: ",mPosts.size()+"");
        for (final DB_Post post : mPosts){
            Log.d("CHECK SIZE: ",post.DownloadUrls.toString());
            TextView txt1 = new TextView(this);
            txt1.setText(post.Title);
            //txt1.setId()
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
            txt1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    /*
                    Intent intent = new Intent(PublisherDashboardActivity.this,EventDetail.class);
                    intent.putExtra("post",post);
                    startActivity(intent);*/
                    UserEventList.ToNextActivity nTask = new UserEventList.ToNextActivity(post);
                    nTask.execute();
                }
            });

        }
    }

    public class ToNextActivity extends AsyncTask<Void,Void,Void> {
        public DB_Post mPost = null;
        public ToNextActivity(DB_Post post){
            mPost = post;
        }

        @Override
        protected void onPreExecute() {

        }
        @Override
        protected Void doInBackground(Void... users) {
            Intent intent = new Intent(UserEventList.this,UserEventDetail.class);
            intent.putStringArrayListExtra("ImageUrls",mPost.DownloadUrls);
            intent.putExtra("AuthorEmail",mPost.AuthorEmail);
            intent.putExtra("Title",mPost.Title);
            intent.putExtra("Description",mPost.Description);
            intent.putExtra("year",mPost.year);
            intent.putExtra("month",mPost.month);
            intent.putExtra("day",mPost.day);
            intent.putExtra("hour",mPost.hour);
            intent.putExtra("minute",mPost.minute);
            intent.putExtra("CurrentUser",currentUser);
            startActivity(intent);
            return (Void)null;
        }
    }


    public class GetPostsTask extends AsyncTask<Void,Void,Boolean> {
        public DB_util db = null;
        public String mEmail = null;
        public GetPostsTask(){
            this.db = UserEventList.this.db;
            this.mEmail = UserEventList.this.mEmail;
        }

        @Override
        protected void onPreExecute() {

        }
        //Actual doing in background TASK.
        @Override
        protected Boolean doInBackground(Void... users) {
            final CollectionReference col = this.db.postCollection;
            final ArrayList<Boolean> complete = new ArrayList<>();
            final ArrayList<Boolean> res = new ArrayList();
            complete.add(false);
            res.add(true);
//            Query query = col.whereEqualTo("AuthorEmail", mEmail);

            //Log.d("EMAIL IS: ",mEmail);
            Query query = col;
            query.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>(){
                @Override
                public void onSuccess(QuerySnapshot queryDocumentSnapshots){
                    Log.d("GET SUCCESS STAGE","GET SUCCESS STAGE");
                    for (DocumentSnapshot snapshot : queryDocumentSnapshots){
                        Log.d("Has somethig, right?","Has something, right?");
                        UserEventList.this.mPosts.add(snapshot.toObject(DB_Post.class));
                    }
                    complete.set(0,true);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d("FAILURE: ",e.getMessage());
                    res.set(0,false);
                    complete.set(0,true);
                }
            });

            while(!complete.get(0)){
                continue;
            }
            Log.d("CHECK CONTENT SIZE::: ",UserEventList.this.mPosts.size()+"");
            return res.get(0);
        }
        @Override
        protected void onPostExecute(Boolean result) {
            UserEventList.this.displayPosts();
        }
    }


}

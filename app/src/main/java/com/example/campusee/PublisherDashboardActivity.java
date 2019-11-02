package com.example.campusee;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.util.ArrayList;

public class PublisherDashboardActivity extends AppCompatActivity {
    public DB_util db = null;
    public String mEmail = null;
    public ArrayList<DB_Post> mPosts = new ArrayList<>();
    public LinearLayout linearLayout = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publisher_dashboard);
        db = new DB_util();
        Intent intent = getIntent();
        mEmail = intent.getStringExtra("Email");
        Log.d("see see content? ","see see content");
        linearLayout = (LinearLayout)findViewById(R.id.pubLinearLayout);
        GetPostsTask task = new GetPostsTask();
        task.execute((Void)null);
        /*
        ImageView imageview = (ImageView)findViewById(R.id.imageView);
        String url = "https://firebasestorage.googleapis.com/v0/b/usccampusee.appspot.com/o/1572421100618.png?alt=media&token=4d658119-e4aa-478d-b6ec-3702aa246aa4";

        String anotherOne = "https://firebasestorage.googleapis.com/v0/b/usccampusee.appspot.com/o/1572482013887.jpg?alt=media&token=cf5304f7-2794-4bc8-b247-177b899ce365";
        Glide.with(getApplicationContext()).load(url).into(imageview);
        Glide.with(getApplicationContext()).load(anotherOne).into(imageview);
        */
    }

    public void displayPosts(){
        for (final DB_Post post : mPosts){
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
                    ToNextActivity nTask = new ToNextActivity(post);
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
            Intent intent = new Intent(PublisherDashboardActivity.this,EventDetail.class);
            intent.putExtra("post",mPost);
            startActivity(intent);
            return (Void)null;
        }
    }


    public class GetPostsTask extends AsyncTask<Void,Void,Boolean> {
        DB_util db = null;
        public GetPostsTask(){
            this.db = PublisherDashboardActivity.this.db;
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
            Query query = col.whereEqualTo("AuthorEmail",mEmail);
            Log.d("EMAIL IS: ",mEmail);
            query.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>(){
                @Override
                public void onSuccess(QuerySnapshot queryDocumentSnapshots){
                    Log.d("GET SUCCESS STAGE","GET SUCCESS STAGE");
                    for (DocumentSnapshot snapshot : queryDocumentSnapshots){
                        Log.d("Has somethig, right?","Has something, right?");
                        PublisherDashboardActivity.this.mPosts.add(snapshot.toObject(DB_Post.class));
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
            Log.d("CHECK CONTENT SIZE::: ",PublisherDashboardActivity.this.mPosts.size()+"");
            return res.get(0);
        }
        @Override
        protected void onPostExecute(Boolean result) {
            PublisherDashboardActivity.this.displayPosts();
        }
    }

    public void onClickCreatePost(View v){
        Intent intent = new Intent(this,EditPostActivity.class);
        intent.putExtra("Email",mEmail);
        startActivity(intent);
    }

}

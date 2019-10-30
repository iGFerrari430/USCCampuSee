package com.example.campusee;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;

public class EditPostActivity extends AppCompatActivity {
    public static final int PICK_IMAGE_REQUEST = 1;
    public TextView mImageRecord = null;
    public Button mSelectFileButton = null;
    public Button mSubmitButton = null;
    public DB_util db = new DB_util();

    private ArrayList<Uri> mImageList = new ArrayList<>();
    private ArrayList<String> finishedImages = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_post);

        mImageRecord = (TextView)findViewById(R.id.PictureDescription);
        mSelectFileButton = (Button)findViewById(R.id.AddPictureButton);
        mSubmitButton = (Button)findViewById(R.id.SubmitPost);

        mSelectFileButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                openFileChooser();
            }
        });

        mSubmitButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                dealWithSubmitPost();
            }
        });



    }
    public void dealWithSubmitPost(){
        String email = getIntent().getStringExtra("Email");
    }
    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
        && data != null && data.getData() != null){
            mImageList.add(data.getData());
            String info = mImageList.size()+" Pictures Chosen";
            mImageRecord.setText(info);
        }
    }

    public class SubmitPostTask extends AsyncTask<Void,Void,Boolean> {
        public DB_util db = null;
        public String email = null;
        public PostInfo info = null;
        public SubmitPostTask(DB_util db, PostInfo info){
            this.db = db;
            this.info = info;
        }

        @Override
        protected void onPreExecute() {
            //TODO: ADD A SPINNER;
        }
        @Override
        protected Boolean doInBackground(Void... users) {




            return false;
        }
        @Override
        protected void onPostExecute(Boolean result) {
            //TODO: REMOVE THAT SPINNER.
        }
    }

}

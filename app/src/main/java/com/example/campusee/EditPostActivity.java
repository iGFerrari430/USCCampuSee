package com.example.campusee;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.HashMap;

public class EditPostActivity extends AppCompatActivity {
    public static final int PICK_IMAGE_REQUEST = 1;
    public TextView mImageRecord = null;
    public Button mSelectFileButton = null;
    public Button mSubmitButton = null;
    public DB_util db = new DB_util();
    public EditText mEditTitleView = null;
    public boolean isUpLoading = false;
    public EditText mEditDescriptionView = null;
    private ArrayList<Uri> mImageList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_post);

        mImageRecord = (TextView)findViewById(R.id.PictureDescription);
        mSelectFileButton = (Button)findViewById(R.id.AddPictureButton);
        mSubmitButton = (Button)findViewById(R.id.SubmitPost);
        mEditTitleView = findViewById(R.id.editTitle);
        mEditDescriptionView = findViewById(R.id.editDescription);
        mSelectFileButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                openFileChooser();
            }
        });

        mSubmitButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if (!EditPostActivity.this.isUpLoading){dealWithSubmitPost();}
            }
        });



    }
    public void dealWithSubmitPost(){
        String email = getIntent().getStringExtra("Email");
        String title = this.mEditTitleView.getText().toString();
        String description = this.mEditDescriptionView.getText().toString();
        PostInfo info = new PostInfo(email,description,title,this.mImageList);
        SubmitPostTask task = new SubmitPostTask(this.db,info);
        task.execute();
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

    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    public class DB_Post {
        public String AuthorEmail;
        public String Title;
        public String Description;
        public ArrayList<String> ImageUrlList;
        public DB_Post(String AuthorEmail, String Title, String Description, ArrayList<String> ImageUrlList){
            this.AuthorEmail = AuthorEmail;
            this.Title = Title;
            this.Description = Description;
            this.ImageUrlList = ImageUrlList;
        }
    }

    public class SubmitPostTask extends AsyncTask<Void,Void,Boolean> {
        private ArrayList<String> downloadUrls = new ArrayList<>();
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

            Log.d("begin:::","BEGIN DOING IN BACKGROUND...");
            final ArrayList<Boolean> completeChecker = new ArrayList<>();
            completeChecker.add(false);
            final ArrayList<Integer> imageProgressChecker = new ArrayList<>();
            imageProgressChecker.add(0);
            final ArrayList<Boolean> uploadFailure = new ArrayList<>();
            uploadFailure.add(false);
            EditPostActivity.this.isUpLoading = true;
            int ind = 0;
            final ArrayList<Boolean> isFirebaseWorking = new ArrayList<>();
            isFirebaseWorking.add(false);
            while (ind < info.imageList.size()){
                if (isFirebaseWorking.get(0)){
                    continue;
                }else if (uploadFailure.get(0)){
                    break;
                }
                isFirebaseWorking.set(0,true);
                Uri uri = info.imageList.get(ind++);
                Log.d("Now Processing:: ",uri.toString());
                final String actualUrl = System.currentTimeMillis()+"."+EditPostActivity.this.getFileExtension(uri);
                Log.d("Actual URL: ",actualUrl);
                StorageReference fileReference = db.mStorageRef.child(actualUrl);
                fileReference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        imageProgressChecker.set(0,imageProgressChecker.get(0)+1);
                        SubmitPostTask.this.downloadUrls.add(actualUrl);
                        isFirebaseWorking.set(0,false);
                    }
                }).addOnFailureListener(new OnFailureListener(){
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        uploadFailure.set(0,true);
                    }
                }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                        double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                        Log.d("PROGRESS: ",progress+"");
                    }
                });

                Log.d("this is END","this is END");

            }

            while (imageProgressChecker.get(0) < this.info.imageList.size() && !uploadFailure.get(0)){
                continue;
            }
            Log.d("VE: SUCCESS,","IMAGE UPLOADED SUCCESSFULLY");
            if (uploadFailure.get(0)){
                return false;
            }

            DB_Post post = new DB_Post(info.email,info.postTitle,info.postDescription,this.downloadUrls);
            db.db.collection("Post").add(post).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                @Override
                public void onSuccess(DocumentReference documentReference) {
                    completeChecker.set(0,true);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    completeChecker.set(0,true);
                    uploadFailure.set(0,true);
                    Log.d("啥情况,","怎么挂了post?");
                }
            });


            while(!completeChecker.get(0)){
                continue;
            }
            return !uploadFailure.get(0);
        }
        @Override
        protected void onPostExecute(Boolean result) {
            //TODO: REMOVE THAT SPINNER.
            EditPostActivity.this.isUpLoading = false;
            if (result){
                Log.d("可以了","过了");
                Toast.makeText(EditPostActivity.this, "Submit Success", Toast.LENGTH_LONG).show();
            }else{
                Log.d("最后通知","挂了");
                Toast.makeText(EditPostActivity.this, "FAIL TO SUBMIT. TRY AGAIN!", Toast.LENGTH_LONG).show();
            }


        }
    }

}

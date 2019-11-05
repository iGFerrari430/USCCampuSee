package com.example.campusee;


import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.Calendar;

public class EditPostActivity extends AppCompatActivity {

    public static final int PICK_IMAGE_REQUEST = 1;
    public TextView mImageRecord = null;
    public Button mSelectFileButton = null;
    public Button mSubmitButton = null;
    public DB_util db = new DB_util();
    public EditText mEditTitleView = null;
    public boolean isUpLoading = false;
    public EditText mEditDescriptionView = null;
    public TextView mDateSelect = null;
    public DateWrapper dateSelected = null;
    public TextView mTimeSelect = null;
    public TimeWrapper timeSelected = null;
    public DatePickerDialog.OnDateSetListener mDateSetListener;
    public TimePickerDialog.OnTimeSetListener mTimeSetListener;
    public Button mClearPicture = null;
    private ArrayList<Uri> mImageList = new ArrayList<>();
    public int mActivity = 0;
    public int mDay = -1;
    public int mMonth = -1;
    public int mYear = -1;
    public int mHour = -1;
    public int mMinute = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_post);
        Intent intent = getIntent();
        mActivity = intent.getIntExtra("activity", 1);
        if (mActivity == 2) {
            ((EditText)findViewById(R.id.editTitle)).setText(intent.getStringExtra("title"));
            ((EditText)findViewById(R.id.editDescription)).setText(intent.getStringExtra("description"));
        }
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

        mDateSelect = (TextView)findViewById(R.id.dateSelect);
        if (mActivity == 2) {
            mMonth = intent.getIntExtra("month", 0);
            mDay = intent.getIntExtra("day", 0);
            mYear = intent.getIntExtra("year", 0);
            mDateSelect.setText("" + mMonth + "/" + mDay + "/" + mYear);
            EditPostActivity.this.dateSelected = new DateWrapper(mYear,mMonth,mDay);

        }
        mDateSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                if (mActivity == 2) {
                    mMonth--;
                    cal.set(mYear, mMonth, mDay);
                } else if (mActivity ==1) {
                    cal.set(2019, 0, 1);
                }
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dialog = new DatePickerDialog(
                        EditPostActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener,year,month,day
                        );
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                Log.d("DATE: ", "onDateSet: mm/dd/yyy: " + month + "/" + day + "/" + year);

                String date = month + "/" + day + "/" + year;
                EditPostActivity.this.dateSelected = new DateWrapper(year,month,day);
                mDateSelect.setText(date);
            }
        };
        mTimeSelect = (TextView)findViewById(R.id.TimeSelect);
        if (mActivity == 2) {
            mHour = intent.getIntExtra("hour", 0);
            mMinute = intent.getIntExtra("minute", 0);
            mTimeSelect.setText("" + mHour + ":" + mMinute);
            EditPostActivity.this.timeSelected = new TimeWrapper(mHour,mMinute);
        }
        mTimeSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                cal.set(Calendar.HOUR_OF_DAY, mHour);
                cal.set(Calendar.MINUTE, mMinute);
                int hour = cal.get(Calendar.HOUR_OF_DAY);
                int minute = cal.get(Calendar.MINUTE);

                TimePickerDialog dialog = new TimePickerDialog(
                        EditPostActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mTimeSetListener,hour,minute,true
                );
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });
        mTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                String time = hourOfDay+":"+(minute < 10 ? "0" : "")+minute;
                EditPostActivity.this.timeSelected = new TimeWrapper(hourOfDay,minute);
                EditPostActivity.this.mTimeSelect.setText(time);
            }
        };

        mClearPicture = (Button)findViewById(R.id.ClearPicture);
        mClearPicture.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                EditPostActivity.this.clearImages();
            }
        });


    }

    //When submit button is clicked. begin submiting post
    public void dealWithSubmitPost(){
        String email = getIntent().getStringExtra("Email");
        String title = this.mEditTitleView.getText().toString();
        String description = this.mEditDescriptionView.getText().toString();
        if (mActivity == 1) {
            if (title.trim().isEmpty()) {
                Toast.makeText(this, "Please fill in title", Toast.LENGTH_LONG).show();
                return;
            } else if (description.trim().isEmpty()) {
                Toast.makeText(this, "Please fill in description", Toast.LENGTH_LONG).show();
                return;
            } else if (this.dateSelected == null) {
                Toast.makeText(this, "Please select date", Toast.LENGTH_LONG).show();
                return;
            } else if (this.timeSelected == null) {
                Toast.makeText(this, "Please select time", Toast.LENGTH_LONG).show();
                return;
            }
        } else if (mActivity == 2) {
            if (title.trim().isEmpty()) {
                Toast.makeText(this, "Please fill in title", Toast.LENGTH_LONG).show();
                return;
            } else if (description.trim().isEmpty()) {
                Toast.makeText(this, "Please fill in description", Toast.LENGTH_LONG).show();
                return;
            }
        }
        PostInfo info = new PostInfo(email,description,title,this.mImageList);
        SubmitPostTask task = new SubmitPostTask(this.db,info);
        task.execute();
    }

    //open file selector. only images are allowed.
    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }
    //When the file selection work is done.
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
    public void clearImages() {
        mImageRecord.setText("No Pictures Chosen");
        mImageList.clear();
    }
    // Here, we submit the post/images to firebase in the background.
    public class SubmitPostTask extends AsyncTask<Void,Void,Boolean> {
        private ArrayList<String> pathUrls = new ArrayList<>();
        private ArrayList<String> DownloadUrls = new ArrayList<>();
        public DB_util db = null;
        public String email = null;
        public PostInfo info = null;
        public SubmitPostTask(DB_util db, PostInfo info){
            this.db = db;
            this.info = info;
            this.email = info.email;
        }

        @Override
        protected void onPreExecute() {
            //TODO: ADD A LOADING SPINNER;
        }

        //Actual doing in background TASK.
        @Override
        protected Boolean doInBackground(Void... users) {
            Log.d("begin:::","BEGIN DOING IN BACKGROUND...");
            // all the below arraylist only represent one variable which is its first.
            // objects cannot be modified in anonymous inner classes, that's why a list is used.
            final ArrayList<Boolean> completeChecker = new ArrayList<>();
            completeChecker.add(false);
            final ArrayList<Integer> imageProgressChecker = new ArrayList<>();
            imageProgressChecker.add(0);
            final ArrayList<Boolean> uploadFailure = new ArrayList<>();
            uploadFailure.add(false);
            EditPostActivity.this.isUpLoading = true;
            int ind = 0;
            //begin uploading images. the loop won't exit until all images are uploaded.
            while (ind < info.imageList.size()){
                if (uploadFailure.get(0)){
                    break;
                }
                Uri uri = info.imageList.get(ind++);
                Log.d("Now Processing:: ",uri.toString());
                final String actualUrl = System.currentTimeMillis()+"."+EditPostActivity.this.getFileExtension(uri);
                Log.d("Actual URL: ",actualUrl);
                StorageReference fileReference = db.mStorageRef.child(actualUrl);
                fileReference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        imageProgressChecker.set(0,imageProgressChecker.get(0)+1);
                        SubmitPostTask.this.pathUrls.add(actualUrl);
                        SubmitPostTask.this.db.mStorageRef.child(actualUrl).getDownloadUrl().
                                addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        SubmitPostTask.this.DownloadUrls.add(uri.toString());
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                uploadFailure.set(0,true);
                            }
                        });
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
            }

            // check if not all images are uploaded and no failures occur yet.
            while ((this.pathUrls.size() < this.info.imageList.size() || this.DownloadUrls.size() < this.info.imageList.size())&& !uploadFailure.get(0)){
                continue;
            }
            Log.d("Actual SHOULD SIZE: ",this.info.imageList.size()+"");
            Log.d("PATH URL SIZE: ",this.pathUrls.size()+"");
            Log.d("Download Url Size: ",this.DownloadUrls.size()+"");
            // exit, when images fail to upload.
            if (uploadFailure.get(0)){
                return false;
            }
            Log.d("VE: SUCCESS,","IMAGE UPLOADED SUCCESSFULLY");


            DateWrapper dw = EditPostActivity.this.dateSelected;
            TimeWrapper tw = EditPostActivity.this.timeSelected;
            // now begin uploading post.
            DB_Post post = new DB_Post(info.email,info.postTitle,info.postDescription,this.pathUrls,this.DownloadUrls,dw.year,dw.month,dw.day,tw.hour,tw.minute);
//            if (mActivity == 1) {
                db.db.collection("Post").add(post).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        completeChecker.set(0, true);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        completeChecker.set(0, true);
                        uploadFailure.set(0, true);
                        Log.d("啥情况,", "怎么挂了post?");
                    }
                });
//            } else if (mActivity == 2) {
//                db.db.collection("Post").
//            }

            // don't exit when posts are being uploaded.

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
                Intent intent = new Intent(EditPostActivity.this,PublisherDashboardActivity.class);
                intent.putExtra("Email",this.email);
                startActivity(intent);
            }else{
                Log.d("最后通知","挂了");
                Toast.makeText(EditPostActivity.this, "FAIL TO SUBMIT. TRY AGAIN!", Toast.LENGTH_LONG).show();
            }
        }
    }

}
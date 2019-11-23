package com.example.campusee;
import android.content.Intent;
import android.widget.Button;
import android.widget.EditText;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import androidx.test.annotation.UiThreadTest;

import androidx.test.rule.ActivityTestRule;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class EditPostActivityWhiteBoxTest2 {

    @Rule
    public ActivityTestRule<EditPostActivity> mEditPostActivity = new ActivityTestRule<EditPostActivity>(EditPostActivity.class, false, false);

    private EditPostActivity mActivity = null;
    private Button mSubmit = null;
    private Button mAddPicture = null;
    private EditText mEditTitleView = null;
    private EditText mEditDescriptionView = null;

    @Before
    public void setUp() {
        Intent i = new Intent();
        i.putExtra("Email", "yichunlu@usc.edu");
        i.putExtra("activity", 1);
        mActivity = mEditPostActivity.launchActivity(i);
        mSubmit = (Button)mActivity.findViewById(R.id.SubmitPost);
        mAddPicture = (Button)mActivity.findViewById(R.id.AddPictureButton);
        mEditTitleView = mActivity.findViewById(R.id.editTitle);
        mEditDescriptionView = mActivity.findViewById(R.id.editDescription);
    }


    @Test
    @UiThreadTest
    public void testCorrectSubmitPost() {
//        mEditTitleView.requestFocus();
//        mEditTitleView.setText("Title for Test");
        mEditDescriptionView.requestFocus();
        mEditDescriptionView.setText("Description for Test");
        mActivity.dateSelected = new DateWrapper(1,1,1);
        mActivity.timeSelected = new TimeWrapper(0,0);
        assertFalse(mActivity.dealWithSubmitPost());
    }



}

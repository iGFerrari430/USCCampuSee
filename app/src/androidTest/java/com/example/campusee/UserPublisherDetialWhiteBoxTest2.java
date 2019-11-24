package com.example.campusee;

import android.widget.Button;
import android.widget.TextView;

import androidx.test.annotation.UiThreadTest;
import androidx.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import android.content.Intent;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;



public class UserPublisherDetialWhiteBoxTest2 {
    @Rule
    public ActivityTestRule<UserPublisherDetail> mUserPublisherDetail = new ActivityTestRule<UserPublisherDetail>(UserPublisherDetail.class);

    private UserPublisherDetail mActivity = null;
    private Button subscribeButton = null;

    @Before
    public void setUp() throws Exception {
        Intent i = new Intent();
        i.putExtra("PublisherEmail", "zhuxiaow@usc.edu");
        mActivity = mUserPublisherDetail.launchActivity(i);
        subscribeButton = (Button) mActivity.findViewById(R.id.subscribe);
        subscribeButton.setText("unsubscribe");

    }

    @Test
    @UiThreadTest
    public void testSubscribe() {
        subscribeButton.callOnClick();
        assertTrue(mActivity.switchToSubscribee(subscribeButton));
    }
}

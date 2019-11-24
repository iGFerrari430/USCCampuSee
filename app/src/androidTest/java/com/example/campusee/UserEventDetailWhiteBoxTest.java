package com.example.campusee;

import android.content.Intent;
import android.widget.Button;
import android.widget.TextView;

import androidx.test.annotation.UiThreadTest;
import androidx.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertTrue;

public class UserEventDetailWhiteBoxTest {
    @Rule
    public ActivityTestRule<UserEventDetail> mUserEventDetail = new ActivityTestRule<UserEventDetail>(UserEventDetail.class);

    private UserEventDetail mActivity = null;
    private Button subscribeButton = null;

    @Before
    public void setUp() throws Exception {
        ArrayList<String> photoURL = new ArrayList<>();
        photoURL.add("https://firebasestorage.googleapis.com/v0/b/usccampusee.appspot.com/o/1573012788625.jpg?alt=media&token=e016b720-0558-4736-b7b5-f960b8af0c5a");
        Intent i = new Intent();
        i.putExtra("AuthorEmail", "zhuxiaow@usc.edu");
        i.putExtra("Title", "Testing Event");
        i.putExtra("Description","This is an event for testing");
        i.putExtra("year",2019);
        i.putExtra("month",12);
        i.putExtra("day",25);
        i.putExtra("hour",13);
        i.putExtra("minute",15);
        i.putExtra("CurrentUser","yichunlu@usc.edu");
        i.putStringArrayListExtra("ImageUrls", photoURL);
        mActivity = mUserEventDetail.launchActivity(i);
        subscribeButton = (Button) mActivity.findViewById(R.id.subscribe);
        subscribeButton.setText("subscribe");

    }

    @Test
    @UiThreadTest
    public void testSubscribe() {
        subscribeButton.callOnClick();
        assertTrue(mActivity.switchToSubscribee(subscribeButton));
    }
}

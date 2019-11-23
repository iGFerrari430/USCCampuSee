package com.example.campusee;

import android.widget.Button;
import android.widget.EditText;

import androidx.test.annotation.UiThreadTest;
import androidx.test.rule.ActivityTestRule;
import android.content.Intent;
import android.widget.TextView;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class UserDashbroadWhiteTest {
    @Rule
    public ActivityTestRule<NormalUserDashboardActivity> mUserDashbroadActivity = new ActivityTestRule<NormalUserDashboardActivity>(NormalUserDashboardActivity.class);

    private NormalUserDashboardActivity mActivity = null;
    private Button publishers = null;
    private Button events = null;

    @Before
    public void setUp() throws Exception {
        mActivity = mUserDashbroadActivity.getActivity();
        mActivity.email = "zhuxiaow@usc.edu";
        TextView textView=(TextView)mActivity.findViewById(R.id.follow);
        textView.setText("Welcome back, "+ mActivity.email +" !");
        publishers = (Button) mActivity.findViewById(R.id.viewpublishers);
        events = (Button) mActivity.findViewById(R.id.viewevents);
    }

    @Test
    @UiThreadTest
    public void testViewEvent() {
        events.callOnClick();
        assertTrue(mActivity.switchtoUserEventList(events));
    }

    @Test
    @UiThreadTest
    public void testViewPublisher() {
        publishers.callOnClick();
        assertTrue(mActivity.switchtoUserEventList(publishers));
    }
}


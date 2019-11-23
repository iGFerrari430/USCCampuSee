package com.example.campusee;

import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import androidx.test.annotation.UiThreadTest;
import androidx.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


public class LogInActivityTestWhiteBoxTest {
    @Rule
    public ActivityTestRule<LogInActivity> mLoginActivity = new ActivityTestRule<LogInActivity>(LogInActivity.class);

    private LogInActivity mActivity = null;
    private EditText username = null;
    private EditText password = null;
    private RadioGroup type = null;
    private Button loginButton = null;

    @Before
    public void setUp() throws Exception {
        mActivity = mLoginActivity.getActivity();
        username = (EditText) mActivity.findViewById(R.id.LogIn_email);
        password = (EditText) mActivity.findViewById(R.id.LogIn_password);
        loginButton = (Button) mActivity.findViewById(R.id.LogIn_Submit);
    }

    @Test
    @UiThreadTest
    public void testCorrectPassword() {
        username.requestFocus();
        username.setText("zhuxiaow@usc.edu");
        password.requestFocus();
        password.setText("123456");
        loginButton.callOnClick();
//        assertTrue(true);
        assertTrue(mActivity.onLogin(loginButton));
    }
    @Test
    @UiThreadTest
    public void tesIncorrectPassword() {
        username.requestFocus();
        username.setText("zhuxiaow@usc.edu");
        password.requestFocus();
        password.setText("123");
        loginButton.callOnClick();
//        assertTrue(true);
        assertFalse(mActivity.onLogin(loginButton));
    }



//    @Test
//    public void onLogin() {
//    }
}
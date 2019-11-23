package com.example.campusee;

import android.widget.AutoCompleteTextView;
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

public class SignUpWhiteBoxTest {
    @Rule
    public ActivityTestRule<SignUpActivity> mSignUpActivity = new ActivityTestRule<SignUpActivity>(SignUpActivity.class);

    public SignUpActivity mActivity = null;
    public EditText usernameView = null;
    public EditText passwordView = null;
    public Button signupButton = null;
    @Before
    public void setUp() throws Exception {
        mActivity = mSignUpActivity.getActivity();
        usernameView = mActivity.findViewById(R.id.SignupEmail);
        passwordView = mActivity.findViewById(R.id.SignupPassword);
        signupButton = mActivity.findViewById(R.id.SignupSubmit);

    }

    @Test
    @UiThreadTest
    public void testEmptyPassword() {
        usernameView.requestFocus();
        usernameView.setText("zhuxiaow@usc.edu");
        passwordView.requestFocus();
        passwordView.setText("");
        assertFalse(mActivity.onSubmit(signupButton));
    }

    @Test
    @UiThreadTest
    public void testCorrectPassword() {
        usernameView.requestFocus();
        usernameView.setText("zhuxiaow@usc.edu");
        passwordView.requestFocus();
        passwordView.setText("123456");
        assertTrue(mActivity.onSubmit(signupButton));
    }

    @Test
    @UiThreadTest
    public void testEmptyEmail() {
        usernameView.requestFocus();
        usernameView.setText("");
        passwordView.requestFocus();
        passwordView.setText("123456");
        assertFalse(mActivity.onSubmit(signupButton));
    }

}

package com.example.campusee;

import android.content.Intent;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class LoginSignUpBlackBoxTest {
    @Rule
    public ActivityTestRule<SignUpActivity> mSignUpActivityRule = new ActivityTestRule<>(SignUpActivity.class,false,false);

    @Test
    public void checkSignUpUI() {
        Intent i = new Intent();
        mSignUpActivityRule.launchActivity(i);
        onView(withId(R.id.signup)).check(matches(withText("Sign Up")));
    }



}

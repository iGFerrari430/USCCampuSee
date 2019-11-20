package com.example.campusee;
import android.content.Intent;

import androidx.test.espresso.Espresso;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class PublisherBlackBoxTest {
    @Rule
    public ActivityTestRule<SignUpActivity> mSignUpActivityRule = new ActivityTestRule<>(SignUpActivity.class,false,false);

    @Test
    public void EnterEditPostPageTest() {
        Intent i = new Intent();
        mSignUpActivityRule.launchActivity(i);
        long currTime = System.currentTimeMillis();
        String email = currTime+"@ucla.edu";
        String password = currTime+"";

        onView(withId(R.id.SignupEmail)).perform(clearText(),typeText(email));
        onView(withId(R.id.SignupPassword)).perform(clearText(),typeText(password),closeSoftKeyboard());
        onView(withId(R.id.SignUp_Publisher)).perform(click());
        onView(withId(R.id.SignupSubmit)).perform(click());

        onView(withId(R.id.button4)).perform(click());

        onView(withId(R.id.SubmitPost)).check(matches(isDisplayed()));


        mSignUpActivityRule.finishActivity();
    }

    public void makePostTest() {
        Intent i = new Intent();
        mSignUpActivityRule.launchActivity(i);
        long currTime = System.currentTimeMillis();
        String email = currTime+"@ucla.edu";
        String password = currTime+"";

        onView(withId(R.id.SignupEmail)).perform(clearText(),typeText(email));
        onView(withId(R.id.SignupPassword)).perform(clearText(),typeText(password),closeSoftKeyboard());
        onView(withId(R.id.SignUp_Publisher)).perform(click());
        onView(withId(R.id.SignupSubmit)).perform(click());
        onView(withId(R.id.button4)).perform(click());
        onView(withId(R.id.SubmitPost)).check(matches(isDisplayed()));

        mSignUpActivityRule.finishActivity();



    }

}
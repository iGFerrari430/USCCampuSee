package com.example.campusee;

import android.content.Intent;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class PURELoginBlackBoxTest {
    @Rule
    public ActivityTestRule<LogInActivity> mActivityRule = new ActivityTestRule<>(LogInActivity.class,false,false);

    @Test
    public void TestLogInPasswordEmpty() {
        Intent i = new Intent();
        mActivityRule.launchActivity(i);
        //long currTime = System.currentTimeMillis();
        String email = "zhuxiaow@usc.edu";
        String password = "";

        onView(withId(R.id.LogIn_email)).perform(typeText(email),closeSoftKeyboard());
        onView(withId(R.id.LogIn_password)).perform(typeText(password),closeSoftKeyboard());
        onView(withId(R.id.LogIn_Submit)).perform(click());

        onView(withId(R.id.loginSymbol)).check(matches(isDisplayed()));

        mActivityRule.finishActivity();
    }



    @Test
    public void TestLogInFail() {
        Intent i = new Intent();
        mActivityRule.launchActivity(i);
        //long currTime = System.currentTimeMillis();
        String email = "zhuxiaow@usc.edu";
        String password = "1234567";

        onView(withId(R.id.LogIn_email)).perform(typeText(email),closeSoftKeyboard());
        onView(withId(R.id.LogIn_password)).perform(typeText(password),closeSoftKeyboard());

        onView(withId(R.id.LogIn_Publisher)).perform(click());
        onView(withId(R.id.LogIn_Submit)).perform(click());

        onView(withId(R.id.loginSymbol)).check(matches(isDisplayed()));

        mActivityRule.finishActivity();

    }


    @Test
    public void TestLogInPublisherSuccess() {
        Intent i = new Intent();
        mActivityRule.launchActivity(i);
        String email = "zhuxiaow@usc.edu";
        String password = "123456";

        onView(withId(R.id.LogIn_email)).perform(typeText(email),closeSoftKeyboard());
        onView(withId(R.id.LogIn_password)).perform(typeText(password),closeSoftKeyboard());

        onView(withId(R.id.LogIn_Publisher)).perform(click());
        onView(withId(R.id.LogIn_Submit)).perform(click());

        onView(withId(Integer.MAX_VALUE)).check(matches(isDisplayed()));

        mActivityRule.finishActivity();
    }




}
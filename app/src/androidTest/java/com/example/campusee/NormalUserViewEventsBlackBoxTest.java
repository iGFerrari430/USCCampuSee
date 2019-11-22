package com.example.campusee;

import android.content.Context;
import android.content.Intent;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.assertEquals;



@RunWith(AndroidJUnit4.class)
public class NormalUserViewEventsBlackBoxTest {
    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(MainActivity.class,false,false);

//    @Test
//    public void testUI() {
//        Intent i = new Intent();
//        mActivityRule.launchActivity(i);
//        onView(withId(R.id.MainPageSymbol)).check(matches(withText("USC CampuSee")));
//        mActivityRule.finishActivity();
//    }

    @Test
    public void PostDetailTest(){
        Intent i = new Intent();
        mActivityRule.launchActivity(i);
        onView(withId(R.id.Main_LogIn)).perform(click());
        String email = "TestPostDetail@usc.edu";
        String password = "123456";

        onView(withId(R.id.LogIn_email)).perform(typeText(email),closeSoftKeyboard());
        onView(withId(R.id.LogIn_password)).perform(typeText(password),closeSoftKeyboard());

        onView(withId(R.id.LogIn_Submit)).perform(click());

        onView(withId(R.id.follow)).check(matches(isDisplayed()));
        mActivityRule.finishActivity();
    }

    @Test
    public void ViewPublisher() {
        Intent i = new Intent();
        mActivityRule.launchActivity(i);
        onView(withId(R.id.Main_LogIn)).perform(click());
        String email = "yichunlu@usc.edu";
        String password = "123456";

        onView(withId(R.id.LogIn_email)).perform(typeText(email),closeSoftKeyboard());
        onView(withId(R.id.LogIn_password)).perform(typeText(password),closeSoftKeyboard());

        onView(withId(R.id.LogIn_Submit)).perform(click());

        onView(withId(R.id.viewpublishers)).perform(click());

        onView(withText("Our Publishers:")).check(matches(isDisplayed()));
        mActivityRule.finishActivity();
    }

    @Test
    public void EnterPublisherPageTest() {
        Intent i = new Intent();
        mActivityRule.launchActivity(i);
        onView(withId(R.id.Main_Signup)).perform(click());
        long currTime = System.currentTimeMillis();
        String email = currTime+"@ucla.edu";
        String password = currTime+"";

        onView(withId(R.id.SignupEmail)).perform(clearText(),typeText(email));
        onView(withId(R.id.SignupPassword)).perform(clearText(),typeText(password),closeSoftKeyboard());
        onView(withId(R.id.SignupSubmit)).perform(click());

        onView(withId(R.id.viewpublishers)).perform(click());
        onView(withId(Integer.MAX_VALUE)).perform(click());
        onView(withId(R.id.subscribe)).check(matches(isDisplayed()));


        mActivityRule.finishActivity();
    }

    @Test
    public void SubscribePublisherTest() {
        Intent i = new Intent();
        mActivityRule.launchActivity(i);
        onView(withId(R.id.Main_Signup)).perform(click());
        long currTime = System.currentTimeMillis();
        String email = currTime+"@ucla.edu";
        String password = currTime+"";

        onView(withId(R.id.SignupEmail)).perform(clearText(),typeText(email));
        onView(withId(R.id.SignupPassword)).perform(clearText(),typeText(password),closeSoftKeyboard());
        onView(withId(R.id.SignupSubmit)).perform(click());

        onView(withId(R.id.viewpublishers)).perform(click());
        onView(withId(Integer.MAX_VALUE)).perform(click());
        onView(withId(R.id.subscribe)).perform(click());
        onView(withId(R.id.subscribe)).check(matches(withText("UNSUBSCRIBE")));


        mActivityRule.finishActivity();
    }


    @Test
    public void ViewEvent() {
        Intent i = new Intent();
        mActivityRule.launchActivity(i);
        onView(withId(R.id.Main_LogIn)).perform(click());
        String email = "yichunlu@usc.edu";
        String password = "123456";

        onView(withId(R.id.LogIn_email)).perform(typeText(email),closeSoftKeyboard());
        onView(withId(R.id.LogIn_password)).perform(typeText(password),closeSoftKeyboard());

        onView(withId(R.id.LogIn_Submit)).perform(click());

        onView(withId(R.id.viewevents)).perform(click());

        onView(withText("Our Events:")).check(matches(isDisplayed()));
        mActivityRule.finishActivity();
    }

    @Test
    public void SubscribeEventTest() {
        Intent i = new Intent();
        mActivityRule.launchActivity(i);
        onView(withId(R.id.Main_Signup)).perform(click());
        long currTime = System.currentTimeMillis();
        String email = currTime+"@ucla.edu";
        String password = currTime+"";

        onView(withId(R.id.SignupEmail)).perform(clearText(),typeText(email));
        onView(withId(R.id.SignupPassword)).perform(clearText(),typeText(password),closeSoftKeyboard());
        onView(withId(R.id.SignupSubmit)).perform(click());

        onView(withId(R.id.viewevents)).perform(click());
        onView(withId(Integer.MAX_VALUE)).perform(click());
        onView(withId(R.id.subscribe)).perform(click());
        onView(withId(R.id.subscribe)).check(matches(withText("UNSUBSCRIBE")));


        mActivityRule.finishActivity();
    }


}
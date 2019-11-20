package com.example.campusee;
import android.content.Intent;
import android.widget.DatePicker;
import android.widget.TimePicker;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.contrib.PickerActions;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;

import org.hamcrest.Matchers;
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
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.not;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class PublisherBlackBoxTest {
    @Rule
    public ActivityTestRule<SignUpActivity> mSignUpActivityRule = new ActivityTestRule<>(SignUpActivity.class,false,false);

    @Test
    public void testSignUpUI() {
        Intent i = new Intent();
        mSignUpActivityRule.launchActivity(i);
        onView(withId(R.id.signup)).check(matches(withText("Sign Up")));
        mSignUpActivityRule.finishActivity();
    }

    /*
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
    @Test
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

        onView(withId(R.id.editTitle)).perform(clearText(),typeText("James Harden's Birthday"),closeSoftKeyboard());
        onView(withId(R.id.editDescription)).perform(clearText(),typeText("Is Aug 26th, 1989"),closeSoftKeyboard());



        onView(withId(R.id.dateSelect)).perform(click());
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName()))).perform(PickerActions.setDate(1989, 8, 26));
        onView(withText("OK")).perform(click());


        onView(withId(R.id.TimeSelect)).perform(click());
        onView(withClassName(Matchers.equalTo(TimePicker.class.getName()))).perform(PickerActions.setTime(9,25));
        onView(withText("OK")).perform(click());

        onView(withId(R.id.SubmitPost)).perform(click());

        onView(withText("James Harden's Birthday")).check(matches(isDisplayed()));


        mSignUpActivityRule.finishActivity();



    }
    */


}

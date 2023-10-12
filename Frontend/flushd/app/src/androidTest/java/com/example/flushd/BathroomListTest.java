package com.example.flushd;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isClickable;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class BathroomListTest {

    @Rule
    public ActivityScenarioRule<BathroomList> rule = new ActivityScenarioRule<>(BathroomList.class);

    @Test
    public void screenSetup() {
        onView(withId(R.id.BathroomCreate)).check(matches(isClickable()));
        onView(withId(R.id.rvBathrooms)).check(matches(isDisplayed()));
    }

    @Test
    public void createBathroom(){
        onView(withId(R.id.BathroomCreate)).perform(click());

        // Put thread to sleep to allow volley to handle the request
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            // Nothing
        }

        onView(withClassName(new Matcher<String>() {
            @Override
            public boolean matches(Object item) {
                return true;
            }

            @Override
            public void describeMismatch(Object item, Description mismatchDescription) {

            }

            @Override
            public void _dont_implement_Matcher___instead_extend_BaseMatcher_() {

            }

            @Override
            public void describeTo(Description description) {

            }
        }));
    }
}
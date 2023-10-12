package com.example.flushd;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.action.CoordinatesProvider;
import androidx.test.espresso.action.GeneralClickAction;
import androidx.test.espresso.action.Press;
import androidx.test.espresso.action.Tap;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static androidx.test.espresso.matcher.ViewMatchers.isClickable;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;

import static java.util.EnumSet.allOf;

import android.view.View;
import android.widget.TextView;

import com.example.flushd.utils.SHARED;

@RunWith(AndroidJUnit4.class)
public class BathroomCreateTest {
    // Used for delaying the process while volley handles the request
    private static final int SIMULATED_DELAY_MS = 500;

    //@Rule
    //public ActivityScenarioRule<BathroomCreate> bathroomCreateActivityScenarioRule = new ActivityScenarioRule<>(BathroomCreate.class);

    @Rule
    public ActivityScenarioRule<BathroomCreate> rule = new ActivityScenarioRule<>(BathroomCreate.class);

    @Test
    public void desiredScreenSetup(){
        onView(withId(R.id.Cancel)).check(matches(isClickable()));
        onView(withId(R.id.reviewHeader)).check(matches(isDisplayed()));
        onView(withId(R.id.buildingText)).check(matches(isDisplayed()));
        onView(withId(R.id.building)).check(matches(isDisplayed()));
        onView(withId(R.id.floorText)).check(matches(isDisplayed()));
        onView(withId(R.id.floor)).check(matches(isDisplayed()));
        onView(withId(R.id.numStallsText)).check(matches(isDisplayed()));
        onView(withId(R.id.numStalls)).check(matches(isDisplayed()));
        onView(withId(R.id.numUrinalsText)).check(matches(isDisplayed()));
        onView(withId(R.id.numUrinals)).check(matches(isDisplayed()));
        onView(withId(R.id.genderText)).check(matches(isDisplayed()));
        onView(withId(R.id.gender)).check(matches(isDisplayed()));
        onView(withId(R.id.locDescriptionText)).check(matches(isDisplayed()));
        onView(withId(R.id.locDescription)).check(matches(isDisplayed()));
        onView(withId(R.id.Create)).check(matches(isClickable()));
    }

    @Test
    public void createBathroom(){
        onView(withId(R.id.building)).perform(typeText("Hawthorn"));
        onView(withId(R.id.floor)).perform(typeText("1"));
        onView(withId(R.id.numStalls)).perform(typeText("3"));
        onView(withId(R.id.numUrinals)).perform(typeText("2"));
        onView(withId(R.id.locDescription)).perform(typeText("Near door"));
        onView(withId(R.id.Create)).perform(click());

        // Put thread to sleep to allow volley to handle the request
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            // Nothing
        }
    }
}
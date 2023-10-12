package com.example.flushd;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isClickable;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.example.flushd.utils.SHARED;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {
    // Used for delaying the process while volley handles the request
    private static final int SIMULATED_DELAY_MS = 500;

    @Rule
    public ActivityScenarioRule<MainActivity> activityScenarioRule = new ActivityScenarioRule<>(MainActivity.class);
    @Test
    public void desiredScreenSetup(){
        onView(withId(R.id.username)).check(matches(isDisplayed()));
        onView(withId(R.id.password)).check(matches(isDisplayed()));
        onView(withId(R.id.login)).check(matches(isClickable()));
        onView(withId(R.id.register)).check(matches(isClickable()));
    }

    @Test
    public void invalidLogin(){
        activityScenarioRule.getScenario().onActivity(activity -> {
            activity.username.setText("not");
            activity.password.setText("valid");
        });

        onView(withId(R.id.login)).perform(click());
        // Put thread to sleep to allow volley to handle the request
        try {
            Thread.sleep(SIMULATED_DELAY_MS);
        } catch (InterruptedException e) {
            // Nothing
        }

        onView(withId(R.id.invalidLogin)).check(matches(isDisplayed()));
    }

    @Test
    public void switchToRegister(){
        onView(withId(R.id.register)).perform(click());
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

    @Test
    public void userInfoStored(){
        activityScenarioRule.getScenario().onActivity(activity -> {
            activity.username.setText("admin");
            activity.password.setText("password1");
        });

        onView(withId(R.id.login)).perform(click());
        // Put thread to sleep to allow volley to handle the request
        try {
            Thread.sleep(SIMULATED_DELAY_MS);
        } catch (InterruptedException e) {
            // Nothing
        }

        assertEquals(SHARED.getUserID() == 1, true);
        assertEquals(SHARED.getUsername().compareTo("admin") == 0, true);
        assertEquals(SHARED.getFirstName().compareTo("UG") == 0, true);
        assertEquals(SHARED.getLastName().compareTo("1") == 0, true);
        assertEquals(SHARED.getEmail().compareTo("2_UG_1@iastate.edu") == 0, true);
        assertEquals(SHARED.getAccountType().compareTo("moderator") == 0, true);
        assertEquals(SHARED.getActive() == true, true);
    }

    @Test
    public void testAllSetMethods(){
        activityScenarioRule.getScenario().onActivity(activity -> {
            activity.username.setText("admin");
            activity.password.setText("password1");
        });

        onView(withId(R.id.login)).perform(click());
        // Put thread to sleep to allow volley to handle the request
        try {
            Thread.sleep(SIMULATED_DELAY_MS);
        } catch (InterruptedException e) {
            // Nothing
        }

        SHARED.setUserID(1);
        SHARED.setAccountType("Moderator");
        SHARED.setFirstName("Test");
        SHARED.setActive(true);
        SHARED.setEmail("joke@gmail.com");
        SHARED.setPassword("fake");
        SHARED.setLastName("Person");
        SHARED.setUsername("lol");

        assertTrue(SHARED.getUserID() == 1);
        assertTrue(SHARED.getAccountType().compareTo("Moderator") == 0);
        assertTrue(SHARED.getFirstName().compareTo("Test") == 0);
        assertTrue(SHARED.getActive());
        assertTrue(SHARED.getEmail().compareTo("joke@gmail.com") == 0);
        assertTrue(SHARED.getPassword().compareTo("fake") == 0);
        assertTrue(SHARED.getLastName().compareTo("Person") == 0);
        assertTrue(SHARED.getUsername().compareTo("lol") == 0);
    }
}
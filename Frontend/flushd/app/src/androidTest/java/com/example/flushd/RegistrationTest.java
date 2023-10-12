package com.example.flushd;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isClickable;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import static org.junit.Assert.assertTrue;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.flushd.utils.SHARED;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class RegistrationTest {
    // Used for delaying the process while volley handles the request
    private static final int SIMULATED_DELAY_MS = 500;

    @Rule
    public ActivityScenarioRule<RegistrationPage> rule = new ActivityScenarioRule<>(RegistrationPage.class);

    @Test
    public void screenSetup(){
        onView(withId(R.id.cancelRegistration)).check(matches(isClickable()));
        onView(withId(R.id.register)).check(matches(isClickable()));
        onView(withId(R.id.logo)).check(matches(isDisplayed()));
        onView(withId(R.id.firstName)).check(matches(isDisplayed()));
        onView(withId(R.id.lastName)).check(matches(isDisplayed()));
        onView(withId(R.id.email)).check(matches(isDisplayed()));
        onView(withId(R.id.username)).check(matches(isDisplayed()));
        onView(withId(R.id.password)).check(matches(isDisplayed()));
        onView(withId(R.id.passwordCheck)).check(matches(isDisplayed()));
        onView(withId(R.id.userType)).check(matches(isDisplayed()));
    }

    @Test
    public void cancelRegistration(){
        onView(withId(R.id.cancelRegistration)).perform(click());
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
    public void register(){
        onView(withId(R.id.username)).perform(typeText("aaaaaaaaa"));
        onView(withId(R.id.password)).perform(typeText("aaaaaaaaa"));
        onView(withId(R.id.passwordCheck)).perform(typeText("aaaaaaaaa"));
        onView(withId(R.id.firstName)).perform(typeText("Test"));
        onView(withId(R.id.lastName)).perform(typeText("Test"));
        onView(withId(R.id.email)).perform(typeText("Test"));

        onView(withId(R.id.register)).perform(click());
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

        assertTrue(SHARED.getUsername().compareTo("aaaaaaaaa") == 0);
        assertTrue(SHARED.getPassword().compareTo("aaaaaaaaa") == 0);
        assertTrue(SHARED.getActive());
        assertTrue(SHARED.getAccountType().compareTo("User") == 0);
        assertTrue(SHARED.getEmail().compareTo("Test") == 0);
        assertTrue(SHARED.getFirstName().compareTo("Test") == 0);
        assertTrue(SHARED.getLastName().compareTo("Test") == 0);
    }
}
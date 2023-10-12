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
public class CreateReviewTest {

    @Rule
    public ActivityScenarioRule<CreateReview> rule = new ActivityScenarioRule<>(CreateReview.class);

    @Test
    public void screenSetup() {
        onView(withId(R.id.Cancel)).check(matches(isClickable()));
        onView(withId(R.id.PostReview)).check(matches(isClickable()));
        onView(withId(R.id.bathroomPic)).check(matches(isDisplayed()));
        onView(withId(R.id.reviewHeader)).check(matches(isDisplayed()));
        onView(withId(R.id.bathroomInfo)).check(matches(isDisplayed()));
        onView(withId(R.id.cleanlinessText)).check(matches(isDisplayed()));
        onView(withId(R.id.cleanlinessRating)).check(matches(isDisplayed()));
        onView(withId(R.id.smellText)).check(matches(isDisplayed()));
        onView(withId(R.id.smellRating)).check(matches(isDisplayed()));
        onView(withId(R.id.privacyText)).check(matches(isDisplayed()));
        onView(withId(R.id.privacyRating)).check(matches(isDisplayed()));
        onView(withId(R.id.accessibilityText)).check(matches(isDisplayed()));
        onView(withId(R.id.accessibilityRating)).check(matches(isDisplayed()));
        onView(withId(R.id.contentText)).check(matches(isDisplayed()));
        onView(withId(R.id.content)).check(matches(isDisplayed()));
    }

    @Test
    public void postComment(){
        onView(withId(R.id.content)).perform(typeText("Testing purposes..."));
        onView(withId(R.id.PostReview)).perform(click());

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

    @Test
    public void cancelReview(){
        onView(withId(R.id.Cancel)).perform(click());

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
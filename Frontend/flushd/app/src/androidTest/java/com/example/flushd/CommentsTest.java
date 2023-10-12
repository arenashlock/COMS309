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

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class CommentsTest {

    @Rule
    public ActivityScenarioRule<Comments> rule = new ActivityScenarioRule<>(Comments.class);

    @Test
    public void screenSetup() {
        onView(withId(R.id.Back)).check(matches(isClickable()));
        onView(withId(R.id.PostComment)).check(matches(isClickable()));
        onView(withId(R.id.postedBy)).check(matches(isDisplayed()));
        onView(withId(R.id.reviewUser)).check(matches(isDisplayed()));
        onView(withId(R.id.dateReview)).check(matches(isDisplayed()));
        onView(withId(R.id.cleanlinessReviewText)).check(matches(isDisplayed()));
        onView(withId(R.id.cleanlinessReview)).check(matches(isDisplayed()));
        onView(withId(R.id.cleanlinessStar)).check(matches(isDisplayed()));
        onView(withId(R.id.smellReview)).check(matches(isDisplayed()));
        onView(withId(R.id.smellReviewText)).check(matches(isDisplayed()));
        onView(withId(R.id.smellStar)).check(matches(isDisplayed()));
        onView(withId(R.id.privacyReviewText)).check(matches(isDisplayed()));
        onView(withId(R.id.privacyReview)).check(matches(isDisplayed()));
        onView(withId(R.id.privacyStar)).check(matches(isDisplayed()));
        onView(withId(R.id.accessibilityReviewText)).check(matches(isDisplayed()));
        onView(withId(R.id.accessibilityReview)).check(matches(isDisplayed()));
        onView(withId(R.id.accessibilityStar)).check(matches(isDisplayed()));
        onView(withId(R.id.reasonReview)).check(matches(isDisplayed()));
        onView(withId(R.id.reasonReviewText)).check(matches(isDisplayed()));
        onView(withId(R.id.rvComments)).check(matches(isDisplayed()));
    }

    @Test
    public void postComment(){
        onView(withId(R.id.PostComment)).perform(click());
        onView(withId(R.id.CancelComment)).check(matches(isClickable()));
        onView(withId(R.id.SendComment)).check(matches(isClickable()));
        onView(withId(R.id.commentContent)).check(matches(isDisplayed()));

        onView(withId(R.id.commentContent)).perform(typeText("Testing purposes..."));
        onView(withId(R.id.SendComment)).perform(click());

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
    public void cancelComment(){
        onView(withId(R.id.Back)).perform(click());

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
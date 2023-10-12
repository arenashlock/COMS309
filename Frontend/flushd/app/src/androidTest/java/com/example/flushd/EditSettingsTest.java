package com.example.flushd;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isClickable;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import android.view.View;

import androidx.test.espresso.ViewAction;
import androidx.test.espresso.action.CoordinatesProvider;
import androidx.test.espresso.action.GeneralClickAction;
import androidx.test.espresso.action.Press;
import androidx.test.espresso.action.Tap;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class EditSettingsTest {

    @Rule
    public ActivityScenarioRule<MainActivity> rule = new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void screenSetup(){
        rule.getScenario().onActivity(activity -> {
            activity.username.setText("admin");
            activity.password.setText("password1");
        });
        onView(withId(R.id.login)).perform(click());
        // Put thread to sleep to allow volley to handle the request
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            // Nothing
        }
        onView(withId(R.id.toolbar)).perform(clickXY(0,0));
        onView(withId(R.id.nav_settings)).perform(click());
        onView(withId(R.id.editSettings)).perform(click());

        onView(withId(R.id.cancelUpdate)).check(matches(isClickable()));
        onView(withId(R.id.updateSettings)).check(matches(isClickable()));
        onView(withId(R.id.deleteAccount)).check(matches(isClickable()));
        onView(withId(R.id.logo)).check(matches(isDisplayed()));
        onView(withId(R.id.firstName)).check(matches(isDisplayed()));
        onView(withId(R.id.lastName)).check(matches(isDisplayed()));
        onView(withId(R.id.email)).check(matches(isDisplayed()));
        onView(withId(R.id.username)).check(matches(isDisplayed()));
        onView(withId(R.id.password)).check(matches(isDisplayed()));
        onView(withId(R.id.passwordCheck)).check(matches(isDisplayed()));
    }

    @Test
    public void cancelEditSettings() {
        rule.getScenario().onActivity(activity -> {
            activity.username.setText("admin");
            activity.password.setText("password1");
        });
        onView(withId(R.id.login)).perform(click());
        // Put thread to sleep to allow volley to handle the request
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            // Nothing
        }
        onView(withId(R.id.toolbar)).perform(clickXY(0,0));
        onView(withId(R.id.nav_settings)).perform(click());
        onView(withId(R.id.editSettings)).perform(click());

        onView(withId(R.id.cancelUpdate)).perform(click());

        onView(withClassName(new Matcher<String>() {
            @Override
            public void describeTo(Description description) {
            }

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
        }));
    }

    @Test
    public void changeSettings() {
        rule.getScenario().onActivity(activity -> {
            activity.username.setText("aaaaa");
            activity.password.setText("aaaaa");
        });
        onView(withId(R.id.login)).perform(click());
        // Put thread to sleep to allow volley to handle the request
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            // Nothing
        }
        onView(withId(R.id.toolbar)).perform(clickXY(0,0));
        onView(withId(R.id.nav_settings)).perform(click());
        onView(withId(R.id.editSettings)).perform(click());

        onView(withId(R.id.firstName)).perform(typeText("1"));
        onView(withId(R.id.lastName)).perform(typeText("1"));
        onView(withId(R.id.email)).perform(typeText("1"));
        onView(withId(R.id.username)).perform(typeText("1"));
        onView(withId(R.id.password)).perform(typeText("1"));
        onView(withId(R.id.passwordCheck)).perform(typeText("1"));
        onView(withId(R.id.updateSettings)).perform(click());

        onView(withClassName(new Matcher<String>() {
            @Override
            public void describeTo(Description description) {
            }

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
        }));
    }

    public static ViewAction clickXY(final int x, final int y){
        return new GeneralClickAction(
                Tap.SINGLE,
                new CoordinatesProvider() {
                    @Override
                    public float[] calculateCoordinates(View view) {

                        final int[] screenPos = new int[2];
                        view.getLocationOnScreen(screenPos);

                        final float screenX = screenPos[0] + x;
                        final float screenY = screenPos[1] + y;
                        float[] coordinates = {screenX, screenY};

                        return coordinates;
                    }
                },
                Press.FINGER);
    }
}

package com.udacity.gradle.builditbigger.ui;


import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;
import android.widget.TextView;

import com.udacity.gradle.builditbigger.IdlingResourceSingleton;
import com.udacity.gradle.builditbigger.R;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.core.AllOf.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    private final IdlingResource mIdlingResource = IdlingResourceSingleton.getInstance().getIdlingResource();

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void registerIdlingResource() {

        IdlingRegistry.getInstance().register(mIdlingResource);
    }

    @After
    public void unregisterIdlingResource() {
        if (mIdlingResource != null) {
            IdlingRegistry.getInstance().unregister(mIdlingResource);
        }
    }

    @Test
    public void mainActivityTest() {

        ViewInteraction buttonTellJoke = onView(
                allOf(withId(R.id.button_tell_joke),
                        isDisplayed()));
        buttonTellJoke.perform(click());

        ViewInteraction jokeTextView = onView(
                allOf(withId(R.id.text_view_display_thing), isDisplayed()));
        jokeTextView.check(matches(textLengthNotZero()));
    }

    public static TypeSafeMatcher<View> textLengthNotZero() {
        return new TypeSafeMatcher<View>() {
            @Override
            protected boolean matchesSafely(View item) {
                return ((TextView) item).getText().toString().length() > 0;
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("textLengthNotZero");
            }
        };
    }
}

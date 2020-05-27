package com.tugraz.flatshareapp;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class RoommatesActivityTest {

    private static final String ROOMMATE_FIRSTNAME = "Guy";
    private static final String ROOMMATE_LASTNAME = "Someguy";
    private static final String BIRTHDAY = "11-11-11";
    private static final String ROOMMATE_SINCE = "19-01-18";

    @Rule
    public ActivityScenarioRule<OverviewActivity> activityRule
            = new ActivityScenarioRule<>(OverviewActivity.class);

    @Test
    public void addRoommate() {
        onView(withId(R.id.btn_roommates_add)).perform(click());

        onView(withId(R.id.input_rommates_detail_first_name)).perform(typeText(ROOMMATE_FIRSTNAME), closeSoftKeyboard());
        onView(withId(R.id.input_rommates_detail_last_name)).perform(typeText(ROOMMATE_LASTNAME), closeSoftKeyboard());
        onView(withId(R.id.input_rommates_detail_birthday)).perform(typeText(BIRTHDAY), closeSoftKeyboard());
        onView(withId(R.id.input_rommates_detail_roommates_since)).perform(typeText(ROOMMATE_SINCE), closeSoftKeyboard());

        onView(withText(ROOMMATE_FIRSTNAME)).check(matches(isDisplayed()));

        //TODO check info in edit too

    }
}

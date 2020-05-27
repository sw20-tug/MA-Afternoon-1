package com.tugraz.flatshareapp;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class CreateFlatFormActivityTest {

    private static final String FLAT_NAME = "Test Flat";
    private static final String FLAT_STREET_NAME = "Example Drive";
    private static final String FLAT_STREET_NUMBER = "22a";
    private static final String FLAT_CITY = "Graz";
    private static final String FLAT_COUNTRY = "Austria";

    @Rule
    public ActivityScenarioRule<CreateFlatFormActivity> activityRule
            = new ActivityScenarioRule<>(CreateFlatFormActivity.class);

    @Rule
    public ActivityScenarioRule<OverviewActivity> activityRule2
            = new ActivityScenarioRule<>(OverviewActivity.class);

    @Test
    public void createFirstFlat() {
        onView(withId(R.id.editFlatName)).perform(typeText(FLAT_NAME), closeSoftKeyboard());
        onView(withId(R.id.editStreetName)).perform(typeText(FLAT_STREET_NAME), closeSoftKeyboard());
        onView(withId(R.id.editStreetNumber)).perform(typeText(FLAT_STREET_NUMBER), closeSoftKeyboard());
        onView(withId(R.id.editCity)).perform(typeText(FLAT_CITY), closeSoftKeyboard());
        onView(withId(R.id.editCountry)).perform(typeText(FLAT_COUNTRY), closeSoftKeyboard());

        onView(withId(R.id.buttonCreateFlat)).perform(click());

        onView(withId(R.id.tv_flat_name)).check(matches(withText(FLAT_NAME)));
        onView(withId(R.id.tv_street_name_value)).check(matches(withText(FLAT_STREET_NAME)));
        onView(withId(R.id.tv_street_number_value)).check(matches(withText(FLAT_STREET_NUMBER)));
        onView(withId(R.id.tv_city_value)).check(matches(withText(FLAT_CITY)));
        onView(withId(R.id.tv_country_value)).check(matches(withText(FLAT_COUNTRY)));
    }
}

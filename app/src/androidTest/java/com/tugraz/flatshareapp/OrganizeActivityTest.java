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
import static androidx.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class OrganizeActivityTest {

    private static final String ORGANIZE_FLATNAME = "Some Flat";
    private static final String ORGANIZE_STREETNAME = "Some Street";
    private static final String ORGANIZE_STREETNUMBER = "1";
    private static final String ORGANIZE_CITYNAME = "Some City";
    private static final String ORGANIZE_COUNTRYNAME = "Some Country";

    @Rule
    public ActivityScenarioRule<OverviewActivity> activityRule
            = new ActivityScenarioRule<>(OverviewActivity.class);

    @Test
    public void addOrganizeFlat() {

        onView(withId(R.id.flat_list_entry_flat_add)).perform(click());

        onView(withId(R.id.input_flat_list_product_name)).perform(typeText(ORGANIZE_FLATNAME), closeSoftKeyboard());
        onView(withId(R.id.input_flat_list_street_name)).perform(typeText(ORGANIZE_STREETNAME), closeSoftKeyboard());
        onView(withId(R.id.input_flat_list_street_number)).perform(typeText(ORGANIZE_STREETNUMBER), closeSoftKeyboard());
        onView(withId(R.id.input_flat_list_city)).perform(typeText(ORGANIZE_CITYNAME), closeSoftKeyboard());
        onView(withId(R.id.input_flat_list_country)).perform(typeText(ORGANIZE_COUNTRYNAME), closeSoftKeyboard());



        onView(withId(R.id.button_flatlist_detail_edit)).perform(click());
    }
}

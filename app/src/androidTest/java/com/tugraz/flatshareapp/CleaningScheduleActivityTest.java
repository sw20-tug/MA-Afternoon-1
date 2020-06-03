package com.tugraz.flatshareapp;


import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import com.tugraz.flatshareapp.database.CleaningDao;
import com.tugraz.flatshareapp.database.Models.Cleaning;
import com.tugraz.flatshareapp.database.Models.ShoppingList;

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
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.theInstance;


import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class CleaningScheduleActivityTest {

    private static final String CLEANING_DESCRIPTION = "Do this";

    CleaningDao cleaningDao;

    CleaningScheduleActivityTest(CleaningDao cleaningDao) {
        this.cleaningDao = cleaningDao;
    }

    @Rule
    public ActivityScenarioRule<OverviewActivity> activityRule
            = new ActivityScenarioRule<>(OverviewActivity.class);

    @Test
    public void addCleaningTask() {
        onView(withId(R.id.btn_cleaning_schedule_add)).perform(click());
        onView(withId(R.id.cleaning_description)).perform(typeText(CLEANING_DESCRIPTION), closeSoftKeyboard());
        onView(withId(R.id.button_cleaning_schedule_edit_save)).perform(click());

        boolean contains_entry = false;
        for (Cleaning cleaning : cleaningDao.getAllCleanings()) {
            if(cleaning.getDescription().equals(CLEANING_DESCRIPTION)) {
                contains_entry = true;
            }
        }

        assert(contains_entry);
    }
}


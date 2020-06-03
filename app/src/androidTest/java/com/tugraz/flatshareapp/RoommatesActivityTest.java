package com.tugraz.flatshareapp;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import com.tugraz.flatshareapp.database.Models.Roommate;
import com.tugraz.flatshareapp.database.RoommateDao;

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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class RoommatesActivityTest {

    private static final String ROOMMATE_FIRSTNAME = "Guy";
    private static final String ROOMMATE_LASTNAME = "Someguy";
    private static final String BIRTHDAY = "11-11-11";
    private static final String ROOMMATE_SINCE = "19-01-18";

    private RoommateDao roommateDao;

    RoommatesActivityTest(RoommateDao roommateDao) {
        this.roommateDao = roommateDao;
    }

    @Rule
    public ActivityScenarioRule<OverviewActivity> activityRule
            = new ActivityScenarioRule<>(OverviewActivity.class);

    public void backButtonPressed() {
        pressBack();
    }

    @Test
    public void addRoommate() {
        onView(withId(R.id.btn_roommates_add)).perform(click());

        onView(withId(R.id.input_rommates_detail_first_name)).perform(typeText(ROOMMATE_FIRSTNAME), closeSoftKeyboard());
        onView(withId(R.id.input_rommates_detail_last_name)).perform(typeText(ROOMMATE_LASTNAME), closeSoftKeyboard());
        onView(withId(R.id.input_rommates_detail_birthday)).perform(typeText(BIRTHDAY), closeSoftKeyboard());
        onView(withId(R.id.input_rommates_detail_roommates_since)).perform(typeText(ROOMMATE_SINCE), closeSoftKeyboard());

        onView(withId(R.id.btn_roomates_detail_save)).perform(click());

//        onView(withText(ROOMMATE_FIRSTNAME)).check(matches(isDisplayed()));

        SimpleDateFormat f = new SimpleDateFormat("dd-MM-yy");

        Date birthday = new Date();
        Date roommate_since = new Date();
        try {
            birthday = f.parse(BIRTHDAY);
            roommate_since = f.parse(BIRTHDAY);
        } catch (ParseException e) {
            e.printStackTrace();
            assert(false);
        }

        boolean contains_entry = false;
        for (Roommate roommate : roommateDao.getAllRoommates()) {
            if(roommate.getName().equals(ROOMMATE_FIRSTNAME)
                    && roommate.getLastName().equals(ROOMMATE_LASTNAME)
                    && birthday.compareTo(new Date(roommate.getBirthday())) == 0
                    && roommate_since.compareTo(new Date(roommate.getRoomateSince())) == 0) {
                contains_entry = true;
            }
        }

        assert(contains_entry);
    }
}

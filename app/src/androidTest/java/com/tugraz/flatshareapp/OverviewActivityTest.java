package com.tugraz.flatshareapp;

import android.app.Activity;
import android.content.Context;

import androidx.room.Room;
import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.platform.app.InstrumentationRegistry;

import com.tugraz.flatshareapp.database.AppDatabase;
import com.tugraz.flatshareapp.database.FlatDao;
import com.tugraz.flatshareapp.database.FlatRepository;
import com.tugraz.flatshareapp.database.Models.Flat;
import com.tugraz.flatshareapp.database.RoommateDao;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

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

@RunWith(AndroidJUnit4.class)
@LargeTest
public class OverviewActivityTest {

    private FlatDao flatDao;

    OverviewActivityTest(FlatDao flatDao) {
        this.flatDao = flatDao;
    }

    @Rule
    public ActivityScenarioRule<OverviewActivity> activityRule
            = new ActivityScenarioRule<>(OverviewActivity.class);

    @Test
    public void checkFlatInformation() {
        Flat activeFlat = null;
        for(Flat flat : flatDao.getAllFlats()) {
            if(flat.getActive())  {
                activeFlat = flat;
                break;
            }
        }

        assert(activeFlat != null);

        onView(withId(R.id.tv_flat_name)).check(matches(withText(activeFlat.getName())));
        onView(withId(R.id.tv_street_name_value)).check(matches(withText(activeFlat.getStreetName())));
        onView(withId(R.id.tv_street_number_value)).check(matches(withText(activeFlat.getStreetNumber())));
        onView(withId(R.id.tv_city_value)).check(matches(withText(activeFlat.getCity())));
        onView(withId(R.id.tv_country_value)).check(matches(withText(activeFlat.getCountry())));
    }

    public void clickRoommates() {
        onView(withId(R.id.btn_room_mates)).perform(click());
        onView(withId(R.id.room_mates_layout)).check(matches(isDisplayed()));
    }

    public void clickShoppingList() {
        onView(withId(R.id.btn_shopping_list)).perform(click());
        onView(withId(R.id.shopping_list_layout)).check(matches(isDisplayed()));
    }

    public void clickCleaningSchedule() {
        onView(withId(R.id.btn_cleaning_schedule)).perform(click());
        onView(withId(R.id.cleaning_schedule_layout)).check(matches(isDisplayed()));
    }

    public void clickOrganizeFlat() {
        onView(withId(R.id.btn_organizeflat)).perform(click());
        onView(withId(R.id.flat_list_layout)).check(matches(isDisplayed()));
    }

    public void clickFinancial() {
        onView(withId(R.id.btn_financing)).perform(click());
        onView(withId(R.id.financing_list_layout)).check(matches(isDisplayed()));
    }

    public void clickBill() {
        onView(withId(R.id.btn_bill)).perform(click());
        onView(withId(R.id.bill_list_layout)).check(matches(isDisplayed()));
    }

    @Test
    public void checkButtons() {
        clickRoommates();
        pressBack();

        clickShoppingList();
        pressBack();

        clickCleaningSchedule();
        pressBack();

        clickOrganizeFlat();
        pressBack();

        clickFinancial();
        pressBack();

        clickBill();
        pressBack();
    }
}

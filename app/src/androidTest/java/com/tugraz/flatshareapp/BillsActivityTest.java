package com.tugraz.flatshareapp;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class BillsActivityTest {

    private static final String BILL_NAME = "Some Bill";
    private static final String BILL_PRICE = "100";

    @Rule
    public ActivityScenarioRule<OverviewActivity> activityRule
            = new ActivityScenarioRule<>(OverviewActivity.class);

    @Test
    public void addBillItem() {

        onView(withId(R.id.btn_bills_list_add)).perform(click());

        onView(withId(R.id.text_bills_detail_value)).perform(typeText(BILL_NAME));
        onView(withId(R.id.text_bills_detail_price_value)).perform(typeText(BILL_PRICE));

        onView(withId(R.id.btn_bills_detail_save)).perform(click());
    }
}

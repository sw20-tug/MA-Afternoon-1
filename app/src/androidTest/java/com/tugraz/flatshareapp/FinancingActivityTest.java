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
public class FinancingActivityTest {

    private static final String FINANCE_FURNITURENAME = "Some Furniture";
    private static final String FINANCE_FURNITUREPRICE = "100";

    @Rule
    public ActivityScenarioRule<OverviewActivity> activityRule
            = new ActivityScenarioRule<>(OverviewActivity.class);

    @Test
    public void addFinancingItem() {

        onView(withId(R.id.btn_financing_furniture_list_add)).perform(click());

        onView(withId(R.id.input_financing_furniture_detail_item_name)).perform(typeText(FINANCE_FURNITURENAME));
        onView(withId(R.id.input_financing_furniture_detail_item_price)).perform(typeText(FINANCE_FURNITUREPRICE));

        onView(withId(R.id.financing_furniture_scrollView1)).perform(click());

        onView(withId(R.id.btn_financing_furniture_detail_save)).perform(click());
    }
}

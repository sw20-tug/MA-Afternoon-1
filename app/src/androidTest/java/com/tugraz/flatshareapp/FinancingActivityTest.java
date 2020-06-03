package com.tugraz.flatshareapp;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import com.tugraz.flatshareapp.database.FinanceDao;
import com.tugraz.flatshareapp.database.Models.Cleaning;
import com.tugraz.flatshareapp.database.Models.Finance;

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
    private static final float FINANCE_FURNITUREPRICE = 100f;

    FinanceDao financeDao;

    FinancingActivityTest(FinanceDao financeDao) {
        this.financeDao = financeDao;
    }

    @Rule
    public ActivityScenarioRule<OverviewActivity> activityRule
            = new ActivityScenarioRule<>(OverviewActivity.class);

    @Test
    public void addFinancingItem() {

        onView(withId(R.id.btn_financing_furniture_list_add)).perform(click());

        onView(withId(R.id.input_financing_furniture_detail_item_name)).perform(typeText(FINANCE_FURNITURENAME));
        onView(withId(R.id.input_financing_furniture_detail_item_price)).perform(typeText(String.valueOf(FINANCE_FURNITUREPRICE)));

//        onView(withId(R.id.financing_furniture_scrollView1)).perform(click());

        onView(withId(R.id.btn_financing_furniture_detail_save)).perform(click());

        boolean contains_entry = false;
        for (Finance finance : financeDao.getAllFinances()) {
            if(finance.getDescription().equals(FINANCE_FURNITURENAME)
                && finance.getValue() == FINANCE_FURNITUREPRICE) {
                contains_entry = true;
            }
        }

        assert(contains_entry);
    }
}

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
public class ShoppingListActivityTest {

    private static final String SHOPINGLIST_ITEMNAME = "Some Item";
    private static final String SHOPINGLIST_ITEMDESCRIPTION = "Description";

    @Rule
    public ActivityScenarioRule<OverviewActivity> activityRule
            = new ActivityScenarioRule<>(OverviewActivity.class);

    @Test
    public void addShoppingListItem() {
        onView(withId(R.id.btn_shopping_list_add)).perform(click());

        onView(withId(R.id.input_shopping_list_product_name)).perform(typeText(SHOPINGLIST_ITEMNAME), closeSoftKeyboard());
        onView(withId(R.id.input_shopping_list_product_description)).perform(typeText(SHOPINGLIST_ITEMDESCRIPTION), closeSoftKeyboard());

        onView(withId(R.id.btn_shopping_detail_save)).perform(click());
    }
}

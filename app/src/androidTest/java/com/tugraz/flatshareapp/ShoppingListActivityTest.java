package com.tugraz.flatshareapp;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import com.tugraz.flatshareapp.database.Models.Roommate;
import com.tugraz.flatshareapp.database.Models.ShoppingList;
import com.tugraz.flatshareapp.database.ShoppingListDao;

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

import java.util.Date;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class ShoppingListActivityTest {

    private static final String SHOPINGLIST_ITEMNAME = "Some Item";
    private static final String SHOPINGLIST_ITEMDESCRIPTION = "Description";

    private ShoppingListDao shoppingListDao;

    ShoppingListActivityTest(ShoppingListDao shoppingListDao) {
        this.shoppingListDao = shoppingListDao;
    }

    @Rule
    public ActivityScenarioRule<OverviewActivity> activityRule
            = new ActivityScenarioRule<>(OverviewActivity.class);

    @Test
    public void addShoppingListItem() {
        onView(withId(R.id.btn_shopping_list_add)).perform(click());

        onView(withId(R.id.input_shopping_list_product_name)).perform(typeText(SHOPINGLIST_ITEMNAME), closeSoftKeyboard());
        onView(withId(R.id.input_shopping_list_product_description)).perform(typeText(SHOPINGLIST_ITEMDESCRIPTION), closeSoftKeyboard());

        onView(withId(R.id.btn_shopping_detail_save)).perform(click());

        boolean contains_entry = false;
        for (ShoppingList shoppingList : shoppingListDao.getAllShoppingLists()) {
            if(shoppingList.getName().equals(SHOPINGLIST_ITEMNAME)
                    && shoppingList.getDescription().equals(SHOPINGLIST_ITEMDESCRIPTION)) {
                contains_entry = true;
            }
        }

        assert(contains_entry);
    }
}

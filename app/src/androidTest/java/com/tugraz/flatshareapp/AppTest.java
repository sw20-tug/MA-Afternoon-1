package com.tugraz.flatshareapp;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.tugraz.flatshareapp.database.AppDatabase;
import com.tugraz.flatshareapp.database.CleaningDao_Impl;
import com.tugraz.flatshareapp.database.FlatDao;
import com.tugraz.flatshareapp.database.RoommateDao;
import com.tugraz.flatshareapp.database.ShoppingListDao;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class AppTest {

    AppDatabase db;
    FlatDao flatDao;
    RoommateDao roommateDao;
    Context appContext;
    ShoppingListDao shoppingListDao;
    CleaningDao_Impl cleaningDao;



    private OverviewActivityTest overviewActivityTest;
    private RoommatesActivityTest roommatesActivityTest;
    private ShoppingListActivityTest shoppingListActivityTest;
    private CleaningScheduleActivityTest cleaningScheduleActivityTest;
    private OrganizeActivityTest organizeActivityTest;
    private FinancingActivityTest financingActivityTest;
    private BillsActivityTest billsActivityTest;
    private ReportActivityTest reportActivityTest;

    private void checkFlatsEmptyAndFix() {
        if(flatDao.getAllFlats().isEmpty())
            test_firstStart();
    }

    private void clearDatabase() {
        db.flatDao().deleteAllFlats();
        db.roommateDao().deleteAllRoommates();
        db.cleaningDao().deleteAllCleanings();
        db.shoppingListDao().deleteAllShoppingLists();
        db.financeDao().deleteAllFinances();
        db.billDao().deleteAllBills();
    }

    private void startActivity(String activityName) {

        Instrumentation mInstrumentation = getInstrumentation();

        // We register our interest in the activity
        Instrumentation.ActivityMonitor monitor = mInstrumentation.addMonitor(activityName, null, false);
        // We launch it
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setClassName(mInstrumentation.getTargetContext(), activityName);
        mInstrumentation.startActivitySync(intent);

        Activity currentActivity = getInstrumentation().waitForMonitor(monitor);
        assertNotNull(currentActivity);
        // We register our interest in the next activity from the sequence in this use case
//        mInstrumentation.removeMonitor(monitor);
//        monitor = mInstrumentation.addMonitor(CreateFlatFormActivity.class.getName(), null, false);
    }

    @Before
    public void init() {
        appContext = getInstrumentation().getTargetContext();
        this.db = AppDatabase.getInstance(appContext);
        this.flatDao = db.flatDao();
        this.roommateDao = db.roommateDao();


        overviewActivityTest = new OverviewActivityTest(flatDao);
        roommatesActivityTest = new RoommatesActivityTest();
        shoppingListActivityTest = new ShoppingListActivityTest();
        cleaningScheduleActivityTest = new CleaningScheduleActivityTest();
        organizeActivityTest = new OrganizeActivityTest();
        financingActivityTest = new FinancingActivityTest();
        billsActivityTest = new BillsActivityTest();
        reportActivityTest = new ReportActivityTest();

        // Context of the app under test.
        assertEquals("com.tugraz.flatshareapp", appContext.getPackageName());

        startActivity(OverviewActivity.class.getName());
    }

    @Test
    public void test_firstStart() {
        clearDatabase();

        CreateFlatFormActivityTest createFlatFormActivityTest = new CreateFlatFormActivityTest();
        createFlatFormActivityTest.createFirstFlat();
    }

    @Test
    public void test_OverviewActiveFlatData() {
        checkFlatsEmptyAndFix();
        overviewActivityTest.checkFlatInformation();
    }

    @Test
    public void test_OverviewButtons() {
        checkFlatsEmptyAndFix();
        overviewActivityTest.checkButtons();
    }

    @Test
    public void test_RoommatesButtons() {
        checkFlatsEmptyAndFix();
        overviewActivityTest.clickRoommates();
        roommatesActivityTest.addRoommate();
    }

    @Test
    public void test_ShoppingListButton() {
        checkFlatsEmptyAndFix();
        overviewActivityTest.clickShoppingList();
        shoppingListActivityTest.addShoppingListItem();
    }

    @Test
    public void test_CleaningScheduleButton() {
        checkFlatsEmptyAndFix();
        overviewActivityTest.clickCleaningSchedule();
        cleaningScheduleActivityTest.addCleaningTask();
    }


    @Test
    public void test_OrganizeFlatButton() {
        checkFlatsEmptyAndFix();
        overviewActivityTest.clickOrganizeFlat();
        organizeActivityTest.addOrganizeFlat();
    }

    @Test
    public void test_FinancingFurnitureButton() {
        checkFlatsEmptyAndFix();
        overviewActivityTest.clickFinancial();
        financingActivityTest.addFinancingItem();

    }

    @Test
    public void test_BillsButton() {
        checkFlatsEmptyAndFix();
        overviewActivityTest.clickBill();
        billsActivityTest.addBillItem();
    }

    /*@Test
    public void test_ReportButton() {
        checkFlatsEmptyAndFix();
    }

     */
}
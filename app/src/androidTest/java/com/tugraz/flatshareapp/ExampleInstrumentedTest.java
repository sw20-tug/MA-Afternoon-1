package com.tugraz.flatshareapp;

import android.content.Context;

import androidx.room.Room;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.tugraz.flatshareapp.database.AppDatabase;
import com.tugraz.flatshareapp.database.FlatDao;
import com.tugraz.flatshareapp.database.FlatRepository;
import com.tugraz.flatshareapp.database.Models.Flat;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

    private FlatDao flatDao;
    private AppDatabase db;



    @Before
    public void loadDb() {
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        db = Room.inMemoryDatabaseBuilder(appContext, AppDatabase.class).build();
        flatDao = db.flatDao();
    }

    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.tugraz.flatshareapp", appContext.getPackageName());
    }

    @Test
    public void createFlat() throws Exception {

        Flat testFlat = new Flat("test123", "streetName1", "streetNumber1", "city1", "country1", true);
        flatDao.insert(testFlat);
        boolean found = false;
        for(Flat flat: flatDao.getAllFlats()) {
            if (flat.getName().equals("test123")) {
                found = true;
            }
        }
        assertTrue(found);
    }
}

package com.tugraz.flatshareapp;

import android.content.Context;

import androidx.room.Room;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.tugraz.flatshareapp.database.AppDatabase;
import com.tugraz.flatshareapp.database.FlatDao;
import com.tugraz.flatshareapp.database.Models.Flat;
import com.tugraz.flatshareapp.database.Models.Roommate;
import com.tugraz.flatshareapp.database.RoommateDao;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

    private FlatDao flatDao;
    private RoommateDao roommateDao;
    private AppDatabase db;



    @Before
    public void loadDb() {
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        db = Room.inMemoryDatabaseBuilder(appContext, AppDatabase.class).build();
        flatDao = db.flatDao();
        roommateDao = db.roommateDao();
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
        flatDao.delete(testFlat);
        assertTrue(found);
    }

    @Test
    public void addRoomate() {
        Roommate testRoommate = new Roommate("testName", "testLastname",new Date(), new Date(), 0);
        roommateDao.insert(testRoommate);
        boolean found = false;
        for(Roommate roommate: roommateDao.getAllRoommates()) {
            if(roommate.getName().equals("testName")) {
                found = true;
            }
        }

        roommateDao.delete(testRoommate);
        assertTrue(found);
    }

    @Test
    public void editRoomate() {
        Roommate testRoommate = new Roommate("testName", "testLastname",new Date(), new Date(), 0);
        testRoommate.setId((int)roommateDao.insert(testRoommate));
        boolean found = false;
        for(Roommate roommate: roommateDao.getAllRoommates()) {
            if(roommate.getName().equals("testName")) {
                found = true;
            }
        }
        assertTrue(found);

        testRoommate.setName("bla");
        testRoommate.setLastName("bla2");

        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.DATE, 1);

        testRoommate.setBirthday(cal.getTimeInMillis());
        testRoommate.setRoomateSince(cal.getTimeInMillis());
        testRoommate.setFlatId(1);

        roommateDao.update(testRoommate);
        Roommate updatedRoommate = null;
        for(Roommate roommate: roommateDao.getAllRoommates()) {
            if(roommate.getId() == testRoommate.getId())  {
                updatedRoommate = roommate;
            }
        }

        roommateDao.delete(testRoommate);

        assertNotNull(updatedRoommate);
        assertEquals(testRoommate.getName(), updatedRoommate.getName());
        assertEquals(testRoommate.getLastName(), updatedRoommate.getLastName());
        assertEquals(testRoommate.getBirthday(), updatedRoommate.getBirthday());
        assertEquals(testRoommate.getRoomateSince(), updatedRoommate.getRoomateSince());
        assertEquals(testRoommate.getFlatId(), updatedRoommate.getFlatId());

    }

    @Test
    public void deleteRoommate() {
        Roommate testRoommate = new Roommate("testName", "testLastname",new Date(), new Date(), 0);
        testRoommate.setId((int)roommateDao.insert(testRoommate));
        boolean found = false;
        for(Roommate roommate: roommateDao.getAllRoommates()) {
            if(roommate.getName().equals("testName")) {
                found = true;
            }
        }
        boolean deleted = true;
        roommateDao.delete(testRoommate);
        assertTrue(found);

        for(Roommate roommate: roommateDao.getAllRoommates()) {
            if(roommate.getName().equals("testName")) {
                deleted = false;
            }
        }
        assertTrue(deleted);
    }


}

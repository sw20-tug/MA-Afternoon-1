package com.tugraz.flatshareapp.database;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import com.tugraz.flatshareapp.database.Models.Roommate;

import java.util.List;

public class RoommateRepository {
    private static final String TAG = RoommateRepository.class.getSimpleName();
    private RoommateDao roommateDao;

    public RoommateRepository(Application application) {
        AppDatabase database = AppDatabase.getInstance(application);
        roommateDao = database.roommateDao();
    }

    public void insert(Roommate roommate) {
        new RoommateRepository.InsertRoommatetAsyncTask(roommateDao).execute(roommate);
        Log.e("","Inserted " + roommate.toString());
    }

    public void update(Roommate roommate) {
        new RoommateRepository.UpdateRoommateAsyncTask(roommateDao).execute(roommate);
    }

    public void delete(Roommate roommate) {
        new RoommateRepository.DeleteRoommateAsyncTask(roommateDao).execute(roommate);
    }

    public void deleteAllRoommates() {
        new RoommateRepository.DeleteAllRoommatesAsyncTask(roommateDao).execute();
    }

    public List<Roommate> getAllRoommates() throws Exception{
        return new RoommateRepository.GetAllRoommatesAsyncTask(roommateDao).execute().get();
    }

    private static class GetAllRoommatesAsyncTask extends AsyncTask<Void, Void, List<Roommate>> {
        private RoommateDao roommateDao;

        private GetAllRoommatesAsyncTask(RoommateDao roommateDao) {
            this.roommateDao = roommateDao;
        }

        @Override
        protected List<Roommate> doInBackground(Void... voids) {
            Log.e(TAG, "doInBackground: getting roommates", null);
            List<Roommate> roommates = roommateDao.getAllRoommates();
            Log.e(TAG, roommates.toString(), null);
            return roommates;
        }
    }

    private static class InsertRoommatetAsyncTask extends AsyncTask<Roommate, Void, Void> {
        private RoommateDao roommateDao;

        private InsertRoommatetAsyncTask(RoommateDao roommateDao) {
            this.roommateDao = roommateDao;
        }

        @Override
        protected Void doInBackground(Roommate... roommates) {
            roommateDao.insert(roommates[0]);
            return null;
        }
    }

    private static class UpdateRoommateAsyncTask extends AsyncTask<Roommate, Void, Void> {
        private RoommateDao roommateDao;

        private UpdateRoommateAsyncTask(RoommateDao roommateDao) {
            this.roommateDao = roommateDao;
        }

        @Override
        protected Void doInBackground(Roommate... roommates) {
            roommateDao.update(roommates[0]);
            return null;
        }
    }

    private static class DeleteRoommateAsyncTask extends AsyncTask<Roommate, Void, Void> {
        private RoommateDao roommateDao;

        private DeleteRoommateAsyncTask(RoommateDao roommateDao) {
            this.roommateDao = roommateDao;
        }

        @Override
        protected Void doInBackground(Roommate... roommates) {
            roommateDao.delete(roommates[0]);
            return null;
        }
    }

    private static class DeleteAllRoommatesAsyncTask extends AsyncTask<Roommate, Void, Void> {
        private RoommateDao roommateDao;

        private DeleteAllRoommatesAsyncTask(RoommateDao roommateDao) {
            this.roommateDao = roommateDao;
        }

        @Override
        protected Void doInBackground(Roommate... roommates) {
            roommateDao.deleteAllRoommates();
            return null;
        }
    }
}

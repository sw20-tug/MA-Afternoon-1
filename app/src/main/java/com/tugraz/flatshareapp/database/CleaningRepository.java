package com.tugraz.flatshareapp.database;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import com.tugraz.flatshareapp.database.Models.Cleaning;

import java.util.List;

public class CleaningRepository {

    private static final String TAG = CleaningRepository.class.getSimpleName();
    private CleaningDao cleaningDao;

    public CleaningRepository(Application application) {
        AppDatabase database = AppDatabase.getInstance(application);
        cleaningDao = database.cleaningDao();
    }

    public void insert(Cleaning cleaning) {
        new InsertCleaningAsyncTask(cleaningDao).execute(cleaning);
        Log.e("","Inserted " + cleaning.toString());
    }

    public void update(Cleaning cleaning) {
        new CleaningRepository.UpdateCleaningAsyncTask(cleaningDao).execute(cleaning);
    }

    public void delete(Cleaning cleaning) {
        new CleaningRepository.DeleteCleaningAsyncTask(cleaningDao).execute(cleaning);
    }

    public void deleteAllCleanings() {
        new CleaningRepository.DeleteAllCleaningsAsyncTask(cleaningDao).execute();
    }

    public List<Cleaning> getAllCleanings() throws Exception{
        return new CleaningRepository.getAllCleaningsAsyncTask(cleaningDao).execute().get();
    }

    private static class getAllCleaningsAsyncTask extends AsyncTask<Void, Void, List<Cleaning>> {
        private CleaningDao cleaningDao;

        private getAllCleaningsAsyncTask(CleaningDao cleaningDao) {
            this.cleaningDao = cleaningDao;
        }

        @Override
        protected List<Cleaning> doInBackground(Void... voids) {
            Log.e(TAG, "doInBackground: getting cleanings", null);
            List<Cleaning> cleanings = cleaningDao.getAllCleanings();
            Log.e(TAG, cleanings.toString(), null);
            return cleanings;
        }
    }

    private static class InsertCleaningAsyncTask extends AsyncTask<Cleaning, Void, Void> {
        private CleaningDao cleaningDao;

        private InsertCleaningAsyncTask(CleaningDao cleaningDao) {
            this.cleaningDao = cleaningDao;
        }

        @Override
        protected Void doInBackground(Cleaning... cleanings) {
            cleaningDao.insert(cleanings[0]);
            return null;
        }
    }

    private static class UpdateCleaningAsyncTask extends AsyncTask<Cleaning, Void, Void> {
        private CleaningDao cleaningDao;

        private UpdateCleaningAsyncTask(CleaningDao cleaningDao) {
            this.cleaningDao = cleaningDao;
        }

        @Override
        protected Void doInBackground(Cleaning... cleanings) {
            cleaningDao.update(cleanings[0]);
            return null;
        }
    }

    private static class DeleteCleaningAsyncTask extends AsyncTask<Cleaning, Void, Void> {
        private CleaningDao cleaningDao;

        private DeleteCleaningAsyncTask(CleaningDao cleaningDao) {
            this.cleaningDao = cleaningDao;
        }

        @Override
        protected Void doInBackground(Cleaning... cleanings) {
            cleaningDao.delete(cleanings[0]);
            return null;
        }
    }

    private static class DeleteAllCleaningsAsyncTask extends AsyncTask<Cleaning, Void, Void> {
        private CleaningDao cleaningDao;

        private DeleteAllCleaningsAsyncTask(CleaningDao cleaningDao) {
            this.cleaningDao = cleaningDao;
        }

        @Override
        protected Void doInBackground(Cleaning... cleanings) {
            cleaningDao.deleteAllCleanings();
            return null;
        }
    }
}

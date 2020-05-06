package com.tugraz.flatshareapp.database;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import com.tugraz.flatshareapp.database.Models.Flat;

import java.util.List;

public class FlatRepository {

    private static final String TAG = FlatRepository.class.getSimpleName();
    private FlatDao flatDao;

    public FlatRepository(Application application) {
        AppDatabase database = AppDatabase.getInstance(application);
        flatDao = database.flatDao();
       // flats = flatDao.getAllFlats();
    }

    public void insert(Flat flat) {
        new InsertFlatAsyncTask(flatDao).execute(flat);
        Log.e("","Inserted " + flat.toString());
    }

    public void update(Flat flat) {
        new UpdateFlatAsyncTask(flatDao).execute(flat);
    }

    public void delete(Flat flat) {
        new DeleteFlatAsyncTask(flatDao).execute(flat);
    }

    public void deleteAllNotes() {
        new DeleteAllFlatsFlatAsyncTask(flatDao).execute();
    }

    public List<Flat> getAllFlats() throws Exception{
        return new GetAllFlatsAsyncTask(flatDao).execute().get();
    }

    private static class GetAllFlatsAsyncTask extends AsyncTask<Void, Void, List<Flat>> {
        private FlatDao flatDao;

        private GetAllFlatsAsyncTask(FlatDao flatDao) {
            this.flatDao = flatDao;
        }

        @Override
        protected List<Flat> doInBackground(Void... voids) {
            Log.e(TAG, "doInBackground: getting flats", null);
            List<Flat> flats = flatDao.getAllFlats();
            Log.e(TAG, flats.toString(), null);
            return flats;
        }
    }

    private static class InsertFlatAsyncTask extends AsyncTask<Flat, Void, Void> {
        private FlatDao flatDao;

        private InsertFlatAsyncTask(FlatDao flatDao) {
            this.flatDao = flatDao;
        }

        @Override
        protected Void doInBackground(Flat... flats) {
            flatDao.insert(flats[0]);
            return null;
        }
    }

    private static class UpdateFlatAsyncTask extends AsyncTask<Flat, Void, Void> {
        private FlatDao flatDao;

        private UpdateFlatAsyncTask(FlatDao flatDao) {
            this.flatDao = flatDao;
        }

        @Override
        protected Void doInBackground(Flat... flats) {
            flatDao.update(flats[0]);
            return null;
        }
    }

    private static class DeleteFlatAsyncTask extends AsyncTask<Flat, Void, Void> {
        private FlatDao flatDao;

        private DeleteFlatAsyncTask(FlatDao flatDao) {
            this.flatDao = flatDao;
        }

        @Override
        protected Void doInBackground(Flat... flats) {
            flatDao.delete(flats[0]);
            return null;
        }
    }

    private static class DeleteAllFlatsFlatAsyncTask extends AsyncTask<Flat, Void, Void> {
        private FlatDao flatDao;

        private DeleteAllFlatsFlatAsyncTask(FlatDao flatDao) {
            this.flatDao = flatDao;
        }

        @Override
        protected Void doInBackground(Flat... flats) {
            flatDao.deleteAllFlats();
            return null;
        }
    }
}

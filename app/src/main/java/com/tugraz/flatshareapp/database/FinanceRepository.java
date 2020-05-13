package com.tugraz.flatshareapp.database;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import com.tugraz.flatshareapp.database.Models.Finance;

import java.util.List;

public class FinanceRepository {

    private static final String TAG = FinanceRepository.class.getSimpleName();
    private FinanceDao financeDao;

    public FinanceRepository(Application application)
    {
        AppDatabase database = AppDatabase.getInstance(application);
        financeDao = database.financeDao();
    }

    public void insert(Finance finance)
    {
        new FinanceRepository.InsertFinanceAsyncTask(financeDao).execute(finance);
    }

    public void update(Finance finance)
    {
        new FinanceRepository.UpdateFinanceAsyncTask(financeDao).execute(finance);
    }

    public void delete(Finance finance)
    {
        new FinanceRepository.DeleteFinanceAsyncTask(financeDao).execute(finance);
    }

    public void deleteAllFinances()
    {
        new FinanceRepository.DeleteAllFinanceAsyncTask(financeDao).execute();
    }

    public List<Finance> getAllFinances() throws Exception
    {
        return new FinanceRepository.GetAllFinanceAsyncTask(financeDao).execute().get();
    }

    private static class GetAllFinanceAsyncTask extends AsyncTask<Void, Void, List<Finance>>
    {
        private FinanceDao financeDao;

        private GetAllFinanceAsyncTask(FinanceDao financeDao)
        {
            this.financeDao = financeDao;
        }

        @Override
        protected List<Finance> doInBackground(Void... voids)
        {
            Log.e(TAG, "doInBackground: getting cleanings", null);
            List<Finance> finances = financeDao.getAllFinances();
            Log.e(TAG, finances.toString(), null);
            return finances;
        }
    }

    private static class InsertFinanceAsyncTask extends AsyncTask<Finance, Void, Void> {
        private FinanceDao financeDao;

        private InsertFinanceAsyncTask(FinanceDao financeDao) {
            this.financeDao = financeDao;
        }

        @Override
        protected Void doInBackground(Finance... finances) {
            financeDao.insert(finances[0]);
            return null;
        }
    }

    private static class UpdateFinanceAsyncTask extends AsyncTask<Finance, Void, Void> {
        private FinanceDao financeDao;

        private UpdateFinanceAsyncTask(FinanceDao financeDao) {
            this.financeDao = financeDao;
        }

        @Override
        protected Void doInBackground(Finance... finances) {
            financeDao.update(finances[0]);
            return null;
        }
    }

    private static class DeleteFinanceAsyncTask extends AsyncTask<Finance, Void, Void> {
        private FinanceDao financeDao;

        private DeleteFinanceAsyncTask(FinanceDao financeDao) {
            this.financeDao = financeDao;
        }

        @Override
        protected Void doInBackground(Finance... finances) {
            financeDao.delete(finances[0]);
            return null;
        }
    }

    private static class DeleteAllFinanceAsyncTask extends AsyncTask<Finance, Void, Void> {
        private FinanceDao financeDao;

        private DeleteAllFinanceAsyncTask(FinanceDao financeDao) {
            this.financeDao = financeDao;
        }

        @Override
        protected Void doInBackground(Finance... finances) {
            financeDao.deleteAllFinances();
            return null;
        }
    }
}

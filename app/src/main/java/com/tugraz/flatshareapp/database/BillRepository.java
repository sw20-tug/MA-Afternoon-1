package com.tugraz.flatshareapp.database;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import com.tugraz.flatshareapp.database.Models.Bill;

import java.util.List;

public class BillRepository {

    private static final String TAG = BillRepository.class.getSimpleName();
    private BillDao billDao;

    public  BillRepository(Application application)
    {
        AppDatabase database = AppDatabase.getInstance(application);
        billDao = database.billDao();
    }

    public void insert(Bill bill)
    {
        new BillRepository.InsertBillAsyncTask(billDao).execute(bill);
        Log.e("","Inserted " + bill.toString());
    }

    public void update(Bill bill)
    {
        new BillRepository.UpdateBillAsyncTask(billDao).execute(bill);
    }

    public void delete(Bill bill)
    {
        new BillRepository.DeleteBillAsyncTask(billDao).execute(bill);
    }

    public void deleteAllBills()
    {
        new BillRepository.DeleteAllBillsAsyncTask(billDao).execute();
    }

    public List<Bill> getAllBills() throws Exception
    {
        return new BillRepository.GetAllBillsAsyncTask(billDao).execute().get();
    }

    private static class GetAllBillsAsyncTask extends AsyncTask<Void, Void, List<Bill>>
    {
        private BillDao billDao;

        private GetAllBillsAsyncTask(BillDao billDao)
        {
            this.billDao = billDao;
        }

        @Override
        protected List<Bill> doInBackground(Void... voids)
        {
            return billDao.getAllBills();
        }
    }

    private static class InsertBillAsyncTask extends AsyncTask<Bill, Void, Void>
    {
        private BillDao billDao;

        private InsertBillAsyncTask(BillDao billDao)
        {
            this.billDao = billDao;
        }

        @Override
        protected Void doInBackground(Bill... bills)
        {
            billDao.insert(bills[0]);
            return null;
        }
    }

    private static class UpdateBillAsyncTask extends AsyncTask<Bill, Void, Void>
    {
        private BillDao billDao;

        private UpdateBillAsyncTask(BillDao billDao)
        {
            this.billDao = billDao;
        }

        @Override
        protected Void doInBackground(Bill... bills)
        {
            billDao.update(bills[0]);
            return null;
        }
    }

    private static class DeleteBillAsyncTask extends AsyncTask<Bill, Void, Void>
    {
        private BillDao billDao;

        private DeleteBillAsyncTask(BillDao billDao)
        {
            this.billDao = billDao;
        }

        @Override
        protected Void doInBackground(Bill... bills)
        {
            billDao.delete(bills[0]);
            return null;
        }
    }

    private static class DeleteAllBillsAsyncTask extends AsyncTask<Bill, Void, Void>
    {
        private BillDao billDao;

        private DeleteAllBillsAsyncTask(BillDao billDao)
        {
            this.billDao = billDao;
        }

        @Override
        protected Void doInBackground(Bill... bills)
        {
            billDao.deleteAllBills();
            return null;
        }
    }
}

package com.tugraz.flatshareapp.database;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import com.tugraz.flatshareapp.database.Models.ShoppingList;

import java.util.List;

public class ShoppingListRepository {
    private static final String TAG = FlatRepository.class.getSimpleName();
    private ShoppingListDao shoppingListDao;

    public ShoppingListRepository(Application application) {
        AppDatabase database = AppDatabase.getInstance(application);
        shoppingListDao = database.shoppingListDao();
    }

    public void insert(ShoppingList shoppingList) {
        new ShoppingListRepository.InsertShoppingListAsyncTask(shoppingListDao).execute(shoppingList);
        Log.e("","Inserted " + shoppingList.toString());
    }

    public void update(ShoppingList shoppingList) {
        new ShoppingListRepository.UpdateShoppingListAsyncTask(shoppingListDao).execute(shoppingList);
    }

    public void delete(ShoppingList shoppingList) {
        new ShoppingListRepository.DeleteShoppingListAsyncTask(shoppingListDao).execute(shoppingList);
    }

    public void deleteAllNotes() {
        new ShoppingListRepository.DeleteAllShoppingListsAsyncTask(shoppingListDao).execute();
    }

    public List<ShoppingList> getAllFlats() throws Exception{
        return new ShoppingListRepository.GetAllShoppingListsAsyncTask(shoppingListDao).execute().get();
    }

    private static class GetAllShoppingListsAsyncTask extends AsyncTask<Void, Void, List<ShoppingList>> {
        private ShoppingListDao shoppingListDao;

        private GetAllShoppingListsAsyncTask(ShoppingListDao shoppingListDao) {
            this.shoppingListDao = shoppingListDao;
        }

        @Override
        protected List<ShoppingList> doInBackground(Void... voids) {
            Log.e(TAG, "doInBackground: getting shopping lists", null);
            List<ShoppingList> shoppingLists = shoppingListDao.getAllShoppingLists();
            Log.e(TAG, shoppingLists.toString(), null);
            return shoppingLists;
        }
    }

    private static class InsertShoppingListAsyncTask extends AsyncTask<ShoppingList, Void, Void> {
        private ShoppingListDao shoppingListDao;

        private InsertShoppingListAsyncTask(ShoppingListDao shoppingListDao) {
            this.shoppingListDao = shoppingListDao;
        }

        @Override
        protected Void doInBackground(ShoppingList... shoppingLists) {
            shoppingListDao.insert(shoppingLists[0]);
            return null;
        }
    }

    private static class UpdateShoppingListAsyncTask extends AsyncTask<ShoppingList, Void, Void> {
        private ShoppingListDao shoppingListDao;

        private UpdateShoppingListAsyncTask(ShoppingListDao shoppingListDao) {
            this.shoppingListDao = shoppingListDao;
        }

        @Override
        protected Void doInBackground(ShoppingList... shoppingLists) {
            shoppingListDao.update(shoppingLists[0]);
            return null;
        }
    }

    private static class DeleteShoppingListAsyncTask extends AsyncTask<ShoppingList, Void, Void> {
        private ShoppingListDao shoppingListDao;

        private DeleteShoppingListAsyncTask(ShoppingListDao shoppingListDao) {
            this.shoppingListDao = shoppingListDao;
        }

        @Override
        protected Void doInBackground(ShoppingList... shoppingLists) {
            shoppingListDao.delete(shoppingLists[0]);
            return null;
        }
    }

    private static class DeleteAllShoppingListsAsyncTask extends AsyncTask<ShoppingList, Void, Void> {
        private ShoppingListDao shoppingListDao;

        private DeleteAllShoppingListsAsyncTask(ShoppingListDao shoppingListDao) {
            this.shoppingListDao = shoppingListDao;
        }

        @Override
        protected Void doInBackground(ShoppingList... shoppingLists) {
            shoppingListDao.deleteAllShoppingLists();
            return null;
        }
    }
}

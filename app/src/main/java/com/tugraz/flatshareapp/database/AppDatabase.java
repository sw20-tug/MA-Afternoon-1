package com.tugraz.flatshareapp.database;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.tugraz.flatshareapp.database.Models.Flat;

@Database(entities = {Flat.class}, version = 2)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase instance;

    public abstract FlatDao flatDao();

    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "flat_database")
                    .addCallback(roomCallback)
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopuldateDbAsyncTask(instance).execute();
        }
    };

    private static class PopuldateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        private FlatDao flatDao;

        private PopuldateDbAsyncTask(AppDatabase db) {
            flatDao = db.flatDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            flatDao.insert(new Flat("name1", "streetName1", "streetNumber1", "city1", "country1", true));
            flatDao.insert(new Flat("name2", "streetName2", "streetNumber2", "city2", "country2",false));
            flatDao.insert(new Flat("name3", "streetName3", "streetNumber3", "city3", "country3", false));
            return null;
        }
    }

}

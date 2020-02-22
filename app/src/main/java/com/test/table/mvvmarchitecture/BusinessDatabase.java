package com.test.table.mvvmarchitecture;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = Business.class, version = 1)
public abstract class BusinessDatabase extends RoomDatabase {

    private static BusinessDatabase instance;

    public abstract BusinessDao businessDao();

    public static synchronized BusinessDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    BusinessDatabase.class, "business_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        private BusinessDao businessDao;

        private PopulateDbAsyncTask(BusinessDatabase db) {
            businessDao = db.businessDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            businessDao.insert(new Business("Title 1", "Description 1", 1));
            businessDao.insert(new Business("Title 2", "Description 2", 2));
            businessDao.insert(new Business("Title 3", "Description 3", 3));
            return null;
        }
    }
}
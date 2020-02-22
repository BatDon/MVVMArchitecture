package com.test.table.mvvmarchitecture;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class BusinessRepository {
    private BusinessDao businessDao;
    private LiveData<List<Business>> allBusinesses;

    public BusinessRepository(Application application) {
        BusinessDatabase database = BusinessDatabase.getInstance(application);
        businessDao = database.businessDao();
        allBusinesses = businessDao.getAllBusinesses();
    }

    public void insert(Business business) {
        new InsertBusinessAsyncTask(businessDao).execute(business);
    }

    public void update(Business business) {
        new UpdateBusinessAsyncTask(businessDao).execute(business);
    }

    public void delete(Business business) {
        new DeleteBusinessAsyncTask(businessDao).execute(business);
    }

    public void deleteAllBusinesses() {
        new DeleteAllBusinessesAsyncTask(businessDao).execute();
    }

    public LiveData<List<Business>> getAllBusinesses() {
        return allBusinesses;
    }

    private static class InsertBusinessAsyncTask extends AsyncTask<Business, Void, Void> {
        private BusinessDao businessDao;

        private InsertBusinessAsyncTask(BusinessDao businessDao) {
            this.businessDao = businessDao;
        }

        @Override
        protected Void doInBackground(Business... businesses) {
            businessDao.insert(businesses[0]);
            return null;
        }
    }

    private static class UpdateBusinessAsyncTask extends AsyncTask<Business, Void, Void> {
        private BusinessDao businessDao;

        private UpdateBusinessAsyncTask(BusinessDao businessDao) {
            this.businessDao = businessDao;
        }

        @Override
        protected Void doInBackground(Business... businesses) {
            businessDao.update(businesses[0]);
            return null;
        }
    }

    private static class DeleteBusinessAsyncTask extends AsyncTask<Business, Void, Void> {
        private BusinessDao businessDao;

        private DeleteBusinessAsyncTask(BusinessDao businessDao) {
            this.businessDao = businessDao;
        }

        @Override
        protected Void doInBackground(Business... businesses) {
            businessDao.delete(businesses[0]);
            return null;
        }
    }

    private static class DeleteAllBusinessesAsyncTask extends AsyncTask<Void, Void, Void> {
        private BusinessDao businessDao;

        private DeleteAllBusinessesAsyncTask(BusinessDao businessDao) {
            this.businessDao = businessDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            businessDao.deleteAllBusinesses();
            return null;
        }
    }
}
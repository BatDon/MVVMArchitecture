package com.test.table.mvvmarchitecture;



import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;


public class BusinessViewModel extends AndroidViewModel {
    private BusinessRepository repository;
    private LiveData<List<Business>> allBusinesses;

    public BusinessViewModel(@NonNull Application application) {
        super(application);
        repository = new BusinessRepository(application);
        allBusinesses = repository.getAllBusinesses();
    }

    public void insert(Business business) {
        repository.insert(business);
    }

    public void update(Business business) {
        repository.update(business);
    }

    public void delete(Business business) {
        repository.delete(business);
    }

    public void deleteAllBusinesses() {
        repository.deleteAllBusinesses();
    }

    public LiveData<List<Business>> getAllBusinesses() {
        return allBusinesses;
    }
}

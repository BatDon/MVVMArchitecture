package com.test.table.mvvmarchitecture;



import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import javax.security.auth.login.LoginException;


public class BusinessViewModel extends AndroidViewModel {
    private BusinessRepository repository;
    private LiveData<List<Business>> allBusinesses;
    private LiveData<List<Business>> filteredBusinesses;

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

    public LiveData<List<Business>> getFilteredBusinesses(String businessTitle) {
        Log.i("ViewModel 0 ","filteredBusinesses "+filteredBusinesses);
        filteredBusinesses=repository.getFilteredBusinesses(businessTitle);
//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        if(filteredBusinesses.getValue()==null){
//            Log.i("ViewModel ","filteredBusinesses "+filteredBusinesses.getValue());
//            Log.i("ViewModel ","all Businesses "+allBusinesses.getValue());
//            return allBusinesses;
//        }
//        else {
//            Log.i("ViewModel 1 ","filteredBusinesses "+filteredBusinesses.getValue());
//            if(filteredBusinesses.getValue()!=null){
//               Log.i("ViewModel 2= ", "size= "+filteredBusinesses.getValue().size());
//            }
//            return filteredBusinesses;
//            }

        return filteredBusinesses;

    }
}

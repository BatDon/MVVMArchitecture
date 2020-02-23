package com.test.table.mvvmarchitecture;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface BusinessDao {

    @Insert
    void insert(Business business);

    @Update
    void update(Business business);

    @Delete
    void delete(Business business);

    @Query("DELETE FROM business_table")
    void deleteAllBusinesses();

    @Query("SELECT * FROM business_table ORDER BY priority DESC")
    LiveData<List<Business>> getAllBusinesses();

    @Query("SELECT * FROM business_table WHERE title LIKE :businessTitle")
    LiveData<List<Business>> getFilteredBusinesses(String businessTitle);

//    @Query("SELECT COUNT(title) FROM business_table")
//            int getBusinessCount();
}

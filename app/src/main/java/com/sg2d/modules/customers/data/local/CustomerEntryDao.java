package com.sg2d.modules.customers.data.local;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import io.reactivex.Flowable;

@Dao
public interface CustomerEntryDao {

//    @Query("SELECT * FROM WeatherEntryTable WHERE date >= :date")
//    Flowable<List<WeatherEntryTable>> getAll(Date date);

    @Query("SELECT * FROM CustomerEntryTable ORDER BY level Asc")
    Flowable<List<CustomerEntryTable>> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(CustomerEntryTable customerEntry);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertBulk(List<CustomerEntryTable> customerEntry);

    @Query("DELETE FROM CustomerEntryTable")
    void delete();

    @Delete
    void delete(CustomerEntryTable customerEntry);
}

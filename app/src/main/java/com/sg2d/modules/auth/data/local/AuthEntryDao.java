package com.sg2d.modules.customers.data.local;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.sg2d.modules.auth.data.local.AuthEntryTable;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;

@Dao
public interface AuthEntryDao {

//    @Query("SELECT * FROM WeatherEntryTable WHERE date >= :date")
//    Flowable<List<WeatherEntryTable>> getAll(Date date);

    @Query("SELECT * FROM AuthEntryTable Limit 1")
    Single<AuthEntryTable> getAuthEntry();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(AuthEntryTable authEntry);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertBulk(List<AuthEntryTable> authEntry);

    @Query("DELETE FROM AuthEntryTable")
    void delete();

    @Delete
    void delete(AuthEntryTable customerEntry);
}

package com.jagoancoding.tobuy.db;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface PurchaseDao {

    @Query("SELECT * FROM Purchase")
    LiveData<List<Purchase>> getAllPurchases();

    @Query("SELECT * FROM Purchase " +
            "WHERE Purchase.id LIKE :id")
    LiveData<Purchase> loadPurchaseById(Long id);

    @Query("SELECT * FROM Purchase " +
            "WHERE Purchase.name LIKE :name " +
            "LIMIT 1")
    LiveData<Purchase> loadPurchaseByName(String name);

    @Insert(onConflict = REPLACE)
    long insert(Purchase purchase);

    @Update
    int update(Purchase... purchases);

    @Delete
    void delete(Purchase... purchases);

    @Query("DELETE FROM Purchase")
    void deleteAll();
}

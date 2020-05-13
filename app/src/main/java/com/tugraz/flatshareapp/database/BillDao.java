package com.tugraz.flatshareapp.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.tugraz.flatshareapp.database.Models.Bill;

import java.util.List;

@Dao
public interface BillDao {

    @Insert
    void insert(Bill bill);

    @Update
    void update(Bill bill);

    @Delete
    void delete(Bill bill);

    @Query("DELETE FROM bill")
    void deleteAllBills();

    @Query("SELECT * FROM bill")
    List<Bill> getAllBills();
}

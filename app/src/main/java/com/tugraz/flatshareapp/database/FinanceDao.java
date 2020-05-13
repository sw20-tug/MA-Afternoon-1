package com.tugraz.flatshareapp.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.tugraz.flatshareapp.database.Models.Finance;

import java.util.List;

@Dao
public interface FinanceDao {

    @Insert
    void insert(Finance finance);

    @Insert
    void update(Finance finance);

    @Delete
    void delete(Finance finance);

    @Query("DELETE FROM finance")
    void deleteAllFinances();

    @Query("SELECT * FROM finance")
    List<Finance> getAllFinances();
}

package com.tugraz.flatshareapp.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.tugraz.flatshareapp.database.Models.Cleaning;

import java.util.List;

@Dao
public interface CleaningDao {

    @Insert
    void insert(Cleaning cleaning);

    @Update
    void update(Cleaning cleaning);

    @Delete
    void delete(Cleaning cleaning);

    @Query("DELETE FROM cleaning")
    void deleteAllCleanings();

    @Query("SELECT * FROM cleaning")
    List<Cleaning> getAllCleanings();
}

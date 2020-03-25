package com.tugraz.flatshareapp.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.tugraz.flatshareapp.database.Models.Flat;

import java.util.List;

@Dao
public interface FlatDao {

    @Insert
    void insert(Flat flat);

    @Update
    void update(Flat flat);

    @Delete
    void delete(Flat flat);

    @Query("DELETE FROM flat")
    void deleteAllFlats();

    @Query("SELECT * FROM flat")
    List<Flat> getAllFlats();
}

package com.tugraz.flatshareapp.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.tugraz.flatshareapp.database.Models.Roommate;

import java.util.List;

@Dao
public interface RoommateDao {

    @Query("SELECT * FROM roommate WHERE id=:id")
    Roommate getRoommate(int id);

    @Insert
    long insert(Roommate roommate);

    @Update
    int update(Roommate roommate);

    @Delete
    void delete(Roommate roommate);

    @Query("DELETE FROM roommate")
    void deleteAllRoommates();

    @Query("SELECT * FROM roommate")
    List<Roommate> getAllRoommates();


}

package com.tugraz.flatshareapp.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.tugraz.flatshareapp.database.Models.ShoppingList;

import java.util.List;

@Dao
public interface ShoppingListDao {

    @Insert
    void insert(ShoppingList shoppingList);

    @Update
    void update(ShoppingList shoppingList);

    @Delete
    void delete(ShoppingList shoppingList);

    @Query("DELETE FROM shoppingList")
    void deleteAllShoppingLists();

    @Query("SELECT * FROM shoppingList")
    List<ShoppingList> getAllShoppingLists();
}


package com.tugraz.flatshareapp.database.Models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity
public class ShoppingList {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private String description;
    private boolean completed;
    private int flatId;

    public ShoppingList(String name, String description, Boolean completed, int flatId) {
        this.name = name;
        this.description = description;
        this.completed = completed;
        this.flatId = flatId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {this.id = id; }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }

    public int getFlatId() {
        return flatId;
    }

    public void setFlatId(int flatId) {
        this.flatId = flatId;
    }
}

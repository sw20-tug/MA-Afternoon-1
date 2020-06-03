package com.tugraz.flatshareapp.database.Models;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Finance {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String description;
    private float value;
    private int roommateId;
    private int flatId;

    public Finance(String description, float value, int roommateId, int flatId)
    {
        this.description = description;
        this.value = value;
        this.roommateId = roommateId;
        this.flatId = flatId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public int getRoommateId() {
        return roommateId;
    }

    public void setRoommateId(int roommateId) {
        this.roommateId = roommateId;
    }

    public int getFlatId() {
        return flatId;
    }

    public void setFlatId(int flatId) {
        this.flatId = flatId;
    }

    @NonNull
    @Override
    public String toString() {
        return description + " " + String.format("%.2f",value) + " €";
    }
}

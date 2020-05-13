package com.tugraz.flatshareapp.database.Models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Bill {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String description;
    private float value;
    private boolean monthly;
    private int flatId;

    public Bill(int id, String description, float value, boolean monthly, int flatId)
    {
        this.id = id;
        this.description = description;
        this.value = value;
        this.monthly = monthly;
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

    public boolean isMonthly() {
        return monthly;
    }

    public void setMonthly(boolean monthly) {
        this.monthly = monthly;
    }

    public int getFlatId() {
        return flatId;
    }

    public void setFlatId(int flatId) {
        this.flatId = flatId;
    }
}

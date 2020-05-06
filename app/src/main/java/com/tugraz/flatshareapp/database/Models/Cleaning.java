package com.tugraz.flatshareapp.database.Models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Cleaning {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private boolean weekly;
    private int dayOffSet;
    private String description;
    private boolean completed;
    private int roomateId;
    private int flatId;

    public Cleaning(boolean weekly, int dayOffSet, String description, boolean completed, int roomateId, int flatId) {
        this.weekly = weekly;
        this.dayOffSet = dayOffSet;
        this.description = description;
        this.completed = completed;
        this.roomateId = roomateId;
        this.flatId = flatId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {this.id = id; }

    public boolean isWeekly() {
        return weekly;
    }

    public void setWeekly(boolean weekly) {
        this.weekly = weekly;
    }

    public int getDayOffSet() {
        return dayOffSet;
    }

    public void setDayOffSet(int dayOffSet) {
        this.dayOffSet = dayOffSet;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public int getRoomateId() {
        return roomateId;
    }

    public void setRoomateId(int roomateId) {
        this.roomateId = roomateId;
    }

    public int getFlatId() {
        return flatId;
    }

    public void setFlatId(int flatId) {
        this.flatId = flatId;
    }
}

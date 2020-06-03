package com.tugraz.flatshareapp.database.Models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity
public class Cleaning {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private boolean weekly;
    private long doneTimestamp;
    private String description;
    private boolean completed;
    private int roommateId;
    private int flatId;

    public Cleaning(boolean weekly, long doneTimestamp, String description, boolean completed, int roommateId, int flatId) {
        this.weekly = weekly;
        this.doneTimestamp = doneTimestamp;
        this.description = description;
        this.completed = completed;
        this.roommateId = roommateId;
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

    public long getDoneTimestamp() {
        return doneTimestamp;
    }

    public void setDoneTimestamp(long doneTimestamp) {
        this.doneTimestamp = doneTimestamp;
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
}

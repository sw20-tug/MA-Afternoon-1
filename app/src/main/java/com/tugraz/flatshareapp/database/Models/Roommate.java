package com.tugraz.flatshareapp.database.Models;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity
public class Roommate {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private String lastName;
    private long roomateSince;
    private long birthday;
    private int flatId;

    public Roommate(String name, String lastName, Date roomateSince, Date birthday, int flatId) {
        this.name = name;
        this.lastName = lastName;
        this.roomateSince = roomateSince.getTime();
        this.birthday = birthday.getTime();
        this.flatId = flatId;
    }

    public Roommate(){

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

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public long getRoomateSince() { return roomateSince; }

    public void setRoomateSince(long roomateSince) {
        this.roomateSince = roomateSince;
    }

    public long getBirthday() { return birthday; }

    public void setBirthday(long birthday) { this.birthday = birthday; }

    public int getFlatId() {
        return flatId;
    }

    public void setFlatId(int flatId) {
        this.flatId = flatId;
    }

    @NonNull
    @Override
    public String toString() {
        return this.name + " " + this.lastName;
    }
}

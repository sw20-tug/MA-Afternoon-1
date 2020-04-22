package com.tugraz.flatshareapp.database.Models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity
public class Roommate {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private String lastName;
    private Date roomateSince;
    private Date birthday;
    private int flatId;

    public Roommate(String name, String lastName, Date roomateSince, Date birthday, int flatId) {
        this.name = name;
        this.lastName = lastName;
        this.roomateSince = roomateSince;
        this.birthday = birthday;
        this.flatId = flatId;
    }

    public Roommate(){

    }

    public int getId() {
        return id;
    }


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

    public Date getRoomateSince() {
        return roomateSince;
    }

    public void setRoomateSince(Date roomateSince) {
        this.roomateSince = roomateSince;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public int getFlatId() {
        return flatId;
    }

    public void setFlatId(int flatId) {
        this.flatId = flatId;
    }
}

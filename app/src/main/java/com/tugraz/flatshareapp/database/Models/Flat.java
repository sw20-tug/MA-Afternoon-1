package com.tugraz.flatshareapp.database.Models;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Flat {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private String streetName;
    private String streetNumber;
    private String city;
    private String country;

    public Flat(String name, String streetName, String streetNumber, String city, String country) {
        this.name = name;
        this.streetName = streetName;
        this.streetNumber = streetNumber;
        this.city = city;
        this.country = country;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NonNull
    @Override
    public String toString() {
        return "Flat: name=" + name + ", streetName=" + streetName +" ....";
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getStreetName() {
        return streetName;
    }

    public String getStreetNumber() {
        return streetNumber;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }
}

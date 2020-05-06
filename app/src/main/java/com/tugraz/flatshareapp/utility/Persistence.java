package com.tugraz.flatshareapp.utility;

public class Persistence {

    private static Persistence instance;

    private int activeFlatID;

    Persistence()
    {

    }

    public static Persistence Instance()
    {
        if(instance == null)
        {
            instance = new Persistence();
            return instance;
        }
        else
        {
            return instance;
        }
    }

    public void setActiveFlatID(int id)
    {
        activeFlatID = id;
    }

    public int getActiveFlatID()
    {
        return  activeFlatID;
    }
}

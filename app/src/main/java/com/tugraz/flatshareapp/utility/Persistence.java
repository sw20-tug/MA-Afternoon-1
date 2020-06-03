package com.tugraz.flatshareapp.utility;

public class Persistence {

    public static int INITIAL_ID = 0;

    private static Persistence instance;

    private int activeFlatID;

    private int activeRoommateId;

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

    public void setActiveRoommateId(int activeRoommateId) {
        this.activeRoommateId = activeRoommateId;
    }

    public int getActiveFlatID()
    {
        return  activeFlatID;
    }

    public int getActiveRoommateId() {
        return activeRoommateId;
    }
}

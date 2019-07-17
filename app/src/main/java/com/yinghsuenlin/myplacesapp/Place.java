package com.yinghsuenlin.myplacesapp;

public class Place
{
    private String mName;
    private String mDescription;

    public Place(String name, String description)
    {
        mName = name;
        mDescription = description;
    }

    public String getName()
    {
        return mName;
    }

    public void setName(String name)
    {
        mName = name;
    }

    public String getDescription()
    {
        return mDescription;
    }

    public void setDescription(String description)
    {
        mDescription = description;
    }
}

package com.example.wardrobewizard.data.model;

public class Clothing
{
    private int clothingID;
    private String name;

    public int getClothingID()
    {
        return this.clothingID;
    }
    public String getName()
    {
        return this.name;
    }

    public void setClothingID(int clothingID)
    {
        this.clothingID = clothingID;
    }

    public void setName(String name)
    {
        this.name = name;
    }
}
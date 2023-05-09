import java.util.ArrayList;

package com.example.wardrobewizard.data.model;

public class Closet
{
    private ArrayList<Clothing> clothingList;

    public Closet(ArrayList<Clothing> clothingList)
    {
        this.clothingList = clothingList;
    }

    public ArrayList<Clothing> getClothingList()
    {
        return clothingList;
    }

    public void addClothing(Clothing cl)
    {
        this.clothingList.add(cl);
    }

    public String removeClothing(String name)
    {
        for(int i = 0; i < this.clothingList.size(); i++)
        {
            if(this.clothingList.get(i).getName().equalsIgnoreCase(name))
            {
                this.clothingList.remove(this.clothingList.get(i));
                return this.clothingList.get(i).getName();
            }
        }

        return "";
    }

    //TODO implement sortClothing() method (determine what order in which the list is to be sorted)
}

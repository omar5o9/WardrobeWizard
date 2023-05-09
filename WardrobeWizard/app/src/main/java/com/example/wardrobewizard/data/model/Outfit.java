import java.util.ArrayList;

package com.example.wardrobewizard.data.model;

public class Outfit
{
    private ArrayList<Clothing> clothingList;

    public Outfit(ArrayList<Clothing> clothingList)
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
}

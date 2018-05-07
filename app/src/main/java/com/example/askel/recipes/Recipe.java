package com.example.askel.recipes;

import java.util.ArrayList;

/**
 * This class is an object used to retrieve recipe data
 * from FireBase.
 * @author Askel Eirik Johansson
 * @version 1.0
 * @since 05/05/2018
 */

public class Recipe {
    public final String key;
    public int priority;
    public final ArrayList<String> itemList;

    public Recipe(String key){
        this.key = key;
        this.priority = 0;
        this.itemList = new ArrayList<>();
    }

    /**
     * This method adds a string to "itemList"
     * @param s gets appended to "itemList"
     */
    public void appendList(String s){
        this.itemList.add(s);
    }

}

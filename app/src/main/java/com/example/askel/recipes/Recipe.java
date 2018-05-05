package com.example.askel.recipes;

import java.util.ArrayList;

/**
 * Created by askel on 04/05/2018.
 */

public class Recipe {
    public String key;
    public int priority;
    public ArrayList<String> itemList;

    public Recipe(String key){
        this.key = key;
        this.priority = 0;
        this.itemList = new ArrayList<>();
    }

    public void appendList(String s){
        this.itemList.add(s);
    }

}

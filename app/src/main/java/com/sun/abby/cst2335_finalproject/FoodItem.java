package com.sun.abby.cst2335_finalproject;

/**
 * Model class for each food item
 * Created by Abby on 2016-12-05.
 */

public class FoodItem {
    String name;
    String description;
    int id;

    public FoodItem(String name, String description){
        setName(name);
        setDescription(description);
    }

    public FoodItem(int id, String name, String description){
        setName(name);
        setId(id);
        setDescription(description);
    }

    public void setName(String name){ this.name = name; }
    public void setDescription(String description){ this.description = description; }
    public void setId(int id){ this.id = id; }
    public String getName(){ return name; }
    public int getId(){ return id; }
    public String getDescription(){ return description; }

    public String toString(){
        return getName();
    }
}

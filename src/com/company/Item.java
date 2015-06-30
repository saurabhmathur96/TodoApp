package com.company;

import java.util.stream.Stream;

/**
 * Created by saurabh on 30/6/15.
 */
public class Item {
    private  String _description;
    private String[] _categories;

    public Item(String description){
        this(description, new String[0]);
    }
    public Item(String description, String[] categories){
        this._description = description;
        this._categories = categories.clone();
    }

    public String get_description(){
        return _description;
    }
    public String[] get_categories(){
        return _categories;
    }
    public Boolean inCategory(String category){
        return Stream.of(_categories).anyMatch(c->category.equals(c));
    }
    @Override
    public String toString(){
        String res = "<Item description: " + _description + "; categories: [";
        String prefix = "";
        for(String category:_categories){
            res += prefix + category;
            prefix = ", ";
        }
        res += "]>";
        return  res;
    }
}

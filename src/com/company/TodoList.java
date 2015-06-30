package com.company;


import java.util.Collection;
import java.util.Iterator;
import java.util.stream.Collectors;


/**
 * Created by saurabh on 30/6/15.
 */
public class TodoList implements Iterable<Item>{
    private  final Collection<Item> _items;

    public  TodoList(){
        _items = new java.util.ArrayList<>();
    }

    public TodoList(Collection<Item> items) {
        this._items = items;

    }

    public void addItem(Item item){
        _items.add(item);
    }

    public void deleteItem(Item item){
        _items.remove(item);
    }

    public Collection<Item> inCategory(String category){
        return  _items.stream().filter(c->c.inCategory(category)).collect(Collectors.toList());
    }

    @Override
    public Iterator<Item> iterator(){
        return _items.iterator();
    }

    @Override
    public  String  toString(){
        String res = "[";
        String prefix = "";
        for(Item i : _items){
            res += prefix + i.toString();
            prefix = ", ";
        }
        res += "]";
        return  res;
    }
}

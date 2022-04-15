package model;

import exceptions.ListItemNotFoundException;

import java.util.HashMap;

public class ToDoList {

    private final HashMap<String, ListItem> items;

    public ToDoList(){
        this.items = new HashMap<>();
    }

    public void addListItem(ListItem item){
        this.items.put(item.getTitle(), item);
    }

    public boolean removeListItem(String title){
        return items.remove(title) != null;

    }

    public void clearAllListItems(){
        this.items.clear();
    }

    public ListItem getListItem(String listItemName)throws ListItemNotFoundException{
        ListItem item = items.get(listItemName);
        if (item != null){
            return item;
        }
        throw new ListItemNotFoundException("Item '" + listItemName + "' cannot be found");
    }

    public HashMap<String, ListItem> getAllListItems(){
        return this.items;
    }

    public void printAllListItems(){
        items.values().forEach(System.out::println);
    }
}

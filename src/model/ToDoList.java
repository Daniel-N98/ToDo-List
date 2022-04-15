package model;

import java.util.ArrayList;

public class ToDoList {

    private ArrayList<ListItem> items;

    public void addListItem(ListItem item){
        this.items.add(item);
    }

    public void removeListItem(ListItem item){
        this.items.remove(item);
    }

    public void clearAllListItems(){
        this.items.clear();
    }

    public ListItem getListItem(String listItemName){
        for (ListItem item : items){
            if (item.getTitle().equals(listItemName)){
                return item; // Item found with this title, return it.
            }
        }
        return null; // No item with this title exists
    }

    public ArrayList<ListItem> getAllListItems(){
        return this.items;
    }
}

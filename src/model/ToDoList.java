package model;

import exceptions.InvalidDateTimeFormatException;
import exceptions.InvalidItemStatusException;
import exceptions.ListItemAlreadyExists;
import exceptions.ListItemNotFoundException;
import types.ItemStatus;
import util.DateParser;
import util.InputReader;

import java.time.LocalDateTime;
import java.util.HashMap;

public class ToDoList {

    private final HashMap<String, ListItem> items;

    public ToDoList() {
        this.items = new HashMap<>();
    }

    public void createListItem(InputReader reader) throws ListItemAlreadyExists {
        ListItem listItem = new ListItem();

        String title = reader.getNextText("\nEnter the ToDo-List title"); // Request the title of the new item to be added
        if (listItemExists(title)) { // Check the item with that title does not already exist
            throw new ListItemAlreadyExists("An item with name '" + title + "' already exists"); // Item already exists, throw exception
        }

        String text = reader.getNextText("\nEnter the ToDo-List description");  // Request the description of the new item to be added

        listItem.setTitle(title);
        listItem.setText(text);
        addDueDate(reader, listItem); // Asks the user to add a due date

        addToDoListItem(listItem);
    }

    private void addToDoListItem(ListItem item) {
        this.items.put(item.getTitle(), item);
    }

    public void removeListItem(InputReader reader) throws ListItemNotFoundException {
        String title = reader.getNextText("\nEnter the ToDo-List title"); // Request the title of the item to be removed
        if (items.remove(title) == null) {
            throw new ListItemNotFoundException("Item '" + title + "' cannot be found");
        }
        items.remove(title);
        System.out.println("\nThe list item has been removed.");
    }

    public void clearAllListItems() {
        this.items.clear();
    }

    public ListItem getListItem(String listItemName) throws ListItemNotFoundException {
        ListItem item = items.get(listItemName);
        if (item != null) {
            return item;
        }
        throw new ListItemNotFoundException("Item '" + listItemName + "' cannot be found");
    }

    public HashMap<String, ListItem> getAllListItems() {
        return this.items;
    }


    public void addDueDate(InputReader reader, ListItem listItem) {
        String dueDate = reader.getNextText("\nEnter the due date using format [yyyy-MM-dd HH:mm][Leave blank if none]");
        if (dueDate.length() == 0) return; // No due date was provided, return
        try {
            LocalDateTime dateTime = DateParser.parseStringToLocalDateTime(dueDate, "yyyy-MM-dd HH:mm"); // Format the dueDate String into a LocalDateTime with the provided format
            listItem.setDueDate(dateTime); // Set the date
        } catch (InvalidDateTimeFormatException e) {
            e.printStackTrace();
        }
    }

    public void printAllListItems() {
        getAllListItems().values().forEach(System.out::println);
    }

    private boolean listItemExists(String title) {
        return items.containsKey(title);
    }
}

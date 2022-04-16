package model;

import exceptions.InvalidItemStatusException;
import exceptions.ListItemAlreadyExists;
import exceptions.ListItemNotFoundException;
import types.ItemStatus;
import util.InputReader;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;

public class ToDoList {

    private final HashMap<String, ListItem> items;

    public ToDoList() {
        this.items = new HashMap<>();
    }

    public void createListItem(InputReader reader) throws ListItemAlreadyExists {
        ListItem listItem = new ListItem();

        String title = reader.getNextText("\nEnter the ToDo-List title"); // Request the title of the new item to be added
        if (listItemExists(title)) {
            throw new ListItemAlreadyExists("An item with name '" + title + "' already exists");
        }

        String text = reader.getNextText("\nEnter the ToDo-List description");  // Request the description of the new item to be added

        listItem.setTitle(title);
        listItem.setText(text);
        try {
            listItem.setStatus(ItemStatus.PENDING);
        } catch (InvalidItemStatusException e) {
            e.printStackTrace();
        }
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
        if (dueDate.length() == 0) return;
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"); // Format used for the LocalDateTime
            LocalDateTime dateDateTime = LocalDateTime.parse(dueDate, formatter); // Format the dueDate String into a LocalDateTime using the formatter
            listItem.setDueDate(dateDateTime);

        } catch (DateTimeParseException e) {
            System.out.println("\nIncorrect date format");
        }
    }

    public void printAllListItems() {
        getAllListItems().values().forEach(System.out::println);
    }

    private boolean listItemExists(String title) {
        return items.containsKey(title);
    }
}

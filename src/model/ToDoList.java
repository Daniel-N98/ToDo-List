package model;

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

    public void createListItem(InputReader reader) {
        ListItem listItem = new ListItem();

        String title = reader.getNextText("Enter the ToDo-List title"); // Request the title of the new item to be added
        String text = reader.getNextText("Enter the ToDo-List description");  // Request the description of the new item to be added

        listItem.setTitle(title);
        listItem.setText(text);
        listItem.setStatus(ItemStatus.PENDING);
        addDueDate(reader, listItem); // Asks the user to add a due date

        addToDoListItem(listItem);
    }

    private void addToDoListItem(ListItem item){
        this.items.put(item.getTitle(), item);
    }

    public boolean removeListItem(InputReader reader) {
        String title = reader.getNextText("Enter the ToDo-List title"); // Request the title of the item to be removed

        return items.remove(title) != null;
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
        String dueDate = reader.getNextText("Enter the due date using format [yyyy-MM-dd HH:mm][Leave blank if none]");
        if (dueDate.length() == 0) return;
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"); // Format used for the LocalDateTime
            LocalDateTime dateDateTime = LocalDateTime.parse(dueDate, formatter); // Format the dueDate String into a LocalDateTime using the formatter
            listItem.setDueDate(dateDateTime);

        } catch (DateTimeParseException e) {
            System.out.println("Incorrect date format");
        }
    }

    public void printAllListItems() {
        getAllListItems().values().forEach(System.out::println);
    }
}

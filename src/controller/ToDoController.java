package controller;

import exceptions.ListItemNotFoundException;
import model.ListItem;
import model.ToDoList;
import types.ItemStatus;
import util.InputReader;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class ToDoController {
    private final InputReader reader;
    private final ToDoList toDoList;

    public ToDoController() {
        this.reader = new InputReader(); // Instantiate the InputReader
        this.toDoList = new ToDoList();
        MenuController menuController = new MenuController(); // Instantiate the MenuController
        int option;

        do {
            menuController.printMainMenu(); // Prints out the main menu
            option = menuController.requestUserOption(this.reader); // Returns user input

            selectMenu(option); // Select the menu based on user input
        } while (option != 6);
    }

    // TODO - Create methods to request user input to create/remove/view ToDo list items

    // TODO - Add item into SQL database
    public void addToDoItem() {
        toDoList.addListItem(createToDoItem());
    }

    // TODO - Create a ToDo item based on user input
    private ListItem createToDoItem() {
        String title = reader.getNextText("Enter the ToDo-List title"); // Request the title of the new item to be added
        String text = reader.getNextText("Enter the ToDo-List description");  // Request the description of the new item to be added
        String dueDate = reader.getNextText("Enter the due date using format [yyyy-MM-dd HH:mm][Leave blank if none]");
        ListItem listItem = new ListItem(title, text, ItemStatus.PENDING);

        if (dueDate.length() > 0) {
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"); // Format used for the LocalDateTime
                LocalDateTime dateDateTime = LocalDateTime.parse(dueDate, formatter); // Format the dueDate String into a LocalDateTime using the formatter
                listItem.setDueDate(dateDateTime);
            } catch (DateTimeParseException e) {
                System.out.println("Incorrect date format");
            }
        }
        return listItem;
    }

    // TODO - Remove item from SQL database
    public void removeToDoItem() {
        String title = reader.getNextText("Enter the ToDo-List title"); // Request the title of the item to be removed

        if (toDoList.removeListItem(title)) {
            System.out.println("Removed successfully"); // Removed successfully
        } else {
            System.out.println("Failed to remove.."); // Error removing item
        }
    }

    // TODO - Print all list items in a readable format
    public void printToDoList() {
        toDoList.printAllListItems();
    }

    public void clearToDoList() {
        toDoList.clearAllListItems(); // Remove all items from the ToDoList
    }

    public void updateToDoList() {
        String title = reader.getNextText("Enter the ToDo-List title"); // Request the title of the item to be updated
        ListItem listItemSelected;
        try {
            listItemSelected = toDoList.getListItem(title);
        } catch (ListItemNotFoundException e) {
            e.printStackTrace();
        }

        // TODO - Print menu with options for editing the existing item above
    }


    private void selectMenu(int option) {
        switch (option) {
            case 1 -> printToDoList();
            case 2 -> addToDoItem();
            case 3 -> removeToDoItem();
            case 4 -> clearToDoList();
            case 5 -> updateToDoList();
        }
    }
}

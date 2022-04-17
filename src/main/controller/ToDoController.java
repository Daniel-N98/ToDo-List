package main.controller;

import main.exceptions.ListItemNotFoundException;
import main.model.ToDoList;
import main.types.ItemStatus;
import main.util.InputReader;
import main.exceptions.InvalidItemStatusException;
import main.exceptions.ListItemAlreadyExistsException;
import main.model.ListItem;

public class ToDoController {

    private final InputReader reader;
    private final ToDoList toDoList;
    private final MenuController menuController = new MenuController(); // Instantiate the MenuController

    /**
     * Constructor for the ToDoController class
     * Instantiates global variables and begins the application
     */
    public ToDoController() {
        this.reader = new InputReader(); // Instantiate the InputReader
        this.toDoList = new ToDoList();

        start();
    }

    /**
     * Begins the application by printing the Title,
     * and printing the main menu to the user.
     * Passes the option selected by the user
     */
    private void start() {
        int option;

        System.out.println(MenuController.TITLE);
        do {
            option = printMenuReturnInput(menuController.getMainMenu()); // Prints out the provided menu, and returns the input from the user
            selectMenu(option); // Select the menu based on user input
        } while (option != 6);
    }

    /**
     * Attempts to add a new ListItem into the database
     */
    public void addToDoItem() {
        try {
            ListItem item = toDoList.createListItem(reader); // Creates the ListItem from user input
            toDoList.addToDoListItem(item); // Stores the ListItem in the database
            System.out.println("List item has been created");
        } catch (ListItemAlreadyExistsException e) {
            e.printStackTrace();
        }
    }

    /**
     * Attempts to remove a ListItem from the database
     */
    public void removeToDoItem() {
        try {
            toDoList.removeListItem(reader.getNextText("\nEnter the list item title"));
            System.out.println("\nThe list item has been removed.");
        }catch (ListItemNotFoundException e){
            e.printStackTrace();
        }
    }

    /**
     * Prints all ListItem objects from the database
     */
    public void printToDoList() {
        toDoList.printAllListItems(); // Print all the list items to the console
    }

    /**
     * Clears all ListItem objects from the database
     */
    public void clearToDoList() {
        toDoList.clearAllListItems(); // Remove all items from the ToDoList
    }

    /**
     * Attempts to update a ListItem based on the user input
     */
    public void updateToDoList() {

        ListItem listItemSelected;
        try {
            listItemSelected = toDoList.getListItem(reader.getNextText("\nEnter the list item title")); // Attempt to retrieve a list item with the passed title
            int option;

            do {
                toDoList.removeListItem(listItemSelected.getTitle()); // Remove the item whilst it is being edited
                printItemBeingEdited(listItemSelected); // Print out the item that is being edited
                option = printMenuReturnInput(menuController.getItemEditorMenu()); // Print out the menu and initialize 'option' variable with the int returned

                selectFromEditorMenu(listItemSelected, option); // Calls a method based on the option

                toDoList.updateListItem(listItemSelected); // Replace the item
            } while (option != 5);

        } catch (ListItemNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Attempts to update the status of a ListItem
     *
     * @param item to update
     */
    public void updateItemStatus(ListItem item) {
        int option;
        printItemBeingEdited(item);
        option = printMenuReturnInput(menuController.getStatusEditorMenu());

        if (option < 4 && option > 0) {
            try {
                ItemStatus status = ItemStatus.getStatus(option - 1); // Retrieve the ItemStatus at the index specified.
                item.setStatus(status); // Update the items status
            } catch (InvalidItemStatusException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Selects the correct method based on the passed parameter
     *
     * @param option to select
     */
    private void selectMenu(int option) {
        switch (option) {
            case 1 -> printToDoList();
            case 2 -> addToDoItem();
            case 3 -> removeToDoItem();
            case 4 -> clearToDoList();
            case 5 -> updateToDoList();
        }
    }

    /**
     * Edits the ListItem based on user input
     *
     * @param listItem item to edit
     * @param option   to select
     */
    private void selectFromEditorMenu(ListItem listItem, int option) {
        switch (option) {
            case 1 -> listItem.setTitle(reader.getNextText("\nEnter a new list item title")); // Sets the item title with the value returned from the reader
            case 2 -> listItem.setText(reader.getNextText("\nEnter a new description")); // Sets the item description with the value returned from the reader
            case 3 -> toDoList.addDueDate(reader, listItem); // Sets the item due date with the value returned from the reader
            case 4 -> updateItemStatus(listItem); // Calls the method to print out, and handle the updateItemStatus menu
        }
    }

    /**
     * Prints out the ListItem parameter to show what ListItem is being edited
     *
     * @param item to print out
     */
    private void printItemBeingEdited(ListItem item) {
        System.out.println("\nYou are editing: \n" + item.toString());
    }

    /**
     * Prints out the passed menu and returns the users next input
     *
     * @param menu to print out
     * @return int provided by the user
     */
    private int printMenuReturnInput(String menu) {
        System.out.println(menu);
        return menuController.requestUserOption(reader);
    }
}

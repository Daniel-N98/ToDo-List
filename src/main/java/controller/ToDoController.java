package controller;

import exceptions.InvalidItemTitleException;
import exceptions.InvalidOptionException;
import exceptions.ListItemNotFoundException;
import model.ToDoList;
import types.ItemStatus;
import util.InputReader;
import exceptions.InvalidItemStatusException;
import model.ListItem;

public class ToDoController {

    // Declare the InputReader, used to read user input
    private final InputReader reader;

    // Declare the ToDoList, controls ListItem Objects
    private final ToDoList toDoList;

    // Instantiate the MenuController
    private final MenuController menuController = new MenuController();

    /**
     * Constructor for the ToDoController class
     * Instantiates global variables and begins the application
     */
    public ToDoController() {
        // Instantiate the instance variables
        this.reader = new InputReader();
        this.toDoList = new ToDoList();

        // Begin the application
        start();
    }

    /**
     * Begins the application by printing the Title,
     * and printing the main menu to the user.
     * Passes the option selected by the user
     */
    private void start() {
        int option;

        // Print out the initial starting message of the application
        System.out.println(MenuController.TITLE);
        do {
            try {
                // Prints out the provided menu, and returns the input from the user
                option = printMenuReturnInput(this.menuController.getMainMenu());
                // Select the menu based on user input
                selectMenu(option);
            } catch (InvalidOptionException e) {
                e.printStackTrace();
                option = 0;
            }
        } while (option != 6);
    }

    /**
     * Attempts to add a new ListItem into the database
     */
    private void addToDoItem() {
        // Stores a ListItem in the database
        this.toDoList.addToDoListItem(reader);
    }

    /**
     * Attempts to remove a ListItem from the database
     */
    private void removeToDoItem() {
        // Removes a ListItem from the database
        this.toDoList.removeListItem(reader);
    }

    /**
     * Attempts to update a ListItem based on the user input
     */
    private void updateToDoList() {

        ListItem listItemSelected;
        int option;

        try {
            listItemSelected = this.toDoList.getListItem(this.reader.getNextText("\nEnter the list item title")); // Attempt to retrieve a list item with the passed title
            do {
                // Print out the passed menu, and return the input from the user
                option = printMenuReturnInput(this.menuController.getItemEditorMenu());
                // Update the ListItem with the selected option
                updateItem(listItemSelected, option);

                System.out.println(listItemSelected.getTitle() + " has been updated and saved.");
            } while (option != 5);
        } catch (ListItemNotFoundException | InvalidItemTitleException | InvalidOptionException e) {
            e.printStackTrace();
        }
    }

    /**
     * Perform updates on the item with the selected option
     *
     * @param item   to be edited
     * @param option selected
     */
    private void updateItem(ListItem item, int option) {
        // Print the ListItem
        toDoList.printItem(item);
        // Remove the ListItem from the database while it's being updated
        this.toDoList.removeListItem(item);
        // Begin editing the ListItem
        selectFromEditorMenu(item, option);
        // Insert the ListItem back into the database
        this.toDoList.updateListItem(item);
    }

    /**
     * Attempts to update the status of a ListItem
     *
     * @param item to update
     */
    private void updateItemStatus(ListItem item) {
        int option;
        // Print the ListItem
        toDoList.printItem(item);
        try {
            // Print out the passed menu, and return the input from the user
            option = printMenuReturnInput(this.menuController.getStatusEditorMenu());
            // Retrieve the ItemStatus at the index specified. (-1 since the values begin at index '0')
            ItemStatus status = ItemStatus.getStatus(option - 1);
            // Update the items status
            item.setStatus(status);
        } catch (InvalidItemStatusException | InvalidOptionException e) {
            e.printStackTrace();
        }
    }

    /**
     * Selects the correct method based on the passed parameter
     *
     * @param option to select
     */
    private void selectMenu(int option) {
        switch (option) {
            case 1 -> toDoList.printAllListItems();
            case 2 -> addToDoItem();
            case 3 -> removeToDoItem();
            case 4 -> toDoList.clearAllListItems();
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
            case 1 -> this.toDoList.updateTitle(listItem, reader); // Sets the item title with the value returned from the reader
            case 2 -> listItem.setDescription(this.reader.getNextText("\nEnter a new description")); // Sets the item description with the value returned from the reader
            case 3 -> this.toDoList.addDueDate(this.reader, listItem); // Sets the item due date with the value returned from the reader
            case 4 -> updateItemStatus(listItem); // Calls the method to print out, and handle the updateItemStatus menu
        }
    }

    /**
     * Prints out the passed menu and returns the users next input
     *
     * @param menu to print out
     * @return int provided by the user
     * @throws InvalidOptionException if the user enters an invalid option
     */
    private int printMenuReturnInput(String menu) throws InvalidOptionException {
        return this.menuController.requestUserOption(menu, this.reader);
    }
}

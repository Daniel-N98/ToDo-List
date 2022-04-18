package controller;

import exceptions.InvalidDateTimeFormatException;
import exceptions.ListItemNotFoundException;
import model.ToDoList;
import types.ItemStatus;
import util.InputReader;
import exceptions.InvalidItemStatusException;
import model.ListItem;

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
            option = printMenuReturnInput(this.menuController.getMainMenu()); // Prints out the provided menu, and returns the input from the user
            selectMenu(option); // Select the menu based on user input
        } while (option != 6);
    }

    /**
     * Attempts to add a new ListItem into the database
     */
    public void addToDoItem() {
        this.toDoList.addToDoListItem(reader); // Stores the ListItem in the database
    }

    /**
     * Attempts to remove a ListItem from the database
     */
    public void removeToDoItem() {
        this.toDoList.removeListItem(reader);
    }

    /**
     * Attempts to update a ListItem based on the user input
     */
    public void updateToDoList() {

        ListItem listItemSelected;
        int option;

        try {
            listItemSelected = this.toDoList.getListItem(this.reader.getNextText("\nEnter the list item title")); // Attempt to retrieve a list item with the passed title
            do {
                option = printMenuReturnInput(this.menuController.getItemEditorMenu()); // Print out the menu and initialize 'option' variable with the int returned
                updateItem(listItemSelected, option);

                System.out.println(listItemSelected.getTitle() + " has been updated and saved.");
            } while (option != 5);
        } catch (ListItemNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Perform updates on the item with the selected option
     *
     * @param item to be edited
     * @param option selected
     */
    private void updateItem(ListItem item, int option) {
        toDoList.printItem(item);
        this.toDoList.removeListItem(item);
        selectFromEditorMenu(item, option);
        this.toDoList.updateListItem(item);
    }

    /**
     * Attempts to update the status of a ListItem
     *
     * @param item to update
     */
    public void updateItemStatus(ListItem item) {
        int option;
        toDoList.printItem(item);
        option = printMenuReturnInput(this.menuController.getStatusEditorMenu());

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
            case 1 -> listItem.setTitle(this.reader.getNextText("\nEnter a new list item title")); // Sets the item title with the value returned from the reader
            case 2 -> listItem.setText(this.reader.getNextText("\nEnter a new description")); // Sets the item description with the value returned from the reader
            case 3 -> {
                try {
                    this.toDoList.addDueDate(this.reader, listItem); // Sets the item due date with the value returned from the reader
                } catch (InvalidDateTimeFormatException e) {
                    e.printStackTrace();
                }
            }
            case 4 -> updateItemStatus(listItem); // Calls the method to print out, and handle the updateItemStatus menu
        }
    }

    /**
     * Prints out the passed menu and returns the users next input
     *
     * @param menu to print out
     * @return int provided by the user
     */
    private int printMenuReturnInput(String menu) {
        return this.menuController.requestUserOption(menu, this.reader);
    }
}

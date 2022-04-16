package controller;

import exceptions.InvalidItemStatusException;
import exceptions.ListItemAlreadyExists;
import exceptions.ListItemNotFoundException;
import model.ListItem;
import model.ToDoList;
import types.ItemStatus;
import util.InputReader;

public class ToDoController {
    private final InputReader reader;
    private final ToDoList toDoList;
    private final MenuController menuController = new MenuController(); // Instantiate the MenuController

    public ToDoController() {
        this.reader = new InputReader(); // Instantiate the InputReader
        this.toDoList = new ToDoList();
        int option;

        System.out.println(MenuController.TITLE);
        do {
            option = printMenuReturnInput(menuController.getMainMenu()); // Prints out the provided menu, and returns the input from the user
            selectMenu(option); // Select the menu based on user input
        } while (option != 6);
    }

    public void addToDoItem() {
        try {
            toDoList.createListItem(reader);
        }catch (ListItemAlreadyExists e){
            e.printStackTrace();
        }
    }

    public void removeToDoItem() {
        try {
            toDoList.removeListItem(reader);
        }catch (ListItemNotFoundException e){
            e.printStackTrace();
        }
    }

    public void printToDoList() {
        toDoList.printAllListItems(); // Print all the list items to the console
    }

    public void clearToDoList() {
        toDoList.clearAllListItems(); // Remove all items from the ToDoList
    }

    public void updateToDoList() {

        ListItem listItemSelected;
        try {
            listItemSelected = toDoList.getListItem(reader.getNextText("\nEnter the list item title")); // Attempt to retrieve a list item with the passed title
            int option;

            do {
                printItemBeingEdited(listItemSelected); // Print out the item that is being edited
                option = printMenuReturnInput(menuController.getItemEditorMenu()); // Print out the menu and initialize 'option' variable with the int returned

                selectFromEditorMenu(listItemSelected, option); // Calls a method based on the option
            } while (option != 5);

        } catch (ListItemNotFoundException e) {
            e.printStackTrace();
        }
    }

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


    private void selectMenu(int option) {
        switch (option) {
            case 1 -> printToDoList();
            case 2 -> addToDoItem();
            case 3 -> removeToDoItem();
            case 4 -> clearToDoList();
            case 5 -> updateToDoList();
        }
    }

    private void selectFromEditorMenu(ListItem listItem, int option) {
        switch (option) {
            case 1 -> listItem.setTitle(reader.getNextText("\nEnter a new title")); // Sets the item title with the value returned from the reader
            case 2 -> listItem.setText(reader.getNextText("\nEnter a new description")); // Sets the item description with the value returned from the reader
            case 3 -> toDoList.addDueDate(reader, listItem); // Sets the item due date with the value returned from the reader
            case 4 -> updateItemStatus(listItem); // Calls the method to print out, and handle the updateItemStatus menu
        }
    }

    private void printItemBeingEdited(ListItem item){
        System.out.println("\nYou are editing: \n" + item.toString());
    }

    private int printMenuReturnInput(String menu){
        System.out.println(menu);
        return menuController.requestUserOption(reader);
    }
}

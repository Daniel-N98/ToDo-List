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
            menuController.printMainMenu(); // Prints out the main menu
            option = menuController.requestUserOption(this.reader); // Returns user input

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
        toDoList.printAllListItems();
    }

    public void clearToDoList() {
        toDoList.clearAllListItems(); // Remove all items from the ToDoList
    }

    public void updateToDoList() {
        String title = reader.getNextText("\nEnter the ToDo-List title"); // Request the title of the item to be updated
        ListItem listItemSelected;
        try {
            listItemSelected = toDoList.getListItem(title); // Attempt to retrieve a list item with the passed title
            int option;

            do {
                printItemBeingEdited(listItemSelected);
                menuController.printItemEditorMenu();
                option = menuController.requestUserOption(reader);
                selectFromEditorMenu(listItemSelected, option);
            } while (option != 5);

        } catch (ListItemNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void updateItemStatus(ListItem item) {
        int option;
        printItemBeingEdited(item);
        menuController.printStatusEditor();
        option = menuController.requestUserOption(reader);

        if (option < 4 && option > 0) {
            try {
                ItemStatus status = ItemStatus.getStatus(option - 1);
                item.setStatus(status);
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
            case 1 -> listItem.setTitle(reader.getNextText("\nEnter a new title"));
            case 2 -> listItem.setText(reader.getNextText("\nEnter a new description"));
            case 3 -> toDoList.addDueDate(reader, listItem);
            case 4 -> updateItemStatus(listItem);
        }
    }

    private void printItemBeingEdited(ListItem item){
        System.out.println("\nYou are editing: \n" + item.toString());
    }
}

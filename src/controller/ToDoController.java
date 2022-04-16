package controller;

import exceptions.ListItemNotFoundException;
import model.ListItem;
import model.ToDoList;
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
        toDoList.createListItem(reader);
    }

    public void removeToDoItem() {
        System.out.println("The list item " + (!toDoList.removeListItem(reader) ? "has not " : "has ") + "been removed.");
    }

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
            listItemSelected = toDoList.getListItem(title); // Attempt to retrieve a list item with the passed title
            int option;

            do {
                menuController.printItemEditorMenu();
                option = menuController.requestUserOption(reader);
                selectFromEditorMenu(listItemSelected, option);
            } while (option != 4);

        } catch (ListItemNotFoundException e) {
            e.printStackTrace();
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
            case 1 -> listItem.setTitle(reader.getNextText("Enter a new title"));
            case 2 -> listItem.setText(reader.getNextText("Enter a new description"));
            case 3 -> toDoList.addDueDate(reader, listItem);
        }
    }
}

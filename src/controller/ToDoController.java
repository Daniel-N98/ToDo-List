package controller;

import model.ListItem;
import types.ItemStatus;
import util.InputReader;

public class ToDoController {
    private final InputReader reader;

    public ToDoController(){
        this.reader = new InputReader();
    }

    // TODO - Create methods to request user input to create/remove/view ToDo list items

    // TODO - Add item into SQL database
    private void addToDoItem(ListItem item){
    }

    // TODO - Create a ToDo item based on user input
    public void createToDoItem(){
        String title = reader.getNextText("Enter the ToDo-List title");
        String text = reader.getNextText("Enter the ToDo-List description");
        addToDoItem(new ListItem(title, text, ItemStatus.PENDING));
    }

    // TODO - Remove item from SQL database
    public void removeToDoItem(){
        String title = reader.getNextText("Enter the ToDo-List title");

        // TODO - Check that the TO-DO item exists, and remove it
    }

    // TODO - Print all list items in a readable format
    public void printToDoList(){
    }

    public void clearToDoList(){

    }
}

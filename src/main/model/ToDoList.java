package main.model;

import main.dao.ToDoListRepository;
import main.exceptions.InvalidDateTimeFormatException;
import main.exceptions.ListItemAlreadyExistsException;
import main.exceptions.ListItemNotFoundException;

import main.util.DateParser;
import main.util.InputReader;

import java.time.LocalDateTime;

public class ToDoList {

    // Repository for handling interacting with the backend
    private final ToDoListRepository repository;

    /**
     * Constructor for the ToDoList class. Instantiates the ToDoListRepository
     */
    public ToDoList() {
        this.repository = new ToDoListRepository();
    }

    /**
     * Creates and returns a new ListItem object from user input.
     * Checks that a ListItem with the same name does not already exist
     * within the database
     *
     * @param reader to read user input
     * @return listItem created from user input
     * @throws ListItemAlreadyExistsException An item with this name already exists
     */
    public ListItem createListItem(InputReader reader) throws ListItemAlreadyExistsException {
        ListItem listItem = new ListItem();

        String title = reader.getNextText("\nEnter the list item title"); // Request the title of the new item to be added
        if (listItemExists(title)) { // Check the item with that title does not already exist
            throw new ListItemAlreadyExistsException("An item with name '" + title + "' already exists"); // Item already exists, throw exception
        }

        String text = reader.getNextText("\nEnter the list item description");  // Request the description of the new item to be added

        listItem.setTitle(title);
        listItem.setText(text);
        addDueDate(reader, listItem); // Asks the user to add a due date

        return listItem;
    }

    /**
     * Adds a ListItem into the database
     *
     * @param item to be added to the database
     */
    public void addToDoListItem(ListItem item) {
        this.repository.addListItem(item);
    }

    /**
     * Removes a ListItem from the database
     * @param title to be removed from the database
     */
    public void removeListItem(String title) throws ListItemNotFoundException {
        repository.removeListItem(title);
    }

    public void updateListItem(ListItem item){
        repository.updateListItem(item);
    }

    /**
     * Removes all ListItem objects from the database
     */
    public void clearAllListItems() {
        repository.removeAllItems();
    }

    /**
     * Finds a ListItem in the database by name
     *
     * @param listItemName to find in the database
     * @return ListItem found
     * @throws ListItemNotFoundException a ListItem could not be found
     */
    public ListItem getListItem(String listItemName) throws ListItemNotFoundException {
        return repository.getItemByTitle(listItemName);
    }

    /**
     * Requests a due date from the user and applies it to the ListItem parameter
     *
     * @param reader to read user input
     * @param listItem to apply the due date to
     */
    public void addDueDate(InputReader reader, ListItem listItem) {
        String dueDate = reader.getNextText("\nEnter the due date using format [yyyy-MM-dd HH:mm][Leave blank if none]");
        if (dueDate.length() == 0) return; // No due date was provided, return
        try {
            LocalDateTime dateTime = DateParser.parseStringToLocalDateTime(dueDate, "yyyy-MM-dd HH:mm"); // Format the dueDate String into a LocalDateTime with the provided format
            listItem.setDueDate(dateTime); // Set the date
        } catch (InvalidDateTimeFormatException e) {
            e.printStackTrace();
        }
    }

    /**
     * Prints all ListItem objects found in the database
     */
    public void printAllListItems() {
        repository.getAllListItems().forEach(System.out::println);
    }

    /**
     * Returns whether a ListItem is present in the database
     *
     * @param title to find in the database
     * @return true if a ListItem is found with that name
     */
    private boolean listItemExists(String title) {
        return repository.doesListItemExist(title);
    }
}

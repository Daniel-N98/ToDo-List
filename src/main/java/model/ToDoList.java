package model;

import dao.ToDoListRepository;
import exceptions.InvalidDateTimeFormatException;
import exceptions.ListItemAlreadyExistsException;
import exceptions.ListItemNotFoundException;
import util.DateParser;
import util.InputReader;

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
    public ListItem createListItem(InputReader reader) throws ListItemAlreadyExistsException, InvalidDateTimeFormatException {
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
     *
     * @param title to be removed from the database
     */
    public void removeListItem(String title) throws ListItemNotFoundException {
        this.repository.removeListItem(title);
    }

    public void updateListItem(ListItem item) {
        this.repository.updateListItem(item);
    }

    /**
     * Removes all ListItem objects from the database
     */
    public void clearAllListItems() {
        this.repository.removeAllItems();
    }

    /**
     * Finds a ListItem in the database by name
     *
     * @param listItemName to find in the database
     * @return ListItem found
     * @throws ListItemNotFoundException a ListItem could not be found
     */
    public ListItem getListItem(String listItemName) throws ListItemNotFoundException {
        return this.repository.getItemByTitle(listItemName);
    }

    /**
     * Requests a due date from the user and applies it to the ListItem parameter
     *
     * @param reader   to read user input
     * @param listItem to apply the due date to
     */
    public void addDueDate(InputReader reader, ListItem listItem) throws InvalidDateTimeFormatException {
        String dueDate = reader.getNextText("\nEnter the due date using format [yyyy-MM-dd HH:mm][Leave blank if none]");
        if (dueDate.length() == 0) return; // No due date was provided, return
        LocalDateTime dateTime = DateParser.parseStringToLocalDateTime(dueDate, "yyyy-MM-dd HH:mm"); // Format the dueDate String into a LocalDateTime with the provided format
        listItem.setDueDate(dateTime); // Set the date
    }

    /**
     * Prints all ListItem objects found in the database
     */
    public void printAllListItems() {
        this.repository.getAllListItems().forEach(System.out::println);
    }

    /**
     * Returns whether a ListItem is present in the database
     *
     * @param title to find in the database
     * @return true if a ListItem is found with that name
     */
    private boolean listItemExists(String title) {
        return this.repository.doesListItemExist(title);
    }
}

package model;

import dao.ToDoListRepository;
import exceptions.InvalidDateTimeFormatException;
import exceptions.InvalidItemTitleException;
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
     * @throws InvalidDateTimeFormatException if the date is invalid
     */
    private ListItem createListItem(InputReader reader) throws ListItemAlreadyExistsException, InvalidDateTimeFormatException, InvalidItemTitleException {
        ListItem listItem = new ListItem();

        String title = requestItemTitle(reader); // Request the title of the new item to be added

        if (listItemExists(title)) { // Check the item with that title does not already exist
            throw new ListItemAlreadyExistsException("An item with name '" + title + "' already exists"); // Item already exists, throw exception
        }

        String text = reader.getNextText("\nEnter the list item description");  // Request the description of the new item to be added

        listItem.setTitle(title);
        listItem.setDescription(text);
        addDueDate(reader, listItem); // Asks the user to add a due date

        return listItem;
    }

    /**
     * Adds a ListItem into the database
     *
     * @param reader to read user input
     */
    public void addToDoListItem(InputReader reader) {

        try {
            ListItem item = createListItem(reader); // Creates the ListItem from user input
            this.repository.addListItem(item); // Stores the ListItem in the database
            System.out.println(item.getTitle() + " has been added to your to-do list.");
        } catch (ListItemAlreadyExistsException | InvalidDateTimeFormatException | InvalidItemTitleException e) {
            e.printStackTrace();
        }
    }

    /**
     * Removes a ListItem from the database
     *
     * @param reader to read user input
     */
    public void removeListItem(InputReader reader) {
        try {
            String itemRemovingTitle = requestItemTitle(reader);
            this.repository.removeListItem(itemRemovingTitle);
            System.out.println("\n" + itemRemovingTitle + " has been removed from your to-do list");
        } catch (ListItemNotFoundException | InvalidItemTitleException e) {
            e.printStackTrace();
        }
    }

    /**
     * Removes a ListItem from the database
     *
     * @param item to remove
     */
    public void removeListItem(ListItem item) {
        try {
            this.repository.removeListItem(item.getTitle());
        } catch (ListItemNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Updates (adds) a ListItem into the database
     *
     * @param item to add into the database
     */
    public void updateListItem(ListItem item) {
        this.repository.addListItem(item);
    }

    /**
     * Removes all ListItem objects from the database
     */
    public void clearAllListItems() {
        this.repository.removeAllItems();
        System.out.println("\nThe to-do list has been cleared.");
    }

    /**
     * Finds a ListItem in the database by name
     *
     * @param listItemName to find in the database
     * @return ListItem found
     * @throws ListItemNotFoundException if the list item is not found
     */
    public ListItem getListItem(String listItemName) throws ListItemNotFoundException, InvalidItemTitleException {
        if (listItemName.length() == 0) {
            throw new InvalidItemTitleException("Item title '" + listItemName + "' is invalid.");
        }
        return this.repository.getItemByTitle(listItemName);
    }

    /**
     * Requests a due date from the user and applies it to the ListItem parameter
     * String date is parsed with the DateParser class
     *
     * @param reader   to read user input
     * @param listItem to apply the due date to
     */
    public void addDueDate(InputReader reader, ListItem listItem) {
        String dueDate = reader.getNextText("\nEnter the due date using format [yyyy-MM-dd HH:mm][Leave blank if none]");
        if (dueDate.length() != 0) { // No due date was provided, return
            try {
                LocalDateTime dateTime = DateParser.parseStringToLocalDateTime(dueDate, "yyyy-MM-dd HH:mm"); // Format the dueDate String into a LocalDateTime with the provided format
                listItem.setDueDate(dateTime); // Set the date
            } catch (InvalidDateTimeFormatException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Prints out the ListItem to the console
     *
     * @param item to print out
     */
    public void printItem(ListItem item) {
        try {
            System.out.println(item);
        } catch (NullPointerException e) {
            System.out.println("Item '" + "' cannot be found");
        }
    }

    /**
     * Prints all ListItem objects found in the database
     */
    public void printAllListItems() {
        this.repository.getAllListItems().forEach(System.out::println);
    }

    /**
     * Request a new ListItem title from the user, and catch exception
     *
     * @param item   to be updated
     * @param reader to read user input
     */
    public void updateTitle(ListItem item, InputReader reader) {
        try {
            String title = requestItemTitle(reader);
            if (listItemExists(title)) {
                // ListItem with this title already exists.
                throw new ListItemAlreadyExistsException("An item with name '" + title + "' already exists");
            }
            // ListItem title is valid, and does not already exist - update title.
            item.setTitle(title);
        } catch (InvalidItemTitleException | ListItemAlreadyExistsException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns whether a ListItem is present in the database
     *
     * @param title to find in the database
     * @return true if a ListItem is found with that title
     */
    public boolean listItemExists(String title) {
        return this.repository.doesListItemExist(title);
    }

    /**
     * Request the ListItem title from the user and check that it is valid.
     *
     * @param reader to read user input
     * @return title of the ListItem
     * @throws InvalidItemTitleException If the ListItem title is invalid
     */
    private String requestItemTitle(InputReader reader) throws InvalidItemTitleException {
        String title = reader.getNextText("\nEnter the list item title");
        if (title.length() == 0) {
            // ListItem title cannot be an empty String, throw new Exception.
            throw new InvalidItemTitleException("Item title '" + title + "' is invalid.");
        }
        // ListItem title is valid
        return title;
    }
}

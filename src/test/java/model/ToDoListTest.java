package model;

import exceptions.InvalidDateTimeFormatException;
import exceptions.ListItemNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import util.InputReader;
import util.TestUtil;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;


class ToDoListTest {

    private final ToDoList list = new ToDoList();

    /**
     * Instantiate the ToDoList and set the System out to the ByteArrayOutputStream,
     * so we can simulate user input.
     */
    @BeforeEach
    void setUp() {
        ByteArrayOutputStream typeOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(typeOut));
    }

    /**
     * Set the System in, and out back to System.in & System.Out
     * Clear all list items in the database
     */
    @AfterEach
    void tearDown() {
        System.setIn(System.in);
        System.setOut(System.out);
        // Clear items before we begin to ensure we've got a clean table to work with
        list.clearAllListItems();
    }

    /**
     * Test creation and storing of a new ListItem works as intended
     */
    @Test
    void createListItemTest() {
        // Item title, Description, DueDate
        TestUtil.simulateUserInput("NewOneYo", "Desc", "2021-01-01 23:59");
        list.addToDoListItem(new InputReader());
    }

    /**
     * Test that removing a ListItem works as intended
     * Once the ListItem has been removed, and we attempt to
     * get the list item by name, the 'ListItemNotFoundException.class' will be thrown
     * as the ListItem no longer exists
     */
    @Test
    void removeListItemTest() {
        // Creates and stores an example ListItem in the database with the name "Example list item"
        TestUtil.createExampleItem();

        // Item title
        TestUtil.simulateUserInput("Example list item");

        // Remove item
        list.removeListItem(new InputReader());

        // Check that the correct exception is thrown to show the ListItem does not exist.
        Throwable exception = assertThrows(ListItemNotFoundException.class, () -> list.getListItem("Example list item"));
        assertEquals("Item 'Example list item' cannot be found", exception.getMessage());
    }

    /**
     * Test that getting a ListItem from the database works correctly.
     *
     * @throws ListItemNotFoundException if the ListItem cannot be found in the database
     */
    @Test
    void getListItemTest() throws ListItemNotFoundException {
        TestUtil.simulateUserInput("getListItemTest", "Desc", "2021-01-01 23:59", "getListItemTest");
        list.addToDoListItem(new InputReader());
        ListItem item = list.getListItem("getListItemTest");

        assertNotNull(item);
        assertEquals("getListItemTest", item.getTitle());
    }

    /**
     * Test adding a Due Date to the ListItem works correctly
     * Parsed time will replace the space between Date & Time with a T
     *
     * @throws InvalidDateTimeFormatException if String could not be parsed to a date
     */
    @Test
    void addDueDateTest() throws InvalidDateTimeFormatException {
        // Creates and stores an example ListItem in the database with the name "Example list item"
        ListItem item = TestUtil.createExampleItem();

        // Assert that the ListItem due date is null
        assertNull(item.getDueDate());

        // Update the ListItem due date
        TestUtil.simulateUserInput("2025-05-05 22:40");
        list.addDueDate(new InputReader(), item);

        // Assert that the ListItem due date is what is passed above.
        assertEquals("2025-05-05T22:40", item.getDueDate().toString());
    }
}
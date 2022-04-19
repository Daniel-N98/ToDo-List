package model;

import exceptions.InvalidDateTimeFormatException;
import exceptions.ListItemNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import types.ItemStatus;
import util.InputReader;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;


class ToDoListTest {

    private ToDoList list;

    @BeforeEach
    void setUp() {
        this.list = new ToDoList();
        ByteArrayOutputStream typeOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(typeOut));
    }

    @AfterEach
    void tearDown() {
        System.setIn(System.in);
        System.setOut(System.out);
    }

    /**
     * Test creation of a new ListItem
     */
    @Test
    void createListItemTest() {
        // Item title, Description, DueDate
        simulateUserInput("NewOneYo", "Desc", "2021-01-01 23:59");
        list.addToDoListItem(new InputReader());
    }

    @Test
    void removeListItemTest() {
        // Item title
        simulateUserInput("NewOneYo");
        // Remove item
        list.removeListItem(new InputReader());

        // Check that the correct exception is thrown to show the ListItem does not exist.
        Throwable exception = assertThrows(ListItemNotFoundException.class, () -> list.getListItem("NewOneYo"));
        assertEquals("Item 'NewOneYo' cannot be found", exception.getMessage());
    }

    /**
     * Test that getting a ListItem from the database works correctly.
     *
     * @throws ListItemNotFoundException if the ListItem cannot be found in the database
     */
    @Test
    void getListItemTest() throws ListItemNotFoundException {
        simulateUserInput("getListItemTest", "Desc", "2021-01-01 23:59", "getListItemTest");
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
        ListItem item = new ListItem("addDueDateTest", "test case", ItemStatus.PENDING);
        simulateUserInput("2025-05-05 22:40");
        list.addDueDate(new InputReader(), item);
        assertEquals("2025-05-05T22:40", item.getDueDate().toString());
    }

    /**
     * Utility for simulating user input in the console window.
     * Builds a list of input strings, separated by the "line.separator"
     *
     * @param inputLines to print out (as user)
     */
    public void simulateUserInput(String... inputLines){
        StringBuilder builder = new StringBuilder();
        for (String inputLine : inputLines) {
            builder.append(inputLine); // Adds the next String in the list
            builder.append(System.getProperty("line.separator")); // Enters the next line
        }
        System.setIn(new ByteArrayInputStream(builder.toString().getBytes())); // Sets the input stream
    }
}
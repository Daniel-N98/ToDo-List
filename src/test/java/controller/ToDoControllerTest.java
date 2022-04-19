package controller;

import static org.junit.jupiter.api.Assertions.*;

import exceptions.ListItemNotFoundException;
import model.ListItem;
import model.ToDoList;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import types.ItemStatus;
import util.TestUtil;

class ToDoControllerTest {

    private final ToDoList list = new ToDoList();

    @AfterEach
    void tearDown(){
        // Clear items before we begin to ensure we've got a clean table to work with
        list.clearAllListItems();
    }

    /**
     * Test that navigating to the "Add to your to-do list" menu, and creating a ListItem works correctly
     * @throws ListItemNotFoundException if the item does not exist
     */
    @Test
    void addToDoItemTest() throws ListItemNotFoundException {
        String[] inputLines = {"2", "New Title", // Add to your to-do list | Title
                "New Description", // Description
                "2021-04-01 14:25", // Due date
                "6"}; // Exit value
        TestUtil.simulateUserInput(inputLines);
        new ToDoController();

        // Assert that the item is Not null
        assertNotNull(list.getListItem("New Title"));
    }

    /**
     * Test that navigating to the "Remove from your to-do list" menu, and removing a ListItem works correctly
     */
    @Test
    void removeToDoItemTest() {
        // Creates and stores an example ListItem in the database with the name "Example list item"
        TestUtil.createExampleItem();

        // Remove from your to-do list | Title | Exit value
        String[] inputLines = {"3", "Example list item", "6"};
        TestUtil.simulateUserInput(inputLines);
        new ToDoController();

        // Assert that the item cannot be found
        assertFalse(list.listItemExists("Example list item"));
    }

    /**
     * Test that clearing the List works as intended
     */
    @Test
    void clearAllListItemsTest() {
        // Creates and stores an example ListItem in the database with the name "Example list item"
        TestUtil.createExampleItem();

        // Assert it was added correctly
        assertTrue(list.listItemExists("Example list item"));

        String[] inputLines = {"4", "6"}; // Clear to-do list | Exit value
        TestUtil.simulateUserInput(inputLines);
        new ToDoController();

        // Assert that the item can no longer be found
        assertFalse(list.listItemExists("Example list item"));
    }

    /**
     * Test that navigating to the "Update to-do list" menu, and editing the properties
     * works as intended.
     */
    @Test
    void updateToDoListTest() throws ListItemNotFoundException {
        // Creates and stores an example ListItem in the database with the name "Example list item"
        ListItem item = TestUtil.createExampleItem();

        // Assert that the title is not equal to what it will be updated to.
        assertNotEquals("New title", item.getTitle());

        // Option | Title | Option | Exit value
        String[] inputLines = {"5", "Example list item", // Update to-do list | Title
                "1", "New title", // Edit title | New title
                "2", "New description", // Edit description | New description
                "3", "2021-01-01 05:15", // Edit due date | New Due date
                "4", "3", // Edit status | Status Option
                "5", "6"}; // Return to main menu | Exit value
        TestUtil.simulateUserInput(inputLines);
        new ToDoController();

        // Retrieve the updated ListItem from the database
        ListItem updatedItem = list.getListItem("New title");

        // Assert the properties have been updated to what was set above.
        assertEquals("New title", updatedItem.getTitle());
        assertEquals("New description", updatedItem.getText());
        assertEquals("2021-01-01T05:15", updatedItem.getDueDate().toString());
        assertEquals(ItemStatus.COMPLETED, updatedItem.getStatus());
    }
}
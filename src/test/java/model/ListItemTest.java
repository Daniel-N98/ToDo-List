package model;

import exceptions.InvalidDateTimeFormatException;
import exceptions.InvalidItemStatusException;
import org.junit.jupiter.api.Test;
import types.ItemStatus;
import util.DateParser;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ListItemTest {

    /**
     * Test the constructor for the ListItem class
     * Object instance variables will be instantiated with the parameters
     * Timestamp is set by default with this constructor
     * Due date is not instantiated here
     */
    @Test
    void createListItemTest() {
        ListItem createListItem = new ListItem("Title", "Description", ItemStatus.PENDING);
        assertNotNull(createListItem);
        assertEquals("Title", createListItem.getTitle());
        assertEquals("Description", createListItem.getText());
        assertEquals(ItemStatus.PENDING, createListItem.getStatus());
    }

    /**
     * Test the constructor for the ListItem class (@AllArgsConstructor)
     *
     * All object instance variables are instantiated with this constructor.
     * Test that Title, Status & DueDate are all instantiated correctly
     */
    @Test
    void testAllArgsConstructor() throws InvalidDateTimeFormatException {
        // Declare the current time now to pass as the TimeStamp and assert it further in the method.
        LocalDateTime now = LocalDateTime.now();

        // Use the constructor requiring all arguments to create the ListItem
        ListItem allArgsItem = new ListItem("Title",
                "Description",
                now,
                DateParser.parseStringToLocalDateTime("2021-01-01 22:20", "yyyy-MM-dd HH:mm"),
                ItemStatus.PROGRESS);

        // Assert all the properties are as expected
        assertNotNull(allArgsItem);
        assertEquals("Title", allArgsItem.getTitle());
        assertEquals("Description", allArgsItem.getText());
        assertEquals(now, allArgsItem.getTimestamp());
        assertEquals("2021-01-01T22:20", allArgsItem.getDueDate().toString());
        assertEquals(ItemStatus.PROGRESS, allArgsItem.getStatus());
    }

    /**
     * Test that the UpdateStatus is working as intended
     *
     * @throws InvalidItemStatusException if status is invalid
     */
    @Test
    void setStatusTest() throws InvalidItemStatusException {
        // Default ListItem constructor - ItemStatus is instantiated with PENDING as default.
        ListItem setStatusItem = new ListItem();
        assertEquals(ItemStatus.PENDING, setStatusItem.getStatus());

        // Update the ItemStatus to the PROGRESS status
        setStatusItem.setStatus(ItemStatus.PROGRESS);
        // Assert the change has been made
        assertEquals(ItemStatus.PROGRESS, setStatusItem.getStatus());

        // Update the ItemStatus to the COMPLETED status
        setStatusItem.setStatus(ItemStatus.COMPLETED);
        // Assert the change has been made
        assertEquals(ItemStatus.COMPLETED, setStatusItem.getStatus());

        // Assert that the 'InvalidItemStatusException' is thrown when the ItemStatus value is set to 'Null'
        Throwable exception = assertThrows(InvalidItemStatusException.class, () -> setStatusItem.setStatus(null));
        assertEquals("ItemStatus cannot be null", exception.getMessage());
    }
}
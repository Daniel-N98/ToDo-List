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
        LocalDateTime now = LocalDateTime.now();
        ListItem allArgsItem = new ListItem("Title",
                "Description",
                now,
                DateParser.parseStringToLocalDateTime("2021-01-01 22:20", "yyyy-MM-dd HH:mm"),
                ItemStatus.PROGRESS);

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
        ListItem setStatusItem = new ListItem();
        assertEquals(ItemStatus.PENDING, setStatusItem.getStatus());

        setStatusItem.setStatus(ItemStatus.PROGRESS);
        assertEquals(ItemStatus.PROGRESS, setStatusItem.getStatus());

        setStatusItem.setStatus(ItemStatus.COMPLETED);
        assertEquals(ItemStatus.COMPLETED, setStatusItem.getStatus());

        Throwable exception = assertThrows(InvalidItemStatusException.class, () -> setStatusItem.setStatus(null));
        assertEquals("ItemStatus cannot be null", exception.getMessage());
    }
}
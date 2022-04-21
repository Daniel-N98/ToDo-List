package model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import exceptions.InvalidDateTimeFormatException;
import org.apache.commons.text.WordUtils;
import types.ItemStatus;
import exceptions.InvalidItemStatusException;
import util.DateParser;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Getter
@Setter
@AllArgsConstructor
public class ListItem {

    // Title of the ListItem
    private String title;

    // Description of the ListItem
    private String description;

    // Date & Time that the ListItem was created
    private LocalDateTime timestamp;

    // Date the ListItem is due on, may not be set.
    private LocalDateTime dueDate;

    // The Status of the ListItem <PENDING, PROGRESS, COMPLETED>
    private ItemStatus status;

    /**
     * Default constructor for the ListItem
     * Instantiates the timestamp variable with the current date & time
     */
    public ListItem() {
        this.timestamp = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES); // Instantiate the timestamp variable with the current date & time
        this.status = ItemStatus.PENDING; // Item status is instantiated to PENDING as default
    }

    /**
     * Constructor for the ListItem
     * Instantiates title, description, status and timestamp properties
     *
     * @param title  of the list item
     * @param description   of the list item
     * @param status of the list item (Default: PENDING)
     */
    public ListItem(String title, String description, ItemStatus status) {
        this.title = title;
        this.description = description;
        try {
            this.timestamp = DateParser.parseStringToLocalDateTime(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES).toString(), "yyyy-MM-dd HH:mm");
        } catch (InvalidDateTimeFormatException e) {
            e.printStackTrace();
        }
        this.status = status;
    }

    /**
     * Sets the status of the ListItem
     *
     * @param status to set
     * @throws InvalidItemStatusException invalid Status provided
     */
    public void setStatus(ItemStatus status) throws InvalidItemStatusException {
        if (status == null) {
            throw new InvalidItemStatusException("ItemStatus cannot be null");
        }
        System.out.println("\nItem status has been updated: [" + this.status + "]" + " -> " + "[" + status + "]");
        this.status = status;
    }

    /**
     * Returns a readable version of this object
     *
     * @return String readable ListItem
     */
    @Override
    public String toString() {
        return "=".repeat(30) +
                "\nTitle: [" + this.title +
                "]\n\nDescription: [" + WordUtils.wrap(this.description, 90) +
                "]\n\nCreated: [" + this.timestamp.toString().replace("T", " ") +
                (this.dueDate != null ? "]\nDue date: [" + this.dueDate.toString().replace("T", " ") : "") +
                "]\nStatus: [" + this.status + "]\n";
    }
}

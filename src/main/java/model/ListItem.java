package model;

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
public class ListItem {

    private String title;
    private String text; //
    private LocalDateTime timestamp; // Date/Time that the ListItem was created
    private LocalDateTime dueDate; // Due date for the ListItem
    private ItemStatus status; // The status of the ListItem

    /**
     * Default constructor for the ListItem
     * Instantiates the timestamp variable with the current date & time
     */
    public ListItem() {
        this.timestamp = LocalDateTime.now(); // Instantiate the timestamp variable with the current date/time
        this.status = ItemStatus.PENDING;
    }

    /**
     * Constructor for the ListItem
     * Instantiates title, text, status and timestamp properties
     *
     * @param title  of the list item
     * @param text   of the list item
     * @param status of the list item (Default: PENDING)
     */
    public ListItem(String title, String text, ItemStatus status) {
        this.title = title;
        this.text = text;
        try {
            this.timestamp = DateParser.parseStringToLocalDateTime(LocalDateTime.now().toString(), "yyyy-MM-dd HH:mm").truncatedTo(ChronoUnit.SECONDS);
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
            throw new InvalidItemStatusException("Invalid status provided");
        }
        System.out.println("\nItem status has been updated: [" + this.status + "]" + " -> " + "[" + status + "]");
        this.status = status;
    }

    /**
     * Returns a readable version of this object
     *
     * @return String
     */
    @Override
    public String toString() {
        return "=".repeat(30) +
                "\nTitle: [" + this.title +
                "]\n\nDescription: [" + WordUtils.wrap(this.text, 90) +
                "]\n\nCreated: [" + this.timestamp.toString() +
                (this.dueDate != null ? "]\nDue date: [" + this.dueDate.toString().replace("T", " ") : "") +
                "]\nStatus: [" + this.status + "]\n";
    }
}

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

    private String title;
    private String text; //
    private LocalDateTime timestamp; // Date & Time that the ListItem was created
    private LocalDateTime dueDate;
    private ItemStatus status;

    /**
     * Default constructor for the ListItem
     * Instantiates the timestamp variable with the current date & time
     */
    public ListItem() {
        this.timestamp = LocalDateTime.now(); // Instantiate the timestamp variable with the current date & time
        this.status = ItemStatus.PENDING; // Item status is instantiated to PENDING as default
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
                "]\n\nDescription: [" + WordUtils.wrap(this.text, 90) +
                "]\n\nCreated: [" + this.timestamp.toString() +
                (this.dueDate != null ? "]\nDue date: [" + this.dueDate.toString().replace("T", " ") : "") +
                "]\nStatus: [" + this.status + "]\n";
    }
}

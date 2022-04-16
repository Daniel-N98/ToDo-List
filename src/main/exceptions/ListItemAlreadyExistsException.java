package main.exceptions;

public class ListItemAlreadyExistsException extends Exception {
    public ListItemAlreadyExistsException(String message) {
        super(message);
    }
}

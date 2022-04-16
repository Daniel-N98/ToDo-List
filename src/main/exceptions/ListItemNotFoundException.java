package main.exceptions;

public class ListItemNotFoundException extends Exception{

    public ListItemNotFoundException(String message){
        super(message);
    }
}

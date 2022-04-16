package main.exceptions;

public class ListItemAlreadyExists extends Exception{
    public ListItemAlreadyExists(String message){
        super(message);
    }
}

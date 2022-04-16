package main.exceptions;

public class InvalidItemStatusException extends Exception{

    public InvalidItemStatusException(String message){
        super(message);
    }
}

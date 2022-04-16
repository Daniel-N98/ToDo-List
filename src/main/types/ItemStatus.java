package main.types;

import main.exceptions.InvalidItemStatusException;

public enum ItemStatus {
    PENDING,
    PROGRESS,
    COMPLETED;

    public static ItemStatus getStatus(int i) throws InvalidItemStatusException {
        try {
            return values()[i];
        }catch (ArrayIndexOutOfBoundsException e){
            throw new InvalidItemStatusException("Invalid item status");
        }
    }
}

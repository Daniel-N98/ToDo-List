package types;

import exceptions.InvalidItemStatusException;

public enum ItemStatus {
    PENDING,
    PROGRESS,
    COMPLETED;

    /**
     * Returns the value at index
     *
     * @param index status index
     * @return ItemStatus at index
     * @throws InvalidItemStatusException if index is out of bounds
     */
    public static ItemStatus getStatus(int index) throws InvalidItemStatusException {
        try {
            // Returns the value at the index parameter
            return values()[index];
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new InvalidItemStatusException("Invalid item status");
        }
    }
}

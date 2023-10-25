package model;
/**
 * Exception thrown while trying to add an item to stock that is already there
 * @author Fijalkowskim
 * @version %I%, %G%
 */
public class ItemAlreadyInStock extends Exception{
    /**
     * Sets error message.
     * @param errorMessage Error message.
     */
    public ItemAlreadyInStock(String errorMessage) {
        super(errorMessage);
    }
}

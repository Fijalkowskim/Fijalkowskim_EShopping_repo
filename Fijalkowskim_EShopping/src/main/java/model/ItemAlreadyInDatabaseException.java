package model;
/**
 * Exception thrown while trying to add an item to database that is already there
 * @author Fijalkowskim
 * @version 1.0
 */
public class ItemAlreadyInDatabaseException extends Exception{
    /**
     * Sets error message.
     * @param errorMessage Error message.
     */
    public ItemAlreadyInDatabaseException(String errorMessage) {
        super(errorMessage);
    }
}

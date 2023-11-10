package fijalkowskim.fijalkowskim_eshopping.model;
/**
 * Exception thrown while trying to get an item that is not in database.
 * @author Fijalkowskim
 * @version 1.1
 */
public class ItemNotInDatabaseException extends Exception {
    /**
     * Sets error message.
     *
     * @param errorMessage Error message.
     */
    public ItemNotInDatabaseException(String errorMessage) {
        super(errorMessage);
    }
}

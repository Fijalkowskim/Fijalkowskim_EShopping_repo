package model;
/**
 * Exception thrown while trying to buy an item, and it is not in stock.
 * @author Fijalkowskim
 * @version 1.1
 */
public class ItemNotInStockException extends Exception{
    /**
     * Sets error message.
     * @param errorMessage Error message.
     */
    public ItemNotInStockException(String errorMessage) {
        super(errorMessage);
    }
}

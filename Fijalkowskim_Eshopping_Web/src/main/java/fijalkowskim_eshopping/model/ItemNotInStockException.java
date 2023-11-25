package fijalkowskim_eshopping.model;
/**
 * Exception thrown while trying to buy an item, and it is not in stock.
 * @author Fijalkowskim
 * @version 2.0
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
